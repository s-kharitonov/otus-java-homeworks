package ru.otus.lists;

import java.util.*;
import java.util.function.Consumer;

public class DIYArrayList<E> implements List<E> {

	private static final int DEFAULT_CAPACITY = 10;

	private Object[] elements;
	private int capacity;

	public DIYArrayList() {
		this.elements = new Object[DEFAULT_CAPACITY];
	}

	public DIYArrayList(final int capacity) {
		if (capacity >= 0) {
			this.elements = new Object[capacity];
		} else {
			throw new IllegalArgumentException("size cannot be less than 0");
		}
	}

	@Override
	public int size() {
		return capacity;
	}

	@Override
	public boolean isEmpty() {
		return capacity == 0;
	}

	@Override
	public boolean contains(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<E> iterator() {
		return new DIYIterator<>(this, elements.length);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, capacity);
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(final E e) {
		add(capacity, e);
		return true;
	}

	@Override
	public boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		Arrays.fill(elements, null);
		capacity = 0;
	}

	@Override
	public E get(final int index) {
		Objects.checkIndex(index, capacity);
		return getElement(index);
	}

	@Override
	public E set(final int index, final E element) {
		Objects.checkIndex(index, capacity);

		final E previousElement = getElement(index);

		elements[index] = element;

		return previousElement;
	}

	@Override
	public void add(final int index, final E element) {
		if (isNotValidIndex(index)) {
			throw new IndexOutOfBoundsException();
		}

		if (index == elements.length) {
			elements = grow();
		}

		elements[index] = element;
		capacity++;
	}

	@Override
	public E remove(final int index) {
		Objects.checkIndex(index, capacity);
		final E previousElement = getElement(index);
		final int newCapacity = capacity - 1;

		System.arraycopy(elements, index + 1, elements, index, newCapacity - index);
		capacity = newCapacity;

		return previousElement;
	}

	@Override
	public int indexOf(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new DIYListIterator<>(this, elements.length);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void sort(final Comparator<? super E> c) {
		Arrays.sort((E[]) elements, 0, capacity, c);
	}

	@Override
	public ListIterator<E> listIterator(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return Arrays.toString(toArray());
	}

	@SuppressWarnings("unchecked")
	private E getElement(final int index) {
		return (E) elements[index];
	}

	private boolean isNotValidIndex(final int index) {
		return index < 0 || index > capacity;
	}

	private Object[] grow() {
		return Arrays.copyOf(elements, elements.length * 2);
	}

	private static class DIYIterator<E> implements Iterator<E> {

		final DIYArrayList<E> list;
		int cursor;
		int capacity;

		public DIYIterator(final DIYArrayList<E> list, final int capacity) {
			this.list = list;
			this.capacity = capacity;
		}

		@Override
		public boolean hasNext() {
			return cursor != list.size();
		}

		@Override
		public E next() {

			if (cursor >= list.size()) {
				throw new NoSuchElementException();
			}

			if (cursor >= capacity) {
				throw new ConcurrentModificationException();
			}

			cursor++;

			return list.get(cursor - 1);
		}

		@Override
		public void forEachRemaining(final Consumer<? super E> action) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}
	}

	private static class DIYListIterator<E> extends DIYIterator<E> implements ListIterator<E> {

		public DIYListIterator(final DIYArrayList<E> list, final int capacity) {
			super(list, capacity);
		}

		@Override
		public boolean hasPrevious() {
			return cursor != 0;
		}

		@Override
		public E previous() {

			if (cursor < 0) {
				throw new NoSuchElementException();
			}

			if (cursor >= capacity) {
				throw new ConcurrentModificationException();
			}

			cursor--;

			return list.get(cursor + 1);
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void set(final E e) {
			try {
				list.set(cursor - 1, e);
			} catch (IndexOutOfBoundsException error) {
				throw new ConcurrentModificationException();
			}
		}

		@Override
		public void add(final E e) {
			try {
				list.add(cursor + 1, e);
				this.cursor++;
			} catch (IndexOutOfBoundsException error) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
