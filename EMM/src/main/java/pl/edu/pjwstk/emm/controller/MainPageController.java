/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.emm.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pjwstk.emm.model.ClassifierTree;
import pl.edu.pjwstk.emm.utils.ObjectClassifier;

/**
 *
 * @author Sergio
 */
@Controller
@RequestMapping("/index")
public class MainPageController {
	
	public MainPageController() {
		super();
	}
	
	@RequestMapping("/index")
    public String home(HttpSession session, ModelMap model) {
        ObjectClassifier oc = new ObjectClassifier();
		oc.generateFeatures();
		oc.generateRandomTrainList(true,5);
		oc.groupObjects();
		model.addAttribute("map1", oc.getPartitionedObjects().get("white"));
		model.addAttribute("map2", oc.getPartitionedObjects().get("dark"));
		model.addAttribute("map3", oc.getPartitionedObjects().get("flat"));
		model.addAttribute("map4", oc.getPartitionedObjects().get("cheese"));
		model.addAttribute("map5", oc.getPartitionedObjects().get("ham"));
		model.addAttribute("map6", oc.getPartitionedObjects().get("cottage cheese"));
		model.addAttribute("map7", oc.getPartitionedObjects().get("none"));
		model.addAttribute("map8", oc.getPartitionedObjects().get("ketchup"));
		model.addAttribute("map9", oc.getPartitionedObjects().get("honey mustard"));
		model.addAttribute("test", "teesssstt");
		model.addAttribute("trainingSet", oc.getTrainingSet());
		//System.out.println(oc.infoT() + " - " +oc.infoX(0)+" =\n"+oc.gain(0));
		//System.out.println(oc.infoT());
		System.out.println(oc.gain(0)+"/"+oc.splitInfo(0)+"="+oc.gainRatio(0));
		//System.out.println(oc.gainRatio(0));
		//System.out.println(oc.gainRatio(0));
		ClassifierTree tree = new ClassifierTree(oc);
		//String aa = tree.toString();
		//System.out.println(tree.toString());
		tree.printNodes();
        return "index";
    }
}
