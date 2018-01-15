package my.java8.lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorExample {
	public static void main(String[] args) {
		List<Developer> listDevs = getDevelopers();

		System.out.println("Before Sort");
		for (Developer developer : listDevs) {
			System.out.println(developer);
		}

		//sort by age
		Collections.sort(listDevs, new Comparator<Developer>() {
			@Override
			public int compare(Developer o1, Developer o2) {
				return o1.getAge() - o2.getAge();
			}
		});
		//lambda
		listDevs.sort((Developer o1, Developer o2)->o1.getAge()-o2.getAge());
		//lambda, valid, parameter type is optional
		listDevs.sort((o1, o2)->o1.getAge()-o2.getAge());
		System.out.println("After sort by age");
		for (Developer developer : listDevs) {
			System.out.println(developer);
		}

		//sort by name
		Collections.sort(listDevs, new Comparator<Developer>() {
			@Override
			public int compare(Developer o1, Developer o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		//lambda
		listDevs.sort((Developer o1, Developer o2)->o1.getName().compareTo(o2.getName()));
		//lambda
		listDevs.sort((o1, o2)->o1.getName().compareTo(o2.getName()));
		System.out.println("After sort by name");
		for (Developer developer : listDevs) {
			System.out.println(developer);
		}

		//sort by salary
		Collections.sort(listDevs, new Comparator<Developer>() {
			@Override
			public int compare(Developer o1, Developer o2) {
				return o1.getSalary().compareTo(o2.getSalary());
			}
		});
		//lambda
		listDevs.sort((Developer o1, Developer o2)->o1.getSalary().compareTo(o2.getSalary()));
		//lambda
		listDevs.sort((o1, o2)->o1.getSalary().compareTo(o2.getSalary()));
		System.out.println("After sort by salary");
		for (Developer developer : listDevs) {
			System.out.println(developer);
		}

		Comparator<Developer> salaryComparator = (o1, o2)->o1.getSalary().compareTo(o2.getSalary());
		listDevs.sort(salaryComparator);
		listDevs.sort(salaryComparator.reversed());

	}
	private static List<Developer> getDevelopers() {

		List<Developer> result = new ArrayList<Developer>();
		result.add(new Developer("mkyong", new BigDecimal("70000"), 33));
		result.add(new Developer("alvin", new BigDecimal("80000"), 20));
		result.add(new Developer("jason", new BigDecimal("100000"), 10));
		result.add(new Developer("iris", new BigDecimal("170000"), 55));

		return result;

	}
}

class Developer{
	private String name;
	private BigDecimal salary;
	private Integer age;
	
	public Developer() {
		super();
	}
	
	public Developer(String name, BigDecimal salary, Integer age) {
		super();
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
