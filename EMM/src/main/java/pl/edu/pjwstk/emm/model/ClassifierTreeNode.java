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

	Feature feature;
	ClassifierTreeNode parent;
	ArrayList<ClassifierTreeNode> children;
	Integer level;
	String branchLabel = "";

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

	public void setParent(ClassifierTreeNode node) {
		this.parent = node;
	}
	
	@Override
	public String toString(){
		String line = "";
		line+="Node: "+feature.getName()+" ("+level+") [";
		for (ClassifierTreeNode node : children) {
			line+=node.feature.getName()+", ";
		}
		line+="]";
		return line;
	}
}
