package org.emayor.notification.composer;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * Created on 19.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author mxs
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DocumentParser {
	
	Document doc;
	
	public DocumentParser(String input) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			this.doc = builder.parse(new InputSource(new StringReader(input)));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {}
	}
	
	
	public String getNodeValue(String node) {
		String result = null;
		try {
			/*System.out.println("DOC:" + doc.toString());
			System.out.println("Node:" + node);*/
			result = XPathAPI.selectSingleNode(doc,node+"/text()").getNodeValue();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {}
		return result;
	}
	
	public String getNodeListValue(String node) {
		String result = null;
		Node elem = null;
		try {
			/*System.out.println("DOC:" + doc.toString());
			System.out.println("Node:" + node);*/
			elem = XPathAPI.selectSingleNode(doc,node);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			t.transform(new DOMSource(elem), new StreamResult(sw));
			result = sw.toString();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {}
		return result;
	}
}
