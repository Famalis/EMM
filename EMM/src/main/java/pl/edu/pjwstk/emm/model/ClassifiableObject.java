/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
public class ClassifiableObject implements Serializable{
	private static final Logger LOG = Logger.getLogger(ClassifiableObject.class.getName());
	
	
	private ArrayList<ObjectFeature> features;
	private boolean acceptable;
	
	public ClassifiableObject() {
		this.features = new ArrayList<>();
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
	
	@Override
	public String toString(){
		String line = "";
		for (ObjectFeature oc : features) {
			line+=oc.getValue()+" ";
		}
		return line+acceptable;
	}
	
	@Override
	public boolean equals(Object obj) {
		ClassifiableObject co;
		try {
			co = (ClassifiableObject) obj;
		} catch (Exception e) {
			LOG.warning("Can't compare diffrent object than ClassifiableObject");
			e.printStackTrace();
			return false;
		}
		for (int i = 0; i<this.features.size(); i++) {
			if(!this.features.get(i).getValue()
					.equals(co.features.get(i).getValue())) {
				return false;
			}
		}
		return true;
	}

	
}
