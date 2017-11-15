package my.guava.env;

import com.google.common.base.Objects;

/**
 * Objects类提供适用于所有对象，如equals, hashCode等辅助函数
 * 
 * @Description:
 * @author: Binke Zhang
 * @date: 2017年11月15日 下午6:32:50
 * 
 * @Copyright: 2017 www.cdsunrise.net Inc. All rights reserved.
 */
public class ObjectsTest {

	public static void main(String[] args) {
		Student s1 = new Student("Mahesh", "Parashar", 1, "VI");
		Student s2 = new Student("Suresh", null, 3, null);

		System.out.println(s1.equals(s2));
		System.out.println(s1.hashCode());
	}

}

class Student {
	private String firstName;
	private String lastName;
	private int rollNo;
	private String className;

	public Student(String firstName, String lastName, int rollNo, String className) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rollNo = rollNo;
		this.className = className;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Student) || object == null) {
			return false;
		}
		Student student = (Student) object;
		// no need to handle null here
		// Objects.equal("test", "test") == true
		// Objects.equal("test", null) == false
		// Objects.equal(null, "test") == false
		// Objects.equal(null, null) == true
		return Objects.equal(firstName, student.firstName) // first name can be
															// null
				&& Objects.equal(lastName, student.lastName) // last name can be
																// null
				&& Objects.equal(rollNo, student.rollNo) && Objects.equal(className, student.className);// class
																										// name
																										// can
																										// be
																										// null
	}

	@Override
	public int hashCode() {
		// no need to compute hashCode by self
		return Objects.hashCode(className, rollNo);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
