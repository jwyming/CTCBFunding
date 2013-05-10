package com.eds.ctcb.demo.xml;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class XmlVisitor extends VisitorSupport {

	public static XmlVisitor instance;

	private List<String> fieldProList;

	private List<String> elementsList;

	private List<String> propertyValueList;

	private XmlVisitor() {
		fieldProList = new LinkedList<String>();
		elementsList = new LinkedList<String>();
		propertyValueList = new LinkedList<String>();
		;
	}

	public static XmlVisitor getInstance() {
		if (instance == null) {
			instance = new XmlVisitor();
		}
		return instance;
	}

	@Override
	public void visit(Element arg0) {

		elementsList.add(arg0.getName());

		if (arg0.getName().equals("field")) {
			List lst = arg0.attributes();
			for (int i = 0; i < lst.size(); i++) {
				StringBuffer str = new StringBuffer();
				Attribute attri = (Attribute) lst.get(i);

				str.append(attri.getName() + "=" + attri.getValue());
				fieldProList.add(str.toString());
				if (attri.getName().equals("property")) {
					propertyValueList.add(attri.getValue());
				}
			}
		}

	}

	public List<String> getElementsList() {
		return elementsList;
	}

	public List<String> getFieldProList() {
		return fieldProList;
	}

	public void visit(Attribute arg0) {

	}

	public List<String> getPropertyValueList() {
		return propertyValueList;
	}

}
