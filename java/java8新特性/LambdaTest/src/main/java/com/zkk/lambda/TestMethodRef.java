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
 * һ����������: ��� lambda���е����ݣ��з����Ѿ�ʵ���ˣ����ǿ���ʹ�á��������á� ���������Ϊ����������Lambda ���ʽ������һ�ֱ�����ʽ��
 * 
 * 
 * ��Ҫ�����ֱ�����ʽ ����::ʵ�������� ��::��̬������ ��::ʵ��������
 * 
 * 
 * ��:����������
 * 
 * ��ʽ: ClassName::new
 * ע�⣺ ��Ҫ���õĹ����������б�Ҫ�뺯����ʽ�ӿ��еĳ��󷽷��Ĳ����б���һ�¡�
 * 
 * ������������
 * Type::new
 * 
 * @author gzbbxu
 *
 */
public class TestMethodRef {
	//��������
	@Test
	public void test7(){
		Function<Integer,String[]> fun = (x)->new String[x];
		String[] srs = fun.apply(10);
		System.out.println(srs.length);
		
		
		Function<Integer,String[]> fun2 = String[]::new;
		String[] sts2  = fun2.apply(20);
		System.out.println(sts2.length);
	}

	// ����������
	@Test
	public void test5() {

		Supplier<Employee> sup = () -> new Employee("", 1, 1);
		//����������
		Supplier<Employee> sup2 = Employee::new;
		Employee emp = sup2.get();
		System.out.println(emp);
	}
	@Test
	public void test6(){
		Function<Integer,Employee> fun = (x)->new Employee(x);
		//�м����������͵��ü��������� ������
		Function<Integer,Employee> fun2 = Employee::new;
		
		
		Employee employee = fun2.apply(101);
		System.out.println(employee);
		
		
//		BiFunction<Integer, Integer, Employee> bf = Employee::new;
		
		
	}

	// ����::ʵ��������
	@Test
	public void test1() {
		Consumer<String> con = (x) -> System.out.println(x);
		PrintStream ps = System.out;

		// ��Ҫע�����: ��Ҫʵ�ֵĽӿ���ĳ��󷽷��������б�ͷ���ֵ���ͣ�
		// Ҫ�뵱ǰ���õ�������� �Ĳ����б�ͷ���ֵ���� ����һ��
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

	// ��̬����
	@Test
	public void test3() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

		Comparator<Integer> com2 = Integer::compare;
	}

	// �� ::ʵ��������
	@Test
	public void test4() {
		BiPredicate<String, String> bp = (x, y) -> x.equals(y);
		// ��� ��һ����������������ĵ����ߣ��ڶ�����������������ĵ��ò���ʱ�����ǾͿ���ʹ������:��������
		BiPredicate<String, String> bp2 = String::equals;
	}

}
