package ru.otus.jdbc.mapper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicJdbcMapper<T> implements JdbcMapper<T> {

	private static final Logger logger = LoggerFactory.getLogger(BasicJdbcMapper.class);

	private final Connection connection;
	private final DbExecutor<T> executor;
	private final EntityClassMetaData<T> classMetaData;
	private final EntitySQLMetaData sqlMetaData;

	public BasicJdbcMapper(final Class<T> clazz, final Connection connection, final DbExecutor<T> executor) {
		this.classMetaData = new BasicEntityClassMetaData<>(clazz);
		this.sqlMetaData = new BasicEntitySQLMetaData<>(classMetaData);
		this.connection = connection;
		this.executor = executor;
	}

	@Override
	public void insert(final T objectData) {
		final String sqlQuery = sqlMetaData.getInsertSql();
		final List<Object> params = classMetaData.getFieldsWithoutId()
				.stream()
				.map(getFieldValue(objectData))
				.collect(Collectors.toList());

		try {
			executor.executeInsert(connection, sqlQuery, params);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void update(final T objectData) {
		final String sqlQuery = sqlMetaData.getUpdateSql();
		final List<Object> params = classMetaData.getFieldsWithoutId()
				.stream()
				.map(getFieldValue(objectData))
				.collect(Collectors.toList());

		try {
			executor.executeInsert(connection, sqlQuery, params);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdate(final T objectData) {
		final String insertSqlQuery = sqlMetaData.getInsertSql();
		final String updateSqlQuery = sqlMetaData.getUpdateSql();
		final List<Object> params = classMetaData.getFieldsWithoutId()
				.stream()
				.map(getFieldValue(objectData))
				.collect(Collectors.toList());

		try {
			insertOrUpdate(insertSqlQuery, updateSqlQuery, params);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public T findById(final long id, final Class<T> clazz) {
		final String selectQuery = sqlMetaData.getSelectByIdSql();
		try {
			final Optional<T> result = executor.executeSelect(connection, selectQuery, id, readObjectFromRs());

			return result.orElseThrow();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private Function<ResultSet, T> readObjectFromRs() {
		return (rs) -> {
			try {
				if (rs.next()) {
					final Object[] params = extractParamValues(rs);

					return classMetaData.getConstructor().newInstance(params);
				}
			} catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			return null;
		};
	}

	private Object[] extractParamValues(ResultSet rs) {
		return classMetaData.getAllFields()
				.stream()
				.map(Field::getName)
				.map((name) -> {
					try {
						return rs.getObject(name);
					} catch (SQLException e) {
						return null;
					}
				})
				.toArray();
	}

	private void insertOrUpdate(final String insertQuery, final String updateQuery, final List<Object> params) throws SQLException {
		try {
			executor.executeInsert(connection, insertQuery, params);
		} catch (SQLException e) {
			executor.executeInsert(connection, updateQuery, params);
		}
	}

	private Function<Field, Object> getFieldValue(final Object owner) {
		return (field) -> {
			field.setAccessible(true);
			try {
				return field.get(owner);
			} catch (IllegalAccessException e) {
				return null;
			}
		};
	}
}
