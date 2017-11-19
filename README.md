# AutoAdjustSeekbar

A custom SeekBar that can auto adjust to around value

# Screenshot

![screenshot](https://raw.githubusercontent.com/csming1995/AutoAdjustSeekbar/master/autoadjustseekbar.gif)

## Add dependency
### **Step 1.** Add the JitPack repository to your build file
### Add it in your root build.gradle at the end of repositories:   

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### **Step 2.** Add the dependency
```gradle
dependencies {
	compile 'com.github.csming1995:AutoAdjustSeekbar:1.0-1beta'
}
```

## How to use
### Activity
```java
public class MainActivity extends AppCompatActivity {

    private AutoAdjustSeekbar mAutoAdjustSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutoAdjustSeekbar = findViewById(R.id.seek_bar);
        mAutoAdjustSeekbar.getConfigBuilder()
                .setProgressColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTexts(new String[]{"xxxx", "yxxx", "ydyy", "xdxx", "ttt"})
                .build();
        mAutoAdjustSeekbar.setOnProgressChangedListener(new AutoAdjustSeekbar.OnProgressChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Log.d("activity", value + " ");
            }

            @Override
            public void onNodeChanged(int index) {
                Log.d("activity", index + " ");
            }
        });
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center">

    <com.csm.autoadjustseekbar.AutoAdjustSeekbar
        android:id="@+id/seek_bar"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        app:max="100"
        app:min="0"
        app:progress="50"
        app:thumbBitmap="@drawable/thumb_normal"
        app:progress_color="@color/colorSeekBarProgressContent"
        app:background_color="@color/colorSeekBarProgressBackground"
        app:progress_size="2"/>
</LinearLayout>
```