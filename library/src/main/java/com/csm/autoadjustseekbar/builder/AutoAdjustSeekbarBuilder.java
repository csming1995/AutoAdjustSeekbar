package com.csm.autoadjustseekbar.builder;

import android.support.annotation.ColorInt;

import com.csm.autoadjustseekbar.AutoAdjustSeekbar;

import java.util.LinkedList;

/**
 * Created by csm on 2017/11/15.
 */

public class AutoAdjustSeekbarBuilder {

    //最大值
    private int max;
    //最小值
    private int min;
    //当前值
    private int progress;

    //进度条 进度颜色
    private int progressColor;
    //节点的颜色
    private int nodeColor;
    //进度条 底条颜色
    private int backgroundColor;
    //进度条 宽度
    private int progressBarSize;
    //Thumb的大小
    private float thumbSize;
    //Text的字号
    private int textSize;
    //对应各个位置节点的文字；
    private LinkedList<String> texts = new LinkedList<>();
    private boolean isAutoAdjust = true;
    private boolean isShowText = true;


    private AutoAdjustSeekbar mAutoAdjustSeekbar;

    public AutoAdjustSeekbarBuilder(AutoAdjustSeekbar autoAdjustSeekBar) {
        mAutoAdjustSeekbar = autoAdjustSeekBar;
    }

    public void build() {
        mAutoAdjustSeekbar.config(this);
    }

    public AutoAdjustSeekbarBuilder setMax(int max) {
        this.max = max;
        return this;
    }

    public AutoAdjustSeekbarBuilder setMin(int min) {
        this.min = min;
        return this;
    }

    public AutoAdjustSeekbarBuilder setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public AutoAdjustSeekbarBuilder setProgressColor(@ColorInt int progressColor) {
        this.progressColor = progressColor;
        return this;
    }

    public AutoAdjustSeekbarBuilder setNodeColor(@ColorInt int nodeColor) {
        this.nodeColor = nodeColor;
        return this;
    }

    public AutoAdjustSeekbarBuilder setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public AutoAdjustSeekbarBuilder setProgressBarSize(int progressBarSize) {
        this.progressBarSize = progressBarSize;
        return this;
    }

    public AutoAdjustSeekbarBuilder setThumbSize(float thumbSize) {
        this.thumbSize = thumbSize;
        return this;
    }

    public AutoAdjustSeekbarBuilder setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public AutoAdjustSeekbarBuilder setTexts(String... texts) {
        int length = texts.length;
        for (int index = 0; index < length; index++){
            this.texts.add(texts[index]);
        }
        return this;
    }

    public AutoAdjustSeekbarBuilder setTexts(LinkedList<String> texts) {
        this.texts = texts;
        return this;
    }

    public AutoAdjustSeekbarBuilder setAutoAdjust(boolean autoAdjust) {
        isAutoAdjust = autoAdjust;
        return this;
    }

    public AutoAdjustSeekbarBuilder setShowText(boolean isShowText) {
        this.isShowText = isShowText;
        return this;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getProgress() {
        return progress;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public int getNodeColor(){
        return nodeColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getProgressBarSize() {
        return progressBarSize;
    }

    public float getThumbSize() {
        return thumbSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public LinkedList<String> getTexts() {
        return texts;
    }

    public boolean isAutoAdjust() {
        return isAutoAdjust;
    }

    public boolean isShowText(){
        return isShowText;
    }
}
