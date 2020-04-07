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
 * java8内置 四大核心函数式接口
 * @author gzbbxu
 *Consume<T> 消费型接口
 *   void accept(T t)
 *   
 *Suplier<T>：供给型接口
 *T get
 *Function<T,R> 函数型接口
 * 	R apply(T t);
 * 
 *Predicate<T> 断言型接口
 * boolean test(T t);
 */
public class TestLambda3 {
	//Consume<T> 消费型接口 有去无回
	@Test
	public void test1(){
		happy(100,m->System.out.println("--------"+m));
	}
	
	public void happy(double money,Consumer<Double> conn){
		conn.accept(money);
	}
	
//	Suplier<T>：供给型接口  返回对象
	@Test
	public void test2(){
		List<Integer> num = getNumberList(10,()->(int)(Math.random()*100));
		for(Integer in : num){
			System.out.println(in);
		}
	}
	//需求，产生一些数，放入集合
	public List<Integer> getNumberList(int num,Supplier<Integer> sup){
		List<Integer> list =new ArrayList<Integer>();
		for(int i = 0; i< num;i++){
			list.add(sup.get());
		}
		
		return list;
		
	}
	
	//函数型接口
	//需求：处理字符串
	public void test3(){
		
		strHandler(" fdsa ",(str)->str.trim());
	}
	public String strHandler(String str,Function<String,String> fun){
		return fun.apply(str);
	}
	
	//断言接口
	//需求：将满足条件的字符串 放入集合中.
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
