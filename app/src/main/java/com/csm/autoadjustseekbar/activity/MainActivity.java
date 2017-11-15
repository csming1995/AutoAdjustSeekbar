package com.csm.autoadjustseekbar.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.csm.autoadjustseekbar.R;
import com.csm.autoadjustseekbar.widget.AutoAdjustSeekbar;

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
