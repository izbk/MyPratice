package my.guava.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;

/**
 * Ordering(排序)可以被看作是一个丰富的比较具有增强功能的链接， 多个实用方法，多类型排序功能等。
 * 
 * @Description:
 * @author: Binke Zhang
 * @date: 2017年11月15日 下午6:27:22
 * 
 * @Copyright: 2017 www.cdsunrise.net Inc. All rights reserved.
 */
public class OptionalOrderingTest {

	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(new Integer(5));
		numbers.add(new Integer(2));
		numbers.add(new Integer(15));
		numbers.add(new Integer(51));
		numbers.add(new Integer(53));
		numbers.add(new Integer(35));
		numbers.add(new Integer(45));
		numbers.add(new Integer(32));
		numbers.add(new Integer(43));
		numbers.add(new Integer(16));

		@SuppressWarnings("rawtypes")
		Ordering<Comparable> ordering = Ordering.natural();
		System.out.println("Input List: ");
		System.out.println(numbers);

		Collections.sort(numbers, ordering);
		System.out.println("Sorted List: ");
		System.out.println(numbers);

		System.out.println("======================");
		System.out.println("List is sorted: " + ordering.isOrdered(numbers));
		System.out.println("Minimum: " + ordering.min(numbers));
		System.out.println("Maximum: " + ordering.max(numbers));

		Collections.sort(numbers, ordering.reverse());
		System.out.println("Reverse: " + numbers);

		numbers.add(null);
		System.out.println("Null added to Sorted List: ");
		System.out.println(numbers);

		Collections.sort(numbers, ordering.nullsFirst());
		System.out.println("Null first Sorted List: ");
		System.out.println(numbers);
		System.out.println("======================");

		List<String> names = new ArrayList<String>();
		names.add("Ram");
		names.add("Shyam");
		names.add("Mohan");
		names.add("Sohan");
		names.add("Ramesh");
		names.add("Suresh");
		names.add("Naresh");
		names.add("Mahesh");
		names.add(null);
		names.add("Vikas");
		names.add("Deepak");

		System.out.println("Another List: ");
		System.out.println(names);

		Collections.sort(names, ordering.nullsFirst().reverse());
		System.out.println("Null first then reverse sorted list: ");
		System.out.println(names);
	}
}
