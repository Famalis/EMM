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
	
	public ClassifierTreeNode() {

	}
	
	public void addChild(ClassifierTreeNode node) {
		if(!children.contains(node)) {
			children.add(node);
			node.setParent(node);
		}
	}
	
	public void setParent(ClassifierTreeNode node) {
		this.parent = node;
	}
}
