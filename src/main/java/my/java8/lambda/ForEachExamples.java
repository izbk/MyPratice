package my.java8.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForEachExamples {
	public static void main(String[] args) {
		//1.1 Normal way to loop a Map.
		Map<String, Integer> items = new HashMap<>();
		items.put("A", 10);
		items.put("B", 20);
		items.put("C", 30);
		items.put("D", 40);
		items.put("E", 50);
		items.put("F", 60);

		for (Map.Entry<String, Integer> entry : items.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
		}
		
		//1.2 In Java 8, you can loop a Map with forEach + lambda expression.
		items.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));

		items.forEach((k,v)->{
			System.out.println("Item : " + k + " Count : " + v);
			if("E".equals(k)){
				System.out.println("Hello E");
			}
		});
		
		//2.1 Normal for-loop to loop a List.
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");

		for(String item : list){
			System.out.println(item);
		}
		
		//2.2 In Java 8, you can loop a List with forEach + lambda expression or method reference.
		list.forEach(item->System.out.println(item));
		list.forEach(item->{
			if("C".equals(item)){
				System.out.println(item);
			}
		});
		list.forEach(System.out::println);
		list.stream().filter(s->s.contains("B")).forEach(System.out::println);
		
	}
}
