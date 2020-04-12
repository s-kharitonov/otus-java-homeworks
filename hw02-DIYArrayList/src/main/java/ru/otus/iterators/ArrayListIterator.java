package ru.otus.iterators;

import ru.otus.lists.DIYArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ArrayListIterator<E> implements Iterator<E> {

	private final DIYArrayList<E> list;
	private int cursor;

	public ArrayListIterator(final DIYArrayList<E> list) {
		this.list = list;
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
