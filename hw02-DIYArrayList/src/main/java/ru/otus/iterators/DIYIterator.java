package ru.otus.iterators;

import ru.otus.lists.DIYArrayList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class DIYIterator<E> implements Iterator<E> {

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
