### 1. 线程池

- 关键参数  
   - corePoolSize 当线程总数超过核心线程数时，任何线程在任务结束后，在keepAliveTime时长之内没有新任务，就会退出
   - maximumPoolSize 最大线程数
   - keepAliveTime 线程闲置时长（非核心线程 或者 allowCoreThreadTimeOut=true）
   - unit 时间单位
   - workQueue 缓冲队列
   - threadFactory
   - rejectHandler

- ctl int 前3位存放 线程池状态 后29位记录任务数量

- AtomicInteger
    - CAS

- 缺点
    - FixedThreadPool,SingleThread 允许请求队列长度为Integer.MAX_VALUE，导致堆积请求，造成OOM

    - CachedThreadPoll,ScheduledThreadPool 允许创建线程数量为Integer.MAX_VALUE，创建大量线程，导致OOM

- ReentrantLock 
    - 与synchronized区别
        - ReentrantLock可响应中断
        - ReentrantLock 需要成对调用
        - ReentrantLock 可以实现公平锁
        - ReentrantLock 实现超时
        - ReentrantLock tryLock
        
    - 公平锁

### WebSocket

[与Http的区别](https://www.html.cn/qa/other/19549.html)
[链接](http://www.ruanyifeng.com/blog/2017/05/websocket.html)
[链接](https://blog.csdn.net/LL845876425/article/details/106393358)

- 心跳
  协议本身心跳，只能保证到对方网关是通的，并不能保证数据包真的被处理了，所以还要加一个业务层面的心跳和确认消息


### 跨进程
Binder、管道、消息队列、socket、共享内存、信号量、信号

UID:应用程序身份标示 
PID:进程ID

[知乎](https://www.zhihu.com/question/39440766/answer/89210950)

- Binder

[Binder系列](http://gityuan.com/2015/10/31/binder-prepare/)
 
 1. Handler,Looper,MessageQueue,Message,ThreadLocal,IntentService,HandlerThread  

 2. OKHttp源码

---

### App启动流程
[链接](https://segmentfault.com/a/1190000022672032)

[链接](https://wiki.jikexueyuan.com/project/deep-android-v2/activity.html)
- SystemServer
- AMS
- ActivityThread
- Binder
[链接](https://juejin.cn/post/6844903764986462221)

### Launch Mode
---

### MultiDex,动态加载
---

### 设计模式 
---

### 类加载机制
---

### **静态代理、动态代理**
+ 代理分为静态代理和动态代理两种。
+ 静态代理，代理类需要自己编写代码写成。
+ 动态代理，代理类通过 Proxy.newInstance() 方法生成。
+ 不管是静态代理还是动态代理，代理与被代理者都要实现两样接口，它们的实质是面向接口编程。
+ 静态代理和动态代理的区别是在于要不要开发者自己定义 Proxy 类。
+ 动态代理通过 Proxy 动态生成 proxy class，但是它也指定了一个 InvocationHandler 的实现类。
+ 代理模式本质上的目的是为了增强现有代码的功能。

[链接](https://www.cnblogs.com/cC-Zhou/p/9525638.html)

---

### 自定义View


### SystemServer

### **RxJava**

just:将同种数据源组合放到被观察者上面

from:将类似数组、集合的数据源放到被观察者上面

map:将一种数据源，转化成另外一种

flatmap:将一种数据源，转化成另外一种数据，并且被转化的数据是乱序排列的

concatmap:将一种数据源，转化成另外一种数据，并且被转化的数据是按照先前的数据源顺序排序的

toList:将数组的形式转化成List集合

subscribeOn:设置Observable的call方法所在的线程，也就是数据来源的线程

observeOn:设置subscribe的call方法所在的线程，也就是数据处理的线程

filter:在被观察者的数据层过滤数据

onErrorResumeNext:出错的时候，可以指定出错的时候的被观察者

retryWhen:出错的时候，重新走一遍被订阅的过程

concat:合并相同类型的被观察者到一个被观察者身上，有点类似集合、数组拼接数据。

zip:处理多种不同结果集的数据发射，一般用得多的地方是多个网络请求组合然后统一处理业务逻辑。 还有很多操作符就自己去看，这些操作符已经够面试用的了。


### 内存优化 OOM


### gradle 打包流程


### AsyncTask源码




### 位运算
左移(补0)<< 
右移(正数补0 负数补1) >> 
无符号右移(补0)>>>
java int与c不一样，不受硬件和操作系统影响
int 32位，左右移位数大于等于32位操作时，会先求余（%）后再进行移动操作。
long 64位，左右移位数大于等于64位操作时，会先求余（%）后再进行移动操作。
