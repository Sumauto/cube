1. #### 缓存机制(CacheInterceptor)

   ##### 调用方式

   ```kotlin
   val request = Request.Builder().url(url)
       .cacheControl(CacheControl.FORCE_NETWORK)//FORCE_NETWORK、FORCE_CACHE
       .build()
   ```

   ##### 请求头 cache-control

   |      字段      |                             说明                             |
   | :------------: | :----------------------------------------------------------: |
   |    no-cache    |        不要读取缓存中的文件，要求向WEB服务器重新请求         |
   |    no-store    |                    请求和响应都禁止被缓存                    |
   |   max-age=0    | 表示当访问此网页后的max-age秒内再次访问不会去服务器请求，其功能与Expires类似，只是Expires是根据某个特定日期值做比较。一但缓存者自身的时间不准确.则结果可能就是错误的，而max-age,显然无此问题.。Max-age的优先级也是高于Expires的 |
   |  max-stale=0   |        允许读取过期时间必须小于max-stale 值的缓存对象        |
   |  min-fresh=0   | 接受其max-age生命期大于其当前时间 跟 min-fresh 值之和的缓存对象 |
   | only-if-cached | 告知缓存者,我希望内容来自缓存，我并不关心被缓存响应,是否是新鲜的 |
   |  no-transform  |        告知代理,不要更改媒体类型,比如jpg,被你改成png.        |

   ##### 响应头

   | 字段             |                             说明                             |
   | ---------------- | :----------------------------------------------------------: |
   | proxy-revalidate | 与Must-revalidate类似，区别在于：proxy-revalidate要排除掉用户代理的缓存的。即其规则并不应用于用户代理的本地缓存上 |
   | public           | 数据内容皆被储存起来，就连有密码保护的网页也储存，安全性很低 |
   | private          | 数据内容只能被储存到私有的cache，仅对某个用户有效，不能共享  |
   | no-cache         | 可以缓存，但是只有在跟WEB服务器验证了其有效后，才能返回给客户端 |
   | no-store         |                    请求和响应都禁止被缓存                    |
   | max-age          |                  本响应包含的对象的过期时间                  |
   | must-revalidate  | 如果缓存过期了，会再次和原来的服务器确定是否为最新数据，而不是和中间的proxy |
   | max-stale        |        允许读取过期时间必须小于max-stale 值的缓存对象        |
   | s-maxage         | 与max-age的唯一区别是,s-maxage仅仅应用于共享缓存.而不应用于用户代理的本地缓存等针对单用户的缓存. 另外,s-maxage的优先级要高于max-age. |
   | no-transform     |        告知代理,不要更改媒体类型,比如jpg,被你改成png.        |


2. #### 线程池(TaskRunner)

3. 建立连接

   创建Socket，如果需要代理，则尝试创建隧道。隧道的创建过程有个重试机制，如果隧道创建失败，则请求失败。

4. 
