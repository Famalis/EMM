/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pjwstk.emm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;
import pl.edu.pjwstk.emm.model.ClassifiableObject;
import pl.edu.pjwstk.emm.model.Feature;
import pl.edu.pjwstk.emm.model.ObjectFeature;

/**
 *
 * @author Sergio
 */
public class ObjectClassifier implements Serializable {

	private static final Logger LOG = Logger.getLogger(ObjectClassifier.class.getName());

	private ArrayList<ClassifiableObject> trainingSet;
	private HashMap<String, ArrayList<ClassifiableObject>> partitionedObjects;

	public ObjectClassifier() {
		System.out.println("Entropy 1,0: "
				+ entropy(
						(double) 1,
						(double) 0));
		System.out.println("Entropy 0,1: "
				+ entropy(
						(double) 0,
						(double) 1));
	}

	public void generateFeatures(String filePath) {
		File f = new File(filePath);
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				String[] values = Arrays.copyOfRange(data, 1, data.length);
				Feature newFt = new Feature(data[0], values);
				LOG.info(newFt.getName() + Arrays.toString(newFt.getPossibleValues().toArray()));
			}
		} catch (FileNotFoundException ex) {
			LOG.warning(ex.toString());
		} catch (IOException ex) {
			LOG.warning(ex.toString());
		}
	}

	public void generateFeatures() {
		Feature.possibleFeatures.clear();
		String bread = "bread";
		String[] breadValues = {"white", "dark", "flat"};
		String inside = "inside";
		String[] insideValues = {"cheese", "ham", "cottage cheese"};
		String season = "season";
		String[] seasonValues = {"none", "ketchup", "honey mustard"};
		Feature newFt = new Feature(bread, breadValues);
		newFt = new Feature(inside, insideValues);
		newFt = new Feature(season, seasonValues);
	}

	public void genereateTrainList() {
		for (int i = 0; i < Feature.possibleFeatures.size(); i++) {

		}
	}

	public void generateRandomTrainList(boolean makeNew, int num) {
		try {
			File f = new File("emmObjectDataFile");
			FileInputStream fis = new FileInputStream("emmObjectDataFile");
			ObjectInputStream ois = new ObjectInputStream(fis);
			trainingSet = (ArrayList<ClassifiableObject>) ois.readObject();
			System.out.println("Data loaded from file " + f.getAbsolutePath());
		} catch (Exception e) {
			trainingSet = new ArrayList<>();
		}
		if (trainingSet.isEmpty() || makeNew) {
			trainingSet = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				ClassifiableObject co = new ClassifiableObject();
				ArrayList<ObjectFeature> features = new ArrayList<>();
				for (int j = 0; j < Feature.possibleFeatures.size(); j++) {
					int ranInt = (int) (Math.random() * Feature.possibleFeatures.get(j).getPossibleValues().size());
					ObjectFeature of = new ObjectFeature(j, Feature.possibleFeatures.get(j).getPossibleValues().get(ranInt));
					features.add(of);
				}
				co.setFeatures(features);
				co.setAcceptable(false);
				if (features.get(1).getValue().equals("cottage cheese")
						&& features.get(2).getValue().equals("none")) {
					co.setAcceptable(true);
				} else if (features.get(2).getValue().equals("honey mustard")
						&& !features.get(1).getValue().equals("cottage cheese")) {
					co.setAcceptable(true);
				} else if (features.get(1).getValue().equals("cheese")
						&& features.get(2).getValue().equals("ketchup")) {
					co.setAcceptable(true);
				} else if (features.get(1).getValue().equals("ham")
						&& features.get(2).getValue().equals("ketchup")) {
					co.setAcceptable(true);
				} else if (!features.get(1).getValue().equals("cottage cheese")
						&& features.get(0).getValue().equals("flat")) {
					co.setAcceptable(true);
				}
				try {
					FileOutputStream fos = new FileOutputStream("emmObjectDataFile");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(trainingSet);
					oos.close();;
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				trainingSet.add(co);
				LOG.info(co.toString());
			}
		}
	}

	public void generateFeaturesFromFile() {
		try {
			File f = new File("possibleFeatures.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] lineArr = line.split(",");
				String featureName = lineArr[0];
				String[] values = Arrays.copyOfRange(lineArr, 1, lineArr.length);
				Feature newFt = new Feature(featureName, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, ArrayList<ClassifiableObject>> getPartitionedObjects() {
		return partitionedObjects;
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

	/**
	 * Informacje o klasach. Obiekt może być true albo false.
	 *
	 * @return
	 */
	public double infoT() {
		double C = 0;
		double c = 0;
		double S = trainingSet.size();
		for (ClassifiableObject oc : trainingSet) {
			if (oc.isAcceptable()) {
				C++;
			}
		}
		c = S - C;
		double result = entropy(C / S, c / S);
		return result;
	}

	/**
	 * Podział na klasy.
	 *
	 * @param featureId
	 * @return
	 */
	public double infoX(Integer featureId) {
		double result = 0D;
		for (String fValue : Feature.possibleFeatures.get(featureId).getPossibleValues()) {
			double positive = 0.0;
			double negative = 0.0;
			for (ClassifiableObject co : partitionedObjects.get(fValue)) {
				if (co.isAcceptable()) {
					positive++;
				} else {
					negative++;
				}
			}
			/*
			 double groupSize = partitionedObjects.get(fValue).size();
			 double a = groupSize/trainingSet.size();
			 double b = (positive/groupSize)*log2(positive/groupSize);
			 double c = (negative/groupSize)*log2(negative/groupSize);
			 /*result += groupSize/trainingSet.size() *
			 (-positive/groupSize*log2(positive/groupSize)
			 -negative/groupSize*log2(negative/groupSize));
			 */
			double groupSize = (double) partitionedObjects.get(fValue).size();
			result += groupSize / (double) trainingSet.size()
					* entropy(positive / groupSize, negative / groupSize);
		}
		return result;
	}

	public double infoSpecific(Integer featureId, String value) {
		double result = 0D;
		for (String fValue : Feature.possibleFeatures.get(featureId).getPossibleValues()) {
			if (fValue.equals(value)) {
				double positive = 0.0;
				double negative = 0.0;
				for (ClassifiableObject co : partitionedObjects.get(fValue)) {
					if (co.isAcceptable()) {
						positive++;
					} else {
						negative++;
					}
				}
				double groupSize = (double) partitionedObjects.get(fValue).size();
				result += groupSize / (double) trainingSet.size()
						* entropy(positive / groupSize, negative / groupSize);
			}
		}
		return result;
	}

    /**
     * Zwraca procent "poprawności" atrybutu, czyli ile procent obiektów w cesze
     * należy do klasy dzielącej.
     * @param featureId
     * @param value
     * @return 
     */
	public double attributeValueChance(Integer featureId, String value) {
		double result = 0D;
		for (String fValue : Feature.possibleFeatures.get(featureId).getPossibleValues()) {
			if (fValue.equals(value)) {
				double positive = 0.0;
				double negative = 0.0;
				for (ClassifiableObject co : partitionedObjects.get(fValue)) {
					if (co.isAcceptable()) {
						positive++;
					} else {
						negative++;
					}
				}
				result = positive / (positive + negative);
			}
		}
		return result;
	}

    /**
     * Zwraca ile procent obiektów na liście należy do klasy dzielącej.
     * @param list
     * @return 
     */
    public double subsetInClass(ArrayList<ClassifiableObject> list) {
        double positive = 0.0;
        //double negative = 0.0;
        for (ClassifiableObject co : list) {
          if(co.isAcceptable()) {
              positive++;
          }  
        }
        double listSize = (double)list.size();
        return positive/listSize;
    }
	public double splitInfo(Integer featureId) {
		double result = 0D;
		for (String fValue : Feature.possibleFeatures.get(featureId).getPossibleValues()) {
			double groupSize = (double) partitionedObjects.get(fValue).size();
			double setSize = (double) trainingSet.size();
			double a = (-1.0 * groupSize) / setSize;
			double loga = log2((double) groupSize / (double) setSize);
			result += a * loga;
		}
		return result;
	}

	public double gain(Integer featureId) {
		return infoT() - infoX(featureId);
	}

    /**
     * Zwraca id cechy z z najlepszym Gain.
     * @return 
     */
	public int getBestGain() {
		double best = 0.0;
		int bestId = -1;
		for (int i = 0; i < Feature.possibleFeatures.size(); i++) {
			if (best < gain(i)) {
				best = gain(i);
				bestId = i;
			}
		}
		return bestId;
	}
    
    /**
     * Zwraca id cechy z najlepszym GainRatio.
     * @return 
     */
    public int getBestGainRatio() {
		double best = 0.0;
		int bestId = -1;
		for (int i = 0; i < Feature.possibleFeatures.size(); i++) {
			if (best < gainRatio(i)) {
				best = gainRatio(i);
				bestId = i;
			}
		}
		return bestId;
	}

	public double gainRatio(Integer featureId) {
		return gain(featureId) / splitInfo(featureId);
	}

	public double groupObjects() {
		partitionedObjects = new HashMap<>();
		for (int i = 0; i < Feature.possibleFeatures.size(); i++) {
			for (String value : Feature.possibleFeatures.get(i).getPossibleValues()) {
				partitionedObjects.put(value, new ArrayList<ClassifiableObject>());
				for (ClassifiableObject oc : trainingSet) {
					if (oc.getObjectFeatures().get(i).getValue().equals(value)) {
						partitionedObjects.get(value).add(oc);
						//LOG.info(value+": "+oc);
					}
				}
			}
		}
		return 0D;
	}

	public ArrayList<ClassifiableObject> getTrainingSet() {
		return trainingSet;
	}
	
	public ArrayList<ClassifiableObject> getSubsetContainingValues(String... values) {
		ArrayList<ClassifiableObject> set = new ArrayList<>();
		for (ClassifiableObject oc : trainingSet) {
			for (String value : values) {
                int success = 0;
				for (ObjectFeature of : oc.getObjectFeatures()) {
					if (of.getValue().equals(value)
							|| value==null) {
                        success++;
                        break;
					}
                    if(success==Feature.possibleFeatures.size()) {
                        set.add(oc);
                    }
				}
			}
		}
		return set;
	}
	
}
