package com.zkk.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TestLambda {

	@Test
	public void test1() {
		Comparator<Integer> com = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};
		TreeSet<Integer> ts = new TreeSet();
	}

	@Test
	public void test2() {
		Comparator<Integer> com = (o1, o2) -> {
			return Integer.compare(01, o2);
		};

		Comparator<Integer> com2 = (o1, o2) -> Integer.compare(o1, o2);

	}

	List<Employee> list = Arrays.asList(new Employee("张三", 18, 9999.99),
			new Employee("李四", 38, 343.99), new Employee("王五", 58, 3453543.99),
			new Employee("赵六", 38, 902.99), new Employee("田七", 80, 9239.99));

	// 需求： 获取当前公司 员工年龄大于35的员工信息

	@Test
	public void test3() {
		List<Employee> emps = filterEmploees(list);
		for (Employee emp : list) {
			emp.toString();
		}
	}

	public List<Employee> filterEmploees(List<Employee> list) {
		List<Employee> emps = new ArrayList<Employee>();
		for (Employee emp : list) {
			if (emp.getAge() >= 35) {
				emps.add(emp);
			}
		}
		return emps;
	}

	// 需求变更： 获取公司中，员工工资大于5000 的员工信息。
	public List<Employee> fiterEmployees2(List<Employee> list) {
		List<Employee> emps = new ArrayList<Employee>();
		for (Employee emp : list) {
			if (emp.getSalary() >= 5000) {
				emps.add(emp);
			}
		}
		return emps;
	}

	// 对代码的 优化方式之一，就是设计模式

	// 优化方式一：这种方式 只需要一个方法。策略设计模式。 创建类实现接口即可。

	@Test
	public void test4() {
		List<Employee> emps = filterEmployee(list, new FilterEmploeeByAge());
		for (Employee emp : emps) {
			System.out.println(emp);
		}

		List<Employee> emps2 = filterEmployee(list, new FilterEmploeeBySalary());
		for (Employee emp : emps2) {
			// emp.toString();
			System.out.println(emp);
		}
	}

	public List<Employee> filterEmployee(List<Employee> list,
			MyPredicate<Employee> mp) {
		List<Employee> emps = new ArrayList<Employee>();
		for (Employee emp : list) {
			if (mp.test(emp)) {
				emps.add(emp);
			}
		}
		return emps;
	}

	// 优化方式二：匿名内部类
	@Test
	public void test5() {
		List<Employee> emps = filterEmployee(list, new MyPredicate<Employee>() {

			@Override
			public boolean test(Employee t) {
				// TODO Auto-generated method stub
				return t.getSalary() < 5000;
			}
		});
	}

	// 优化方式三：Lambada优化,只需要把关键点提取出来

	@Test
	public void test6() {
		List<Employee> emps = filterEmployee(list, (e) -> e.getSalary() < 5000);
		emps.forEach(System.out::println);
		// System.out.println(emp);
	}

	// 优化方式四:Stream API

	@Test
	public void test7() {
		list.stream().filter(e -> e.getSalary() >= 5000).limit(2)// 取出前2个
				.forEach(System.out::println);

		System.out.println("====================");

		list.stream().map(Employee::getName).forEach(System.out::println);
		
		
		List<Employee> collect = list.stream().filter(e -> e.getSalary() >= 5000).limit(1).collect(Collectors.toList());
		
		collect.forEach(System.out::println);
		
	}

}
