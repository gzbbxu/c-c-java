package com.zkk.lambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
public class TestStreamApi {
	
	//1,创建stream
	//2 中间操作
	//3 终止操作
    @Test
    public void test1(){
    	//1,可以通过Collection 系列集合的stream () 串行流 或者 parallelStream() 并行流
    	List<String> list = new ArrayList<>();
    	Stream<String> stream = list.stream();
    	
    	//2 ，通过Arrays 的静态方法stream() 获取数组流
    	Employee[] emps = new Employee[10];
    	
    	Stream<Employee> stream2 = Arrays.stream(emps);
    	
    	
    	//3 通过Stream 的of对象。
    	Stream<String> stream3 = Stream.of("aa","bb","cc");
    	
    	//4 ,创建无限流
    	//迭代
    	Stream<Integer> stream4 = Stream.iterate(0,(x)->x+2);
    	stream4.limit(10).forEach(System.out::println);
    	
    	//生成
    	Stream.generate(()->Math.random()).limit(2).forEach(System.out::println);
    	
    	
    }
}
