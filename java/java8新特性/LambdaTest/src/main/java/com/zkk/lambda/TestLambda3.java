package com.zkk.lambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;
/**
 * java8���� �Ĵ���ĺ���ʽ�ӿ�
 * @author gzbbxu
 *Consume<T> �����ͽӿ�
 *   void accept(T t)
 *   
 *Suplier<T>�������ͽӿ�
 *T get
 *Function<T,R> �����ͽӿ�
 * 	R apply(T t);
 * 
 *Predicate<T> �����ͽӿ�
 * boolean test(T t);
 */
public class TestLambda3 {
	//Consume<T> �����ͽӿ� ��ȥ�޻�
	@Test
	public void test1(){
		happy(100,m->System.out.println("--------"+m));
	}
	
	public void happy(double money,Consumer<Double> conn){
		conn.accept(money);
	}
	
//	Suplier<T>�������ͽӿ�  ���ض���
	@Test
	public void test2(){
		List<Integer> num = getNumberList(10,()->(int)(Math.random()*100));
		for(Integer in : num){
			System.out.println(in);
		}
	}
	//���󣬲���һЩ�������뼯��
	public List<Integer> getNumberList(int num,Supplier<Integer> sup){
		List<Integer> list =new ArrayList<Integer>();
		for(int i = 0; i< num;i++){
			list.add(sup.get());
		}
		
		return list;
		
	}
	
	//�����ͽӿ�
	//���󣺴����ַ���
	public void test3(){
		
		strHandler(" fdsa ",(str)->str.trim());
	}
	public String strHandler(String str,Function<String,String> fun){
		return fun.apply(str);
	}
	
	//���Խӿ�
	//���󣺽������������ַ��� ���뼯����.
	public void test4(){
		List<String> strList  = Arrays.asList("ret","bb","dd","xx");
		filterStr(strList,(str)->str.length()>=3);
		
	}
	public List<String > filterStr(List<String> list,Predicate<String> pre){
		List<String> strList = new ArrayList<>();
		for(String str: strList){
			if(pre.test(str)){
				strList.add(str);
			}
		}
		return strList;
	}
	
}
