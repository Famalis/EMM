package pl.edu.pjwstk.teaclassifier.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.pjwstk.teaclassifier.classifying.TeaClassifier;
import pl.edu.pjwstk.teaclassifier.model.tree.TeaTree;
import pl.edu.pjwstk.teaclassifier.utils.Utils;

@Controller
@RequestMapping("/index")
public class IndexController {

	private static final Logger LOG = Logger.getLogger(IndexController.class.getName());

	private TeaTree tree;
	private TeaClassifier tc;

	private void writeDataToModel(ModelMap model) {
		model.addAttribute("treeHtml", tree.htmlString());
		model.addAttribute("treeJson", Utils.convertObjectToJSON(tree));
		model.addAttribute("tree", tree);
		model.addAttribute("root", tree.getRoot());
		model.addAttribute("rootJson", Utils.convertObjectToJSON(tree.getRoot()));
		model.addAttribute("nodesList", Utils.convertObjectListToJSON(TeaTree.nodes(tree.getRoot())));
		model.addAttribute("trainingSet", tc.getTrainingSet());
		model.addAttribute("sugarThreshold", tc.sugarGain()[1]);
	}

	@RequestMapping("/index")
	public String home(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if (tc == null) {
			tc = new TeaClassifier();
			tc.generateTrainingSet();
		}
		LOG.log(Level.INFO, "Sugar {0} {1}", new Object[]{tc.gainRatio(TeaClassifier.SUGAR), tc.sugarGain()[1]});
		LOG.log(Level.INFO, "Addition {0}", tc.gainRatio(TeaClassifier.ADDITION));
		LOG.log(Level.INFO, "Tea type {0}", tc.gainRatio(TeaClassifier.TEA_TYPE));
		if (tree == null) {
			tree = new TeaTree(tc);
			LOG.info("new tree");
		}
		LOG.info(Utils.convertObjectToJSON(tree.getRoot()));
		tree.print();
		writeDataToModel(model);
		return "index";
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public String query(@RequestBody String query) {
		String dataString = query.substring(10, query.length() - 2);
		String[] data = dataString.split(",");
		String teaType = data[0];
		String addition = data[1];
		Double sugar = Double.parseDouble(data[2]);
		return tree.queryTea(teaType, addition, sugar) + "";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			String fileName = "teaDataFile.txt";
			for (FileItem fi : items) {
				if (fi.getFieldName().equals("userFile")) {
					System.out.println(fi.getFieldName());
					InputStream is = fi.getInputStream();
					byte[] barr = new byte[(int) fi.getSize()];
					is.read(barr);
					FileOutputStream fos = new FileOutputStream(fileName);
					fos.write(barr);
					fos.close();
					is.close();
				}
			}
			tc = new TeaClassifier();
			tc.generateTrainingSetFromTxt(fileName);
			tree = new TeaTree(tc);
			writeDataToModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

}
