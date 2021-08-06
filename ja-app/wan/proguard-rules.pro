# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##############################################
##################  混淆基本配置信息  ###########
##############################################

# 代码混淆压缩比,在0-7之间,默认为5,一般不需要改
-optimizationpasses 5
# 混淆时不使用大小写混合,混淆后的类名为小写
-dontusemixedcaseclassnames
# 不跳过library中的非public的类
# 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 不进行优化，建议使用此选项，因为根据proguard-android-optimize.txt中的描述，
# 优化可能会造成一些潜在风险，不能保证在所有版本的Dalvik上都正常运行
# -dontoptimize
# 不做预校验,preverify是proguard的4个步骤之一
# Android不需要preverify,去掉这一步可加快混淆速度
-dontpreverify
# 指定混淆是采用的算法,后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法,一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护代码中的Annotation不被混淆
# 这在JSON实体映射时非常重要,比如fastJson,Gson
-keepattributes *Annotation*
# 避免混淆泛型
# 这在JSO实体映射时非常重要,比如fastJson,Gson
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses

##############################################
##################  是否记录日志文件  ###########
##############################################

# 混淆后就会生成映射文件
# 打印混淆的详细信息
-verbose
# apk 包内所有 class 的内部结构
# -dump class_files.txt
# 未混淆的类和成员
# -printseeds seeds.txt
# 列出从 apk 中删除的代码
-printusage unused.txt
# 混淆前后的映射
# 包含有类名 -> 混淆后类名的映射关系
#-printmapping proguard_mapping.txt
#为了保留所需的信息，以便 Crashlytics 生成人能阅读的崩溃报告，请在您的 Proguard 或 Dexguard 配置文件中添加以下行：
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
#要让 Crashlytics 自动上传 ProGuard 或 DexGuard 映射文件，请从配置文件中移除以下行（若存在）：
#-printmapping mapping.txt
##############################################
##################  需要保留的信息  #############
##############################################

# 保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保留了继承自Activity,Application这些类的子类
# 因为这些子类都有可能被外部调用
# 比如说,第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class * extends adnroid.app.Fragment
# AndroidX 防止混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep class androidx.core.app.**
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# 保留在Activity中的方法参数时view的方法
# 从而我们在layout里面编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保留自定义控件(继承自View)不被混淆
-keep public class * extends android.view.View {
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable{
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable {
  static ** CREATOR;
}
# 保留Serializable序列化的类不被混淆
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 对于R(资源)下的所有类及其方法,都不能被混淆
-keep class **.R$* { *; }
# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

##############################################
##################  项目代码  #############
##############################################
# 保留所有IAppLife不被混淆
-keep public class * extends com.jay.base_lib.app.appdelegate.IAppLife
# 保留实体类和成员不被混淆
-keep class com.jay.base_component.network.bean.** {
    *;
}


#############################################＃
##################  arouter   ################
##############################################
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider
# If @Autowired is used for injection in non-Activity classes, add the following rules to prevent injection failures
# -keepnames class * {
#    @com.alibaba.android.arouter.facade.annotation.Autowired <fields>;
#}

#OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#OkHttp logging-interceptor
-dontwarn okhttp3.logging.**
-keep class okhttp3.logging.** { *;}

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

##############################################
##################  Glide  ###################
##############################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

##############################################
##################  EventBus  ################
##############################################
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


# Gson混淆
-dontwarn com.google.gson.**
-keep class com.google.gson.** {*;}
-keep class sun.misc.Unsafe { *; }

# Retrofit 2.x
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# rxjava 1.x
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

##############################################
##################  Butter Knife  ############
##############################################
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }