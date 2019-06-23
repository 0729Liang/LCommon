# LCommon

根据自己的开发经验总结的一些工具类和自定义控件

一、导入依赖

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

	{
	        implementation 'com.github.0729Liang:LCommon:0.0.5'
	}

二、使用

1. 新建 Application , 进行初始化

```
public class LBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LCommon.init(this, BuildConfig.class);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LLogX.e("内存不足，请注意回收！！！");
    }
}

```

2. 在 Manifest 的 application 中添加新建的 LBaseApplication

```

    <application
        android:name=".base.LBaseApplication"
        android:allowBackup="true"
        ...>
        ....
    </application>

```


