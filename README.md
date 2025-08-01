# generatevideo
## 使用heygem生成数字人短视频，口播视频，克隆人，项目分两端（两子项目）：文案管理端，和视频生产端

# 源码部分：
#### 视频生成端项目 gen_video_sample
#### 视频文案管理系统 clonedub-gen-sample
#### 数据库脚本文件 genvideosample.sql

#### 视频生成端项目部署前提：
#### 1、	安装heygem docker方式安装，具体安装见heygem官方文档，建议采用默认路径安装。
#### 2、	安装FFmpeg

# 部署要求：
## 视频生成端项目(gen_video_sample)：
使用Python开发，需要和heygem部署在同一台机子，原因：视频生产端需要操作heygem相关文件输出目录和文件。
项目配置文件是：BASE_VAR.py，看注解配置即可，注意相关目录配置，建议按配置的来建立。

导入项目到vs，后运行：python start_gen_shuziren.py
#### 最后生成的视频，在：D:\heygem_data\face2face\temp 目录下，因为这个项目是从一个完整的项目下拆分出来的，还没来得及将生成的视频转移到指定目录，完整项目见 https://www.zhiyingxiaoke.top 

## 视频文案管理系统：
使用的是jfinal框架，部署在局域网另外一台机，或者公网，需要满足的条件是：视频生成端能http访问到视频管理端，（视频生产端需要从文案管理端读取文案和下载对应的数字人视频）

配置文件是：demo-config-dev.txt
其中：有用到AI大模型，建议配置为火山的ds，调用速度比较正常，详细看配置。

# 产品演示
### 最后可以生成的效果： https://live.csdn.net/v/487232 （开源版本，少了动态的大标题和动态图片插入）

# 产品截图
<img src="https://github.com/1970168137/generatevideo/blob/main/info/A1.png" alt="产品1" title="产品1" width="400" />
<img src="https://github.com/1970168137/generatevideo/blob/main/info/A2.png" alt="产品2" title="产品2" width="700" />
<img src="https://github.com/1970168137/generatevideo/blob/main/info/A3.png" alt="产品3" title="产品3" width="700" />
<img src="https://github.com/1970168137/generatevideo/blob/main/info/A4.png" alt="产品3" title="产品4" width="800" />

### 其他：
#### 版权声明：heygem请遵守heygem官方的使用协议

