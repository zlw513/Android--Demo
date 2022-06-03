# Android--Demo

2022安卓最新 基于CC框架 完整的组件化开发示例。

使用到jetpack架构组件，MVVM设计思路，携程，探索了hilt在组件化中使用方式。

组件单独运行时，支持跨组件调用。 

注：跨组件调用需要打开 关联启动权限，应用自启动权限，否则失效。且需要在manifest中配置需要访问的包名。 详见代码module_common的manifest

项目结构：

component_explore  发现组件  （可单独运行）
component_login 登录组件  （可单独运行）
component_mine 我的组件  （可单独运行）
lib_data  存放实体类的  每个component都对应其下一个文件夹，只允许对应模块负责人动对应文件夹下代码
module_base 基础模块  业务无关
mudule_common  项目基础模块  与业务有相关

如果你想用CC做组件化项目开发，而且想用mvvm jetpack架构组件 携程等比较新潮的开发技术，那么这个项目就是很好的示例， 截至目前，错过这个村,还真没别的店了。
--2022/06/03

![1图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-11-53-01-368_com.zhlw.zhlwc.jpg)
![2图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-11-53-07-968_com.zhlw.zhlwc.jpg)
![3图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-11-53-12-071_com.zhlw.zhlwc.jpg)
![4图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-11-53-19-510_com.zhlw.zhlwc.jpg)
![5图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-11-53-45-135_com.zhlw.zhlwc.jpg)
![6图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-11-53-30-809_com.zhlw.zhlwc.jpg)
![7图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-12-36-59-917_com.zhlw.zhlwc.jpg)
![8图片](https://github.com/zlw513/Android--Demo/blob/main/images/Screenshot_2022-05-27-12-37-22-201_com.zhlw.zhlwc.jpg)
