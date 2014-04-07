/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pjwstk.emm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import pl.edu.pjwstk.emm.utils.ObjectClassifier;

/**
 *
 * @author sergi_000
 */
public class ClassifierTree {

    private ClassifierTreeNode root;

    public ClassifierTree(ObjectClassifier oc) {
        root = new ClassifierTreeNode();
        Integer bestId = oc.getBestGainRatio();
        root.setFeature(Feature.possibleFeatures.get(bestId));
        ArrayList<Integer> undecidedFeatures = new ArrayList<>();
        for (int i = 0; i < Feature.possibleFeatures.size(); i++) {
            if (i != bestId) {
                undecidedFeatures.add(i);
            }
        }
        String[] featureValuesCombination = new String[Feature.possibleFeatures.size()];
        root.setLevel(0);
        root.addChildren(buildTree(oc, root, undecidedFeatures, featureValuesCombination, 0));
        System.out.println(root.toString());
    }

    public ArrayList<ClassifierTreeNode> buildTree(ObjectClassifier oc,
            ClassifierTreeNode parent, ArrayList<Integer> undecidedFeatures,
            String[] featureValuesCombination, int level) {
        System.out.println("1 undecided features" + Arrays.toString(undecidedFeatures.toArray()));
        ArrayList<ClassifierTreeNode> children = new ArrayList<>();

        for (String fValue : parent.getFeature().getPossibleValues()) {
            String[] newfeatureValuesCombination = Arrays
                    .copyOf(featureValuesCombination, featureValuesCombination.length);
            for (int i = 0; i < newfeatureValuesCombination.length; i++) {
                if (newfeatureValuesCombination[i] == null) {
                    newfeatureValuesCombination[i] = fValue;
                    break;
                }
            }
            if(newfeatureValuesCombination[2]!=null) {
                System.out.println(Arrays.toString(newfeatureValuesCombination));
            }
            //System.out.println("Combination " + Arrays.toString(newfeatureValuesCombination));
            ArrayList<ClassifiableObject> objectsMeetingRequirements
                    = oc.getSubsetContainingValues(newfeatureValuesCombination);
            /*
             Jeśli nie ma już cech to zwracamy liścia.
             */
            if (undecidedFeatures.isEmpty() &&
                    !objectsMeetingRequirements.isEmpty()) {
                ClassifierTreeNode child = new ClassifierTreeNode();
                if (70.0 < oc.subsetInClass(objectsMeetingRequirements)) {
                    child.setClassValue("Eatable");
                } else {
                    child.setClassValue("Not eatable");
                }
                children.add(child);
                System.out.println("Leaf " + child.toString());
                return children;
            } /*
             Jeśli nie ma żadnych możliwych dzieci dla węzła, to nic nie tworzymy,
             ani liscia ani kolejncyh węzłów.
             */ 
            else if (objectsMeetingRequirements.isEmpty()) {
                System.out.println("No child or leaf");
                return children;
            } else {

                /*
                 Sprawdzamy czy wszystkie obiekty od danej kombinacji mają taką 
                 samą wartość (true albo false), jeśli tak to tworzymy liścia.
                 */
                double correctness = oc.subsetInClass(objectsMeetingRequirements);
                if (correctness == 0.0) {
                    ClassifierTreeNode child = new ClassifierTreeNode();
                    child.setClassValue("Not eatable");
                    children.add(child);
                    //System.out.println("Leaf " + child.toString());
                    return children;
                } else if (correctness == 1.0) {
                    ClassifierTreeNode child = new ClassifierTreeNode();
                    child.setClassValue("Eatable");
                    //System.out.println("Leaf " + child.toString());
                    return children;
                } else {

                    /*
                     Jeśli węzeł nie jest liściem to zaczynamy dodawać mu gałęzi.
                     */
                    ArrayList<Integer> newUndecidedFeatures = new ArrayList<>();
                    newUndecidedFeatures.addAll(undecidedFeatures);
                    ClassifierTreeNode child = new ClassifierTreeNode();
                    double bestGainRatio = -10.0;
                    Integer bestId = -10;
                    for (Integer id : newUndecidedFeatures) {
                        double gainRatio = oc.gainRatio(id);
                        if (gainRatio > bestGainRatio) {
                            bestId = id;
                            bestGainRatio = gainRatio;
                        }
                    }
                    newUndecidedFeatures.remove(bestId);
                    child.setFeature(Feature.possibleFeatures.get(bestId));
                    //System.out.println(bestId+"\n"+Arrays.toString(newUndecidedFeatures.toArray()));                        
                    child.setBranchLabel(fValue);
                    child.setLevel(level + 1);
                    child.addChildren(buildTree(oc, child, newUndecidedFeatures, newfeatureValuesCombination, level + 1));
                    //System.out.println("Child: " + child.toString());
                    children.add(child);
                }
            }
        }
        System.out.println("2 undecided features" + Arrays.toString(undecidedFeatures.toArray()));
        return children;
    }

    @Override
    public String toString() {
        return printTree(root);
    }

    private String printTree(ClassifierTreeNode parent) {
        String line = "\n|";
        for (int i = 0; i < parent.getLevel(); i++) {
            line += "-";
        }
        line += parent.getFeature().getName();
        if (!parent.getChildren().isEmpty()) {
            for (ClassifierTreeNode node : parent.getChildren()) {
                line += printTree(node);
            }
        }
        return line;
    }
}
