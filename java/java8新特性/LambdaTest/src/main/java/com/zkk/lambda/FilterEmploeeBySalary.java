package com.zkk.lambda;

public class FilterEmploeeBySalary implements MyPredicate<Employee> {

	@Override
	public boolean test(Employee t) {
		return t.getSalary() >= 50000;
	}

}
