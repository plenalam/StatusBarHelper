# StatusBarHelper
[![](https://jitpack.io/v/plenalam/StatusBarHelper.svg)](https://jitpack.io/#plenalam/StatusBarHelper)

## Introduction
Easy to set Android Statusbar.
## Useage
```
Add it in your root build.gradle at the end of repositories:
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
