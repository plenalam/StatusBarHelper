# StatusBarHelper
[![](https://jitpack.io/v/plenalam/StatusBarHelper.svg)](https://jitpack.io/#plenalam/StatusBarHelper)

## Introduction
Easy to set Android Statusbar.  
[中文](https://github.com/plenalam/StatusBarHelper/blob/master/README_ZH.md)

## Useage
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

## Example
```xml
<com.plena.statusbarhelper.StatusBarRelativeLayout
	android:id="@+id/rl_title"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="@drawable/bg_title"
	android:gravity="center">
		<TextView
                style="@style/TitleTextViewStyle"
                android:text="Login" />
</com.plena.statusbarhelper.StatusBarRelativeLayout>
```

```java
 new StatusBarHelper(this).setSystemStatusBar()
        .setStatusBarLightMode(true)
        .setWindowFullScreen(false);
```
