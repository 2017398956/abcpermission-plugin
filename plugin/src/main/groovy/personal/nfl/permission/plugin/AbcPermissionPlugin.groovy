package personal.nfl.permission.plugin

import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class AbcPermissionPlugin implements Plugin<Project> {
    void apply(Project project) {

        project.repositories.maven {
            url 'https://jitpack.io'
        }

        project.dependencies {
            // implementation ('com.github.2017398956:AbcPermission:permissionSupport:1.6.5')
            implementation("com.github.2017398956:AbcPermission:1.6.7") {
                exclude module: 'permissionAnnotation'
                exclude module: 'permissionCompiler'
//                exclude module: 'app'
            }
            // compileOnly ('com.github.2017398956:AbcPermission:permissionAnnotation:1.6.5')
            compileOnly("com.github.2017398956:AbcPermission:1.6.7") {
                exclude module: 'permissionSupport'
                exclude module: 'permissionCompiler'
//                exclude module: 'app'
            }
            // annotationProcessor ('com.github.2017398956:AbcPermission:permissionCompiler:1.6.5')
            annotationProcessor("com.github.2017398956:AbcPermission:1.6.7") {
                exclude module: 'permissionSupport'
//                exclude module: 'app'
            }
        }

        if (project.hasProperty('android') && project.android != null) {
            if (project.android.hasProperty('applicationVariants')
                    && project.android.applicationVariants != null) {
                project.android.applicationVariants.all { variant ->
                    doLast(variant.getJavaCompileProvider().get())
                }
            }
            if (project.android.hasProperty('libraryVariants')
                    && project.android.libraryVariants != null) {
                project.android.libraryVariants.all { variant ->
                    doLast(variant.getJavaCompileProvider().get())
                }
            }
        }
    }

    private void doLast(Task javaCompile) {
        javaCompile.doLast {
            String[] args = ["-showWeaveInfo",
                             "-1.5",
                             "-inpath", javaCompile.destinationDir.toString(),
                             "-aspectpath", javaCompile.classpath.asPath,
                             "-d", javaCompile.destinationDir.toString(),
                             "-classpath", javaCompile.classpath.asPath,
                             "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
            MessageHandler handler = new MessageHandler(true)
            new Main().run(args, handler)

            def log = project.logger
            for (IMessage message : handler.getMessages(null, true)) {
                switch (message.getKind()) {
                    case IMessage.ABORT:
                    case IMessage.ERROR:
                    case IMessage.FAIL:
                        log.error message.message, message.thrown
                        break
                    case IMessage.WARNING:
                    case IMessage.INFO:
                        log.info message.message, message.thrown
                        break
                    case IMessage.DEBUG:
                        log.debug message.message, message.thrown
                        break
                }
            }
        }
    }
}