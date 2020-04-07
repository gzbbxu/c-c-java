### hashmap

哈希表：底层其实就是个数组，数组中，存的都是entry.

哈希表，默认的大小是多少呢？是16

哈希表有什么好处呢？数组都有索引值。

当往里面添加对象的话，首先会调用对象的hashCode方法，根据哈希算法，对hashCode 进行运算，

运算后，生成数组的索引值，根据索引值，找到数组对应的位置。

如果当前位置没有对象在，直接存储。

相应的也会有一种情况，创建对象，**调用hashCode 方法**，生成的索引，在数组中，有对象存在，

那么，**再通过equals 比较两个对象的内容**，如果内容一样，则对象的value 覆盖掉，存在的value.

**，如果equals 比较不相同，则形成链表，在java1.7,后加入的放在前面。形成链表**。

这种情况，称为**碰撞**。这种情况，应该是尽可能去避免的，如果这里面的元素过多的话，效率低。



如果在有一个新对象，经过hashCode 生成的索引，如果跟上面所的位置一样，**则每个元素都要进行一次equals 比较**。所以数量比较多的情况，效率低下。



尽量避免：**hashCode ,equals 写的尽量严谨一些。并且让hashCode, equals 方法保持一致**。（就是对象的内容一样，生成的hashCode 一样，equals 方法返回true）

但是，尽管HashCode ,equals 写的再严谨，这种碰撞的情况也是避免不了的，只能说让它概率减少。

避免不了，也不能让当前索引的链表太长。



hashMap 提供了一种加载因子，（默认0.75），就是说，**现有元素到达哈希表的75%的时候，就进行扩容**，

一旦扩容，**则对链表中的每个元素进行重新运算，扩容以后新的位置**。（进过hash算法运算，找到新的位置），

在某种情况下，碰撞的概率就降低了。碰撞的内容就少了。

极端的情况下，里面的元素一个一个都找一遍，效率还是低，最极端的情况下，找到最后一个。



于是，在jdk1.8以后，就对它进行了改变。就不是原来的数组+链表了。而是 **数组+链表+红黑树**

什么时候，变红黑树呢？

**碰撞的个数大于8的时候，并且总元素大于64的时候**，这种时候，就把链表转换成红黑树。

转换成红黑树好在哪？

**除了添加以外，都比链表快**。（1.7 是添加到链表前面，1.8是添加到链表末尾），添加链表直接添加后面就可以了。

红黑树添加的时候，就得比较了。（比如：比红黑树的右节点大，放右边），除了添加，其他的效率都高了。



不仅如此，**而且扩容以后，换位置，也不会再进行hashCode 运算。原来哈希表的总长度 + 当前所在的位置** 就是扩容后所在的位置。

<img src="01.png" style="zoom:75%;" />





### 底层的内存结构

堆，栈，方法区（方法区属于堆的永久区的一部分）

为什么方法区属于堆的永久区的一部分，为什么画图画在堆的外面？

永久区存一些加载类的信息，核心类库，永久区的内容几乎不会被垃圾回收机制回收。

回收的条件比较苛刻。 

jvm 厂商是有很多种的，oracle-sun Hotspot 常用的， oracle  JRocket （oracle 自己的）

IBM J9 JVM ,  Taobo JVM (阿里)，几乎这些厂商都把永久区从堆区剥离出来，所以平常画，不画在堆里。



java8 以后，永久区彻底的被干掉了，取而代之的是Metaspace （元空间），这个相对于原来的方法区，有什么不同呢？

最大的不同就是Metaspace使用的是物理内存

### Lambda 表达式

lambda 是一个匿名函数，我们可以把Lambda 表达式理解为一段可以传递的代码（**将代码像数据一样进行传递**）。可以写出更简洁，更灵活的代码。作为一种更紧凑的代码风格，让java的语言表达能力得到了提升。



### **java常用接口**(四大核心函数式接口)

| 函数式接口              | 参数类型 | 返回类型 | 用途                                                         |
| ----------------------- | -------- | -------- | ------------------------------------------------------------ |
| Consumer<T>消费型接>    | T        | void     | 对类型为T的对象应用操作，包含方法，<br>void accetp(T t)      |
| Supplier<T>供给型接口   | 无       | T        | 返回类型为T的对象，包含方法：T get（）；                     |
| Function<T,R>函数型接口 | T        | R        | 对类型为T的对象应用操作，并返回结果<br>结果是R类型的对象，包含方法：R apply(T t); |
| Predicate<T>断定型接口  | T        | boolean  | 确定类型为T的对象是否满足约束，并返回boolean 值，包含方法boolean test(T  t) |

```java
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

```



### 方法引用和构造器引用

```java
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

```





















