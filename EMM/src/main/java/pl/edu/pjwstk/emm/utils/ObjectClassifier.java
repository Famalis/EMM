/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pjwstk.emm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class ObjectClassifier {

	private static final Logger LOG = Logger.getLogger(ObjectClassifier.class.getName());

	private ArrayList<ClassifiableObject> trainingSet;
	private HashMap<String, ArrayList<ClassifiableObject>> partitionedObjects;

	public ObjectClassifier() {

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

	public void createTrainSet() {
		trainingSet = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
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
			trainingSet.add(co);
			LOG.info(co.toString());
		}
	}

	public ArrayList<ClassifiableObject> getTrainSet() {
		return trainingSet;
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
		return result;
	}

	/**
	 * Informacje o klasach. Obiekt może być true albo false.
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
		double result = entropy(C/S, c/S);
		return result;
	}

	/**
	 * Podział na klasy.
	 * @param featureId
	 * @return 
	 */
	public double infoX(Integer featureId) {
		double result = 0D;
		for (String fValue : Feature.possibleFeatures.get(featureId).getPossibleValues()) {
			double positive = 0.0;
			double negative = 0.0;
			for (ClassifiableObject co : partitionedObjects.get(fValue)){
				if(co.isAcceptable()) {
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
			double groupSize = (double)partitionedObjects.get(fValue).size();
			result += groupSize/(double)trainingSet.size() *
					entropy(positive/groupSize,negative/groupSize);
		}
		return result;
	}
	
	public double splitInfo(Integer featureId) {
		double result = 0D;
		for (String fValue : Feature.possibleFeatures.get(featureId).getPossibleValues()) {
			double positive = 0.0;
			double negative = 0.0;
			for (ClassifiableObject co : partitionedObjects.get(fValue)){
				if(co.isAcceptable()) {
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
			double groupSize = (double)partitionedObjects.get(fValue).size();
			result += -1D*groupSize/(double)trainingSet.size() *
					entropy(groupSize/(double)trainingSet.size());
		}
		return result;
	}
	
	public double gain(Integer featureId) {
		return infoT() - infoX(featureId);
	}
	
	public double gainRatio(Integer featureId) {
		return gain(featureId)/splitInfo(featureId);
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
}
