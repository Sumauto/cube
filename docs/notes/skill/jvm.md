
### JVM规范

[官方文档](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7)

#### class文件格式

```
ClassFile {
    u4             magic;               //魔数，固定值0xCAFEBABE
    u2             minor_version;       //次版本号
    u2             major_version;       //主版本号
    u2             constant_pool_count; //常量的个数
    cp_info        constant_pool[constant_pool_count-1];  //具体的常量池内容
    u2             access_flags;        //访问标识
    u2             this_class;          //当前类索引
    u2             super_class;         //父类索引
    u2             interfaces_count;    //接口的个数
    u2             interfaces[interfaces_count];          //具体的接口内容
    u2             fields_count;        //字段的个数
    field_info     fields[fields_count];                  //具体的字段内容
    u2             methods_count;       //方法的个数
    method_info    methods[methods_count];                //具体的方法内容
    u2             attributes_count;    //属性的个数
    attribute_info attributes[attributes_count];          //具体的属性内容
}
```

- javap -v Demo.class
- 小端模式、大端模式

| 内存地址 | 06   |   07 |
| :------: | ---- | ---: |
| 字节数据 | 00   |   34 |

​	大端：0034 小端：3400

- 无符号数

  - u1,u2,u4 代表1 、2、4个字节的无符号数

  - 描述数字、索引饮用、数量值或者按照UTF-8编码构成的字符串

    

- 表



#### JDK,JRE,JVM的关系

JDK包含JRE和一些工具，JRE包含一些Java API和JVM，JVM是运行class文件的地方



#### 类加载机制

[GitYuan 博客](http://gityuan.com/2015/10/25/jvm-class-loading/)

- 加载(Loading)

- 链接(Linking)

  - 验证

    - 文件格式验证
    - 元数据验证（是否符合java语法规范）
    - 字节码验证
    - 符号引用验证

  - 准备

    - static变量分配内存，设置初始值

  - 解析

    将符号引用替换成直接引用

    - 符号引用
    - 直接引用

- 初始化

  - clinit(类的构造方法)

  - 初始化时机

    

- 使用

  - 可以new一个对象出来了



#### JVM框架

- 方法区
  - 常量池
- 堆
  - 存放对象(新生代、老生代)
- 线程私有
  - 程序计数器、方法栈、Native方法栈

- GC机制

JVM堆、元数据



#### 引用

- WeakReference

  与ReferenceQueue关联后，当对象没有被引用时，就会进入ReferenceQueue



- SoftReference

  与ReferenceQueue关联后，当系统内存不足时，就会进入ReferenceQueue