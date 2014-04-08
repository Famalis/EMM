/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.teaclassifier.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.pjwstk.teaclassifier.classifying.TeaClassifier;
import pl.edu.pjwstk.teaclassifier.model.tree.TeaTree;
import pl.edu.pjwstk.teaclassifier.utils.Utils;

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
		tc.generateTrainingSetFromTxt("teaDat.txt");
		//tc.generateTrainingSet(10);
		System.out.println("Sugar "+tc.gainRatio(TeaClassifier.SUGAR)+" "+tc.sugarGain()[1]);
		System.out.println("Addition "+tc.gainRatio(TeaClassifier.ADDITION));
		System.out.println("Tea type "+tc.gainRatio(TeaClassifier.TEA_TYPE));
		TeaTree tree = new TeaTree(tc);
		System.out.println(Utils.convertObjectToJSON(tree.getRoot()));		
		tree.print();
		model.put("treeHtml", tree.htmlString());
		model.put("treeJson", Utils.convertObjectToJSON(tree));
		model.put("root", tree.getRoot());
		model.put("rootJson", Utils.convertObjectToJSON(tree.getRoot()));
		return "index";
	}
	
	@RequestMapping("/getTree")
	@ResponseBody
	public ResponseEntity<String> getTree(ModelMap model, HttpServletResponse response) {
		TeaClassifier tc = new TeaClassifier();
		//tc.generateTrainingSet(10);
		tc.generateTrainingSetFromTxt("teaDat.txt");
		TeaTree tree = new TeaTree(tc);
		Map<String, Object> initData = new HashMap<>();
		initData.put("tree", tree);
		return new ResponseEntity<String>(Utils.convertObjectToJSON(tree)
				, HttpStatus.CREATED);
	}
	
}
