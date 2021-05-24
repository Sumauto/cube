1.在手机上启动shell进程

adb push remote_shell.dex /data/local/tmp

adb shell
cd /data/local/tmp

app_process -Djava.class.path=/data/local/tmp/remote_shell.dex /system/bin com.sumauto.remote.Main

2.安装app-debug.apk
    点击app内"循环输出ABC"按钮
    打开手机上任意app，让输入框获取焦点，输入框会自动输入abc


