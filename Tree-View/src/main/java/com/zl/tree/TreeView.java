package com.zl.tree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Time: 2019/12/16 0016
 * Author: zoulong
 */
public class TreeView extends View {
    private int screenWidth;
    private int screenHeight;
    private TreeForm treeForm;
    private StateCalculate stateCalculate;
    private TreeConfig treeConfig;
    Paint paintRect = new Paint();
    Paint paintText = new Paint();
    Paint paintLine = new Paint();
    Paint paintDesDrop = new Paint();
    Paint paintDesText = new Paint();
    float moveEventX = 0;
    float moveEventY = 0;
    float clickEventX = 0;
    float clickEventY = 0;

    private int viewWidth;
    private int viewHeight;
    public TreeView(Context context, TreeData treeData, TreeConfig treeConfig) {
        super(context);
        init(treeData, treeConfig);
    }

    public TreeView(Context context, AttributeSet attrs, TreeData treeData, TreeConfig treeConfig) {
        super(context, attrs);
        init(treeData, treeConfig);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr, TreeData treeData, TreeConfig treeConfig) {
        super(context, attrs, defStyleAttr);
        init(treeData, treeConfig);
    }

    private void init(TreeData treeData, TreeConfig treeConfig) {
        this.treeConfig = treeConfig;
        Display display = ((WindowManager)getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        treeForm = new TreeForm(treeConfig, treeData);
        stateCalculate = new StateCalculate(treeConfig);
        arrangement();
        resetPaint();
        setFocusableInTouchMode(true);
    }

    public void arrangement(){
        if(treeForm.width + stateCalculate.width + treeConfig.interval_state < screenWidth){
            viewWidth = treeForm.width + stateCalculate.width + treeConfig.interval_state;
            stateCalculate.move(treeForm.width + treeConfig.interval_state, treeConfig.state_margin_top);
        }else{
            viewWidth = screenWidth;
            stateCalculate.move(screenWidth - stateCalculate.width, treeConfig.state_margin_top);
        }
        if(treeForm.height > stateCalculate.height + treeConfig.state_margin_top){
            if(treeForm.height > screenHeight){
                viewHeight = screenHeight;
            }else{
                viewHeight = treeForm.height;
            }
        }else{
            if(stateCalculate.height + treeConfig.state_margin_top > screenHeight){
                viewHeight = screenHeight;
            }else{
                viewHeight = stateCalculate.height + treeConfig.state_margin_top;
            }
        }
    }

    private void resetPaint(){
        paintDesDrop.reset();
        paintText.reset();
        paintRect.reset();
        paintLine.reset();
        paintDesText.reset();
        paintRect.setAntiAlias(true);
        paintRect.setColor(Color.BLACK);
        paintRect.setDither(true);
        paintRect.setStrokeWidth(1);
        paintRect.setStyle(Paint.Style.STROKE);
        paintRect.setStrokeJoin(Paint.Join.ROUND);

        paintText.setAntiAlias(true);
        paintText.setColor(Color.BLACK);
        paintText.setDither(true);
        paintText.setStrokeWidth(1);
        paintText.setTextSize(treeConfig.textSize);

        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setDither(true);
        paintLine.setStrokeWidth(1);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeJoin(Paint.Join.ROUND);

        paintDesDrop.setAntiAlias(true);
        paintDesDrop.setColor(Color.BLACK);
        paintDesDrop.setDither(true);
        paintDesDrop.setAlpha(150);

        paintDesText.setAntiAlias(true);
//        paintDesText.setColor(Color.parseColor("#B0B0B0"));
        paintDesText.setColor(Color.WHITE);
        paintDesText.setDither(true);
        paintDesText.setStrokeWidth(1);
        paintDesText.setTextAlign(Paint.Align.CENTER);
        paintDesText.setTextSize(treeConfig.textSize - 5);
    }

    private void setPaintByState(State state){
        paintRect.reset();
        paintRect.setAntiAlias(true);
        paintRect.setColor(state.color);
        paintRect.setDither(true);
        paintRect.setStrokeWidth(1);
        paintRect.setStyle(Paint.Style.STROKE);

        paintText.reset();
        paintText.setAntiAlias(true);
        paintText.setColor(state.color);
        paintText.setDither(true);
        paintText.setStrokeWidth(1);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(treeConfig.textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //step1 画背景
        canvas.drawColor(treeConfig.backGroundColor);
        //step2 画树行
        drawTree(canvas);
        //step3画树形间的连线
        drawTreeLine(canvas);
        //step4 话状态
        drawState(canvas);
        //step5 画节点描述信息
        drawDes(canvas);
        //画笔重置
        resetPaint();
        //数据更新后可以刷新页面展示新数据
        postInvalidateDelayed(1000);
    }

    //画树
    private void drawTree(Canvas canvas){
        //step1 画树名称
        Rect treeRect = treeForm.getRegionRect();
        setPaintByState(treeForm.getState());
        canvas.drawRect(treeRect, paintRect);
        drawTextInRectCenter(canvas, treeForm.getTreeName(), treeRect);
        //step2 画横向树干
        for(TreeForm.TrunkForm trunkForm : treeForm.getTrunkForms()){
            drawTrunk(canvas, trunkForm);
            for(TreeForm.TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                drawBranch(canvas, branchForm);
                for(TreeForm.TrunkForm.BranchForm.LeafForm leafForm : branchForm.getLeafForms()){
                    drawLeaf(canvas, leafForm);
                }
            }
        }
    }
    //画树形间的连线
    private void drawTreeLine(Canvas canvas){
        Path linePath = new Path();
        for(TreeForm.TrunkForm trunkForm : treeForm.getTrunkForms()){
            Rect trunkRect = trunkForm.getTrunkRect();
            linePath.moveTo(treeForm.getRegionRect().centerX(), treeForm.getRegionRect().bottom);
            int compromiseX = treeForm.getRegionRect().centerX() - (treeForm.getRegionRect().centerX() - trunkRect.centerX()) / 2;
            linePath.quadTo(compromiseX, treeForm.getRegionRect().bottom, trunkRect.centerX(), trunkRect.top);
            canvas.drawPath(linePath, paintLine);
            linePath.reset();
            linePath.moveTo(trunkRect.centerX(), trunkRect.bottom);
            for(TreeForm.TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                Rect branchRect = branchForm.getBranchRect();
                linePath.lineTo(branchRect.centerX(), branchRect.top);
                canvas.drawPath(linePath, paintLine);
                linePath.reset();
                for(TreeForm.TrunkForm.BranchForm.LeafForm leafForm : branchForm.getLeafForms()){
                    Rect leafRect = leafForm.getLeafRect();
                    if(leafForm == branchForm.getLeafForms().get(0)){
                        linePath.moveTo(branchRect.right, branchRect.centerY());
                        linePath.quadTo(leafRect.centerX(), branchRect.centerY(), leafRect.centerX(), leafRect.top);
                    }else{
                        linePath.lineTo(leafRect.centerX(), leafRect.top);
                    }
                    canvas.drawPath(linePath, paintLine);
                    linePath.reset();
                    linePath.moveTo(leafRect.centerX(), leafRect.bottom);
                }
                linePath.reset();
                linePath.moveTo(branchRect.centerX(), branchRect.bottom);
            }
        }
    }
    //画横向树枝
    public void drawTrunk(Canvas canvas, TreeForm.TrunkForm trunkForm){
        Rect trunkRect = trunkForm.getTrunkRect();
        setPaintByState(trunkForm.getState());
        canvas.drawRect(trunkRect, paintRect);
        drawTextInRectCenter(canvas, trunkForm.getTrunkName(), trunkRect);
    }
    //画纵向树枝
    public void drawBranch(Canvas canvas, TreeForm.TrunkForm.BranchForm branchForm){
        Rect branchRect = branchForm.getBranchRect();
        setPaintByState(branchForm.getState());
        canvas.drawRect(branchRect, paintRect);
        drawTextInRectCenter(canvas, branchForm.getBranchName(), branchRect);
    }
    //画树叶
    public void drawLeaf(Canvas canvas, TreeForm.TrunkForm.BranchForm.LeafForm leafForm){
        Rect leafRect = leafForm.getLeafRect();
        setPaintByState(leafForm.getState());
        canvas.drawRect(leafRect, paintRect);
        drawTextInRectCenter(canvas, leafForm.getLeafName(), leafRect);
    }
    //画状态
    private void drawState(Canvas canvas) {
        for(StateCalculate.StateForm stateForm : stateCalculate.getStateForms()){
            State state = stateForm.getState();
            paintText.setColor(state.color);
            paintRect.setColor(state.color);
            canvas.drawRect(stateForm.getRect(), paintRect);
            drawTextInRectCenter(canvas, state.name, stateForm.getRect());
            paintText.setStrokeWidth(10);
            canvas.drawPoint(stateForm.getPoint().x, stateForm.getPoint().y, paintText);
        }
    }

    //画描述信息
    private void drawDes(Canvas canvas){
        if(treeForm.isShowDes() && treeForm.getDes() != null){
            drawDes(canvas, treeForm.getRegionRect().right, treeForm.getRegionRect().centerY(), treeForm.getDes());
        }
        for(TreeForm.TrunkForm trunkForm : treeForm.getTrunkForms()){
            if(trunkForm.isShowDes() && trunkForm.getDes() != null){
                drawDes(canvas, trunkForm.getTrunkRect().right, trunkForm.getTrunkRect().centerY(), trunkForm.getDes());
            }
            for(TreeForm.TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                if(branchForm.isShowDes() && branchForm.getDes() != null){
                    drawDes(canvas, branchForm.getBranchRect().right, branchForm.getBranchRect().centerY(), branchForm.getDes());
                }
                for(TreeForm.TrunkForm.BranchForm.LeafForm leafForm : branchForm.getLeafForms()){
                    if(leafForm.isShowDes() && leafForm.getDes() != null)
                    drawDes(canvas, leafForm.getLeafRect().right, leafForm.getLeafRect().centerY(), leafForm.getDes());
                }
            }
        }
    }

    private void drawDes(Canvas canvas, int anchorX, int anchorY, String message){
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(treeConfig.textSize - 5);
        textPaint.setColor(Color.WHITE);
        StaticLayout layout = new StaticLayout(message,textPaint,120, Layout.Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
        canvas.save();
        RectF rectF = new RectF(0, 0, layout.getWidth() + treeConfig.paddingWidth, layout.getHeight() + treeConfig.getPaddingHeight());
        int translateX = anchorX + 30;
        int translateY = anchorY - (int)rectF.height()/2;
        canvas.translate(translateX, translateY);
        canvas.drawRoundRect(rectF, 10, 10, paintDesDrop);
        Path triangle = new Path();
        triangle.moveTo(-30,rectF.height() / 2);
        triangle.lineTo(0,rectF.height()/2 - 10);
        triangle.lineTo(0,rectF.height()/2 + 10);
        triangle.close();
        canvas.drawPath(triangle, paintDesDrop);
        canvas.translate( treeConfig.paddingWidth / 2, treeConfig.paddingHeight / 2);
        layout.draw(canvas);
        canvas.restore();
    }

    private void drawTextInRectCenter(Canvas canvas, String text, Rect rectD){
        Paint.FontMetrics fontMetrics=paintText.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        float baseline=rectD.centerY()+distance;
        canvas.drawText(text, rectD.centerX(), baseline, paintText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isFocusableInTouchMode()) return false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                moveEventX = event.getX();
                moveEventY = event.getY();
                clickEventX = event.getX();
                clickEventY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                scrollBy((int) (downX - event.getX()), (int) (downY - event.getY()));
//                downX = event.getX();
//                downY = event.getY();
                treeForm.moveTree((int) (event.getX() - moveEventX), (int) (event.getY() - moveEventY));
                moveEventX = event.getX();
                moveEventY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                moveEventX = 0;
                moveEventY = 0;
                if(Math.sqrt((double)((event.getX() - clickEventX)*(event.getX() - clickEventX) + (event.getY() -clickEventY)*(event.getY() -clickEventY))) < 20){
                    onClick((int)clickEventX, (int)clickEventY);
                }else{
                    treeForm.cancelShowDes();
                }
                break;
        }
        postInvalidate();
        return true;
    }

    public void onClick(int x, int y){
        if(!treeForm.isHit(x, y)){
            treeForm.cancelShowDes();
        }
    }

    public TreeConfig getTreeConfig() {
        return treeConfig;
    }

    public Bitmap screenshots(){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        //step2 画树行
        drawTree(canvas);
        //step3画树形间的连线
        drawTreeLine(canvas);
        //step4 话状态
        drawState(canvas);
        //step5 画节点描述信息
        drawDes(canvas);
        //画笔重置
        resetPaint();
        return bitmap;
    }
}
