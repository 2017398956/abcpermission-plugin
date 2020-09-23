# abcpermission-plugin

[![Release Version](https://img.shields.io/badge/release-1.5.7-green.svg)](https://github.com/2017398956/abcpermission-plugin/releases)

方便 [AbcPermission](https://github.com/2017398956/AbcPermission "AbcPermission") 接入的插件

**更新日志：**

- **1.5.7**  update abcpermission
- **1.5.0**  支持 kotlin 和 java 混编

    如果在 kotlin 代码中使用了此插件，那么需要在
    apply plugin: 'kotlin-kapt' 之后添加 apply plugin: 'abcpermission.plugin' 
    顺序颠倒后将无法正常执行
- **1.4.0**  update
- **1.3.6**  支持 AndroidX

- **1.3.5**  适配 gradle 3.0+

- **1.3.4**  升级 AbcPermission 为 1.6.3 解决在 gradle4.4中 appcompat-v7 版本冲突问题：考虑到 appcompat-v7 现在是必安装的包，因此在项目中不再传递该依赖，以免造成版本冲突，使用时请自行添加 appcompat-v7 依赖。
