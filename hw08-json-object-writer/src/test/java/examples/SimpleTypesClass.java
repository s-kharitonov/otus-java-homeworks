package examples;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class SimpleTypesClass {
	private final Boolean booleanField = Boolean.FALSE;
	private final Character characterField = Character.MAX_VALUE;
	private final Byte byteField = Byte.MAX_VALUE;
	private final Short shortField = Short.MAX_VALUE;
	private final Integer intField = Integer.MAX_VALUE;
	private final Long longField = Long.MAX_VALUE;
	private final Float floatField = Float.MIN_VALUE;
	private final Double doubleField = Double.MAX_VALUE;
	private final BigInteger bigIntField = BigInteger.TEN;
	private final BigDecimal bigDecimalField = BigDecimal.TEN;
	private final String stringField = "hello world!";
	private final transient String transientField = "hello world!";
	private static final String staticField = "hello world!";
	private final String volatileField = "hello world!";
	private final Object emptyField = null;

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof SimpleTypesClass)) return false;
		final SimpleTypesClass that = (SimpleTypesClass) o;

		return Objects.equals(booleanField, that.booleanField) &&
				Objects.equals(characterField, that.characterField) &&
				Objects.equals(byteField, that.byteField) &&
				Objects.equals(shortField, that.shortField) &&
				Objects.equals(intField, that.intField) &&
				Objects.equals(longField, that.longField) &&
				Objects.equals(floatField, that.floatField) &&
				Objects.equals(doubleField, that.doubleField) &&
				Objects.equals(bigIntField, that.bigIntField) &&
				Objects.equals(bigDecimalField, that.bigDecimalField) &&
				Objects.equals(stringField, that.stringField) &&
				Objects.equals(transientField, that.transientField) &&
				Objects.equals(volatileField, that.volatileField) &&
				Objects.equals(emptyField, that.emptyField);
	}

	@Override
	public int hashCode() {
		return Objects.hash(booleanField, characterField, byteField, shortField,
				intField, longField, floatField, doubleField, bigIntField,
				bigDecimalField, stringField, transientField, volatileField, emptyField);
	}
}