package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class BasicEntitySQLMetaData<T> implements EntitySQLMetaData {

	private final String selectAll;
	private final String selectById;
	private final String insert;
	private final String update;

	public BasicEntitySQLMetaData(final EntityClassMetaData<T> metaData) {
		this.selectAll = createSelectAllQuery(metaData);
		this.selectById = createSelectByIdQuery(metaData);
		this.insert = createInsertQuery(metaData);
		this.update = createUpdateQuery(metaData);
	}

	@Override
	public String getSelectAllSql() {
		return selectAll;
	}

	@Override
	public String getSelectByIdSql() {
		return selectById;
	}

	@Override
	public String getInsertSql() {
		return insert;
	}

	@Override
	public String getUpdateSql() {
		return update;
	}

	private String createUpdateQuery(final EntityClassMetaData<T> metaData) {
		return String.format("update %s set (%s) = (%s) where %s = ?",
				metaData.getName(),
				extractColumnsNames(metaData.getFieldsWithoutId()),
				getParameters(metaData.getFieldsWithoutId()),
				metaData.getIdField().getName());
	}

	private String createInsertQuery(final EntityClassMetaData<T> metaData) {
		return String.format("insert into %s (%s) values (%s)",
				metaData.getName(),
				extractColumnsNames(metaData.getAllFields()),
				getParameters(metaData.getAllFields()));
	}

	private String createSelectAllQuery(final EntityClassMetaData<T> metaData) {
		return String.format("select * from %s", metaData.getName());
	}

	private String createSelectByIdQuery(final EntityClassMetaData<T> metaData) {
		return String.format("select * from %s where %s = ?",
				metaData.getName(),
				metaData.getIdField().getName());
	}

	private String getParameters(final List<Field> fields) {
		final StringBuilder builder = new StringBuilder();

		builder.append("?, ".repeat(fields.size()));

		return builder
				.toString()
				.substring(0, builder.toString().lastIndexOf(", "));
	}

	private String extractColumnsNames(final List<Field> fields) {
		final String names = fields.stream()
				.map((field) -> field.getName() + ", ")
				.collect(Collectors.joining());
		return names.substring(0, names.lastIndexOf(", "));
	}
}
