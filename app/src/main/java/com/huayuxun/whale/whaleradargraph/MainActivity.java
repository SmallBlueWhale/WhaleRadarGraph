package com.huayuxun.whale.whaleradargraph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huayuxun.whale.whaleradargraph.widget.WhaleRadarGraph;

public class MainActivity extends AppCompatActivity {
    WhaleRadarGraph mWhaleRadarGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWhaleRadarGraph = (WhaleRadarGraph)findViewById(R.id.wrg_main_activity);
        mWhaleRadarGraph.addData("金辉",0.9f);
        mWhaleRadarGraph.addData("夏梦",0.5f);
        mWhaleRadarGraph.addData("宝洁",0.5f);
        mWhaleRadarGraph.addData("佳涛",0.5f);
        mWhaleRadarGraph.addData("基基龙",0.5f);
        mWhaleRadarGraph.addData("达达",0.5f);
        mWhaleRadarGraph.reFresh();
    }
}
