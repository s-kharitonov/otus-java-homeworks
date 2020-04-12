package ru.otus;

import ru.otus.lists.DIYArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final List<Integer> numbers = new DIYArrayList<>();
		final List<Integer> nums = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			numbers.add(i);
		}

		Collections.addAll(numbers, 1, 2, 3, 4, 5);
		System.out.println("numbers : " + numbers);

		Collections.addAll(nums, 7, 7, 7, 7, 7);
		Collections.copy(numbers, nums);
		System.out.println("numbers : " + numbers);
		System.out.println("nums : " + nums);

		Collections.sort(numbers, Integer::compareTo);
		System.out.println("numbers : " + numbers);
	}
}