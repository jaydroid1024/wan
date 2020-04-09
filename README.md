

参考文章：[用生命周期规范组件化流程](https://juejin.im/post/5c82971f5188257e5e298ebe)

Demo地址：[WanDroid组件化测试项目](https://github.com/Jay-Droid/WanDroid)

![Demo项目结构](https://github.com/Jay-Droid/WanDroid/blob/master/resource/project_struct.png)



# 1. 组件划分

### 测试Demo组件化架构图

![Demo Architecture](https://github.com/Jay-Droid/WanDroid/blob/master/resource/component_%20architecture_wandroid.png)

### 各个层次详细说明
#### 1.宿主壳和调试壳\app

壳工程依赖了需要集成的业务组件，它可能只有一些配置文件，没有任何代码逻辑。
根据你的需要选择集成你的业务组件，不同的业务组件就组成了不同的APP。


#### 2.常规业务组件\biz_components

如果添加业务组件时需要一些基础资源和数据就需要考虑是否是多个项目会使用它，根据是否是多项目使用以及有效隔离基础资源和数据，将该层次组件分为单项目业务组件和多项目共享业务组件：

- 单项目业务组件只能依赖单项目基础组件
- 多项目业务组件只能依赖公共基础业务组件

常规业务组件按需依赖基础业务组件和基础功能组件，也可以不依赖下层组件的，该层的组件就是我们真正的业务组件了。我们通常按照功能模块来划分业务组件，例如注册登录、首页、消息等。这里的每个业务组件都是一个小的APP，它必须可以单独编译，单独打包成APK在手机上运行。


#### 3.基础业务组件\base_components
根据是否是多项目使用以及有效隔离基础资源和数据，将该层次组件分为单项目基础组件和公共基础业务组：

- 单项目基础组件需要依赖公共基础业务组件，每个项目会有自己的单项目基础组件
- 公共基础业务组件只能存放一些所有项目中都需要的数据

基础业务组件按需依赖基础功能层的组件，该层组件是对一些系统通用的业务能力进行封装的组件。
- 例如公共业务组件，Application、BaseActivity、BaseFragment、mvp、mvvm 基类等；
- 例如分享能力组件，其他业务只要集成该组件就能进行相关分享；
- 例如共享公共数据，可以将用户登录信息缓存在这里等；
- 例如共享公共资源，value、drawable、style等；
- 例如组件间数据通信的接口，可以将Arouter服务所需的IProvider 以及path 放在这里。
- 单项目基础组件需要依赖公共基础业务组件，单项目基础组是针对单项目的，公共基础业务组件是针对多项目的，隔离多项目之间的基础数据
  

#### 4.基础功能组件\base_libs
这个层的组件都是最基础的功能，通常它不包含任何业务逻辑，也可以说这些组件是一些通用的工具类和一些第三方库。或者只有足够优秀的代码才能下沉到基础层。
- 例如日志记录组件，它只是提供了日志记录的能力，你要记录什么样的日志，它并不关心；
- 例如基础UI组件，它是一个全局通用的UI资源库；
- 例如图片加载组件，它是一个全局通用图片加载框架；
- 例如网络服务组件，它封装了网络的请求能力


# 2. 组件创建
创建一个组件，有两个来源：
- 一是拆分而来创建的新组件，
- 二是为新功能创建一个新组件。

### 1. 从原有代码中拆分出的新组件
以前是所有代码都写在app/里一把梭的话，首先要把里面代码全部拉出来新建一个组件，然后再以组件划分的层次逐步处理。
- 1.宿主壳和调试壳\app
- 2.常规业务组件\biz_components
- 3.基础业务组件\base_components
- 4.基础功能组件\base_libs

  ​      
### 2. 为新功能创建新组件
`新功能的确定:`
新组件功能不能与已有的组件功能100%相同，否则这不是新功能。 其次，新组件功能可以与已有组件功能交互，但不能有重合。 最后，新组件功能的划分要保持粒度一致。一个组件一个 Activity是允许的，只要划分的粒度和其他组件的粒度保持一致。组件间独立，架构不一样是允许的。

### 3. 具体创建流程

#### 1. 添加调试壳的步骤

1. 复制一份\app\app_template 重命名为新宿主壳的名字 如: app_login
1. 修改app_login\AndroidManifest.xml 文件指定启动Activity，配置权限等
1. 修改app_login\build.gradle文件指定要调试的组件以及其它需要等配置信息，引入公共等gradle配置文件 `apply from: "../../app_components.gradle" `
1. 在settings.gradle文件中添加引用路径： `include ':app:app_login' `
1. 添加要调试的组件依赖 `implementation project(path: ':biz_components:login')`
如果业务组件发布为了aar改成远程依赖即可，同步代码，运行单个组件调试

>  如果其它项目也需要这个调试壳，目前只能通过代码复制，后期考虑通过创建模版实现

#### 2. 添加常规业务组件的步骤

1. 点击 biz_components 目录右键\New\Module 选择Android Library 新建一个组件 如：login
1. 修改 biz_components\login\build.gradle 文件 添加需要依赖的下层依赖库或者其它第三方依赖,引入公共等gradle配置文件 `apply from: "../../biz_components.gradle" `
1. 在settings.gradle文件中修改引用路径：`include ':biz_components:login'` 
1. 同步代码，添加组件功能，需要考虑是否多个项目会依赖该组件，ButterKnife在组件中的使用问题等等
1. 组件开发完成，可以考虑发布aar，本地依赖或远程依赖，具体查看发布流程

#### 3. 添加基础业务组件的步骤

1. 点击 base_components 目录右键\New\Module 选择Android Library 新建一个组件 如：base_component
1. 修改 base_components\base_component\build.gradle 文件 添加需要依赖的下层依赖库或者其它第三方依赖
1. 在settings.gradle文件中修改引用路径：`include ':base_components:base_component'` 
1. 同步代码，添加组件功能，需要考虑是否多个项目会依赖该组件，ButterKnife在组件中的使用问题
1. 组件开发完成，可以考虑发布aar，本地依赖或远程依赖，具体查看发布流程

> base_component 为所有项目提供基础业务功能
base_component_wan 只为wanDroid 这个项目提供基础业务功能
base_component_wan 需要依赖base_component，也就是说base_component_wan组件中包含base_component中的所有代码，目的是在多项目开发中隔离基础资源

#### 4. 添加基础功能组件的步骤

1. 点击 base_libs 目录右键\New\Module 选择Android Library 新建一个组件 如：base_lib
1. 修改 base_libs\base_lib\build.gradle 文件 添加需要依赖其它第三方依赖
1. 在settings.gradle文件中修改引用路径：`include ':base_libs:base_lib'` 
1. 同步代码，添加组件功能
1. 组件开发完成，可以考虑发布aar，本地依赖或远程依赖，具体查看发布流程



# 3. 组件开发
### 1.单组件调试
！！！禁止切换为独立模式！组件只允许库模式！
现在组件化很流行的做法是把组件划分为库模式和独立模式，在开发时使用独立模式，在发布时使用库模式。比如说，gradle.properties中定义一个常量值 isPlugin（是否是独立模式，true为是，false为否）然后在各个组件的build.gradle中这么写：

```
if (isPlugin.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```

至少有四点原因，足以使你放弃切换这种模式。

- 可靠性问题。注意开发时和发布时开发者面对的组件其实是不一样的。多个组件一起发布打包时经常出现依赖冲突等毛病，和开发时不一样，浪费时间。
- 切换代价。要同时调试两个组件之间的通信，往往要频繁切换库模式和独立模式，每次都Gradle Sync一次，代价极大。
- 第三方库。比如ButterKnife，切换组件成库模式和独立模式的过程中，ButterKnife可能导致切换失败，因为库模式用R2和独立模式用R，侵入性极大。
- 维护代价。由于有两个模式，需要维护两份AndroidManifest.xml，里面细节差异很大。

为解决这个问题，制定规范如下：

- 组件只允许库模式！
- 在库模式的基础上套一个空壳，以间接实现组件的独立模式。
- 第三方库统一按库模式来。组件中ButterKnife统一使用 R2，不得使用R。原来使用R的，请ctrl+shift+R全局替换，用正则表达式替换成 R2。
- 壳只由两个个文件构成。build.gradle和AndroidManifest.xml，事实上，壳可以写个模板文件夹，需要的时候复制粘贴即可。可以在build.gradle里声明AndroidManifest.xml放在同一级目录下，不用创建多余的文件夹。
    - 改下build.gradle声明塞进哪些组件，
    - 改AndroidManifest.xml声明入口Activity（其他Activity的声明只要在组件内声明，Build 时会合并进去）
    
- 好处：
    - 可靠性问题。开发者面对的只有库模式，最终打包的也是库模式的组件。（只用apply plugin: 'com.android.library'）
切换代价。用套壳的方式间接实现独立模式来调试组件，极为灵活。
    - 可以一个组件一个壳，两个组件一个壳，想调试多少组件就把它们都塞进一个壳。
切换壳的过程不需要 Gradle Sync，直接指定哪个壳直接 Build 就行
    - ButterKnife。老项目肯定广泛使用了ButterKnife，在组件化时，一次修改，永久有效（只需R改成R2），不像前面切换两个模式时，需要同时切换R和R2
维护代价。只用维护库模式的组件。空壳随意维护一下就行，反正没有java或kotlin之类的代码。

- 坏处：

    - 每个壳都需要磁盘空间来 build，所以这种做法本质上是用空间换取时间。
    - 暂时没发现其他坏处，有待观察。

### 2.多组件调试
可以一个组件一个壳，两个组件一个壳，想调试多少组件就把它们都塞进一个壳。切换壳的过程不需要 Gradle Sync，直接指定哪个壳直接 Build 就行。

>注意使用 gradle dependencies 查看依赖树，确定是否和其他组件依赖冲突。

### 3.组件内的Aplication生命周期注入
目前是反射实现，实现方式参考Demo中的[ApplicationDelegate.kt](https://github.com/Jay-Droid/WanDroid/blob/master/base_libs/base_lib/src/main/java/com/jaydroid/base_lib/app/appdelegate/ApplicationDelegate.kt)
以后找到更合适单方法可以替换

使用参考 [BaseComponentApp.kt](https://github.com/Jay-Droid/WanDroid/blob/master/base_components/base_component/src/main/java/com/jaydroid/base_component/app/BaseComponentApp.kt)

优先级从高到底顺序初始化，建议下层组件都用高优先级
```
@StringDef(
    PriorityLevel.LOW,
    PriorityLevel.MEDIUM,
    PriorityLevel.HIGH
)
//表示注解所存活的时间在运行时,而不会存在 .class 文件中
@Retention(AnnotationRetention.SOURCE)
annotation class PriorityLevel {
    companion object {
        /**
         * 低优先级
         */
        const val LOW = "LOW"
        /**
         * 中优先级
         */
        const val MEDIUM = "MEDIUM"
        /**
         * 高优先级
         */
        const val HIGH = "HIGH"
    }
}
```


### 4.组件耦合
组件间不得相互依赖，应该相对独立。

组件间的通信必须考虑没有回应的情况。也就是说，单独调试组件时，即使访问了另一个不存在的组件，也不能崩。只要处理好没有回应的情况，就把握住了组件的边界，轻松解耦
ARouter如果找不到组件默认也会有错误提示


### 5.组件间通信
组件间数据传递：EventBus/ARouter-Service
组件间函数调用：ARouter-Service
组件间界面跳转：ARouter
组件间 UI 混合：ARouter-Service
以上技术点点相关代码请参考 [WanDroid](https://github.com/Jay-Droid/WanDroid) 测试Demo

### 6.依赖控制
升级 gradle 到 3.0+，使用 `implementation`, `api`, `runtimeOnly`, `compileOnly` 进行依赖控制。

- implementation:短依赖。我的依赖的依赖不是我的依赖。
- api: 长依赖。我的依赖，以及我的依赖的依赖，都是我的依赖。
- runtimeOnly:不合群依赖。写代码和编译时不会参与，只在生成 APK 时被打包进去。
- compileOnly:假装依赖。只在写代码和编译时有效，不会参与打包。

依赖使用规范：

- implementation: 用于组件范围内的依赖，不与其他组件共享。（作用域是组件内）
- api: 用于基础层的依赖，要穿透基础层，暴露给组件层。（作用域是所有）
- runtimeOnly: 用于 app 宿主壳的依赖，组件间是绝对隔离的，编译时不可见，但参与打包。（无作用域）
- compileOnly: 用于高频依赖，防止 already present 错误。一般是开源库用来依赖 support 库防止与客户的 support 库版本冲突的。


每个组件在开发时随时都有可能引入依赖，那么在引入依赖时，什么时候只给组件，什么时候应该下沉到基础层？

依赖引入原则：`最少依赖原则`。基础层的依赖必须尽量少，不得随意下沉依赖。一个依赖能下沉，当且仅当基础层的某个库需要这个依赖。如果这个依赖只是给组件用的，不是基础库需要的，就算有几十几百个组件都要这个依赖，也要分别在组件的 build.gradle 里加，一定不能下沉到基础层。

就算组件层的不同组件有相同的依赖，打包时也不会有冲突（gradle真好用）。



### 7.组件资源命名规范
组件的build.gradle文件中添加如下代码，强制提示采用组件名称_来命名资源文件的前缀

```
android {

    //给 Module 内的资源名增加前缀, 避免资源名冲突
    resourcePrefix "${project.name.toLowerCase().replaceAll("-", "_")}_"
 }
```
如果某个资源文件不想采用这个规范可以添加`tools:ignore="ResourceName"`忽略这个错误提示

```
<?xml version="1.0" encoding="utf-8"?>
<resources 
xmlns:tools="http://schemas.android.com/tools" 
tools:ignore="ResourceName">
// 颜色、字符串等
</resources>
```



# 4. 组件复用与发布
## 1. 公共组件如何实现多项目共享

### 方案一：将moudle放在本地指定目录实现多项目复用

[参考文章](https://code.luasoftware.com/tutorials/android/create-android-library-to-be-shared-with-multiple-projects/)

这种方案存在的问题：
- 1，多项目引用同一个moudle导致项目结构混乱不易维护
- 2，交叉编译过程中会出现另一个项目的文件，导致编译不通过，需要清理.idea文件夹重新打开

### 方案二：复制粘贴组件

这种方案存在的问题：
- 1，通过人工方式将一个组件等源码导入到另一个项目，不够高效
- 2，需要维护多份相同组件等源码，但是这种方式有利于调试

### 方案三：每个人搭建本地Maven仓库，用git实现本地仓库的同步
[参考文章](https://www.jianshu.com/p/8d7d0cc8fcc3)

这种方案存在的问题：
- 1，需要另一个git仓库来管理这个本地Maven仓库来保持多人同步
- 2，每次更新需要版本管理依赖
- 3，项目中的仓库对应的本地module也需要保留方便调试

### 方案四：搭建公司私有Maven仓库，依靠公司服务保持组件同步
[参考文章](https://blog.csdn.net/qq_33224330/article/details/80135606)

这种方案存在的问题：
- 1，每次更新需要版本管理依赖
- 2，项目中的仓库对应的本地module也需要保留方便调试

这里推荐使用方案三和方案四采用发布aar的方式实现多项目复用，但是方案二可以做为调试时等备选方案

一个组件相对稳定后，把该组件打包成 aar，发布到 maven，在本地 setting.gradle 注释掉该组件的声明，用 arr 替换原来的，可大幅减少编译时间和运存占用。

在组件的build.gradle加入下面这段，sync后执行gradle uploadArchives实现自动打包出 arr 到指定文件夹。


```

/////////////////////////////////////发不到到本地Maven仓库--start/////////////////////////////////////
//TODO 第一步 在组件中添加以下发布代码，并填写相关信息，执行gradle uploadArchives 发布aar到本地Maven仓库
apply plugin: 'maven'
uploadArchives {
    //dependencies path: com.jay.sharelibrary:sharelibrary:1.0.0
    repositories.mavenDeployer {
        // 本地路径
        repository(url: MAVEN_LOCAL_REPO_PATH)
        // 唯一标识
        pom.groupId = "com.jay.sharelibrary"
        // 项目名称
        pom.artifactId = "sharelibrary"
        // 版本号
        pom.version = "1.0.1"
    }
}

//TODO 第二步 在项目的build.gradle中添加Maven仓库地址
/*
1，在gradle.properties配置各自的本地Maven仓库地址
#本地Maven仓库地址
MAVEN_LOCAL_REPO_PATH=file:///Users/Shared/Maven/MavenLocalRepo/
#远程Maven仓库的URL
MAVEN_REPO_RELEASE_URL=http://localhost:8081/repository/maven-releases/
2，引用本地Maven仓库地址
maven {  url MAVEN_LOCAL_REPO_PATH }
*/

//TODO 第三步 在app的build.gradle中添加依赖引用
//依赖本地maven仓库
//implementation 'com.jay.sharelibrary:sharelibrary:1.0.1'
//依赖module
//implementation project(path: ':sharelibrary')

/////////////////////////////////////发不到到本地Maven仓库--end/////////////////////////////////////

```

# 4. 其它问题
## 1，其它流程上点问题

//todo

## 2，具体项目实施问题

//todo