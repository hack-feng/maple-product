## 模块简介

本模块主要介绍了如何进行进行文件上传，通过简单的实例演示了上传文件到本地、阿里云OSS、华为云OBS、七牛云等。

## 目录简介

* bean 实体类
* config 存放的配置
* controller 模拟上传文件调用接口
* properties 上传文件密钥等信息配置
* util 封装工具类

## 上传案例

xxx 代表前缀缀

例如：xxxController

其对应的完整的一套案例包括

~~~
AliOssUtil.java
AliOssProperties.java
AliOssController.java
~~~

### 上传到本地

LocalFile xxx

包括文件上传、批量上传、删除、下载

### 上传到阿里OSS

AliOss xxx

### 上传到华为云OBS

HwyObs xxx

### 上传到七牛云

QiNiuYun xxx

### 上传到又拍云

UpyOss xxx


