package com.csm.autoadjustseekbar.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.csm.autoadjustseekbar.R;
import com.csm.autoadjustseekbar.widget.builder.AutoAdjustSeekbarBuilder;

import java.util.LinkedList;

/**
 * Created by csm on 2017/8/20.
 */

public class AutoAdjustSeekbar extends View {

    private Context mContext;

    //最大值
    private int mMax;
    //最小值
    private int mMin;
    //当前值
    private int mProgress;

    //进度条 进度颜色
    private int mProgressColor;
    //节点的颜色
    private int mNodeColor;
    //进度条 底条颜色
    private int mBackgroundColor;
    //进度条 宽度
    private int mProgressBarSize;

    //背景
    private Bitmap mBackgroundBitmap;
    //Thumb的大小
    private float mThumbSize;
    //thumb的样式
    private Bitmap mThumbBitmap;

    //平均每进度单位 对应的宽度
    //thumb当前所在位置
    private float mAverageValue;
    private float position;

    //控件宽高
    private int mHeight;
    private int mWidth;

    private Paint mPaint;

    private boolean isEnable;

    //对应各个位置节点的文字；
    private LinkedList<String> texts;
    //thumb所在的位置索引；
    private int index = 0;
    //中间一个节点的位置
    private int centerNodeIndex = 0;
    //分段数
    private int selectionCount = 0;
    //每个分段的长度
    private float selectionLength = 0;
    //是否开启自动调整至节点
    private boolean isAutoAdjust = false;

    private AutoAdjustSeekbarBuilder mConfigBuilder;

    private ValueAnimator autoAdjustAnimator;

    /**
     * 监听器，提供给外部监听progress变化
     */
    private OnProgressChangedListener mOnProgressChangedListener;

    public interface OnProgressChangedListener {
        void onValueChanged(int value);

        void onNodeChanged(int index);
    }


    public AutoAdjustSeekbar(Context context) {
        this(context, null);
    }

