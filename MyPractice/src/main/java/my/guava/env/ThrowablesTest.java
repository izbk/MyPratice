package my.guava.env;

import java.io.IOException;

import com.google.common.base.Throwables;
/**
 * Throwable类提供了相关的Throwable接口的实用方法
 * 
 * @Description:  
 * @author: Binke Zhang
 * @date:   2017年11月15日 下午6:44:49   
 *     
 * @Copyright: 2017 www.cdsunrise.net Inc. All rights reserved.
 */
public class ThrowablesTest {

	public static void main(String args[]) {
		try {
			showcaseThrowables();
		} catch (InvalidInputException e) {
			// get the root cause
			System.out.println(Throwables.getRootCause(e));
		} catch (Exception e) {
			// get the stack trace in string format
			System.out.println(Throwables.getStackTraceAsString(e));
		}

		try {
			showcaseThrowables1();
		} catch (Exception e) {
			System.out.println(Throwables.getStackTraceAsString(e));
		}
	}

	public static void showcaseThrowables() throws InvalidInputException {
		try {
			sqrt(-3.0);
		} catch (Throwable e) {
			// check the type of exception and throw it
			Throwables.throwIfInstanceOf(e, InvalidInputException.class);
			Throwables.throwIfUnchecked(e);
		}
	}

	public static void showcaseThrowables1() {
		try {
			int[] data = { 1, 2, 3 };
			getValue(data, 4);
		} catch (Throwable e) {
			Throwables.throwIfInstanceOf(e, IndexOutOfBoundsException.class);
			Throwables.throwIfUnchecked(e);
		}
	}

	public static double sqrt(double input) throws InvalidInputException {
		if (input < 0)
			throw new InvalidInputException();
		return Math.sqrt(input);
	}

	public static double getValue(int[] list, int index) throws IndexOutOfBoundsException {
		return list[index];
	}

	public static void dummyIO() throws IOException {
		throw new IOException();
	}
}

class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;
}