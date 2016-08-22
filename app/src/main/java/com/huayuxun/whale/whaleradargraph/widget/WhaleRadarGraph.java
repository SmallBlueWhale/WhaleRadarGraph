package com.huayuxun.whale.whaleradargraph.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import com.huayuxun.whale.whaleradargraph.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whale on 2016/7/13 0013.
 */

public class WhaleRadarGraph extends View {
    private float mRadius;           //外界多边形的半径  CircleRadius
    private int mLevel;           //外圈层数
    private int mLineColor;       //线条颜色
    private int mShapeColor;      //内多边形颜色
    private int mTextColor;      //文字颜色
    private float mShapeSpan;       //多边形的间隔
    private float mDistance;          //文字到该点的距离
    private List<Pair<String, Float>> mRadarAttrData;//雷达图的属性
    private double mPerAngle;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private int mOverLayerAlph;     //覆盖层的透明度

    private Paint mTextPaint;       //绘制文字画笔
    private float mTextSize;          //文字的大小

    private Context mContext;
    public WhaleRadarGraph(Context context) {
        this(context, null);
        mContext = context;
    }

    public WhaleRadarGraph(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public WhaleRadarGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WhaleRadarGraph, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int att = typedArray.getIndex(i);
            switch (att) {
                case R.styleable.WhaleRadarGraph_shapeColor:
                    mShapeColor = typedArray.getColor(att, Color.YELLOW);
                    break;
                case R.styleable.WhaleRadarGraph_level:
                    mLevel = typedArray.getInt(att, 4);
                    break;
                case R.styleable.WhaleRadarGraph_lineColor:
                    mLineColor = typedArray.getColor(att, Color.GREEN);
                    break;
                case R.styleable.WhaleRadarGraph_radius:
                    mRadius = typedArray.getDimension(R.styleable.WhaleRadarGraph_radius, (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.WhaleRadarGraph_shapeSpan:
                    mShapeSpan = typedArray.getDimension(R.styleable.WhaleRadarGraph_shapeSpan, (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.WhaleRadarGraph_overLayerAlph:
                    mOverLayerAlph = typedArray.getInt(R.styleable.WhaleRadarGraph_overLayerAlph, 50);
                    break;
                case R.styleable.WhaleRadarGraph_textColor:
                    mTextColor = typedArray.getColor(att, Color.BLUE);
                    break;
                case R.styleable.WhaleRadarGraph_textSize:
                    mTextSize = typedArray.getDimension(R.styleable.WhaleRadarGraph_textSize, (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.WhaleRadarGraph_distance:
                    mDistance = typedArray.getDimension(R.styleable.WhaleRadarGraph_distance, (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
            }
        }
        mContext = context;
        typedArray.recycle();
        mRadarAttrData = new ArrayList<>();
        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    public WhaleRadarGraph(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getRadarGraphSize(widthMeasureSpec),getRadarGraphSize(heightMeasureSpec));
    }

    private int getRadarGraphSize(int measureSpec) {
        int width = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            width = specSize;
        }
        else{
            width = (int)(mRadius * 2 + mTextSize * 2 + mDistance * 2);
            if(specMode == MeasureSpec.AT_MOST) {
                width = Math.min(specSize , width);
            }
        }
        return width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRadarAttrData.size() < 3) {
            return;
        }
        initShape(canvas, mRadius, mLineColor, mLevel, mShapeSpan);
        initMyShape(canvas);
    }

    //maxvalue：1
    public void addData(String attrName, float attrValue) {
        mRadarAttrData.add(new Pair<String, Float>(attrName, attrValue));
    }

    //maxvalue：maxvalue
    public void addData(String attrName, float attrValue, float maxvalue) {
        mRadarAttrData.add(new Pair<String, Float>(attrName, attrValue / maxvalue));
        invalidate();
    }


    //初始化多边形以及轴线
    public void initShape(Canvas canvas, float radius, int lineColor, int level, float shapeSpan) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPerAngle = 2 * Math.PI / mRadarAttrData.size();
        Path path = new Path();
        for (int i = 0; i < level; i++) {
            float span = i * radius / level;
            for (int j = 0; j < mRadarAttrData.size(); j++) {
                double testx = (radius - span) * Math.sin(mPerAngle * j);
                double testy = (radius - span) * Math.cos(mPerAngle * j);
                //第一个点处理起来比较麻烦
                if (j == 0) {
                    path.moveTo(0, -(float) (radius - span));
                    mTextPaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(mRadarAttrData.get(0).first, (float) 0, -(float) (radius + mDistance), mTextPaint);
                    canvas.drawPoint(0, -(float) (radius - span), mPaint);
                    canvas.drawLine(0, 0, 0, -(float) (radius - span), mPaint);
                } else {
                    path.lineTo((float) testx, -(float) testy);
                    canvas.drawPoint((float) testx, -(float) testy, mPaint);
                    canvas.drawLine(0, 0, (float) testx, -(float) testy, mPaint);
                    if (mRadarAttrData.size() % 2 == 0 && j == mRadarAttrData.size() / 2) {
                        mTextPaint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance + mTextSize) * Math.sin(mPerAngle * j)), -(float) (((radius + mDistance + mTextSize)) * Math.cos(mPerAngle * j)), mTextPaint);
                    }

                    //Y轴正坐标上，Y轴和X轴上的点需要特别去处理，因为绘制文字，你的文字要居中放置
                    //x轴的正坐标上
                    else if (mRadarAttrData.size() % 4 == 0 && j == mRadarAttrData.size() / 4) {
                        mTextPaint.setTextAlign(Paint.Align.LEFT);
                        Log.e("Math.sin--》", Math.sin(mPerAngle * j) + "");
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance) * Math.sin(mPerAngle * j)), -(float) ((radius + mDistance) * Math.cos(mPerAngle * j)) + mTextSize / 2.5f, mTextPaint);
                    }
                    //x轴的负坐标上
                    else if (mRadarAttrData.size() % 4 == 0 && j == mRadarAttrData.size() / 4 * 3) {
                        mTextPaint.setTextAlign(Paint.Align.RIGHT);
                        Log.e("Math.sin--》", Math.sin(mPerAngle * j) + "");
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance) * Math.sin(mPerAngle * j)), -(float) ((radius + mDistance) * Math.cos(mPerAngle * j)) + mTextSize / 2.5f, mTextPaint);
                    }
                    //第一象限
                    else if (Math.sin(mPerAngle * j) > 0 && Math.cos(mPerAngle * j) > 0) {
                        mTextPaint.setTextAlign(Paint.Align.LEFT);
                        Log.e("Math.sin--》", Math.sin(mPerAngle * j) + "");
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance) * Math.sin(mPerAngle * j)), -(float) (((radius + mDistance)) * Math.cos(mPerAngle * j)), mTextPaint);
                    }
                    //第二象限
                    else if (Math.sin(mPerAngle * j) > 0 && Math.cos(mPerAngle * j) < 0) {
                        if (mRadarAttrData.size() % 2 != 0 && j == mRadarAttrData.size() / 2) {
                            mTextPaint.setTextAlign(Paint.Align.CENTER);
                        } else {
                            mTextPaint.setTextAlign(Paint.Align.LEFT);
                        }
                        Log.e("Math.sin--》", Math.sin(mPerAngle * j) + "");
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance) * Math.sin(mPerAngle * j)), -(float) (((radius + mDistance)) * Math.cos(mPerAngle * j)) + mTextSize, mTextPaint);
                    }
                    //第三象限
                    else if (Math.sin(mPerAngle * j) < 0 && Math.cos(mPerAngle * j) < 0) {
                        if (mRadarAttrData.size() % 2 != 0 && j == mRadarAttrData.size() / 2 + 1) {
                            mTextPaint.setTextAlign(Paint.Align.CENTER);
                        } else {
                            mTextPaint.setTextAlign(Paint.Align.RIGHT);
                        }
                        Log.e("Math.sin--》", Math.sin(mPerAngle * j) + "");
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance) * Math.sin(mPerAngle * j)), -(float) (((radius + mDistance)) * Math.cos(mPerAngle * j)) + mTextSize, mTextPaint);
                    }
                    //第四象限
                    else if (Math.sin(mPerAngle * j) < 0 && Math.cos(mPerAngle * j) > 0) {
                        mTextPaint.setTextAlign(Paint.Align.RIGHT);
                        Log.e("Math.sin--》", Math.sin(mPerAngle * j) + "");
                        canvas.drawText(mRadarAttrData.get(j).first, (float) ((radius + mDistance) * Math.sin(mPerAngle * j)), -(float) ((radius + mDistance) * Math.cos(mPerAngle * j)), mTextPaint);

                    }
                }
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
        canvas.restore();
    }


    //根据百分比绘制黄色或则其它颜色多边形
    private void initMyShape(Canvas canvas) {
        Paint paint = new Paint();
        Paint pointPaint = new Paint();
        pointPaint.setStrokeWidth(10);
        paint.setStrokeWidth(4);
        paint.setColor(mShapeColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(mOverLayerAlph);
        pointPaint.setColor(mShapeColor);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        for (int j = 0; j < mRadarAttrData.size(); j++) {
            double testx = (mRadius * mRadarAttrData.get(j).second) * Math.sin(mPerAngle * j);
            double testy = (mRadius * mRadarAttrData.get(j).second) * Math.cos(mPerAngle * j);
            double nextTestx;
            double nextTesty;
            if (j != mRadarAttrData.size() - 1) {
                nextTestx = (mRadius * mRadarAttrData.get(j + 1).second) * Math.sin(mPerAngle * (j + 1));
                nextTesty = (mRadius * mRadarAttrData.get(j + 1).second) * Math.cos(mPerAngle * (j + 1));
            } else {
                nextTestx = 0;
                nextTesty = mRadius * mRadarAttrData.get(0).second;
            }
            if (j == 0) {
                path.moveTo(0, -(float) (mRadius * mRadarAttrData.get(0).second));
                canvas.drawPoint(0, -(float) (mRadius * mRadarAttrData.get(0).second), pointPaint);
                canvas.drawLine(0, -(float) (mRadius * mRadarAttrData.get(0).second), (float) nextTestx, -(float) nextTesty, pointPaint);

            } else {
                path.lineTo((float) testx, -(float) testy);
                canvas.drawPoint((float) testx, -(float) testy, pointPaint);
                canvas.drawLine((float) testx, -(float) testy, (float) nextTestx, -(float) nextTesty, pointPaint);
            }
        }
        path.close();
        canvas.drawPath(path, paint);
        canvas.restore();

    }
}
