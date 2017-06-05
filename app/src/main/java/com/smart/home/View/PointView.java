package com.smart.home.View;

import android.graphics.Point;

/**
 * Created by qiangbin on 2017/6/4.
 * 自定义点对象
 */
public class PointView extends Point {
    //用于转化密码的下标
    public int index;

    public PointView(int x, int y) {
        super(x, y);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
