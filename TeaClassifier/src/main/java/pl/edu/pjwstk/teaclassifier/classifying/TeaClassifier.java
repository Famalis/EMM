package pl.edu.pjwstk.teaclassifier.classifying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.pjwstk.teaclassifier.model.Tea;

public class TeaClassifier {

	private ArrayList<Tea> trainingSet = new ArrayList<>();
	private static String filePath = "teaData";

	public static int TEA_TYPE = 0, SUGAR = 1, ADDITION = 2;
    private double[] sugarGain = new double[2];
	public TeaClassifier() {
		super();
	}

	public ArrayList<Tea> getTrainingSet() {
		return trainingSet;
	}

	public void setTrainingSet(ArrayList<Tea> trainingSet) {
		this.trainingSet = trainingSet;
	}
	
	public void generateTrainingSetFromTxt(String fileName) {
		try {
			File f = new File(fileName);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine())!=null) {
				String[] data = line.split(",");
				Tea t = new Tea();
				t.setAddition(data[2]);
				t.setSugar(Double.valueOf(data[1]));
				t.setDrinkable(Boolean.valueOf(data[3]));
				t.setTeaType(data[0]);
				trainingSet.add(t);
			}
		} catch (Exception ex) {
			Logger.getLogger(TeaClassifier.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void generateTrainingSet() {
		if (trainingSet.isEmpty()) {
			trainingSet = constantSetGenerate();
		}

	}

	private ArrayList<Tea> constantSetGenerate() {
		ArrayList<Tea> list = new ArrayList<>();
		list.add(new Tea(Tea.BLACK_TEA, 0.0, Tea.NONE, true));
		list.add(new Tea(Tea.BLACK_TEA, 10.0, Tea.MILK, true));
		list.add(new Tea(Tea.BLACK_TEA, 30.0, Tea.LEMON, true));
        list.add(new Tea(Tea.BLACK_TEA, 303.0, Tea.LEMON, true));
        list.add(new Tea(Tea.BLACK_TEA, 403.0, Tea.MILK, true));
        list.add(new Tea(Tea.BLACK_TEA, 350.0, Tea.NONE, true));
		list.add(new Tea(Tea.GREEN_TEA, 0.0, Tea.NONE, true));
		list.add(new Tea(Tea.GREEN_TEA, 25.0, Tea.MILK, false));
		list.add(new Tea(Tea.GREEN_TEA, 43.0, Tea.LEMON, true));
        list.add(new Tea(Tea.GREEN_TEA, 0.0, Tea.LEMON, true));
        list.add(new Tea(Tea.GREEN_TEA, 143.0, Tea.LEMON, false));
		list.add(new Tea(Tea.GREEN_TEA, 0.0, Tea.LEMON, true));
		list.add(new Tea(Tea.GREEN_TEA, 139.0, Tea.LEMON, false));
		list.add(new Tea(Tea.WHITE_TEA, 0.0, Tea.NONE, false));
		list.add(new Tea(Tea.WHITE_TEA, 19.0, Tea.MILK, true));
		list.add(new Tea(Tea.WHITE_TEA, 37.0, Tea.LEMON, false));		
		list.add(new Tea(Tea.WHITE_TEA, 150.0, Tea.MILK, true));		
		list.add(new Tea(Tea.WHITE_TEA, 0.0, Tea.LEMON, false));
		list.add(new Tea(Tea.WHITE_TEA, 0.0, Tea.MILK, true));
		return list;
	}

	private double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	private double entropy(double... values) {
		double result = 0.0;
		for (double d : values) {
			result += d * log2(d);
		}
		result *= (double) -1;
		if (Double.isNaN(result)) {
			return 0.0;
		}
		return result;
	}

	public double info() {
		double positive = 0.0;
		for (Tea t : trainingSet) {
			if (t.isDrinkable()) {
				positive++;
			}
		}
		double negative = trainingSet.size() - positive;
		double result = entropy(positive / trainingSet.size(), negative / trainingSet.size());
		return result;
	}

	public double teaTypeInfo() {
		double result = 0.0;
		double blackTeaSize = 0.0;
		double blackGood = 0.0;
		double blackBad = 0.0;
		double whiteTeaSize = 0.0;
		double whitekGood = 0.0;
		double whiteBad = 0.0;
		double greenTeaSize = 0.0;
		double greenGood = 0.0;
		double greenBad = 0.0;
		for (Tea t : trainingSet) {
			if (t.getTeaType().equals(Tea.BLACK_TEA)) {
				blackTeaSize++;
				if (t.isDrinkable()) {
					blackGood++;
				} else {
					blackBad++;
				}
			}
			if (t.getTeaType().equals(Tea.GREEN_TEA)) {
				whiteTeaSize++;
				if (t.isDrinkable()) {
					whitekGood++;
				} else {
					whiteBad++;
				}
			}
			if (t.getTeaType().equals(Tea.WHITE_TEA)) {
				greenTeaSize++;
				if (t.isDrinkable()) {
					greenGood++;
				} else {
					greenBad++;
				}
			}
		}
		result = blackTeaSize / trainingSet.size() * entropy(blackGood / blackTeaSize, blackBad / blackTeaSize);
		result += whiteTeaSize / trainingSet.size() * entropy(whitekGood / whiteTeaSize, whiteBad / whiteTeaSize);
		result += greenTeaSize / trainingSet.size() * entropy(greenGood / greenTeaSize, greenBad / greenTeaSize);
		return result;
	}

	public double additionInfo() {
		double result = 0.0;
		double milkSize = 0.0;
		double milkGood = 0.0;
		double milkBad = 0.0;
		double lemonSize = 0.0;
		double lemonGood = 0.0;
		double lemonBad = 0.0;
		double noneSize = 0.0;
		double noneGood = 0.0;
		double noneBad = 0.0;
		for (Tea t : trainingSet) {
			if (t.getAddition().equals(Tea.MILK)) {
				milkSize++;
				if (t.isDrinkable()) {
					lemonGood++;
				} else {
					lemonBad++;
				}
			}
			if (t.getAddition().equals(Tea.LEMON)) {
				lemonSize++;
				if (t.isDrinkable()) {
					milkGood++;
				} else {
					milkBad++;
				}
			}
			if (t.getAddition().equals(Tea.NONE)) {
				noneSize++;
				if (t.isDrinkable()) {
					noneGood++;
				} else {
					noneBad++;
				}
			}
		}
		result = milkSize / (double) trainingSet.size() * entropy(milkGood / milkSize, milkBad / milkSize);
		result += lemonSize / (double) trainingSet.size() * entropy(lemonGood / lemonSize, lemonBad / lemonSize);
		result += noneSize / (double) trainingSet.size() * entropy(noneGood / noneSize, noneBad / noneSize);
		return result;
	}

	public double gain(int attr) {
		double result = 0.0;
		switch (attr) {
			case (0):
				result = info() - teaTypeInfo();
				break;
			case (1):
				result = info() - additionInfo();
				break;
			default:
				result = sugarGain()[0];
				break;
		}
		return result;
	}
	
	public double teaTypeSplitInfo() {
		double result = 0.0;
		double blackTeaSize = 0.0;
		double whiteTeaSize = 0.0;
		double greenTeaSize = 0.0;
		for (Tea t : trainingSet) {
			if (t.getTeaType().equals(Tea.BLACK_TEA)) {
				blackTeaSize++;
			}
			if (t.getTeaType().equals(Tea.GREEN_TEA)) {
				whiteTeaSize++;
			}
			if (t.getTeaType().equals(Tea.WHITE_TEA)) {
				greenTeaSize++;
			}
		}
		result = (-1.0*blackTeaSize / (double)trainingSet.size()) * log2(blackTeaSize/(double)trainingSet.size());
		result+= (-1.0*whiteTeaSize / (double)trainingSet.size()) * log2(whiteTeaSize/(double)trainingSet.size());
		result+= (-1.0*greenTeaSize / (double)trainingSet.size()) * log2(greenTeaSize/(double)trainingSet.size());
		return result;
	}
	
	public double additionalSplitInfo() {		
		double result = 0.0;
		double milkSize = 0.0;
		double lemonSize = 0.0;
		double noneSize = 0.0;
		for (Tea t : trainingSet) {
			if (t.getAddition().equals(Tea.MILK)) {
				milkSize++;
			}
			if (t.getAddition().equals(Tea.LEMON)) {
				lemonSize++;
			}
			if (t.getAddition().equals(Tea.NONE)) {
				noneSize++;
			}
		}
		result = (-1.0 * milkSize / (double)trainingSet.size()) * log2(milkSize/(double)trainingSet.size());
		result+= (-1.0 * lemonSize / (double)trainingSet.size()) * log2(lemonSize/(double)trainingSet.size());
		result+= (-1.0 * noneSize / (double)trainingSet.size()) * log2(noneSize/(double)trainingSet.size());
		return result;
	}

	public double gainRatio(int attr) {
		switch(attr) {
			case(0): return gain(attr)/teaTypeSplitInfo();
			case(2): return gain(attr)/additionalSplitInfo();
			default: return sugarGain()[0];
		}
	}
	
	/**
	 * Zwraca tablicę z dwoma wartościami, w której wartość 0 to gain a 1 to
	 * wartość decydująca.
	 *
	 * @return
	 */
	public double[] sugarGain() {
        if(this.sugarGain[1]>0) {
            return sugarGain;
        }
		double bestGain = 0.0;
		double bestValue = 0.0;
		for (Tea t : trainingSet) {
			double tmpGain = gainForSugarValue(t.getSugar());
			double tmpValue = t.getSugar();
			if (tmpGain > bestGain) {
				bestGain = tmpGain;
				bestValue = tmpValue;
			}
		}
        sugarGain[0] = bestGain;
        sugarGain[1] = bestValue;;
		return sugarGain;
	}

	private double gainForSugarValue(double val) {
		double leSize = 0.0;
		double leGood = 0.0;
		double leBad = 0.0;
		double gtSize = 0.0;
		double gtGood = 0.0;
		double gtBad = 0.0;
		for (Tea t : trainingSet) {
			if (t.getSugar() <= val) {
				leSize++;
				if (t.isDrinkable()) {
					leGood++;
				} else {
					leBad++;
				}
			}
			if (t.getSugar() > val) {
				gtSize++;
				if (t.isDrinkable()) {
					gtGood++;
				} else {
					gtBad++;
				}
			}
		}
		double result = leSize / (double) trainingSet.size() * entropy(leGood / leSize, leBad / leSize);
		result += gtSize / (double) trainingSet.size() * entropy(gtGood / gtSize, gtBad / gtSize);
		return info() - result;
	}

    /**
     * sugar: -1 = ignore, 0 = less or equal, 1 = greater
     * @param values
     * @param sugar
     * @return 
     */
	public ArrayList<Tea> getTeasWithValueS(String[] values, int sugar) {

		ArrayList<Tea> teas = new ArrayList<>();

		for (Tea t : trainingSet) {
			if ((t.getTeaType().equals(values[0]) || values[0] == null)
					&& (t.getAddition().equals(values[1]) || values[1] == null)) {
                if(sugar==-1){
                    teas.add(t);
                } else if(sugar==0 && t.getSugar()<=this.sugarGain()[1]) {
                    teas.add(t);
                } else if(sugar==1 && t.getSugar()>this.sugarGain()[1]) {
                    teas.add(t);
                }
				
			}
		}

		return teas;
	}

}
