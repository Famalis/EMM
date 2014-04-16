/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pjwstk.teaclassifier.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.pjwstk.teaclassifier.model.Tea;

/**
 *
 * @author sergio
 */
public class SetGenerator {

	public SetGenerator() {

	}

	public static void main(String[] args) {
		SetGenerator sg = new SetGenerator();
		sg.makeFile();
	}

	public void makeFile() {
		List<Tea> trainingSet = new ArrayList<>();

		try {
			FileWriter fw = new FileWriter(new File("teaData"));
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < 100; i++) {
				Tea t = new Tea();
				int ran = getIntRandom();
				t.setTeaType(Tea.TEA_TYPE_VALUES[ran]);
				ran = getIntRandom();
				t.setAddition(Tea.ADDITION_VALUES[ran]);
				double sugar = Math.random() * 110;
				String sugarStr = sugar+"";
				t.setSugar(Double.parseDouble(sugarStr.substring(0,5)));
				setClass(t);
				trainingSet.add(t);
				bw.write(t.getTeaType()+","+t.getSugar()+","+t.getAddition()+","+t.isDrinkable());
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException ex) {
			Logger.getLogger(SetGenerator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private int getIntRandom() {
		double ran = (Math.random() * 3);
		int intRan = (int) ran;
		return intRan;
	}

	private void setClass(Tea t) {
		if (t.getTeaType().equals(Tea.BLACK_TEA)) {
			t.setDrinkable(true);
		}
		if (t.getTeaType().equals(Tea.GREEN_TEA)
				&& t.getSugar() <= 50
				&& !t.getAddition().equals(Tea.MILK)) {
			t.setDrinkable(true);
		} else if (t.getTeaType().equals(Tea.GREEN_TEA)
				&& t.getSugar() <= 100
				&& t.getAddition().equals(Tea.LEMON)) {
			t.setDrinkable(true);
		}
		if (t.getTeaType().equals(Tea.WHITE_TEA)
				&& t.getSugar() >= 50
				&& t.getAddition().equals(Tea.MILK)) {
			t.setDrinkable(true);
		} else if (t.getTeaType().equals(Tea.WHITE_TEA)
				&& !t.getAddition().equals(Tea.LEMON)) {
			t.setDrinkable(true);
		}
	}
}
