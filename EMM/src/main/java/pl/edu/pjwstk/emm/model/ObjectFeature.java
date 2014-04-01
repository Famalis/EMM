/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.model;

/**
 *
 * @author Sergio
 */
public class ObjectFeature {
	
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
	
}
