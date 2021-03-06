package pl.edu.pjwstk.teaclassifier.model.tree;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;

public class TeaNode implements Serializable {

	public ArrayList<TeaNode> children;
	public TeaNode parent;
	public String label;
    public String idString;
	public String attribute;
	public int attributeNum;
	public String classValue;
	public int level;
	
	public int positive;
	public int negative;
	public String[] valuesCombination = new String[3];

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }
    
	public String getClassValue() {
		return classValue;
	}

	public void setClassValue(String classValue) {
		this.classValue = classValue;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public ArrayList<TeaNode> getChildren() {
		return children;
	}

	public void addChild(TeaNode node) {
		children.add(node);
		node.setParent(this);
        //node.setIdString(this.idString+node.getLabel());
        //System.out.println(this.idString+node.getLabel());
	}

	public void setChildren(ArrayList<TeaNode> children) {
		this.children = children;
	}
	
	@JsonIgnore
	public TeaNode getParent() {
		return parent;
	}

	public void setParent(TeaNode parent) {
		this.parent = parent;
        //this.idString = parent.getIdString()+this.label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAttribute(int num) {
		switch (num) {
			case (0):
				this.attribute = "TEA TYPE";
				break;
			case (1):
				this.attribute = "SUGAR";
				break;
			default:
				this.attribute = "ADDITION";
		}
		this.attributeNum = num;
	}

	public int getAttributeNum() {
		return attributeNum;
	}

	public void setAttributeNum(int attributeNum) {
		this.attributeNum = attributeNum;
	}

	public TeaNode() {
		label = "";
		children = new ArrayList<>();
		parent = null;
		classValue = null;
	}

	@Override
	public String toString() {
		return this.classValue != null ? this.classValue : this.attribute;
	}
	
	@Override
	public boolean equals(Object o) {
		TeaNode tn = (TeaNode) o;
		return tn.valuesCombination == this.valuesCombination;
	}
}
