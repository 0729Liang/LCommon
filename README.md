# LCommon

根据自己的开发经验总结的一些工具类和自定义控件。

该项目分为Demo展示界面和LUtils支持库，如需导入依赖，请参考下面教程。

Demo界面分为三个模块，分别是Utils工具类，View自定义控件，Blog/Learn栏目 

对于*Utils*和*View*模块，已经封装在LUtils的Module中,在**com.liang.liangutils.libs**包下面

而*Blog/Learn*模块只是一些教程和自己平时学习的内容，没有封装，仅供查看


# 界面展示

<center class="half">
    <img src="https://github.com/0729Liang/LCommon/blob/dev/image/LCommon_View.png" width="200"/><img src="https://github.com/0729Liang/LCommon/blob/dev/image/LCommon_Blog.png" width="200"/>
</center>

![image](https://github.com/0729Liang/LCommon/blob/dev/image/LCommon.gif)
<img src="https://github.com/0729Liang/LCommon/blob/dev/image/LCommon_Utils.png" width="200"/>
# 一、导入依赖

To get a Git project into your build:

1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
			maven { url "https://maven.google.com" }
		}
	}

2. Add the dependency

```
{ 
    implementation 'com.github.0729Liang:LCommon:0.0.5' 
}
```

# 二、使用

1. 新建 Application , 进行初始化

```
public class LBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LCommon.init(this, BuildConfig.class);  // 初始化
    }
}

```

2. 在 Manifest 的 application 中添加新建的 LBaseApplication

```
    <application
        android:name=".base.LBaseApplication"
        android:allowBackup="true"
        ...>
        .
        .
        .
    </application>

```


