封装一套JavaFx管理端框架。

本套代码是为了学习和总结。

采用java11和javafx11。

    java11是LTS版，项目迟早是要升级的，所以在学习阶段我目前采用的是java11。
    javafx11需要另行下载。下载地址https://openjfx.cn/openjfx-docs/

javafx单独移植出来之后，更加强大，但是资料就更加的少，各种博客都是基于1.8进行说明的。

学习参考网站


1、https://openjfx.cn/

2、http://www.javafxchina.net/blog/docs/


1、首页Dashboard页面是移植https://github.com/HanSolo/tilesfx

   内容很多地方是学习整理的xJavaFxTool

2、右键左上角的logo，会有弹出菜单。
    具体实现参考 MainController-->initView()
    
3、点击菜单的汉堡包图标，可以折叠左侧菜单列表

4、左侧菜单列表是通过json配置，在resource/router/router.json
    可以按照自己的实际路径进行配置，每一项对应着ListMenuItem。可自己扩展。
    （建议主页这一项保留着）
    
    {
      "path": "/com/vk/fxml",
      "children": [
        {
          "name": "主      页",
          "hidden": false,
          "icon": null,
          "children": null
        },
        {
          "name": "测试",
          "hidden": false,
          "icon": null,
          "children": [
            {
              "name": "子项目"
            },
            {
              "name": "子项目2"
            }
          ]
        }
      ]
    }
    
各种控件可以学习  jfoenix

开发工具包是直接使用org.apache.commons系列

开发工具包 hutool  https://hutool.cn/docs/#/
 
缓存是封装的ehcache。具体类是CacheUtil   

json的封装GsonUtil

本项目还封装RxJava2和retrofit2的网络请求

RxJava思想远不止网络请求，建议好好学习一下RxJava思想



正在准备把Sqlite整合进来