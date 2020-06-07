package examples;

import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class CollectionTypesClass {
	private final Object[] arrayField = new Object[]{1d, "1"};
	private final Collection<?> collectionField = Lists.newArrayList(1d, "1");

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof CollectionTypesClass)) return false;
		final CollectionTypesClass that = (CollectionTypesClass) o;

		return Arrays.equals(arrayField, that.arrayField) &&
				Objects.equals(collectionField, that.collectionField);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(collectionField);
		result = 31 * result + Arrays.hashCode(arrayField);
		return result;
	}
}
