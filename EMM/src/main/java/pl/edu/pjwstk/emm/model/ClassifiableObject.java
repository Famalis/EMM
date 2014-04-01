/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Sergio
 */
public class ClassifiableObject {
	
	private ArrayList<ObjectFeature> features;
	private boolean acceptable;
	
	public ClassifiableObject() {
		
	}
	
	public ClassifiableObject(boolean acceptable, ObjectFeature... features) {
		this.features = new ArrayList<>();
		this.features.addAll(Arrays.asList(features));
		this.acceptable = acceptable;
	}

	public ArrayList<ObjectFeature> getObjectFeatures() {
		return features;
	}

	public boolean isAcceptable() {
		return acceptable;
	}

	public void setFeatures(ArrayList<ObjectFeature> features) {
		this.features = features;
	}

	public void setAcceptable(boolean acceptable) {
		this.acceptable = acceptable;
	}
	
	public String toString(){
		String line = "";
		for (ObjectFeature oc : features) {
			line+=oc.getValue()+" ";
		}
		return line+acceptable;
	}

	
}
