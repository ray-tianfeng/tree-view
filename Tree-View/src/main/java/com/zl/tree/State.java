package com.zl.tree;

import android.graphics.Color;

/**
 * Time: 2019/12/19 0019
 * Author: zoulong
 */
public enum  State {
    NORMAL(Color.BLACK, "默认"),
    SUCCESS(Color.parseColor("#228B22"), "正常"),
    WARN(Color.parseColor("#FFD700"), "警告"),
    ERROR(Color.parseColor("#FF3030"), "错误");
    public int color;
    public String name;
    State(int color, String name){
        this.color = color;
        this.name = name;
    }
}
