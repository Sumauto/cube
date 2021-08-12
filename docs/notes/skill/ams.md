
[链接](https://segmentfault.com/a/1190000022672032)

[链接](https://wiki.jikexueyuan.com/project/deep-android-v2/activity.html)



##### 系统启动流程

1. Android开机之后，通过app_process或者app_process64命令；

   - 命令目录：frameworks/base/cmds/app_process/app_main.cpp

   - --zygote 创建zygote进程，调用com.android.internal.os.ZygoteInit.main

   -  没有--zygote参数，调用com.android.internal.os.RuntimeInit.main

     

1. ZygoteInit启动之后
   1. 调用forkSystemServer，启动system_server进程

   1. com.android.server.SystemServer.main执行

      - 创建MainLooper，创建各种系统服务AMS等

1. RuntimeInit启动之后

   

ZygoteInit



- SystemServer
- AMS
- ActivityThread
- Binder
[链接](https://juejin.cn/post/6844903764986462221)