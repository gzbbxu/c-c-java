package com.zkk.lambda;

import java.util.Comparator;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * //�����﷨ java8 �µĲ�����"->" �ò�����Ϊ��ͷ������ ����Lambda ������ ��ͷ������ ��lambda ���ʽ ���Ϊ�����֣�
 * ��ࣺLambda ���ʽ �Ĳ����б�.(�ӿ��У����󷽷��Ĳ����б�) �Ҳࣺ Lambda ���ʽ������Ҫִ�еĹ��ܣ���Lambda
 * �塣(�ӿڵ�ʵ��,�ӿ�ʵ�ֵĹ���)
 * 
 * 
 * �����ж��������Lambda ʵ�ֵ����ĸ��أ� Lambda ��Ҫ����ʽ�ӿڵ�֧�֡���ν����ʽ�ӿ��У�ֻ��һ�����󷽷��Ľӿڡ���Ϊ ����ʽ�ӿ�
 * 
 *
 * �﷨��ʽһ���޲������޷���ֵ ()->System.out.println("Hello Lambda");
 * 
 * 
 * �﷨��ʽ��:��һ����������һ���޷���ֵ (x)->System.out.println(x);
 * 
 * �﷨��ʽ��:��һ��������������С���ſ���ʡ�Բ�д������ͨ����д�ϡ�
 * 
 * 
 * �﷨��ʽ��:��2�����ϲ������з���ֵ��Lambda �� ���ж�������ֵ. ������䣬Lambda �壬�����д�����
 * 
 * 
 * �﷨��ʽ��:��Lambda �壬ֻ��һ����䣬return �� �����Ŷ�����ʡ�Բ�д��
 * 
 * 
 * �﷨��ʽ��:Lambda���ʽ�Ĳ����б���������ͣ�����ʡ�Բ�д����ΪJVM������ͨ���������ƶϳ��������͡���"�����ƶ�"
 * 
 * Comparator<Integer> com2 = (Integer x, Integer y) -> Integer.compare(x, y);
 * ������ ������һ����ʡ
 * ����������ƶ�����ʡ
 * ��������ʡ��ʡ
 * 
 * 
 * ����Lambaa ���ʽ ��Ҫ����ʽ�ӿڵ�֧�֡�
 * ʲô�Ǻ���ʽ����ӿ��У�ֻ��һ������ʽ�ӿڵķ�������Ϊ����ʽ�ӿڡ�����ʹ��ע��@FunctionalInterface
 * 
 */
public class TestLambda2 {

	// �﷨��ʽһ
	@Test
	public void test1() {
		int num = 0; // jdk 1.7 ǰ��������final ,1.8 final ��ʡ�ԣ����ǻ���final�ġ�
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

	// �﷨��ʽ�� ����һ����������һ���޷���ֵ����һ��������������С���ſ���ʡ�Բ�д
	@Test
	public void test2() {
		Consumer<String> con = (x) -> System.out.println(x);
		con.accept("hello world");

		Consumer<String> con2 = x -> System.out.println(x);
		con2.accept("hello world22");
	}

	// �﷨��ʽ��:
	@Test
	public void test3() {
		Comparator<Integer> com = (x, y) -> {
			System.out.println("����ʽ�ӿ�");
			return Integer.compare(x, y);
		};
	}
	
	//�﷨��ʽ��
	@Test
	public void test4() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		Comparator<Integer> com2 = (Integer x, Integer y) -> Integer.compare(x, y);
	}
	
	
	//���󣺶�һ������������
	
	@Test
	public void test6() {
		Integer num = operation(100,x->x*x);
		System.out.println(num);
	}
	
	public Integer operation(Integer num,MyFun mf){
		return mf.getValue(num);
	}
}
