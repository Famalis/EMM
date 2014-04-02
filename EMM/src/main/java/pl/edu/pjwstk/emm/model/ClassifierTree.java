/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pjwstk.emm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import pl.edu.pjwstk.emm.utils.ObjectClassifier;

/**
 *
 * @author sergio
 */
public class ClassifierTree {

	private static final Logger LOG = Logger.getLogger(ClassifierTree.class.getName());

	ClassifierTreeNode root;
	Integer maxLevel = 0;

	public ClassifierTree(ObjectClassifier oc) {
		ArrayList<Integer> freeFeatures = new ArrayList<>();
		double[] gains = new double[Feature.possibleFeatures.size()];
		int bestId = -1;
		double bestGain = -10;
		for (int i = 0; i < Feature.possibleFeatures.size(); i++) {
			if (oc.gain(i) > bestGain) {
				bestGain = oc.gain(i);
				bestId = i;
			}
			freeFeatures.add(i);
		}
		root = new ClassifierTreeNode();
		root.feature = Feature.possibleFeatures.get(bestId);
		freeFeatures.remove(bestId);
		root.level = maxLevel;
		String[] strArr = new String[Feature.possibleFeatures.size()];
		getChildrenForNode(oc, root, 0, freeFeatures, strArr);
	}

	private void getChildrenForNode(ObjectClassifier oc, ClassifierTreeNode parent, int level, ArrayList<Integer> freeFeatures, String[] values) {
		ArrayList<Integer> freeFeatInstance = new ArrayList<>();
		freeFeatInstance.addAll(freeFeatures);
		Integer bestId = -1;
		double bestGain = -10.0;
		for (Integer in : freeFeatInstance) {
			if (oc.gain(in) > bestGain) {
				bestId = in;
				bestGain = oc.gain(in);
			}
		}
		if (bestId > -1) {
			freeFeatInstance.remove(bestId);
		}

		for (String val : parent.feature.getPossibleValues()) {
			values[level] = val;
			System.out.println(Arrays.toString(values));

			ClassifierTreeNode child = new ClassifierTreeNode();
			child.branchLabel = val;
			child.feature = Feature.possibleFeatures.get(bestId);
			child.level = level + 1;
			getChildrenForNode(oc, child, level + 1, freeFeatInstance, values);
			if (!oc.getSubsetContainingValues(values).isEmpty()
					&& !freeFeatInstance.isEmpty()) {
				parent.addChild(child);

			} else {
			}

		}
	}

	@Override
	public String toString() {
		String strTree = "";
		strTree = getChildrenStringTree(strTree, root);
		String str = "";
		System.out.println(getChildrenString(str, root));
		return strTree;
	}

	public String getChildrenStringTree(String tree, ClassifierTreeNode parent) {
		tree += "\n";
		tree += "|";
		for (int i = 0; i < parent.level; i++) {
			tree += "-";
		}
		tree += parent.feature.getName();
		for (ClassifierTreeNode node : parent.children) {
			tree += getChildrenStringTree(tree, node);
		}
		return tree;
	}

	public String getChildrenString(String str, ClassifierTreeNode parent) {
		str += parent.feature.getName() + " (" + parent.level + ") [";
		for (ClassifierTreeNode node : parent.children) {
			str += node.feature.getName() + ", ";
		}
		str += "]\n";
		for (ClassifierTreeNode node : parent.children) {
			str += getChildrenString(str, node);
		}
		return str;
	}

	public void printNodes() {
		System.out.println("Print nodes");
		printNodesForParent(root);
	}

	private void printNodesForParent(ClassifierTreeNode node) {
		String line = "|";
		if (node == null) {
			return;
		}
		for (int i = 0; i < node.level; i++) {
			line += "-";
		}
		line += node.feature.getName() + "(" + node.level + ") [" + node.branchLabel + "]";
		System.out.println(line);
		if (!node.children.isEmpty()) {
			for (ClassifierTreeNode n : node.children) {
				printNodesForParent(n);
			}
		}
	}

}
