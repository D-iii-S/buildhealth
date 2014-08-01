package org.pescuma.buildhealth.extractor;

import static com.google.common.base.Objects.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.pescuma.buildhealth.core.BuildData;

import com.sun.xml.txw2.IllegalSignatureException;

public abstract class BaseXMLExtractor extends BaseBuildDataExtractor {
	
	protected BaseXMLExtractor(PseudoFiles files) {
		super(files, "xml");
	}
	
	protected BaseXMLExtractor(PseudoFiles files, String... extensions) {
		super(files, extensions);
	}
	
	@Override
	protected void extract(String path, InputStream input, BuildData data) throws IOException {
		try {
			
			Document doc = JDomUtil.parse(input);
			extractDocument(path, doc, data);
			
		} catch (JDOMException e) {
			throw new BuildDataExtractorException(e);
		}
	}
	
	@Override
	protected final void extract(String path, Reader input, BuildData data) throws IOException {
		throw new IllegalStateException("Do not use");
	}
	
	protected abstract void extractDocument(String path, Document doc, BuildData data);
	
	public static void checkRoot(Document doc, String path, String... names) {
		boolean found = false;
		for (String name : names) {
			if (doc.getRootElement().getName().equals(name))
				found = true;
		}
		if (!found)
			throw new BuildDataExtractorException("Invalid file format: top node must be "
					+ StringUtils.join(names, " or ") + " (in " + firstNonNull(path, "<stream>") + ")");
	}
	
	protected void removeNamespace(Document doc, String namespace, String filename) {
		if (!namespace.equals(doc.getRootElement().getNamespace().getURI()))
			throw new BuildDataExtractorException("Invalid file format: incorrect namespace " + namespace + " (in "
					+ firstNonNull(filename, "<stream>") + ")");
		
		removeNamespaces(doc.getRootElement());
		
	}
	
	private void removeNamespaces(Element el) {
		el.hasAdditionalNamespaces();
		el.setNamespace(Namespace.NO_NAMESPACE);
		
		for (Element c : el.getChildren())
			removeNamespaces(c);
	}
	
	public static List<Element> findElementsXPath(Document doc, String xpath) {
		return XPathFactory.instance().compile(xpath, Filters.element()).evaluate(doc);
	}
	
	public static List<Element> findElementsXPath(Element el, String xpath) {
		return XPathFactory.instance().compile(xpath, Filters.element()).evaluate(el);
	}
	
	private static Map<String, XPathExpression<Element>> cachedXPaths = new HashMap<String, XPathExpression<Element>>();
	
	protected static XPathExpression<Element> getOrCreateXPathExpression(String xpath) {
		XPathExpression<Element> result = cachedXPaths.get(xpath);
		if (result != null) {
			return result;
		}
		
		result = XPathFactory.instance().compile(xpath, Filters.element());
		cachedXPaths.put(xpath, result);
		
		return result;
	}
	
	public static List<Element> findElementsXPathCaching(Document doc, String xpath) {
		XPathExpression<Element> expr = getOrCreateXPathExpression(xpath);
		return expr.evaluate(doc);
	}
	
	public static Element findElementXPath(Document doc, String xpath) {
		List<Element> result = findElementsXPath(doc, xpath);
		if (result.isEmpty())
			return null;
		else if (result.size() > 1)
			throw new IllegalSignatureException("More than one element found: " + xpath);
		else
			return result.get(0);
	}
	
}
