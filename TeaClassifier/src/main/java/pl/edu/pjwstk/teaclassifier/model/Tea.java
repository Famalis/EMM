/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.teaclassifier.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pl.edu.pjwstk.teaclassifier.model.tree.TeaNode;

/**
 *
 * @author sergio
 */
public class Tea implements Serializable, Comparable{
	
	public static final String 
			WHITE_TEA = "white tea",
			BLACK_TEA = "black tea",
			GREEN_TEA = "green tea",
			MILK = "milk",
			LEMON = "lemon",
			NONE = "none";
	public static final String[] TEA_TYPE_VALUES = {WHITE_TEA, BLACK_TEA, GREEN_TEA};
	public static final String[] ADDITION_VALUES = {MILK, LEMON, NONE};
	private Double sugar;
	private String teaType;
	private String addition;
	private boolean drinkable;

	public Double getSugar() {
		return sugar;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	public String getTeaType() {
		return teaType;
	}

	public void setTeaType(String teaType) {
		this.teaType = teaType;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public boolean isDrinkable() {
		return drinkable;
	}

	public void setDrinkable(boolean drinkable) {
		this.drinkable = drinkable;
	}
	
	public Tea(String type, Double sugar, String add) {
		super();
		this.drinkable = false;
		this.addition = add;
		this.sugar = sugar;
		this.teaType = type;
	}
    
    public Tea(String type, Double sugar, String add, boolean b) {
		super();
		this.drinkable = false;
		this.addition = add;
		this.sugar = sugar;
		this.teaType = type;
        this.drinkable = b;
	}
	
	public Tea() {
		super();
		this.drinkable = false;
	}

	@Override
	public int compareTo(Object o) {
		Tea t = (Tea) o;
		return (int)(t.getSugar() - this.sugar);
	}
	
	@Override
	public String toString() {
		return this.teaType+", "+this.sugar+" g, "+this.addition+" => "+this.drinkable;
	}
	
	public static List<Tea> convertTeaNodesToTea(List<TeaNode> nodes) {		
		List<Tea> teas = new ArrayList<>();
		for (TeaNode node : nodes) {
			Tea t = new Tea();
			t.setAddition(node.valuesCombination[2]);
			t.setSugar(Double.parseDouble(node.valuesCombination[1]));
			t.setTeaType(node.valuesCombination[0]);
			teas.add(t);
		}
		return teas;
	}
	
}

