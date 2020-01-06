package com.zl.tree;

import android.graphics.Color;

/**
 * Time: 2019/12/19 0019
 * Author: zoulong
 */
public class TreeConfig {
    //设置默认值，可以通过xml自定义属性实现
    public int trunkWidth = 450;//纵向主干在树中的长度
    public int trunkHeight = 60;//竖向主干在树种的高度
    public int leafWidth = 40;//纵向树叶的长度
    public int leafHeight = 30;//竖向树叶的高度

    public int paddingWidth = 40;//树叶和树枝在宽度上的padding
    public int paddingHeight = 20;//树叶和树枝在高度上的padding
    public int textSize = 25;//文字大小

    public int interval_state = 90;//树和状态的间距

    public int state_margin_top = 120;//状态与顶部的间距

    public int backGroundColor = Color.WHITE;

    public int getTrunkWidth() {
        return trunkWidth;
    }

    public void setTrunkWidth(int trunkWidth) {
        this.trunkWidth = trunkWidth;
    }

    public int getTrunkHeight() {
        return trunkHeight;
    }

    public void setTrunkHeight(int trunkHeight) {
        this.trunkHeight = trunkHeight;
    }

    public int getLeafWidth() {
        return leafWidth;
    }

    public void setLeafWidth(int leafWidth) {
        this.leafWidth = leafWidth;
    }

    public int getLeafHeight() {
        return leafHeight;
    }

    public void setLeafHeight(int leafHeight) {
        this.leafHeight = leafHeight;
    }

    public int getPaddingWidth() {
        return paddingWidth;
    }

    public void setPaddingWidth(int paddingWidth) {
        this.paddingWidth = paddingWidth;
    }

    public int getPaddingHeight() {
        return paddingHeight;
    }

    public void setPaddingHeight(int paddingHeight) {
        this.paddingHeight = paddingHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getInterval_state() {
        return interval_state;
    }

    public void setInterval_state(int interval_state) {
        this.interval_state = interval_state;
    }

    public int getState_margin_top() {
        return state_margin_top;
    }

    public void setState_margin_top(int state_margin_top) {
        this.state_margin_top = state_margin_top;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }
}
