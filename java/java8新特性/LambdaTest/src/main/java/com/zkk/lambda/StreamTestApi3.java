package com.zkk.lambda;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.zkk.lambda.Employee.Status;

/**
 * 终止操作
 * 
 * @author gzbbxu
 *
 */
public class StreamTestApi3 {
	/**
	 * 查找匹配
	 * allMatch---检查是否匹配所有元素
	 * anyMatch--检查是否至少匹配一个元素
	 * noneMatch--检查是否没有匹配所有元素
	 * findFirst 返回第一个元素
	 * findAny 返回当前流中的任意元素
	 * count 返回流中元素的总个数
	 * max 返回流中最大值
	 * min 返回流中最小值
	 * 
	 */
	
	List<Employee> list = Arrays.asList(new Employee("张三", 18, 9999.99,Status.FREE),
			new Employee("李四", 38, 343.99,Status.FREE), 
			new Employee("王五", 58, 3453543.99,Status.BUSY),
			
			new Employee("赵六", 38, 902.99,Status.FREE),
			new Employee("田七", 80, 9239.99,Status.BUSY)
			);
	
	
	@Test
	public void test2(){
		long count = list.stream().count();
		System.out.println(count);
		Optional<Employee> max = list.stream().max((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary()));
		System.out.println(max.get());
		
		Optional<Double> min = list.stream().map(Employee::getSalary).min(Double::compare);
		System.out.println(min.get());
	}
	
	@Test
	public void test1(){
		boolean allMatch = list.stream().allMatch(e->e.getStatus().equals(Status.BUSY));
		System.out.print(allMatch);
		
		boolean allMatch2 = list.stream().anyMatch(e->e.getStatus().equals(Status.VOCATION));
		System.out.println(allMatch2);
		
		boolean allMatch3 = list.stream().noneMatch(e->e.getStatus().equals(Status.VOCATION));
		System.out.println(allMatch3);
		
		Optional<Employee> findFirst = list.stream().sorted((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary())).findFirst();
		System.out.println(findFirst.get());
		
		Optional<Employee> findAny = list.stream().filter((e)->e.getStatus().equals(Status.FREE)).findAny();
		System.out.println(findAny.get());
		
		Optional<Employee> findAny2 = list.parallelStream().filter((e)->e.getStatus().equals(Status.FREE)).findAny();
		System.out.println(findAny2.get());
	}
	
	
	/**
	 * 归约 将流中元素反复结合起来，得到一个值
	 */
	@Test
	public void test(){
		List<Integer> array = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Integer sum = array.stream().reduce(0, (x,y)->x+y);   //BinaryOperator 二元运算 ,
		//启始值作为x,流中取出元素作为y,实现x+y, 把结果再作为x.
		System.out.println(sum);
		Optional<Double> optional = list.stream().map(Employee::getSalary).reduce(Double::sum);
		System.out.println(optional.get());
		//备注： map和reduce 的连接通常称为map-reduce 模式，因google 用它进行网络搜索而出名
		
		
	}
	/**
	 * 收集 ： collect --将流转换为其他形式。接收一个collector 接口的实现，用于给stream 中元素做汇总的方法。
	 */
	@Test
	public void test4(){
	   List<String> collect = list.stream().map(Employee::getName).collect(Collectors.toList());
	   for(String str: collect){
		   System.out.println(str);
	   }
	   Set<String> collect2 = list.stream().map(Employee::getName).collect(Collectors.toSet());
	   
	   
	   HashSet<String> collect3 = list.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
	   collect3.forEach(System.out::println);
	}
	
	@Test
	public void test5(){
		//总数
		Long collect = list.stream().collect(Collectors.counting());
		System.out.println(collect);
		//平均
		Double avg = list.stream().collect(Collectors.averagingDouble(Employee::getSalary));
		System.out.println(avg);
		
		
		//总和
		Double double1 = list.stream().collect(Collectors.summingDouble(Employee::getSalary));
		
		System.out.println(double1);
		
		
		//最大
		Optional<Employee> collect2 = list.stream().collect(Collectors.maxBy((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary())));
		System.out.println(collect2.get());
		
		
		//最小
		Optional<Double> min = list.stream().map(Employee::getSalary).collect(Collectors.minBy(Double::compare));
		System.out.println(min.get());
	}
	
	//分组
	@Test
	public void test6(){
		Map<Status, List<Employee>> collect = list.stream().collect(Collectors.groupingBy(Employee::getStatus));
		
	}
	//多级分组
	
	@Test
	public void test7(){
		Map<Status, Map<String, List<Employee>>> collect = list.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e->{
			
			if(((Employee) e).getAge()<=35){
				return "青年";
			}else if(((Employee) e).getAge()<=50){
				return "中年";
			}else{
				return "老年";
			}
		})));
		System.out.println(collect);
	}
	
	//分区 满足条件的一个区，不满足条件的另外一个区
	@Test
	public void test8(){
		Map<Boolean, List<Employee>> collect = list.stream().collect(Collectors.partitioningBy((e)->e.getSalary()>8000));
		System.out.println(collect);
		
	}
	
	//其他方式
	public void test9(){
		DoubleSummaryStatistics doubleSummaryStatistics = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
		doubleSummaryStatistics.getAverage();
		doubleSummaryStatistics.getMax();
		doubleSummaryStatistics.getMin();
		doubleSummaryStatistics.getCount();
	}
	
	@Test
	public void test10(){
		String collect = list.stream().map(Employee::getName).collect(Collectors.joining(","));
		System.out.println(collect);
		
	}
	
}
