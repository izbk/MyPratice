package my.java8.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertMap {
	public static void main(String[] args) {
		useStream2();
	}

	@SuppressWarnings("unused")
	public static void simple() {
		Map<String, String> map = new HashMap<>();

		// Convert all Map keys to a List
		List<String> result = new ArrayList<String>(map.keySet());

		// Convert all Map values to a List
		List<String> result2 = new ArrayList<String>(map.values());

		// Java 8, Convert all Map keys to a List
		List<String> result3 = map.keySet().stream().collect(Collectors.toList());

		// Java 8, Convert all Map values to a List
		List<String> result4 = map.values().stream().collect(Collectors.toList());

		// Java 8, seem a bit long, but you can enjoy the Stream features like filter
		// and etc.
		List<String> result5 = map.values().stream().filter(x -> !"apple".equalsIgnoreCase(x))
				.collect(Collectors.toList());

	}

	public static void earlyJava8() {
		Map<Integer, String> map = new HashMap<>();
		map.put(10, "apple");
		map.put(20, "orange");
		map.put(30, "banana");
		map.put(40, "watermelon");
		map.put(50, "dragonfruit");

		System.out.println("\n1. Export Map Key to List...");

		List<Integer> result = new ArrayList<Integer>(map.keySet());

		result.forEach(System.out::println);

		System.out.println("\n2. Export Map Value to List...");

		List<String> result2 = new ArrayList<String>(map.values());

		result2.forEach(System.out::println);

	}

	/**
	 * For Java 8, you can convert the Map into a stream, process it and returns it
	 * back as a List
	 */
	public static void useStream() {
		Map<Integer, String> map = new HashMap<>();
		map.put(10, "apple");
		map.put(20, "orange");
		map.put(30, "banana");
		map.put(40, "watermelon");
		map.put(50, "dragonfruit");

		System.out.println("\n1. Export Map Key to List...");

		List<Integer> result = map.keySet().stream().collect(Collectors.toList());

		result.forEach(System.out::println);

		System.out.println("\n2. Export Map Value to List...");

		List<String> result2 = map.values().stream().collect(Collectors.toList());

		result2.forEach(System.out::println);

		System.out.println("\n3. Export Map Value to List..., say no to banana");
		List<Integer> result3 = map.keySet().stream().filter(x -> x != 30).collect(Collectors.toList());

		result3.forEach(System.out::println);
	}
	/**
	 * This example is a bit extreme, uses map.entrySet() to convert a Map into 2 List
	 */
	public static void useStream2() {
		Map<Integer, String> map = new HashMap<>();
		map.put(10, "apple");
		map.put(20, "orange");
		map.put(30, "banana");
		map.put(40, "watermelon");
		map.put(50, "dragonfruit");
		
		List<Integer> resultSortedKey = new ArrayList<>();
		List<String> resultValues = map.entrySet().stream()
				.sorted((o1, o2)->o2.getKey().compareTo(o1.getKey()))
				.peek(e -> resultSortedKey.add(e.getKey()))
				.map(x -> x.getValue())
				.filter(x -> !"banana".equalsIgnoreCase(x))
				.collect(Collectors.toList());
		resultSortedKey.forEach(System.out::println);
		resultValues.forEach(System.out::println);
	}

}
