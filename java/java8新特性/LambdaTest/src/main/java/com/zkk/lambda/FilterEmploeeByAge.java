package com.zkk.lambda;

public class FilterEmploeeByAge  implements MyPredicate<Employee>{

	@Override
	public boolean test(Employee t) {
		return t.getAge()>=35;
	}

}
