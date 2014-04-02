/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sergio
 */
public class Feature implements Serializable{
	
	private String name;
	private ArrayList<String> possibleValues;
	public static ArrayList<Feature> possibleFeatures = new ArrayList<>();

	public String getName() {
		return name;
	}

	public ArrayList<String> getPossibleValues() {
		return possibleValues;
	}
	
	public Feature(String name, String... values) {
		this.name = name;
		possibleValues = new ArrayList<>();
		for (String s : values) {
			possibleValues.add(s);
		}
		possibleFeatures.add(this);
	}
}
