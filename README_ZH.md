# StatusBarHelper
[![](https://jitpack.io/v/plenalam/StatusBarHelper.svg)](https://jitpack.io/#plenalam/StatusBarHelper)

## 简介
Android状态栏的一些

- [x] 使用图片或者渐变扩展到标题栏,而不仅仅纯色
- [x] 避免fitsSystemWindows引起的位置异常问题
- [x] 适配一些fitsSystemWindows和clipToPadding失效的4.2手机
- [x] 尽量适配一些国产机型标题栏亮色模式(即标题栏白底黑字)

## 安装
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```
dependencies {
	        implementation 'com.github.plenalam:StatusBarHelper:1.0.0'
	}
```
## 使用
```xml
<com.plena.statusbarhelper.StatusBarRelativeLayout
	android:id="@+id/rl_title"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="@drawable/bg_title"
	android:gravity="center">
		<TextView
                style="@style/TitleTextViewStyle"
                android:text="登录" />
</com.plena.statusbarhelper.StatusBarRelativeLayout>
```
```java
 new StatusBarHelper(this).setSystemStatusBar()
        .setStatusBarLightMode(true)
        .setWindowFullScreen(false);
```
