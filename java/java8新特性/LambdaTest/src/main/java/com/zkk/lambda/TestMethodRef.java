package com.zkk.lambda;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * 一：方法引用: 如果 lambda体中的内容，有方法已经实现了，我们可以使用“方法引用” （可以理解为方法引用是Lambda 表达式的另外一种表现形式）
 * 
 * 
 * 主要有三种表现形式 对象::实力方法名 类::静态方法名 类::实例方法名
 * 
 * 
 * 二:构造器引用
 * 
 * 格式: ClassName::new
 * 注意： 需要调用的构造器参数列表，要与函数样式接口中的抽象方法的参数列表保持一致。
 * 
 * 三：数组引用
 * Type::new
 * 
 * @author gzbbxu
 *
 */
public class TestMethodRef {
	//数组引用
	@Test
	public void test7(){
		Function<Integer,String[]> fun = (x)->new String[x];
		String[] srs = fun.apply(10);
		System.out.println(srs.length);
		
		
		Function<Integer,String[]> fun2 = String[]::new;
		String[] sts2  = fun2.apply(20);
		System.out.println(sts2.length);
	}

	// 构造器引用
	@Test
	public void test5() {

		Supplier<Employee> sup = () -> new Employee("", 1, 1);
		//构造器引用
		Supplier<Employee> sup2 = Employee::new;
		Employee emp = sup2.get();
		System.out.println(emp);
	}
	@Test
	public void test6(){
		Function<Integer,Employee> fun = (x)->new Employee(x);
		//有几个参数，就调用几个参数的 构造器
		Function<Integer,Employee> fun2 = Employee::new;
		
		
		Employee employee = fun2.apply(101);
		System.out.println(employee);
		
		
//		BiFunction<Integer, Integer, Employee> bf = Employee::new;
		
		
	}

	// 对象::实力方法名
	@Test
	public void test1() {
		Consumer<String> con = (x) -> System.out.println(x);
		PrintStream ps = System.out;

		// 需要注意的是: 需要实现的接口类的抽象方法，参数列表和返回值类型，
		// 要与当前调用的这个方法 的参数列表和返回值类型 保持一致
		Consumer<String> con1 = ps::println;

		Consumer<String> con2 = System.out::println;
		con2.accept("hello");
	}

	@Test
	public void test2() {

		Employee emp = new Employee("11", 0, 0);
		Supplier<String> sup = () -> emp.getName();
		String str = sup.get();
		System.out.println(str);

		Supplier<Integer> sup2 = emp::getAge;
		Integer num = sup2.get();
		System.out.println(num);

	}

	// 静态方法
	@Test
	public void test3() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

		Comparator<Integer> com2 = Integer::compare;
	}

	// 类 ::实例方法名
	@Test
	public void test4() {
		BiPredicate<String, String> bp = (x, y) -> x.equals(y);
		// 如果 第一个参数是这个方法的调用者，第二个参数是这个方法的调用参数时，我们就可以使用类名:：方法名
		BiPredicate<String, String> bp2 = String::equals;
	}

}
