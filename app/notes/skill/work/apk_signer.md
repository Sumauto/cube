### 签名机制

阅读APK签名机制，提高打包速度

S:项目里productFlavor太多，每个Flavor都要编译dex和资源，导致整体打包速度太慢

T:提高打包速度

A:查看Apk签名文档，阅读签名工具源码，向V2 签名的Sign Block插入productFlavor类型

R:实现通过一个母包快速生成其他productFlavor



V2: APK是一个Zip格式的文件，中间有一段签名块，id，value按键，每一个Key value存放的信息：区间长度(8)，

末尾的Pair存放的是block padding 全0

0x7109871a v2签名id

| id         | 说明             |
| ---------- | ---------------- |
| 0x7109871a | v2签名id         |
| 0xf05368c0 | v3签名id         |
| 0x42726577 | Padding block id |

