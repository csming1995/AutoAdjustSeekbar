package com.csm.autoadjustseekbar.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csm.autoadjustseekbar.R;
import com.csm.autoadjustseekbar.widget.AutoAdjustSeekbar;


/**
 * Created by csm on 2017/8/14.
 */

public class TestDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_test_dialog, container);
        AutoAdjustSeekbar autoAdjustSeekbar = view.findViewById(R.id.seek_bar);
        autoAdjustSeekbar.getConfigBuilder()
                .setProgressColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                .setTexts(new String[]{"xxxx", "yxxx", "ydyy", "xdxx", "ttt"})
                .build();
        return view;
    }
}
