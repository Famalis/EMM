/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.teaclassifier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pjwstk.teaclassifier.classifying.TeaClassifier;
import pl.edu.pjwstk.teaclassifier.model.tree.TeaTree;

/**
 *
 * @author sergio
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	
	@RequestMapping("/index")
	public String home(ModelMap model) {
		TeaClassifier tc = new TeaClassifier();
		//tc.generateTrainingSetFromTxt("teaDat.txt");
		tc.generateTrainingSet(10);
		System.out.println("Sugar "+tc.gain(TeaClassifier.SUGAR)+" "+tc.sugarGain()[1]);
		System.out.println("Addition "+tc.gain(TeaClassifier.ADDITION));
		System.out.println("Tea type "+tc.gain(TeaClassifier.TEA_TYPE));
		TeaTree tree = new TeaTree(tc);
		System.out.println(tree.getRootStr());	
		tree.print();
		model.put("tree", tree.htmlString());
		return "index";
	}
	
}
