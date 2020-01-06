package com.zl.tree;

import java.util.ArrayList;

/**
 * Time: 2019/12/16 0016
 * Author: zoulong
 */
public class TreeData {
    private String treeName;
    private ArrayList<Trunk> trunks;
    private State state = State.NORMAL;
    private String des;
    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public ArrayList<Trunk> getTrunks() {
        return trunks;
    }

    public void setTrunks(ArrayList<Trunk> trunks) {
        this.trunks = trunks;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static class Trunk{
        private String trunkName;
        private ArrayList<Branch> branches;
        private State state = State.NORMAL;
        private String des;
        public String getTrunkName() {
            return trunkName;
        }

        public void setTrunkName(String trunkName) {
            this.trunkName = trunkName;
        }

        public ArrayList<Branch> getBranches() {
            return branches;
        }

        public void setBranches(ArrayList<Branch> branches) {
            this.branches = branches;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public static class Branch{
            private String branchName;
            private ArrayList<Leaf> leafs;
            private State state = State.NORMAL;
            private String des;
            public String getBranchName() {
                return branchName;
            }

            public void setBranchName(String branchName) {
                this.branchName = branchName;
            }

            public ArrayList<Leaf> getLeafs() {
                return leafs;
            }

            public void setLeafs(ArrayList<Leaf> leafs) {
                this.leafs = leafs;
            }

            public State getState() {
                return state;
            }

            public void setState(State state) {
                this.state = state;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public static class Leaf{
                private String leafName;
                private State state = State.NORMAL;
                private String des;
                public String getLeafName() {
                    return leafName;
                }

                public void setLeafName(String leafName) {
                    this.leafName = leafName;
                }

                public State getState() {
                    return state;
                }

                public void setState(State state) {
                    this.state = state;
                }

                public String getDes() {
                    return des;
                }

                public void setDes(String des) {
                    this.des = des;
                }
            }
        }
    }
}
