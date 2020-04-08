package com.zkk.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class TestStreamApi2 {

	// �м����
	/**
	 * ɸѡ����Ƭ filter ����lamba ,�������ų�ĳЩԪ�� limit-�ض��� ��ʹ��Ԫ�ز������������� skip(n)
	 * ����Ԫ�أ�����һ���ӵ���ǰn��Ԫ�ص�����������Ԫ�ز���n�����򷵻�һ��������limit(n)����
	 * distinct-ɸѡ��ͨ���������ɵ�hashCode() �� equals() ȥ���ظ�Ԫ�ء�
	 */
	List<Employee> list = Arrays.asList(new Employee("����", 18, 9999.99),
			new Employee("����", 38, 343.99), new Employee("����", 58, 3453543.99),
			new Employee("����", 38, 902.99), new Employee("����", 80, 9239.99),
			new Employee("����", 80, 9239.99), new Employee("����", 80, 9239.99));

	// �ڲ�����������������stream API��ɡ�
	@Test
	public void test1() {
		// �м����������ִ���κβ���
		Stream<Employee> stream = list.stream().filter((x) -> x.getAge() > 35);
		// ��ֹ����,һ����ִ��ȫ�����ݣ���Ϊ��������ֵ��
		stream.forEach(System.out::println);

		// �м�����ǲ���ִ���κβ����ģ�ֻ��ִ����ֹ���������е��м������һ���Ե�ִ��ȫ����

		// ����м�����������������γ�һ����ˮ�ߣ�������ˮ���ϳ�����ֹ�����������м������ִ���κεĴ���
		// ������ֹ����ʱ��һ����ȫ��������Ϊ��������ֵ��

	}

	// �ⲿ����
	@Test
	public void test2() {
		Iterator<Employee> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	@Test
	public void test3() {
		list.stream().filter((e) -> {
			System.out.println("��·");
			return e.getSalary() > 5000;
		}).limit(2).forEach(System.out::println);
	}

	@Test
	public void test4() {
		// ����
		list.stream().filter(e -> e.getSalary() > 5000).skip(2)
				.forEach(System.out::println);
	}

	@Test
	public void test5() {
		// ȥ��
		list.stream().filter(e -> e.getSalary() > 5000).distinct()
				.forEach(System.out::println);
	}

	/**
	 * ӳ�� map ����lambda ����Ԫ��ת������Ԫ�ػ���ȡ��Ϣ������һ��������Ϊ������ �ĺ����ᱻӦ�õ�ÿ��Ԫ���ϣ� ������ӳ���һ���µ�Ԫ�ء�
	 * 
	 * flatMap ����һ��������Ϊ������
	 * 
	 * �����е�ÿ��ֵ��ת������һ������Ȼ������е��� ���ӳ�һ������
	 */

	@Test
	public void test6() {
		List<String> strlist = Arrays.asList("aa", "bb", "cc");

		strlist.stream().map((x) -> x.toUpperCase())
				.forEach(System.out::println);
		;

		list.stream().map(Employee::getName).forEach(System.out::println);

		System.out.println("======================");

		Stream<Stream<Character>> stream = strlist.stream().map(
				TestStreamApi2::filterCharacter);
		stream.forEach((sm) -> {
			sm.forEach(System.out::println);
		});

		System.out.println("======================");

		// flat ���� ���Ǳ�ƽ����ƽ��

		strlist.stream().flatMap(TestStreamApi2::filterCharacter);
		strlist.forEach(System.out::println);
	}

	public static Stream<Character> filterCharacter(String str) {
		List<Character> list = new ArrayList<>();
		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}
		return list.stream();
	}

	/**
	 * ���� sorted() ��Ȼ���� Comparable sorted() Comparator ��������
	 */
	@Test
	public void test7() {
		List<String> strlist = Arrays.asList("dd", "bb", "cc");
		strlist.stream().sorted().forEach(System.out::println);
		list.stream().sorted((e1, e2) -> {
			if (e1.getAge() == (e2.getAge())) {
				return e1.getName().compareTo(e2.getName());
			} else {
				return Integer.compare(e1.getAge(), e2.getAge());
			}
		}).forEach(System.out::println);
	}

}
