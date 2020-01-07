package com.zl.tree;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * 状态辅助器，计算位置和宽高
 * Time: 2019/12/19 0019
 * Author: zoulong
 */
public class StateCalculate {
    public int width;
    public int height;
    private TreeConfig treeConfig;
    private final int interval = 50;
    private ArrayList<StateForm> stateForms = new ArrayList<>();

    public StateCalculate(TreeConfig config) {
        this.treeConfig = config;
        initStateForm();
        calculationSpace();
    }

    private void initStateForm() {
        for(int i = 0; i < State.values().length; i++){
            State state = State.values()[i];
            int pointX = 0;
            int pointY = interval * i;
            Point point = new Point(pointX, pointY);
            Rect stateRect = textRect(state.name);
            stateRect.offsetTo(pointX + 30, pointY - stateRect.height()/2);
            StateForm stateForm = new StateForm(state, stateRect, point);
            stateForms.add(stateForm);
        }
    }

    public void move(int x, int y){
        for(StateForm stateForm : stateForms){
            stateForm.getRect().offset(x, y);
            stateForm.getPoint().offset(x, y);
        }
    }

    public void moveTo(int dx, int dy){
        StateForm stateForm = stateForms.get(0);
        int startX = stateForm.getPoint().x;
        int startY = stateForm.getPoint().y;
        int offsetX = dx - startX;
        int offsetY = dy - startY;
        move(offsetX, offsetY);
    }

    private void calculationSpace(){
        for(StateForm stateForm : stateForms){
            width = stateForm.getRect().right > width ? stateForm.getRect().right : width;
            height = stateForm.getRect().bottom > height ? stateForm.getRect().bottom : height;
        }
    }

    public ArrayList<StateForm> getStateForms() {
        return stateForms;
    }

    public Rect textRect(String name){
        Paint pFont = new Paint();
        pFont.setTextSize(treeConfig.textSize);
        Rect rect = new Rect();
        pFont.getTextBounds(name, 0, name.length(), rect);
        rect.left = rect.left - treeConfig.paddingWidth/2;
        rect.right = rect.right + treeConfig.paddingWidth/2;
        rect.top = rect.top - treeConfig.paddingHeight/2;
        rect.bottom = rect.bottom + treeConfig.paddingHeight/2;
        return rect;
    }

    public static class StateForm{
        private State state;
        private Rect rect;
        private Point point;

        public StateForm(State state, Rect rect, Point point) {
            this.state = state;
            this.rect = rect;
            this.point = point;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }
    }
}
