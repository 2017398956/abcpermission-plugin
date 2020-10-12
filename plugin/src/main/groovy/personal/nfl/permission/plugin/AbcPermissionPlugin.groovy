package personal.nfl.permission.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class AbcPermissionPlugin implements Plugin<Project> {

    private String abcPermissionVersion = "1.7.1"
    private String aspectPluginVersion = "2.4"
    /**
     * @param project 这里的 project 对应的引入该插件的 module ，如果需要获取根目录下 build.gradle
     * 中的信息，则需要通过 project.rootProject.XXX 来获取
     */
    void apply(Project project) {
        // 这里会发生在 module 的 Configure project ：${moduleName} 阶段（编译前的配置阶段）
        // 且 对 build.gradle 的操作的环境都和代码的顺序有关，所以在获取变量时如果没注意顺序，则可能获取失败
        // 所以，sourceJDK 和 targetJDK 的获取应该放在 doFirst 中，而不应该直接获取
        if (project.plugins.getPlugin("AspectPlugin") == null){
            project.pluginManager.apply("AspectPlugin")
        }

        project.repositories.maven {
            url 'https://jitpack.io'
        }

//        project.dependencies{
//            api("com.github.2017398956:AbcPermission:1.6.8") {
//                exclude module: 'permissionAnnotation'
//                exclude module: 'permissionCompiler'
//            }
//            provided("com.github.2017398956:AbcPermission:1.6.8") {
//                exclude module: 'permissionSupport'
//                exclude module: 'permissionCompiler'
//            }
//            kapt("com.github.2017398956:AbcPermission:1.6.8") {
//                exclude module: 'permissionSupport'
//            }
//        }

        project.dependencies.add("compileOnly",
                "com.github.2017398956:AbcPermission:${abcPermissionVersion}", {
            "exclude module: 'permissionSupport'"
            "exclude module: 'permissionCompiler'"
        })

        // 默认使用 java 的注解方式
        String annotationMethod = "annotationProcessor"
        if (project.plugins.findPlugin("kotlin-kapt") != null) {
            // 支持 kotlin
            // 如果在 kotlin 代码中使用了注解的方式生成代码，那么需要在
            // apply plugin: 'kotlin-kapt' 之后添加 apply plugin: 'abcpermission.plugin'
            // 顺序颠倒后将无法正常运行
            annotationMethod = "kapt"
        } else {
            // 使用默认的 annotationProcessor
            project.rootProject.buildscript.configurations.each {
                it.dependencies.each {
                    // 如果引入了 kotlin ，这里自动为用户添加 kotlin 支持
                    if ("org.jetbrains.kotlin".equals(it.group) && "kotlin-gradle-plugin".equals(it.name)) {
                        if (project.plugins.findPlugin("kotlin-android") == null) {
                            project.pluginManager.apply("kotlin-android")
                        }
                        project.pluginManager.apply("kotlin-kapt")
                        annotationMethod = "kapt"
                    }
                }
            }
        }
        project.dependencies.add(annotationMethod,
                "com.github.2017398956:AbcPermission:${abcPermissionVersion}", {
            "exclude module: 'permissionSupport'"
            "exclude module: 'permissionCompiler'"
        })
        project.dependencies.add("implementation",
                "com.github.2017398956:AbcPermission:${abcPermissionVersion}", {
            "exclude module: 'permissionAnnotation'"
            "exclude module: 'permissionCompiler'"
        })
    }
}