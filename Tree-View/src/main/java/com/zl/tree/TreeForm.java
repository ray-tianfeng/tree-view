package com.zl.tree;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * 树形将整个树数据化
 * Time: 2019/12/16 0016
 * Author: zoulong
 */
public class TreeForm {
    private Rect regionRect;
    private ArrayList<TrunkForm> trunkForms;
    public int width;
    public int height;
    private TreeConfig treeConfig;
    private TreeData treeData;
    private boolean isShowDes;
    public int nodeMaxWidth = 0;//通过遍历整个树找出最长的一个节点（用于居中）
    public TreeForm(TreeConfig treeConfig, TreeData treeData) {
        this.treeConfig = treeConfig;
        this.treeData = treeData;
        nodeMaxWidth = findTreeMaxNodeWidthByData();
        initTreeForm();
        calculationSpace();
    }

    /**
     * 是否命中树
     * @param x 点击x
     * @param y 点击y
     * @return
     */
    public boolean isHit(int x, int y){
        if(regionRect.contains(x, y)){
            setShowDes(true);
            return true;
        }
        for(TrunkForm trunkForm : trunkForms){
            if(trunkForm.getTrunkRect().contains(x, y)){
                trunkForm.setShowDes(true);
                return true;
            }
            for(TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                if(branchForm.getBranchRect().contains(x, y)){
                    branchForm.setShowDes(true);
                    return true;
                }
                for(TrunkForm.BranchForm.LeafForm leafForm: branchForm.leafForms){
                    if(leafForm.getLeafRect().contains(x, y)){
                        leafForm.setShowDes(true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void cancelShowDes(){
        setShowDes(false);
        for(TrunkForm trunkForm : trunkForms){
            trunkForm.setShowDes(false);
            for(TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                branchForm.setShowDes(false);
                for(TrunkForm.BranchForm.LeafForm leafForm: branchForm.leafForms){
                    leafForm.setShowDes(false);
                }
            }
        }
    }

    private void initTreeForm(){
        Rect treeRect = measureText(treeData.getTreeName());
        treeRect.bottom = treeRect.height();
        treeRect.top = 0;
        setRegionRect(treeRect);
        setTrunkForms(new ArrayList<TrunkForm>());
        for(int t = 0; t < treeData.getTrunks().size(); t++){//横向树枝
            TreeData.Trunk trunk = treeData.getTrunks().get(t);
            Rect trunkRect = measureText(trunk.getTrunkName());
            int trunkCenterX = (nodeMaxWidth + treeConfig.trunkWidth) * t + findTrunkMaxNodeWidthByData(trunk)/2;
            landscapeCenter(trunkCenterX, trunkRect);
            trunkRect.bottom = treeRect.bottom + trunkRect.height() + (int)(treeConfig.trunkHeight*1.5);
            trunkRect.top = treeRect.bottom + (int)(treeConfig.trunkHeight*1.5);
            TrunkForm trunkForm = new TrunkForm();
            trunkForm.setTrunk(trunk);
            trunkForm.setBranchForms(new ArrayList<TrunkForm.BranchForm>());
            trunkForm.setTrunkRect(trunkRect);
            getTrunkForms().add(trunkForm);

            if(trunk.getBranches() == null) continue;
            int branchBaseTop = trunkRect.bottom + treeConfig.trunkHeight;
            for(int b = 0; b < trunk.getBranches().size(); b++){
                TreeData.Trunk.Branch branch = trunk.getBranches().get(b);
                Rect branchRect = measureText(branch.getBranchName());
                landscapeCenter(trunkCenterX, branchRect);
                branchRect.bottom = branchRect.height() + branchBaseTop;
                branchRect.top = branchBaseTop;
                branchBaseTop = (branchRect.bottom + treeConfig.trunkHeight > branchBaseTop) ?  branchRect.bottom + treeConfig.trunkHeight : branchBaseTop;

                TrunkForm.BranchForm branchForm = new TrunkForm.BranchForm();
                branchForm.setLeafForms(new ArrayList<TrunkForm.BranchForm.LeafForm>());
                branchForm.setBranchRect(branchRect);
                branchForm.setBranch(branch);
                trunkForm.getBranchForms().add(branchForm);

                if(branch.getLeafs() == null) continue;
                for(int f = 0; f < branch.getLeafs().size(); f++){
                    TreeData.Trunk.Branch.Leaf leaf = branch.getLeafs().get(f);
                    Rect leafRect = measureText(leaf.getLeafName());
                    int leafCenterX = branchRect.right + treeConfig.leafWidth + nodeMaxWidth/2;
                    landscapeCenter(leafCenterX, leafRect);
                    if(f == 0){
                        leafRect.bottom = leafRect.height() + branchRect.bottom + treeConfig.leafHeight;
                        leafRect.top = branchRect.bottom + treeConfig.leafHeight;
                    }else{
                        TrunkForm.BranchForm.LeafForm leafForm = branchForm.getLeafForms().get(f - 1);
                        leafRect.bottom = leafRect.height() + leafForm.getLeafRect().bottom + treeConfig.leafHeight;
                        leafRect.top = leafForm.getLeafRect().bottom + treeConfig.leafHeight;
                    }

                    TrunkForm.BranchForm.LeafForm leafForm = new TrunkForm.BranchForm.LeafForm();
                    leafForm.setLeafRect(leafRect);
                    leafForm.setLeaf(leaf);
                    branchForm.getLeafForms().add(leafForm);

                    branchBaseTop = (leafRect.bottom  > branchBaseTop) ?  leafRect.bottom : branchBaseTop;
                }
            }
        }
        TrunkForm lastTrunkForm = getTrunkForms().get(getTrunkForms().size() - 1);
        int treeCenterX = lastTrunkForm.getTrunkRect().right / 2;
        landscapeCenter(treeCenterX, treeRect);
    }

    private void landscapeCenter(int centerX, Rect rect){
        int diff = centerX - rect.centerX();
        rect.left = rect.left + diff;
        rect.right = rect.right + diff;
    }

    private int findTreeMaxNodeWidthByData(){
        int nodeMaxWidth = 0;
        for(TreeData.Trunk trunk : treeData.getTrunks()){
            int trunkWidth = measureText(trunk.getTrunkName()).width();
            int trunkNodeMaxWidth = findTrunkMaxNodeWidthByData(trunk);
            if(trunkWidth > nodeMaxWidth){
                nodeMaxWidth = trunkWidth;
            }
            if(trunkNodeMaxWidth > nodeMaxWidth){
                nodeMaxWidth = trunkNodeMaxWidth;
            }
        }
        return nodeMaxWidth;
    }

    private int findTrunkMaxNodeWidthByData(TreeData.Trunk trunk){
        int nodeMaxWidth = 0;
        for(TreeData.Trunk.Branch branch : trunk.getBranches()){
            int branchWidth = measureText(branch.getBranchName()).width();
            if(branchWidth > nodeMaxWidth){
                nodeMaxWidth = branchWidth;
            }
            if(branch.getLeafs() != null){
                for(TreeData.Trunk.Branch.Leaf leaf : branch.getLeafs()){
                    int leafWidth = measureText(leaf.getLeafName()).width();
                    if(leafWidth > nodeMaxWidth){
                        nodeMaxWidth = leafWidth;
                    }
                }
            }
        }
        return nodeMaxWidth;
    }

    private void calculationSpace(){
        for(TrunkForm trunkForm : trunkForms){
            width = (width > trunkForm.getTrunkRect().right) ? width : trunkForm.getTrunkRect().right;
            height = (height > trunkForm.getTrunkRect().bottom) ? height : trunkForm.getTrunkRect().bottom;
            for(TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                width = (width > branchForm.getBranchRect().right) ? width : branchForm.getBranchRect().right;
                height = (height > branchForm.getBranchRect().bottom) ? height : branchForm.getBranchRect().bottom;
                for(TrunkForm.BranchForm.LeafForm leafForm : branchForm.getLeafForms()){
                    width = (width > leafForm.getLeafRect().right) ? width : leafForm.getLeafRect().right;
                    height = (height >  leafForm.getLeafRect().bottom) ? height : leafForm.getLeafRect().bottom;
                }
            }
        }
    }

    public void moveTree(int x, int y){
        regionRect.offset(x, y);
        for(TrunkForm trunkForm : getTrunkForms()){
            trunkForm.getTrunkRect().offset(x, y);
            if(trunkForm.getBranchForms() == null) continue;
            for(TrunkForm.BranchForm branchForm : trunkForm.getBranchForms()){
                branchForm.getBranchRect().offset(x, y);
                if(branchForm.getLeafForms() == null) continue;
                for(TrunkForm.BranchForm.LeafForm leafForm : branchForm.getLeafForms()){
                    leafForm.getLeafRect().offset(x, y);
                }
            }
        }
    }

    public Rect measureText(String name){
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

    public String getTreeName() {
        return treeData.getTreeName();
    }


    public Rect getRegionRect() {
        return regionRect;
    }

    public void setRegionRect(Rect regionRect) {
        this.regionRect = regionRect;
    }

    public ArrayList<TrunkForm> getTrunkForms() {
        return trunkForms;
    }

    public void setTrunkForms(ArrayList<TrunkForm> trunkForms) {
        this.trunkForms = trunkForms;
    }

    public State getState() {
        return treeData.getState();
    }


    public String getDes() {
        return treeData.getDes();
    }

    public boolean isShowDes() {
        return isShowDes;
    }

    public void setShowDes(boolean showDes) {
        isShowDes = showDes;
    }

    public static class TrunkForm{
        private Rect trunkRect;
        private TreeData.Trunk trunk;
        private ArrayList<BranchForm> branchForms;
        private boolean isShowDes;

        public void setTrunk(TreeData.Trunk trunk) {
            this.trunk = trunk;
        }

        public ArrayList<BranchForm> getBranchForms() {
            return branchForms;
        }

        public void setBranchForms(ArrayList<BranchForm> branchForms) {
            this.branchForms = branchForms;
        }

        public String getTrunkName() {
            return trunk.getTrunkName();
        }


        public Rect getTrunkRect() {
            return trunkRect;
        }

        public void setTrunkRect(Rect trunkRect) {
            this.trunkRect = trunkRect;
        }

        public State getState() {
            return trunk.getState();
        }

        public String getDes() {
            return trunk.getDes();
        }

        public boolean isShowDes() {
            return isShowDes;
        }

        public void setShowDes(boolean showDes) {
            isShowDes = showDes;
        }

        public static class BranchForm{
            private Rect branchRect;
            private ArrayList<LeafForm> leafForms;
            private TreeData.Trunk.Branch branch;
            private boolean isShowDes;

            public void setBranch(TreeData.Trunk.Branch branch) {
                this.branch = branch;
            }

            public String getBranchName() {
                return branch.getBranchName();
            }

            public Rect getBranchRect() {
                return branchRect;
            }

            public void setBranchRect(Rect branchRect) {
                this.branchRect = branchRect;
            }

            public ArrayList<LeafForm> getLeafForms() {
                return leafForms;
            }

            public void setLeafForms(ArrayList<LeafForm> leafForms) {
                this.leafForms = leafForms;
            }

            public State getState() {
                return branch.getState();
            }

            public String getDes() {
                return branch.getDes();
            }

            public boolean isShowDes() {
                return isShowDes;
            }

            public void setShowDes(boolean showDes) {
                isShowDes = showDes;
            }

            public static class LeafForm{
                private Rect leafRect;
                private TreeData.Trunk.Branch.Leaf leaf;
                private boolean isShowDes;

                public void setLeaf(TreeData.Trunk.Branch.Leaf leaf) {
                    this.leaf = leaf;
                }

                public String getLeafName() {
                    return leaf.getLeafName();
                }

                public Rect getLeafRect() {
                    return leafRect;
                }

                public void setLeafRect(Rect leafRect) {
                    this.leafRect = leafRect;
                }

                public State getState() {
                    return leaf.getState();
                }


                public String getDes() {
                    return leaf.getDes();
                }


                public boolean isShowDes() {
                    return isShowDes;
                }

                public void setShowDes(boolean showDes) {
                    isShowDes = showDes;
                }
            }
        }
    }
}
