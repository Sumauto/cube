### adb shell am命令

am命令的实现再Am.java中，基本上都是调用了ActivityManagerService的API

```shell
adb shell 

#拨打电话
am start -a android.intent.action.CALL -d tel:10086

#打开链接
am start -a android.intent.action.VIEW -d https://www.baidu.com

#打开Activity,startActivityAsUser
am start -n com.sumauto/.MainActivity -e key value

#打开Service
am startservice com.sumauto/.MyService

#关闭Service
am stopservice com.sumauto/.MyService

#发送广播,broadcastIntent
am broadcast 

#杀指定后台进程,killBackgroundProcesses
am kill com.sumauto

#杀所有后台进程,killAllBackgroundProcesses
am kill-all 

#强杀进程,forceStopPackage
am force-stop com.sumauto

#系统卡住	,hang
am hang

#重启	
am restart

#创建bugreport,requestBugReport
am bug-report

#进程pid的堆信息输出到file	
am dumpheap <pid> <file>

#收紧进程的内存,setProcessMemoryTrimLevel,HIDDEN、RUNNING_MODERATE、BACKGROUND、RUNNING_LOW、MODERATE、RUNNING_CRITICAL、COMPLETE
am send-trim-memory <pid> <level>

#监控，MyActivityController.run
am monitor

```





#### `am start [options] <intent>`

- [options]，存在-W参数调用startActivityAndWait，否则调用startActivityAsUser
  - -D 允许调试
  - -W 等待app启动完成
  - -R <COUNT>  重复启动Activity
  - -S 启动之前先forceStopPackage，强制停止App
  - -opengl-trace 运行获取OpenGL函数的trace
  - -user <USERID> | current 指定用户来运行App，默认为当前用户
  - -start-profiler <FILE> 启动profiler，并将结果发送到<FILE>
  - -P <FILE> 同-start-profiler,不同的是app进入IDLE状态，则停止profiler
  - -sampling <INTERVAL> 设置profiler取样间隔，单位ms
- <intent>
  - 基本参数
    - **-a <Action>** 	*Intent.setAction()*
    - **-n <Component>**  *Intent.setComponent(),eg:com.sumauto/.MainActivity*
    - **-d <data>**   *Intent.setData()*
    - **-t <MIME Type>**  *Intent.setType()*
    - **-c <category>**   *Intent.addCategory()*
    - **-p <pacakge>**  *Intent.setPackage()*
    - **-f <flags>**  *Intent.setFlags()*
  - Extra参数,[al]a代表数组，al代表ArrayList;eg: -eia nums 1,2,3,4
    - **-e/-es[al]**   *String*
    
    - **-esn**   *(String)null*
    
    - **-ez**    *boolean*
    
    - **-ei[al]**    *int*
    
    - **-el[al]**    *long*
    
    - **-ef[al]**    *float*
    
    - **-eu**    *Uri*
    
    - **-ecn**     *componet*
    
  - Flags eg:am broadcast -a broadcast.demo --exclude-stopped-packages
    
      - --grant-read-uri-permission
      - --grant-write-uri-permission
      - --grant-persistable-uri-permission
      - --grant-prefix-uri-permission
      - --debug-log-resolution
      - --exclude-stopped-packages
      - --include-stopped-packages
      - --activity-brought-to-front
      - --activity-clear-top
      - --activity-clear-when-task-reset
      - --activity-exclude-from-recents
      - --activity-launched-from-history
      - --activity-multiple-task
      - --activity-no-animation
      - --activity-no-history
      - --activity-no-user-action
      - --activity-previous-is-to
      - --activity-reorder-to-front
      - --activity-reset-task-if-needed
      - --activity-single-top
      - --activity-clear-task
      - --activity-task-on-home
      - --receiver-registered-only
      - --receiver-replace-pending















