package my.guava.env;

import com.google.common.base.Optional;

/**
 * Optional用于包含非空对象的不可变对象。
 * Optional对象，用于不存在值表示null。
 * 这个类有各种实用的方法，以方便代码来处理为可用或不可用，而不是检查null值。
 * 
 * @Description:
 * @author: Binke Zhang
 * @date: 2017年11月15日 下午5:41:50
 * 
 * @Copyright: 2017 www.cdsunrise.net Inc. All rights reserved.
 */
public class OptionalTest {
	public static void main(String[] args) {
		Integer value1 = null;
		Integer value2 = new Integer(10);
		// 如果nullableReference非空，返回一个包含引用Optional实例;否则返回absent()。
		Optional<Integer> a = Optional.fromNullable(value1);
		// 返回包含给定的非空引用Optional实例。
		Optional<Integer> b = Optional.of(value2);
		System.out.println(sum(a, b));
	}

	public static Integer sum(Optional<Integer> a, Optional<Integer> b) {
		//返回true，如果这Optional包含一个(非空)的实例。
		System.out.println(a.isPresent());
		System.out.println(b.isPresent());
		// 返回所包含的实例(如果存在);否则为默认值0。
		Integer valuea = a.or(new Integer(0));
		// get 返回所包含的实例，它必须存在
		Integer valueb = b.get();
		return valuea + valueb;
	}
}
