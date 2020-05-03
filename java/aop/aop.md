### 代理的概念与作用

为某一个类增加一些功能，异常处理，日志，计算方法运行时间。。。。

代理类的每个方法调用目标类的相同方法，**并在调用方法时加上系统功能的代码**。



如果采用工厂模式和配置文件的方式进行管理，则不需要修改客户端程序。

aop :面向切面编程，**aop的目标就是要使交叉业务模块化**。可以采用将切面代码移动到原始方法周围，这与直接在方法中编写切面代码的运行效果是一样的。

使用代理技术正好可以解决这种问题，代理是实现AOP功能的核心和关键技术。

要为系统中的各个接口的类增加代理功能，那将需要太多的代理类，全部采用静态代理方式，将是一件非常麻烦的事情！写成百上千个代理类。

**Jvm 可以在运行期动态生成类的字节码，这种动态生成的类往往被用作代理类**，即动态代理。

jvm 生成的动态类必须实现一个或多个接口，



### 创建动态类及查看其方法列表信息



用jvM 生成动态类，一定要告诉他一个接口。

  Proxy 类，static class getProxyClass(ClassLoader loader,Class<?>... interface);  /**/在内存中创造一个类。告诉他字节码实现了哪些接口。**

  每个字节码都可以得到自己的类加载器，每个class 一定是由某个类加载器加载进来的。就好象每个人，都有个妈妈。**在内存中创建了字节码，没有加载器去加载，所以指定类加载器**。

```java
package com.zkk;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;


public class ProxyTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//类加载器通常使用接口相同的类加载器
		Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
		//打印结果 sun.proxy.$Proxy0
		System.out.println(clazzProxy1.getName());
		//得到字节码，看看这个类上有什么方法。
		System.out.println("----begin constructor list----");
		Constructor[] constructors = clazzProxy1.getConstructors();
		for(Constructor constructor : constructors){
			//System.out.println(constructor.getName());
			String name = constructor.getName();
			StringBuilder sb= new StringBuilder();
			sb.append(name);
			sb.append('(');
			Class[] clazzParams = constructor.getParameterTypes();
			for(Class clazzParam:clazzParams){
				sb.append(clazzParam.getName()).append(',');
			}
			if(clazzParams != null && clazzParams.length!=0){
				sb.deleteCharAt(sb.length()-1);
			}
		
			sb.append(')');
			System.out.println(sb);
		}
		
		System.out.println("----begin method list----");
		Method[] methods = clazzProxy1.getMethods();
		for(Method method : methods){
			//System.out.println(constructor.getName());
			String name = method.getName();
			StringBuilder sb= new StringBuilder();
			sb.append(name);
			sb.append('(');
			
			Class[] clazzParams = method.getParameterTypes();
			for(Class clazzParam:clazzParams){
				sb.append(clazzParam.getName()).append(',');
			}
			if(clazzParams != null && clazzParams.length!=0){
				sb.deleteCharAt(sb.length()-1);
			}
		
			sb.append(')');
			System.out.println(sb);
		}
	}
```

打印结果

 sun.proxy.$Proxy0

----begin constructor list----

sun.proxy.$Proxy0(java.lang.reflect.InvocationHandler)

----begin method list----

add(java.lang.Object)

remove(java.lang.Object)

equals(java.lang.Object)

toString()

hashCode()

clear()

size()

toArray()

toArray([Ljava.lang.Object;)

addAll(java.util.Collection)

iterator()

containsAll(java.util.Collection)

removeAll(java.util.Collection)

retainAll(java.util.Collection)

contains(java.lang.Object)

isEmpty()

isProxyClass(java.lang.Class)

getProxyClass(java.lang.ClassLoader,[Ljava.lang.Class;)

newProxyInstance(java.lang.ClassLoader,[Ljava.lang.Class;,java.lang.reflect.InvocationHandler)

getInvocationHandler(java.lang.Object)

wait()

wait(long,int)

wait(long)

getClass()

notify()

notifyAll()

### 创建动态类的实例对象及调用其方法

```java
package com.zkk;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;


public class ProxyTest {


	/**
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws Exception {
	//类加载器通常使用接口相同的类加载器
		Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
		//打印结果 sun.proxy.$Proxy0
		System.out.println(clazzProxy1.getName());
		//得到字节码，看看这个类上有什么方法。
		System.out.println("----begin constructor list----");
		Constructor[] constructors = clazzProxy1.getConstructors();
		for(Constructor constructor : constructors){
			//System.out.println(constructor.getName());
			String name = constructor.getName();
			StringBuilder sb= new StringBuilder();
			sb.append(name);
			sb.append('(');
			Class[] clazzParams = constructor.getParameterTypes();
			for(Class clazzParam:clazzParams){
				sb.append(clazzParam.getName()).append(',');
			}
			if(clazzParams != null && clazzParams.length!=0){
				sb.deleteCharAt(sb.length()-1);
			}
		
			sb.append(')');
			System.out.println(sb);
		}
			System.out.println("----begin method list----");
		Method[] methods = clazzProxy1.getMethods();
		for(Method method : methods){
			//System.out.println(constructor.getName());
			String name = method.getName();
			StringBuilder sb= new StringBuilder();
			sb.append(name);
			sb.append('(');
			
			Class[] clazzParams = method.getParameterTypes();
			for(Class clazzParam:clazzParams){
				sb.append(clazzParam.getName()).append(',');
			}
			if(clazzParams != null && clazzParams.length!=0){
				sb.deleteCharAt(sb.length()-1);
			}
		
			sb.append(')');
			System.out.println(sb);
		}
		System.out.println("-------begin create instance object list--------");
		//clazzProxy1.newInstance();  没有不带参数的构造方法
		Constructor constructor = clazzProxy1.getConstructor(InvocationHandler.class);
		class MyInvocationHandler1 implements InvocationHandler{
		
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return null;
			}
			
		}
		Collection proxy1 = (Collection) constructor.newInstance(new MyInvocationHandler1());
		System.out.println(proxy1.toString());//这里null   toStirng 为null
		proxy1.clear();
		proxy1.size();//这里error java.lang.NullPointerException

		
	}


}
```