    public AutoAdjustSeekbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoAdjustSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        setPaint();
    }

    private void initAttr(AttributeSet attrs){
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AutoAdjustSeekBar);

        mMax = typedArray.getInt(R.styleable.AutoAdjustSeekBar_max, 100);
        mMin = typedArray.getInt(R.styleable.AutoAdjustSeekBar_min, 0);
        mProgress = typedArray.getInt(R.styleable.AutoAdjustSeekBar_progress, 100);

        mProgressColor = typedArray.getColor(R.styleable.AutoAdjustSeekBar_progress_color,
                ContextCompat.getColor(mContext, R.color.colorSeekBarProgressContent));
        mNodeColor = typedArray.getColor(R.styleable.AutoAdjustSeekBar_node_color,
                ContextCompat.getColor(mContext, R.color.colorSeekBarProgressContent));
        mBackgroundColor = typedArray.getColor(R.styleable.AutoAdjustSeekBar_background_color,
                ContextCompat.getColor(mContext, R.color.colorSeekBarProgressBackground));

        mProgressBarSize = typedArray.getInt(R.styleable.AutoAdjustSeekBar_progress_size, 10);

        int backgroundBitmapId = typedArray.getResourceId(R.styleable.AutoAdjustSeekBar_backgroundBitmap, 0);
        if (0 != backgroundBitmapId){
            mBackgroundBitmap = BitmapFactory.decodeResource(mContext.getResources(), backgroundBitmapId);
        }

        int thumbBitmapId = typedArray.getResourceId(R.styleable.AutoAdjustSeekBar_thumbBitmap,
                R.drawable.thumb_normal);
        if (thumbBitmapId == R.drawable.thumb_normal){
            Drawable drawable = getContext().getResources().getDrawable(thumbBitmapId);
            mThumbBitmap = drawableToBitmap(drawable);
            mThumbSize = mThumbBitmap.getWidth();
        }else {
            mThumbBitmap = BitmapFactory.decodeResource(mContext.getResources(), thumbBitmapId);
            mThumbSize = mThumbBitmap.getWidth();
        }

        typedArray.recycle();

        isEnable = true;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    private void setPaint(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(mProgressBarSize);
        mPaint.setAntiAlias(true);
    }

    public void config(AutoAdjustSeekbarBuilder builder) {
        mMin = builder.getMin();
        mMax = builder.getMax();
        mProgress = builder.getProgress();

        mProgressColor = builder.getProgressColor();
        mNodeColor = builder.getNodeColor();
        mBackgroundColor = builder.getBackgroundColor();

        mProgressBarSize = builder.getProgressBarSize();
        mThumbSize = builder.getThumbSize();
        if (null != builder.getTexts()){
            texts = builder.getTexts();
        }
        selectionCount = texts.size() - 1;
        centerNodeIndex = selectionCount / 2;
        if (0 < selectionCount){
            selectionLength = (mWidth - mThumbSize) / selectionCount;
        }else {
            selectionLength = mWidth - mThumbSize;
        }
        index = centerNodeIndex;
        isAutoAdjust = builder.isAutoAdjust();
        requestLayout();
    }

    public AutoAdjustSeekbarBuilder getConfigBuilder() {
        if (mConfigBuilder == null) {
            mConfigBuilder = new AutoAdjustSeekbarBuilder(this);
        }

        mConfigBuilder.setMin(mMin);
        mConfigBuilder.setMax(mMax);
        mConfigBuilder.setProgress(mProgress);
        mConfigBuilder.setProgressColor(mProgressColor);
        mConfigBuilder.setNodeColor(mNodeColor);
        mConfigBuilder.setBackgroundColor(mBackgroundColor);
        mConfigBuilder.setProgressBarSize(mProgressBarSize);
        mConfigBuilder.setThumbSize(mThumbSize);
        if (null != texts){
            mConfigBuilder.setTexts(texts);
        }
        mConfigBuilder.setAutoAdjust(isAutoAdjust);

        return mConfigBuilder;
    }

    public void setEnable(boolean enable){
        isEnable = enable;
    }


    public OnProgressChangedListener getOnProgressChangedListener() {
        return mOnProgressChangedListener;
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.mOnProgressChangedListener = onProgressChangedListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = (int) mThumbSize;

        mAverageValue = (mWidth - mThumbSize) / (mMax - mMin);
        position = mAverageValue * (mProgress - mMin) + mThumbSize/2;

        if (0 < selectionCount){
            Log.d("selectionLength", selectionLength + " " + (mWidth - mThumbSize));
            selectionLength = (mWidth - mThumbSize) / selectionCount;
        }else {
            selectionLength = mWidth - mThumbSize;
        }

        //避免越界
        if (position < mThumbSize/2){
            position = mThumbSize/2;
        }
        if (position > mWidth - mThumbSize/2){
            position = mWidth - mThumbSize/2;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, 0);

        mPaint.setColor(mProgressColor);
        canvas.drawLine(mThumbSize/2, mHeight/2, position, mHeight/2, mPaint);

        if (null == mBackgroundBitmap){
            mPaint.setColor(mBackgroundColor);
            canvas.drawLine(position, mHeight/2, mWidth - mThumbSize/2, mHeight/2, mPaint);
        }else {
            canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);
        }

        //绘制节点
        mPaint.setColor(mNodeColor);
        if (0 < selectionCount) {
            for (int index = 0; index <= selectionCount; index ++){
                if (centerNodeIndex == index){
                    canvas.drawCircle(mThumbSize/2 + selectionLength * index, mHeight / 2, mHeight / 2, mPaint);
                }else {
                    canvas.drawCircle(mThumbSize/2 + selectionLength * index, mHeight / 2, mHeight / 4, mPaint);
                }
            }
        }
        canvas.translate(-mThumbSize/2, -mThumbSize/2);
        canvas.drawBitmap(mThumbBitmap, position, mHeight/2, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (MotionEvent.ACTION_DOWN == event.getActionMasked() ||
                MotionEvent.ACTION_MOVE == event.getActionMasked()){
            if (null != autoAdjustAnimator && autoAdjustAnimator.isRunning()){
                autoAdjustAnimator.removeAllUpdateListeners();
                autoAdjustAnimator.cancel();
            }
            //判断是否能够拖动
            if (!isEnable){
                return false;
            }

            //获取当前在控件中的x轴坐标
            float x = event.getX();

            if (x > getWidth() - mThumbSize/2){
                x = getWidth() - mThumbSize/2;
            }
            if (x < mThumbSize/2) {
                x = mThumbSize/2;
            }
            position = x;

            //计算当前的进度值
            //position - mThumbSize/2 计算当前点到起点的长度
            mProgress = Math.round(((position - mThumbSize/2)/mAverageValue) + mMin);
            if (null != mOnProgressChangedListener) {
                mOnProgressChangedListener.onValueChanged(mProgress);
            }
            invalidate();
        }
        else if (MotionEvent.ACTION_UP == event.getActionMasked() ||
                MotionEvent.ACTION_CANCEL == event.getActionMasked()){
            if (!isEnable){
                return false;
            }
            autoAdjustSection();
        }
        return true;
    }

    private void autoAdjustSection(){
        //所在的段
        int selectionIn = (int) ((position - mThumbSize/2) / selectionLength);
        //和前一个节点的距离
        float preDistance = position - (selectionIn * selectionLength + mThumbSize/2);
        //和后一个节点的距离
        float nextDistance = selectionLength - preDistance;
        //最终的位置
        float endPosition = selectionIn * selectionLength + mThumbSize/2;
        index = selectionIn;
        if (preDistance > nextDistance){
            endPosition = (selectionIn + 1) * selectionLength + mThumbSize/2;
            index = selectionIn + 1;
        }
        if (null != mOnProgressChangedListener) {
            mOnProgressChangedListener.onNodeChanged(index);
        }
        autoAdjustAnimator = ValueAnimator.ofFloat(position, endPosition);
        autoAdjustAnimator.setDuration(500);
        autoAdjustAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                position = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        autoAdjustAnimator.start();
    }
}
