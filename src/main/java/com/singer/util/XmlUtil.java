package com.singer.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.singer.common.AppException;

public class XmlUtil {

	private final Log log = LogFactory.getLog(XmlUtil.class);

	private Document document;

	private Node rootNode;

	private DocumentBuilder builder;

	public XmlUtil() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setExpandEntityReferences(true);
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			log.info("ParserConfigurationException");
		}

	}

	public void createDOMDocument(Object obj) throws IOException, SAXException, AppException {
		if (obj instanceof InputSource) {
			document = builder.parse((InputSource) obj);
			rootNode = document.getDocumentElement();
		} else if (obj instanceof File) {
			document = builder.parse((File) obj);
			rootNode = document.getDocumentElement();
		} else if (obj instanceof InputStream) {
			document = builder.parse((InputStream) obj);
			rootNode = document.getDocumentElement();
		} else {
			throw new AppException("Dom Document를 생성할 수 없는 Object입니다");
		}
	}

	public void parse(Object obj) throws SAXException, AppException {
		if (obj == null) {
			throw new AppException("DOM Parsing 대상 Object가 Null입니다");
		}

		ByteArrayInputStream bis = null;
		try {
			if (obj instanceof String) {
				byte[] byteArr = ((String) obj).getBytes("UTF-8");
				bis = new ByteArrayInputStream(byteArr);
				createDOMDocument(bis);
			} else {
				createDOMDocument(obj);
			}
		} catch (IOException e) {
			log.info("IOException");
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e2) {
					log.info("IOException");
				}
			}
		}
	}

	public String getNodeList(String name) {
		NodeList nodeList = document.getElementsByTagName(name);
		Node thisNode = null;
		String value = null;
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				thisNode = nodeList.item(i);
				if (name.equals(thisNode.getNodeName())) {
					value = this.getNodeValue(thisNode);
					break;
				}
			}
		}
		return value;
	}

	public Node getRootNode() {
		return this.rootNode;
	}

	public String getNodeValue(Node node) {
		if (node == null || !node.hasChildNodes()) {
			return "";
		}

		return node.getFirstChild().getNodeValue();
	}

	public String getNodeValue(Node parentNode, String targetName) {
		String value = null;
		Node thisNode = null;
		NodeList nodeList = parentNode.getChildNodes();

		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				thisNode = nodeList.item(i);

				if (targetName.equals(thisNode.getNodeName())) {
					value = this.getNodeValue(thisNode);
					break;
				} else {
					value = getNodeValue(thisNode, targetName);
				}

				if (value != null) {
					break;
				}
			}
		}
		return value;
	}
}
