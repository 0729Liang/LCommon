# LCommon
自己的工具合集

How to use by gradle

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
			maven { url "https://maven.google.com" }
		}
	}

Step 2. Add the dependency

	{
	        implementation 'com.github.0729Liang:LCommon:0.0.3'
	}




