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
 * ��ֹ����
 * 
 * @author gzbbxu
 *
 */
public class StreamTestApi3 {
	/**
	 * ����ƥ��
	 * allMatch---����Ƿ�ƥ������Ԫ��
	 * anyMatch--����Ƿ�����ƥ��һ��Ԫ��
	 * noneMatch--����Ƿ�û��ƥ������Ԫ��
	 * findFirst ���ص�һ��Ԫ��
	 * findAny ���ص�ǰ���е�����Ԫ��
	 * count ��������Ԫ�ص��ܸ���
	 * max �����������ֵ
	 * min ����������Сֵ
	 * 
	 */
	
	List<Employee> list = Arrays.asList(new Employee("����", 18, 9999.99,Status.FREE),
			new Employee("����", 38, 343.99,Status.FREE), 
			new Employee("����", 58, 3453543.99,Status.BUSY),
			
			new Employee("����", 38, 902.99,Status.FREE),
			new Employee("����", 80, 9239.99,Status.BUSY)
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
	 * ��Լ ������Ԫ�ط�������������õ�һ��ֵ
	 */
	@Test
	public void test(){
		List<Integer> array = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Integer sum = array.stream().reduce(0, (x,y)->x+y);   //BinaryOperator ��Ԫ���� ,
		//��ʼֵ��Ϊx,����ȡ��Ԫ����Ϊy,ʵ��x+y, �ѽ������Ϊx.
		System.out.println(sum);
		Optional<Double> optional = list.stream().map(Employee::getSalary).reduce(Double::sum);
		System.out.println(optional.get());
		//��ע�� map��reduce ������ͨ����Ϊmap-reduce ģʽ����google ����������������������
		
		
	}
	/**
	 * �ռ� �� collect --����ת��Ϊ������ʽ������һ��collector �ӿڵ�ʵ�֣����ڸ�stream ��Ԫ�������ܵķ�����
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
		//����
		Long collect = list.stream().collect(Collectors.counting());
		System.out.println(collect);
		//ƽ��
		Double avg = list.stream().collect(Collectors.averagingDouble(Employee::getSalary));
		System.out.println(avg);
		
		
		//�ܺ�
		Double double1 = list.stream().collect(Collectors.summingDouble(Employee::getSalary));
		
		System.out.println(double1);
		
		
		//���
		Optional<Employee> collect2 = list.stream().collect(Collectors.maxBy((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary())));
		System.out.println(collect2.get());
		
		
		//��С
		Optional<Double> min = list.stream().map(Employee::getSalary).collect(Collectors.minBy(Double::compare));
		System.out.println(min.get());
	}
	
	//����
	@Test
	public void test6(){
		Map<Status, List<Employee>> collect = list.stream().collect(Collectors.groupingBy(Employee::getStatus));
		
	}
	//�༶����
	
	@Test
	public void test7(){
		Map<Status, Map<String, List<Employee>>> collect = list.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e->{
			
			if(((Employee) e).getAge()<=35){
				return "����";
			}else if(((Employee) e).getAge()<=50){
				return "����";
			}else{
				return "����";
			}
		})));
		System.out.println(collect);
	}
	
	//���� ����������һ����������������������һ����
	@Test
	public void test8(){
		Map<Boolean, List<Employee>> collect = list.stream().collect(Collectors.partitioningBy((e)->e.getSalary()>8000));
		System.out.println(collect);
		
	}
	
	//������ʽ
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
