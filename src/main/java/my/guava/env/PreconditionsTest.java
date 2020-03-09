package my.guava.env;

import com.google.common.base.Preconditions;

/**
 * Preconditions提供静态方法来检查方法或构造函数，被调用是否给定适当的参数。
 * 它检查的先决条件。其方法失败抛出IllegalArgumentException。
 * 
 * @Description:  
 * @author: Binke Zhang
 * @date:   2017年11月15日 下午5:43:00   
 *     
 * @Copyright: 2017 www.cdsunrise.net Inc. All rights reserved.
 */
public class PreconditionsTest {
	public static void main(String[] args) {
		System.out.println(sqrt(3.0));
		System.out.println(sum(3,3));
		System.out.println(getValue(4));
	}
	public static double sqrt(double input){
		Preconditions.checkArgument(input>0.0,"Illegal Argument passed: Negative value %s.",input);
		return Math.sqrt(input);
	}
	public static double sum(Integer a,Integer b){
		a = Preconditions.checkNotNull(a,"Illegal Argument passed: First parameter is Null.");
		b = Preconditions.checkNotNull(b,"Illegal Argument passed: Second parameter is Null.");
		return a+b;
	}
	public static int getValue(int input){
	      int[] data = {1,2,3,4,5};
	      Preconditions.checkElementIndex(input,data.length,
	         "Illegal Argument passed: Invalid index.");
	      return data[input];
	   }
}
