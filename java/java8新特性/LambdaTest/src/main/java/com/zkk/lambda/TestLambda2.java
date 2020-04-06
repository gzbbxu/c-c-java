package com.zkk.lambda;

import java.util.Comparator;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * //基础语法 java8 新的操作符"->" 该操作称为箭头操作符 或者Lambda 操作符 箭头操作符 将lambda 表达式 拆分为两部分：
 * 左侧：Lambda 表达式 的参数列表.(接口中，抽象方法的参数列表) 右侧： Lambda 表达式中所需要执行的功能，即Lambda
 * 体。(接口的实现,接口实现的功能)
 * 
 * 
 * 比如有多个方法？Lambda 实现的是哪个呢？ Lambda 需要函数式接口的支持。所谓函数式接口中，只有一个抽象方法的接口。称为 函数式接口
 * 
 *
 * 语法格式一：无参数，无返回值 ()->System.out.println("Hello Lambda");
 * 
 * 
 * 语法格式二:有一个参数，有一个无返回值 (x)->System.out.println(x);
 * 
 * 语法格式三:有一个参数，参数的小括号可以省略不写。但是通常都写上。
 * 
 * 
 * 语法格式四:有2个以上参数，有返回值，Lambda 体 中有多条返回值. 多条语句，Lambda 体，必须有大括号
 * 
 * 
 * 语法格式五:若Lambda 体，只有一条语句，return 和 大括号都可以省略不写。
 * 
 * 
 * 语法格式六:Lambda表达式的参数列表的数据类型，可以省略不写，因为JVM编译器通过上下文推断出数据类型。即"类型推断"
 * 
 * Comparator<Integer> com2 = (Integer x, Integer y) -> Integer.compare(x, y);
 * 上联： 左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 * 
 * 
 * 二：Lambaa 表达式 需要函数式接口的支持。
 * 什么是函数式解耦：接口中，只有一个函数式接口的方法，称为函数式接口。可以使用注解@FunctionalInterface
 * 
 */
public class TestLambda2 {

	// 语法格式一
	@Test
	public void test1() {
		int num = 0; // jdk 1.7 前，必须是final ,1.8 final 可省略，但是还是final的。
		Runnable r = new Runnable() {

			@Override
			public void run() {
				System.out.println("hello ===" + num);
			}
		};
		r.run();
		Runnable r1 = () -> System.out.println("xxx");
		r1.run();
	}

	// 语法格式二 ，有一个参数，有一个无返回值，有一个参数，参数的小括号可以省略不写
	@Test
	public void test2() {
		Consumer<String> con = (x) -> System.out.println(x);
		con.accept("hello world");

		Consumer<String> con2 = x -> System.out.println(x);
		con2.accept("hello world22");
	}

	// 语法格式四:
	@Test
	public void test3() {
		Comparator<Integer> com = (x, y) -> {
			System.out.println("函数式接口");
			return Integer.compare(x, y);
		};
	}
	
	//语法格式五
	@Test
	public void test4() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		Comparator<Integer> com2 = (Integer x, Integer y) -> Integer.compare(x, y);
	}
	
	
	//需求：对一个数进行运算
	
	@Test
	public void test6() {
		Integer num = operation(100,x->x*x);
		System.out.println(num);
	}
	
	public Integer operation(Integer num,MyFun mf){
		return mf.getValue(num);
	}
}
