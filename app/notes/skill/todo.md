[准备中，点击返回](../skill_list.md)

### 签名机制

阅读APK签名机制，提高打包速度

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





点击开始按钮

判断是否需要下载，
