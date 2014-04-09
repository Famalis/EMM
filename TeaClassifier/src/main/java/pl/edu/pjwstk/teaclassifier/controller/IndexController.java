/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.pjwstk.teaclassifier.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	private TeaTree tree;
	
	@RequestMapping("/index")
	public String home(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		TeaClassifier tc = new TeaClassifier();
		//tc.generateTrainingSetFromTxt("teaDat.txt");
		tc.generateTrainingSet(10);
		System.out.println("Sugar "+tc.gainRatio(TeaClassifier.SUGAR)+" "+tc.sugarGain()[1]);
		System.out.println("Addition "+tc.gainRatio(TeaClassifier.ADDITION));
		System.out.println("Tea type "+tc.gainRatio(TeaClassifier.TEA_TYPE));
		if(tree == null) {
			tree = new TeaTree(tc);
			System.out.println("new tree");
		}		
		System.out.println(Utils.convertObjectToJSON(tree.getRoot()));		
		tree.print();
		model.addAttribute("treeHtml", tree.htmlString());
		model.addAttribute("treeJson", Utils.convertObjectToJSON(tree));
		model.addAttribute("tree",tree);
		model.addAttribute("root", tree.getRoot());
		model.addAttribute("rootJson", Utils.convertObjectToJSON(tree.getRoot()));
		model.addAttribute("nodesList", Utils.convertObjectListToJSON(TeaTree.nodes(tree.getRoot())));
		model.addAttribute("trainingSet", tc.getTrainingSet());
		System.out.println(tree.queryTea("white tea", "lemon", 25.0));
		return "index";
	}
    
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public String query(@RequestBody String query) {
        String dataString = query.substring(10, query.length()-2);
        String[] data = dataString.split(",");
        String teaType = data[0];
        String addition = data[1];
        Double sugar = Double.parseDouble(data[2]);
        return tree.queryTea(teaType, addition, sugar)+"";
    }
	
}
