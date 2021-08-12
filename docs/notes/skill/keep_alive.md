#### 文件锁保活分析

应用会启动若干个守护进程,记位daemon1、daemon2...

在Application.attachBaseContext时，保活机制开始接入

如果当前进程是daemon进程，就通过app_process32或者app_process或者app_process64创建一个进程

