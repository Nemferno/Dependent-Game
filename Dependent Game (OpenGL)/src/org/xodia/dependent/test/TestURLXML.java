package org.xodia.dependent.test;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestURLXML {
	public static void main(String argv[]) {
 
		try {
			
	    	InputStream inputXml  = new URL("http://s464830726.onlinehome.us/xodia/arcade/dependent/select.php").openConnection().getInputStream();
	
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputXml);
			
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("entry");
		 
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					
					System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
					System.out.println("Score : " + eElement.getElementsByTagName("score").item(0).getTextContent());
					System.out.println("Date Submitted : " + eElement.getElementsByTagName("datesubmitted").item(0).getTextContent());
		 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
