package ru.otus.core.model;

import ru.otus.annotations.Id;

import java.math.BigDecimal;

public class Account {
	@Id
	private long no;
	private String type;
	private BigDecimal rest;

	public Account() {
	}

	public Account(final long no, final String type, final BigDecimal rest) {
		this.no = no;
		this.type = type;
		this.rest = rest;
	}

	public long getNo() {
		return no;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getRest() {
		return rest;
	}

	@Override
	public String toString() {
		return "Account{" +
				"no=" + no +
				", type='" + type + '\'' +
				", rest=" + rest +
				'}';
	}
}
