源码

```c++
#include <iostream>
using namespace std;
 
template<class T>
class auto_ptr {
public:
	explicit auto_ptr(T *p = 0): pointee(p) {}
    auto_ptr(auto_ptr<T>& rhs): pointee(rhs.release()) {}
	~auto_ptr() { delete pointee; }
	auto_ptr<T>& operator=(auto_ptr<T>& rhs)
	{
		if (this != &rhs) reset(rhs.release());
		return *this;
	}
	T& operator*() const { return *pointee; }
	T* operator->() const { return pointee; }
	T* get() const { return pointee; }
	T* release()
	{
		T *oldPointee = pointee;
		pointee = 0;
		return oldPointee;
	}
	void reset(T *p = 0)
	{
		if (pointee != p) {
		       delete pointee;
		       pointee = p;
	        }
        }
private:
	T *pointee;
};
 
```



   下面分析一下，参考《C++ Primer》。

（1）auto_ptr的构造函数带explicit 关键字，必须使用初始化的直接形式来创建auto_ptr对象

  auto_ptr<int> ap(new int(1024));  //ok   

  auto_ptr<int> ap=new int(1024);   //error

（2）auto_ptr 在析构函数中释放了动态分配的空间，因此能自动释放内存。下面函数只动态分配了内存，并没有显示释放。但是编译器保证在展开栈越过f之前运行pi的析构函数。

 void f() { auto_ptr<int> ap(new int(1024)); } 

 （3）auto_ptr重载了解引用操作符和箭头操作符，支持了普通指针的行为。

（4）赋值时删除了左操作数指向的对象

auto_ptr<int> ap1(new int(1024));  
      auto_ptr<int> ap2;

ap2=ap1;

 将ap1赋值给ap2后，删除了ap2原来指的对象；ap2置为指向ap1所指的对象；ap1为未绑定对象。可看代码。

 （5）测试auto_ptr对象

   可以调用get成员函数，该函数返回包含在auto_ptr对象中的基础指针

 if(ap.get())  *ap=512;   //ok      

  if(ap) *ap=512;             //error    

​     

 尽管auto_ptr类模板为处理动态分配的内存提供了安全性和便利性的尺度，但是也存在不少缺陷，接下来结合例子给出auto_ptr的一些缺陷。

 （1**）不要使用auto_ptr对象保存指向静态分配对象的指针**。否则，当auto_ptr对象本身被撤销时，它将试图删除指向非动态分配对象的指针，导致未定义的行为。

 int a=1;

   auto_ptr<int> ap(&a);  //编译没有问题，会导致未定义行为

（2）**不要使两个auto_ptr对象指向同一对象。**

   auto_ptr<int> ap1(new int (1024));

   auto_ptr<int> ap2(ap1.get());

（3）**不要使用auto_ptr对象保存指向动态分配数组的指针**。从源代码中可以看出，它用的是delete操作符，而不是delete [ ] 操作符

  （4）**不要将auto_ptr对象存储在容器中**。因为auto_ptr的复制和赋值具有破坏性。不满足容器要求：复制或赋值后，两个对象必须具有相同值。

     
    
      
    
      
    
      

