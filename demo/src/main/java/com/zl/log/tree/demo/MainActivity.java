package com.zl.log.tree.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zl.tree.State;
import com.zl.tree.TreeConfig;
import com.zl.tree.TreeData;
import com.zl.tree.TreeView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeData treeData = new TreeData();
        treeData.setTreeName("树头");
        treeData.setDes("树头");

        TreeData.Trunk trunk = new TreeData.Trunk();
        trunk.setTrunkName("横向树枝");
        trunk.setDes("横向树枝");
        trunk.setState(State.SUCCESS);
        TreeData.Trunk trunk2 = new TreeData.Trunk();
        trunk2.setTrunkName("横向树枝变大版本");
        trunk2.setDes("横向树枝");
        trunk2.setState(State.SUCCESS);
        ArrayList<TreeData.Trunk> trunks = new ArrayList<>();
        trunks.add(trunk);
        trunks.add(trunk);
//        trunks.add(trunk);
//        trunks.add(trunk2);

        TreeData.Trunk.Branch branch = new TreeData.Trunk.Branch();
        branch.setBranchName("纵向树枝1");
        branch.setDes("纵向树枝1");
        branch.setState(State.ERROR);
        ArrayList<TreeData.Trunk.Branch> branches = new ArrayList<>();
        branches.add(branch);
        branches.add(branch);
        branches.add(branch);
        branches.add(branch);
        branches.add(branch);
        branches.add(branch);

        TreeData.Trunk.Branch.Leaf leaf = new TreeData.Trunk.Branch.Leaf();
        leaf.setLeafName("树叶");
        leaf.setDes("树叶");
        leaf.setState(State.WARN);
        ArrayList<TreeData.Trunk.Branch.Leaf> leafs = new ArrayList<>();
        leafs.add(leaf);
        leafs.add(leaf);
        leafs.add(leaf);
        leafs.add(leaf);
        leafs.add(leaf);

        branch.setLeafs(leafs);
        trunk.setBranches(branches);
        trunk2.setBranches(branches);
        treeData.setTrunks(trunks);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        TreeConfig treeConfig = new TreeConfig();
        treeConfig.setBackGroundColor(Color.WHITE);
        TreeView treeView = new TreeView(this, treeData, new TreeConfig());
        relativeLayout.addView(treeView);
        treeView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundResource(R.drawable.bg);
        setContentView(relativeLayout);
    }
}
