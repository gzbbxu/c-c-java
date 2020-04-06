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

	List<Employee> list = Arrays.asList(new Employee("����", 18, 9999.99),
			new Employee("����", 38, 343.99), new Employee("����", 58, 3453543.99),
			new Employee("����", 38, 902.99), new Employee("����", 80, 9239.99));

	// ���� ��ȡ��ǰ��˾ Ա���������35��Ա����Ϣ

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

	// �������� ��ȡ��˾�У�Ա�����ʴ���5000 ��Ա����Ϣ��
	public List<Employee> fiterEmployees2(List<Employee> list) {
		List<Employee> emps = new ArrayList<Employee>();
		for (Employee emp : list) {
			if (emp.getSalary() >= 5000) {
				emps.add(emp);
			}
		}
		return emps;
	}

	// �Դ���� �Ż���ʽ֮һ���������ģʽ

	// �Ż���ʽһ�����ַ�ʽ ֻ��Ҫһ���������������ģʽ�� ������ʵ�ֽӿڼ��ɡ�

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

	// �Ż���ʽ���������ڲ���
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

	// �Ż���ʽ����Lambada�Ż�,ֻ��Ҫ�ѹؼ�����ȡ����

	@Test
	public void test6() {
		List<Employee> emps = filterEmployee(list, (e) -> e.getSalary() < 5000);
		emps.forEach(System.out::println);
		// System.out.println(emp);
	}

	// �Ż���ʽ��:Stream API

	@Test
	public void test7() {
		list.stream().filter(e -> e.getSalary() >= 5000).limit(2)// ȡ��ǰ2��
				.forEach(System.out::println);

		System.out.println("====================");

		list.stream().map(Employee::getName).forEach(System.out::println);
		
		
		List<Employee> collect = list.stream().filter(e -> e.getSalary() >= 5000).limit(1).collect(Collectors.toList());
		
		collect.forEach(System.out::println);
		
	}

}
