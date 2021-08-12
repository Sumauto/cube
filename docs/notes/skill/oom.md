

#### 内存泄漏的原因

在回收对象时，对象和GC Root之间还存在引用链



#### Android常见内存泄漏

- ##### Handler

当Actiity或者Fragment进入destroy周期时，handler的消息队列中还有未处理的Message，并且Handler还持有Activity或者Fragment的引用，存在引用链：Looper->MessageQueue->Message->Handler->Activity

- ##### ThreadLocal

调用ThreadLocal.set之后，只要Thread还活着，就会存在引用链：Thread->ThreadLocalMap->ThreadLocal->Item

- ##### Thread

异步操作，通过回调引用Acitivity

- ##### 静态变量引用Activity

静态变量是GC Root



#### 内存优化技巧

- ###### Message.obatain 复用Message

  Message.recycleUnchecked() 会把当前消息放到缓存池，缓存池是个链表结构，头指针sPool指向最新的一条缓存Message


- ###### ThreadLocal的set和Remove方法成对调用

- ###### 使用LiveData，代替异步回调


 
​	
