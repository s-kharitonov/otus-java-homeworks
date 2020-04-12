package ru.otus.iterators;

import ru.otus.lists.DIYArrayList;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DIYListIterator<E> extends DIYIterator<E> implements ListIterator<E> {

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
