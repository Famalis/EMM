/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.model;

import java.io.Serializable;

/**
 *
 * @author Sergio
 */
public class ObjectFeature implements Serializable{
	
	private Integer featureId;
	private String value;
	
	public ObjectFeature(Integer id, String value) {
		this.featureId = id;
		this.value = value;
	}
	
	public Feature getFeature() {
		return Feature.possibleFeatures.get(featureId);
	}

	public Integer getFeatureId() {
		return featureId;
	}

	public String getValue() {
		return value;
	}
    
    @Override
    public boolean equals(Object obj) {
        ObjectFeature of = (ObjectFeature) obj;
        if(of.featureId.equals(this.featureId)
                && of.value.equals(this.value)) {
            return true;
        } else {
            return false;
        }
    }
	
}
