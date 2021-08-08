### **静态代理、动态代理**
+ 代理分为静态代理和动态代理两种。
+ 静态代理，代理类需要自己编写代码写成。
+ 动态代理，代理类通过 Proxy.newInstance() 方法生成。
+ 不管是静态代理还是动态代理，代理与被代理者都要实现两样接口，它们的实质是面向接口编程。
+ 静态代理和动态代理的区别是在于要不要开发者自己定义 Proxy 类。
+ 动态代理通过 Proxy 动态生成 proxy class，但是它也指定了一个 InvocationHandler 的实现类。
+ 代理模式本质上的目的是为了增强现有代码的功能。

[代理模式](https://www.cnblogs.com/cC-Zhou/p/9525638.html)



Android 中的设计模式

##### 享元模式

Message.obtain(), ViewHolder ,Resource.getDrawable()



##### 责任链模式

View事件分发，OkHttp拦截器



##### 工厂模式

BitmapFacory



##### 构建模式（Builder）

AlertDialog.Builder

##### 单例模式



##### 装饰模式

RecyclerView.ItemDecor



##### 原型模式

Shape子类的各种clone



##### 适配者模式



桥接模式



组合模式

View树状结构
