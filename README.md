# 雷达图
雷达图,支持定制,颜色，属性，外围圈数，大小，透明度。

# 效果图
# ![image](https://github.com/SmallBlueWhale/WhaleRadarGraph/image/Screenshot.png "效果图")

# 用法
## 1. 添加依赖
克隆本项目添加依赖或者在Gradle中添加依赖:
```gradle
    compile 'com.huayuxun.whale.whaleradargraph.widget.WhaleRadarGraph'
```
 > Gradle方式可能暂时无法使用

## 2. 布局文件中定义
```xml
    <com.huayuxun.whale.whaleradargraph.widget.WhaleRadarGraph
        android:id="@+id/wrg"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

## 3. 添加数据
```java
 final WhaleRadarGraph mWhaleRadarGraph = (WhaleRadarGraph) findViewById(R.id.wrg);
 if (mWhaleRadarGraph != null) {
     mWhaleRadarGraph.addData("技能 A", 0.8f);
     mWhaleRadarGraph.addData("技能 B", 0.6f);
     mWhaleRadarGraph.addData("技能 C", 0.8f);
     mWhaleRadarGraph.addData("技能 D", 0.6f);
     mWhaleRadarGraph.addData("技能 E", 0.8f);
     mWhaleRadarGraph.addData("技能 F", 0.6f);
 }

 mWhaleRadarGraph.refresh();
```

# 更多特性
 -  xml属性

| 含义          | 属性          |
| ------------- |:-------------:|
|外界多边形的半径       |radius       |
|内圈层数     | level  |
|内圈之间的间隔   |shapeSpan   |
|文本字体颜色   |textColor       |
|覆盖层线条颜色 | lineColor|
|覆盖层的透明度       |overLayerAlph      |
|覆盖层内部颜色 |shapeColor|

> 例如:
```xml
    <com.huayuxun.whale.whaleradargraph.widget.WhaleRadarGraph
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/wrg_main_activity"
        app:level="3"
        app:lineColor="#1AFF00"
        app:shapeColor="#FFFF37"
        app:radius="60dp"
        app:shapeSpan="3dp"
        app:overLayerAlph="70"/>
```

- 外部接口
WhaleRadarGraph.addData():可动态添加数据

# License
    The MIT License (MIT)

    Copyright (c) 2016 whale(王金辉)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


# WhaleRadarGraph Name(Developing)
> Radar,support,颜色，属性，外围圈数，大小。

[![NPM Version][npm-image]][npm-url]
[![Build Status][travis-image]][travis-url]
[![Downloads Stats][npm-downloads]][npm-url]

One to two paragraph statement about your product and what it does.

![](header.png)

## Installation

OS X & Linux:

```sh
npm install my-crazy-module --save
```

Windows:

```sh
edit autoexec.bat
```

## Usage example

A few motivating and useful examples of how your product can be used. Spice this up with code blocks and potentially more screenshots.

## Development setup

Describe how to install all development dependencies and how to run an automated test-suite of some kind. Potentially do this for multiple platforms.

```sh
make install
npm test
```

## Release History

* 0.2.1
    * CHANGE: Update docs (module code remains unchanged)
* 0.2.0
    * CHANGE: Remove `setDefaultXYZ()`
    * ADD: Add `init()`
* 0.1.1
    * FIX: Crash when calling `baz()` (Thanks @GenerousContributorName!)
* 0.1.0
    * The first proper release
    * CHANGE: Rename `foo()` to `bar()`
* 0.0.1
    * Work in progress

## Meta

Your Name – [@YourTwitter](https://twitter.com/dbader_org) – YourEmail@example.com

Distributed under the XYZ license. See ``LICENSE`` for more information.

[https://github.com/yourname/github-link](https://github.com/dbader/)

[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
