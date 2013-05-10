package com.eds.ctcb.demo.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jXml {

	private File file;

	private XmlVisitor instance = XmlVisitor.getInstance();

	private boolean isVisited;

	// constructor
	public Dom4jXml(File file) {
		this.file = file;
		isVisited = false;
	}

	// get root element
	public Element getRootElement() {
		Element root = null;
		try {
			SAXReader SAXreader = new SAXReader();
			Document document = SAXreader.read(file);
			root = document.getRootElement();
		} catch (DocumentException e) {
			System.out.print(e.getMessage());
		}
		return root;
	}

	public List<String> getAllElements() {
		if (!isVisited) {
			this.getRootElement().accept(instance);
			isVisited = true;
		}
		return instance.getElementsList();
	}

	public List<String> getFieldProList() {
		if (!isVisited) {
			this.getRootElement().accept(instance);
			isVisited = true;
		}
		return instance.getFieldProList();
	}

	public List<String> getPropertyValueList() {
		if (!isVisited) {
			this.getRootElement().accept(instance);
			isVisited = true;
		}
		return instance.getPropertyValueList();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Dom4jXml xml = new Dom4jXml(
				new File(
						"D:/Eclipse/workspace/TestProj/src/com/eds/ctcb/common/abc.xml"));

		System.out.println("______________________________________");

		// xml.visitElement(xml.getRootElement());

		List elements = xml.getAllElements();
		System.out.println("all elements:");
		for (int i = 0; i < elements.size(); i++) {
			System.out.println(elements.get(i));
		}
		System.out.println("______________________________________");
		List fieldPro = xml.getFieldProList();
		System.out.println("all fields attributes:");
		for (int i = 0; i < fieldPro.size(); i++) {
			System.out.println(fieldPro.get(i));
		}
		System.out.println("______________________________________");
		List valuesOfPro = xml.getPropertyValueList();
		System.out.println("valuesOfPro list:");
		for (int i = 0; i < valuesOfPro.size(); i++) {
			System.out.println(valuesOfPro.get(i));
		}

	}

}
