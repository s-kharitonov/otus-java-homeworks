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
import java.util.*;
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
		try {
			insertThrowable(objectData);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void update(final T objectData) {
		final String sqlQuery = sqlMetaData.getUpdateSql();
		final List<Object> params = extractFieldValues(objectData, classMetaData.getFieldsWithoutId());

		try {
			final var field = classMetaData.getIdField();

			field.setAccessible(true);
			params.add(field.get(objectData));

			executor.executeInsert(connection, sqlQuery, params);
		} catch (SQLException | IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void insertOrUpdate(final T objectData) {
		try {
			insertThrowable(objectData);
		} catch (SQLException e) {
			update(objectData);
		}
	}

	@Override
	public T findById(final long id, final Class<T> clazz) {
		final String selectQuery = sqlMetaData.getSelectByIdSql();
		try {
			final Optional<T> result = executor.executeSelect(connection, selectQuery, id, readObjectFromRs());

			return result.orElseThrow();
		} catch (SQLException | NoSuchElementException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private Function<ResultSet, T> readObjectFromRs() {
		return (rs) -> {
			try {
				if (rs.next()) {
					final Map<String, Object> values = extractValuesFromResult(rs);
					final var defaultConstructor = classMetaData.getConstructor();
					final var obj = defaultConstructor.newInstance();

					fillFiledValues(obj, values);

					return obj;
				}
			} catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			return null;
		};
	}

	private void fillFiledValues(final Object owner, final Map<String, Object> values) {
		final var clazz = owner.getClass();
		final var fields = clazz.getDeclaredFields();

		for (var field : fields) {
			var name = field.getName();

			field.setAccessible(true);
			try {
				field.set(owner, values.get(name));
			} catch (IllegalAccessException ignored) {
			}
		}
	}

	private List<Object> extractFieldValues(final Object owner, final List<Field> fields) {
		return fields.stream()
				.map(getFieldValue(owner))
				.collect(Collectors.toList());
	}

	private Map<String, Object> extractValuesFromResult(ResultSet rs) {
		final Map<String, Object> values = new HashMap<>();

		classMetaData.getAllFields()
				.stream()
				.map(Field::getName)
				.forEach((name) -> {
					try {
						values.put(name, rs.getObject(name));
					} catch (SQLException ignored) {
					}
				});

		return values;
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

	private void insertThrowable(final T objectData) throws SQLException {
		final String sqlQuery = sqlMetaData.getInsertSql();
		final List<Object> params = extractFieldValues(objectData, classMetaData.getAllFields());

		executor.executeInsert(connection, sqlQuery, params);
	}
}
