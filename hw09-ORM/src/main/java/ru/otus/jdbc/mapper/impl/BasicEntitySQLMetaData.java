package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class BasicEntitySQLMetaData<T> implements EntitySQLMetaData {

	private final EntityClassMetaData<T> metaData;

	public BasicEntitySQLMetaData(final EntityClassMetaData<T> metaData) {
		this.metaData = metaData;
	}

	@Override
	public String getSelectAllSql() {
		return String.format("select * from %s", metaData.getName());
	}

	@Override
	public String getSelectByIdSql() {
		return String.format("select * from %s where id = ?", metaData.getName());
	}

	@Override
	public String getInsertSql() {
		return String.format("insert into %s (%s) values (%s)",
				metaData.getName(), extractColumnsName(), getParameters());
	}

	@Override
	public String getUpdateSql() {
		return String.format("update %s set (%s) = (%s) where id = ?",
				metaData.getName(), extractColumnsName(), getParameters());
	}

	private String getParameters() {
		final StringBuilder builder = new StringBuilder();
		final List<Field> fields = metaData.getFieldsWithoutId();

		builder.append("?, ".repeat(fields.size()));

		return builder
				.toString()
				.substring(0, builder.toString().lastIndexOf(", "));
	}

	private String extractColumnsName() {
		final String names = metaData.getFieldsWithoutId()
				.stream()
				.map((field) -> field.getName() + ", ")
				.collect(Collectors.joining());
		return names.substring(0, names.lastIndexOf(", "));
	}
}
