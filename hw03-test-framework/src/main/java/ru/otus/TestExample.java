package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestExample {

	@Before
	public void before() {
		System.out.println("before");
	}

	@Test
	public void test1() {
		System.out.println("1");
	}

	@Test
	public void test2() {
		if (true) {
			throw new RuntimeException();
		}
	}

	@Test
	public void test3() {
		System.out.println("3");
	}

	@After
	public void after() {
		System.out.println("after");
	}
}
