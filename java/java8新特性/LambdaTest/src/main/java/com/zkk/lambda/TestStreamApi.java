package com.zkk.lambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
public class TestStreamApi {
	
	//1,����stream
	//2 �м����
	//3 ��ֹ����
    @Test
    public void test1(){
    	//1,����ͨ��Collection ϵ�м��ϵ�stream () ������ ���� parallelStream() ������
    	List<String> list = new ArrayList<>();
    	Stream<String> stream = list.stream();
    	
    	//2 ��ͨ��Arrays �ľ�̬����stream() ��ȡ������
    	Employee[] emps = new Employee[10];
    	
    	Stream<Employee> stream2 = Arrays.stream(emps);
    	
    	
    	//3 ͨ��Stream ��of����
    	Stream<String> stream3 = Stream.of("aa","bb","cc");
    	
    	//4 ,����������
    	//����
    	Stream<Integer> stream4 = Stream.iterate(0,(x)->x+2);
    	stream4.limit(10).forEach(System.out::println);
    	
    	//����
    	Stream.generate(()->Math.random()).limit(2).forEach(System.out::println);
    	
    	
    }
}
