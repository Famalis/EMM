/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.model;

import java.util.ArrayList;
import java.util.Arrays;
import pl.edu.pjwstk.emm.utils.ObjectClassifier;

/**
 *
 * @author sergio
 */
public class ClassifierTree {
	
	ArrayList<ClassifierTreeNode> nodes = new ArrayList<>();
	ArrayList<Integer> usedFeaturs = new ArrayList<>();
	Integer maxLevel = 0;
	
	public ClassifierTree(ObjectClassifier oc) {
		double[] gains = new double[Feature.possibleFeatures.size()];
		for (int i = 0; i<Feature.possibleFeatures.size(); i++) {
			gains[i] = oc.gain(i);
		}
		Arrays.sort(gains);
		for (int i = gains.length; i>0; i--) {
			ClassifierTreeNode node = new ClassifierTreeNode();
			usedFeaturs.add(i);
				node.feature = Feature.possibleFeatures.get(gains.length - i);
				node.level = maxLevel;
				maxLevel++;
				System.out.println((gains.length - i)+" "+node.feature.getName());
			if(!usedFeaturs.contains(gains.length - i)) {
				
			}
			
			
		}
	}
}
