package com.csm.autoadjustseekbar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.csm.autoadjustseekbar.R;
import com.csm.autoadjustseekbar.widget.AutoAdjustSeekbar;

public class MainActivity extends AppCompatActivity {

    private TestDialogFragment mTestDialogFragment;

    private AutoAdjustSeekbar mBubbleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBubbleSeekBar = findViewById(R.id.seek_bar);
        mBubbleSeekBar.setOnProgressChangedListener(new AutoAdjustSeekbar.OnProgressChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Log.d("activity", value + " ");
            }
        });
        mBubbleSeekBar.setEnable(false);

        mTestDialogFragment = new TestDialogFragment();
        mTestDialogFragment.show(getFragmentManager(), "test");
    }
}
