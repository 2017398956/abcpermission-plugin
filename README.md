# abcpermission-plugin

[![Release Version](https://img.shields.io/badge/release-1.3.5-green.svg)](https://github.com/2017398956/abcpermission-plugin/releases)

方便 [AbcPermission](https://github.com/2017398956/AbcPermission "AbcPermission") 接入的插件

**更新日志：**

- **1.3.5**  适配 gradle 3.0+

- **1.3.4**  升级 AbcPermission 为 1.6.3 解决在 gradle4.4中 appcompat-v7 版本冲突问题：考虑到 appcompat-v7 现在是必安装的包，因此在项目中不再传递该依赖，以免造成版本冲突，使用时请自行添加 appcompat-v7 依赖。
