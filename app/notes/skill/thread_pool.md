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