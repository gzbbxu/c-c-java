package com.zkk.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class TestStreamApi2 {

	// 中间操作
	/**
	 * 筛选与切片 filter 接收lamba ,从流中排除某些元素 limit-截断流 ，使其元素不超过给定数量 skip(n)
	 * 跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流与limit(n)互补
	 * distinct-筛选，通过流所生成的hashCode() 和 equals() 去除重复元素。
	 */
	List<Employee> list = Arrays.asList(new Employee("张三", 18, 9999.99),
			new Employee("李四", 38, 343.99), new Employee("王五", 58, 3453543.99),
			new Employee("赵六", 38, 902.99), new Employee("田七", 80, 9239.99),
			new Employee("田七", 80, 9239.99), new Employee("田七", 80, 9239.99));

	// 内部迭代，迭代操作由stream API完成。
	@Test
	public void test1() {
		// 中间操作：不会执行任何操作
		Stream<Employee> stream = list.stream().filter((x) -> x.getAge() > 35);
		// 终止操作,一次性执行全部内容，称为“惰性求值”
		stream.forEach(System.out::println);

		// 中间操作是不会执行任何操作的，只有执行终止操作，所有的中间操作，一次性的执行全部。

		// 多个中间操作可以连接起来形成一个流水线，除非流水线上出发终止操作，否则中间操作不执行任何的处理
		// 而在终止操作时，一次性全部处理，称为“惰性求值”

	}

	// 外部迭代
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
			System.out.println("短路");
			return e.getSalary() > 5000;
		}).limit(2).forEach(System.out::println);
	}

	@Test
	public void test4() {
		// 跳过
		list.stream().filter(e -> e.getSalary() > 5000).skip(2)
				.forEach(System.out::println);
	}

	@Test
	public void test5() {
		// 去重
		list.stream().filter(e -> e.getSalary() > 5000).distinct()
				.forEach(System.out::println);
	}

	/**
	 * 映射 map 接收lambda ，将元素转成其他元素或提取信息。接收一个函数作为参数， 改函数会被应用到每个元素上， 并将其映射成一个新的元素。
	 * 
	 * flatMap 接收一个函数作为参数，
	 * 
	 * 将流中的每个值都转换成另一个流，然后把所有的流 连接成一个新流
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

		// flat 翻译 就是扁平化，平铺

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
	 * 排序 sorted() 自然排序 Comparable sorted() Comparator 定制排序
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
