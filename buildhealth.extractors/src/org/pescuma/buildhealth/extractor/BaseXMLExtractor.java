package org.pescuma.buildhealth.extractor;

import static com.google.common.base.Objects.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;
import org.pescuma.buildhealth.core.BuildData;

public abstract class BaseXMLExtractor extends BaseBuildDataExtractor {
	
	protected BaseXMLExtractor(PseudoFiles files) {
		super(files, "xml");
	}
	
	protected BaseXMLExtractor(PseudoFiles files, String... extensions) {
		super(files, extensions);
	}
	
	@Override
	protected void extract(String filename, InputStream input, BuildData data) throws IOException {
		try {
			
			Document doc = JDomUtil.parse(input);
			extractDocument(filename, doc, data);
			
		} catch (JDOMException e) {
			throw new BuildDataExtractorException(e);
		}
	}
	
	protected abstract void extractDocument(String filename, Document doc, BuildData data);
	
	protected void checkRoot(Document doc, String name, String filename) {
		if (!doc.getRootElement().getName().equals(name))
			throw new BuildDataExtractorException("Invalid file format: top node must be " + name + " (in "
					+ firstNonNull(filename, "<stream>") + ")");
	}
	
	protected void checkRoot(Document doc, String[] names, String filename) {
		boolean found = false;
		for (String name : names) {
			if (doc.getRootElement().getName().equals(name))
				found = true;
		}
		if (!found)
			throw new BuildDataExtractorException("Invalid file format: top node must be one of "
					+ StringUtils.join(names, " or ") + " (in " + firstNonNull(filename, "<stream>") + ")");
	}
	
	protected List<Element> findElementsXPath(Document doc, String xpath) {
		return XPathFactory.instance().compile(xpath, Filters.element()).evaluate(doc);
	}
	
	protected List<Element> findElementsXPath(Element el, String xpath) {
		return XPathFactory.instance().compile(xpath, Filters.element()).evaluate(el);
	}
	
}
