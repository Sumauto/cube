### 下载管理

来源：AssetsSource、UrlSource

````kotlin
AbstractSource{
	abstract fun proceed():File

  fun notifyUpdate(){

  }

  fun calculateDownloadedBytes():Long{

  }

}
````



```kotlin
[切片内容][切片尾,已下载大小]
```

#### 切片下载：

##### 切片分割算法

- PAGE_SIZE

- 切片数量=文件大小/PAGE_SIZE,最后一个切片>=PAGE_SIZE



##### 切片信息存储

- content:切片内容

- tail:切片尾，记录已下载大小

##### 切片合并

- FileChannel

##### 暂停/恢复

- 切片下载进度存储
- 切片下载进度恢复





##### 游戏表存储

- game_id
- version
- download_url
- path

##### 下载表

- game_id
- version
- url
- total_bytes
- downloaded_bytes
- enque_time



GameManager

- game_id
- version
- url



DownloadManager

- url
- state
- total_bytes
- downloaded_bytes

Task

- url

- slice_id
- start
- downloaded_bytes
- total_bytes
- state
- enque_time



ThreadPool

点击开始按钮

判断是否需要下载，




```flow
start_down=>start: 开始下载
if_need_download=>condition: 需要下载
if_no_task=>condition: 没有任务
create_task=>operation: 创建下载
if_slice=>condition: 是否切片

resume_task=>operation: 恢复下载
if_task=>condition: 有下载任务
do_task=>operation: 下载
if_range=>condition: 是否切片
normal_down=>operation: 普通下载
slice_down=>operation: 分片下载
if_down_ok=>condition: 下载成功

if_slice_down_ok=>condition: 下载成功
merge_slice=>operation: 合并分片
start_apk=>operation: 下载成功
notify_fail=>operation: 下载失败
e=>end: 结束

start_down->if_need_download
if_need_download(yes)->if_no_task
if_need_download(no)->start_apk->e
if_no_task(yes)->create_task->if_slice
if_no_task(no)->resume_task(bottom)->if_slice
if_slice(yes)->slice_down->if_down_ok
if_slice(no,left)->normal_down->if_down_ok
if_down_ok(yes)->start_apk->e
if_down_ok(no)->notify_fail->e

```

```sequence
Title:下载时序图
Client->SDK:请求下载url
SDK->WorkSpace:是否存在对应的完整文件
WorkSpace-->SDK:存在
SDK-->Client:下载成功
note over SDK: 是否正在下载该资源
SDK-->Client:忽略本次请求
note over SDK:是否存在下载任务工作区
SDK-->SDK:从工作区中恢复下载任务
note over SDK:下载进行中
SDK-->Client:下载失败


```

