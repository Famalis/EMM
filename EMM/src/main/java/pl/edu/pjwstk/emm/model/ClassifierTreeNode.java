/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pjwstk.emm.model;

import java.util.ArrayList;

/**
 *
 * @author sergio
 */
public class ClassifierTreeNode {

    private Feature feature;
    private ClassifierTreeNode parent;
    private ArrayList<ClassifierTreeNode> children;
    private Integer level;
    private String branchLabel = "";
    private String classValue = null;

    public String getClassValue() {
        return classValue;
    }

    public void setClassValue(String classValue) {
        this.classValue = classValue;
    }
    
    public boolean isLeaf() {
        if(children.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBranchLabel() {
        return branchLabel;
    }

    public void setBranchLabel(String branchLabel) {
        this.branchLabel = branchLabel;
    }
    
    public ClassifierTreeNode() {
        children = new ArrayList<>();
        level = 0;
    }

    public void addChild(ClassifierTreeNode node) {
        if (!children.contains(node)) {
            children.add(node);
            node.setParent(node);
        }
    }

    public void addChildren(ArrayList<ClassifierTreeNode> nodes) {
        for (ClassifierTreeNode node : nodes) {
            this.addChild(node);
        }
    }

    public ArrayList<ClassifierTreeNode> getChildren() {
        return children;
    }

    public void setParent(ClassifierTreeNode node) {
        this.parent = node;
        if (!node.getChildren().contains(this)) {
            node.addChild(this);
        }
    }

    @Override
    public String toString() {
        String line = "";
        if(classValue != null ){
            return classValue;
        }
        line += "Node: " + feature.getName() + " (" + level + ") [";
        for (ClassifierTreeNode node : children) {
            line += node.feature.getName() + ", ";
        }
        line += "]";
        
        return line;
    }
}
