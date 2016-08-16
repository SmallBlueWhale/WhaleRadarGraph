package com.huayuxun.whale.whaleradargraph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.huayuxun.whale.whaleradargraph.widget.WhaleRadarGraph;

public class MainActivity extends AppCompatActivity {
    WhaleRadarGraph mWhaleRadarGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWhaleRadarGraph = (WhaleRadarGraph)findViewById(R.id.wrg_main_activity);
        mWhaleRadarGraph.addData("金辉",0.9f);
        mWhaleRadarGraph.addData("夏梦",0.3f);
        mWhaleRadarGraph.addData("宝洁",0.5f);
        mWhaleRadarGraph.addData("佳涛",0.5f);
        mWhaleRadarGraph.addData("gaygay龙",0.1f);
        mWhaleRadarGraph.addData("大火包",0.6f);
        mWhaleRadarGraph.addData("吾儿强",0.5f);
        mWhaleRadarGraph.addData("达达",0.7f);
        mWhaleRadarGraph.addData("绿菊林",0.45f);
        mWhaleRadarGraph.getTextSize();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        int width = metric.widthPixels;  // 宽度（PX）
        int height = metric.heightPixels;  // 高度（PX）

        float density = metric.density;  // 密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 密度DPI（120 / 160 / 240）
        Log.e("densityDpi:",densityDpi+"");
    }
}
