package my.java8.misc;

import java.util.Random;

public class RamdomInt {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getRandomNumberInRange(5, 10));
		}
		System.out.println("*************************************");
		for (int i = 0; i < 10; i++) {
			System.out.println(getRandomNumberInRange2(5, 10));
		}
		System.out.println("*****************use java8********************");
		for (int i = 0; i < 10; i++) {
			System.out.println(getRandomNumberInRange3(5, 10));
		}
		
		printRandomNumberInRangeWithSize(5, 10, 10);
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	private static int getRandomNumberInRange2(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return (int)(Math.random() * ((max - min) + 1)) + min;
	}
	
	private static int getRandomNumberInRange3(int min, int max) {
		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}
	
	private static void printRandomNumberInRangeWithSize(int min, int max,int size) {
		new Random().ints(size, min, max).forEach(System.out::println);
	}
}
