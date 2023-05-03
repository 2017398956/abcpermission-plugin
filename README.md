# abcpermission-plugin

[![Release Version](https://img.shields.io/badge/release-1.7.2_1-green.svg)](https://github.com/2017398956/abcpermission-plugin/releases)

方便 [AbcPermission](https://github.com/2017398956/AbcPermission "AbcPermission") 接入的插件

## 更新日志 ##
- **1.7.2_1**  支持AGP8.0
- **1.7.2**  支持AGP7.0.2
- **1.7.1_6**  解决 Linux 和 mac 编译出错的问题
- **1.7.1_5**  接入方式更新
- **1.5.7**  update abcpermission
- **1.5.0**  支持 kotlin 和 java 混编

    如果在 kotlin 代码中使用了此插件，那么需要在
    apply plugin: 'kotlin-kapt' 之后添加 apply plugin: 'abcpermission.plugin' 
    顺序颠倒后将无法正常执行
- **1.4.0**  update
- **1.3.6**  支持 AndroidX

- **1.3.5**  适配 gradle 3.0+

- **1.3.4**  升级 AbcPermission 为 1.6.3 解决在 gradle4.4中 appcompat-v7 版本冲突问题：考虑到 appcompat-v7 现在是必安装的包，因此在项目中不再传递该依赖，以免造成版本冲突，使用时请自行添加 appcompat-v7 依赖。

## 使用说明 ##

在根目录的 build 中加入如下代码

    buildscript {
        repositories {
           ...
           maven { url 'https://jitpack.io' }
        }
        dependencies {
            ...
            classpath "com.github.2017398956:abcpermission-plugin:1.7.2_1"
        }
    }
    
在需要的 module 中添加如下代码
    
    // 如果在 kotlin 代码中使用了此插件，那么需要在
    // apply plugin: 'kotlin-kapt' 之后添加 
    apply plugin: 'abcpermission.plugin'
    
    dependencies { api 'org.aspectj:aspectjrt:1.9.6'}
    
在使用前记得添加 

    // 建议在 application 添加
    AbcPermission.install(context)
    
注解建议使用（其它的是老版本）

    @GetPermissions4AndroidX

插件中提供了一个默认的弹窗（这个弹窗是全局的，只需要设置一次），如果需要修改可以设置 GetPermissionListener （需要在权限申请之前）

    AbcPermission.permissionListener = new AbcPermission.GetPermissionListener(){
            /**
             * 当用户不给权限且选择了不再提示后，会执行这个方法，比如打开 设置 界面
             *
             * @param activity 
             * @param permissions 用户拒绝授予的权限
             */
                @Override
                public void cannotRequestAgain(Activity activity, String[] permissions) {
                
            }

		/**
         * 为了程序不崩溃，被注解的方法在这里抛出异常,需要你自行处理
         *
         * @param throwable
         */

            @Override
            public void exeException(Throwable throwable) {
                
            }
        } ;
        
## 使用示例 ##

配置好环境后只需要在方法前添加一个注解即可

java 示例：

    @GetPermissions4AndroidX(Manifest.permission.READ_CONTACTS)
    private void test() {
        Toast.makeText(this, "测试成功！！", Toast.LENGTH_SHORT).show();
    }
    
kotlin 示例：

    @GetPermissions4AndroidX(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.CALL_PHONE
    )
    private void test() {
        Toast.makeText(this, "测试成功！！", Toast.LENGTH_SHORT).show();
    }