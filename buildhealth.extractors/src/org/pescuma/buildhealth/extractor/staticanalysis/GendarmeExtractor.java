package org.pescuma.buildhealth.extractor.staticanalysis;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.pescuma.buildhealth.core.BuildData;
import org.pescuma.buildhealth.extractor.BaseXMLExtractor;
import org.pescuma.buildhealth.extractor.PseudoFiles;
import org.pescuma.buildhealth.utils.FilenameToLanguage;

// http://www.mono-project.com/Gendarme
public class GendarmeExtractor extends BaseXMLExtractor {
	
	private static final String RULE_PREFIX = "Gendarme.Rules.";
	
	public GendarmeExtractor(PseudoFiles files) {
		super(files);
	}
	
	private static final Pattern SOURCE_PATTERN = Pattern.compile("(.*)\\(\\u2248?(\\d+)(?:,(\\d*))?\\)");
	
	@Override
	protected void extractDocument(String filename, Document doc, BuildData data) {
		checkRoot(doc, "gendarme-output", filename);
		
		Map<String, String> fullNames = findRuleFullNames(doc);
		for (Element rule : findElementsXPath(doc, "/gendarme-output/results/rule")) {
			String uri = rule.getAttributeValue("Uri", "");
			if (uri.isEmpty())
				continue;
			
			String ruleName = fullNames.get(uri);
			if (ruleName == null)
				ruleName = rule.getAttributeValue("Name", "");
			if (ruleName.isEmpty())
				ruleName = uri;
			
			String problem = rule.getChildTextTrim("problem");
			String solution = rule.getChildTextTrim("solution");
			
			for (Element target : rule.getChildren("target")) {
				for (Element defect : target.getChildren("defect")) {
					String severity = defect.getAttributeValue("Severity", "");
					String confidence = defect.getAttributeValue("Confidence", "");
					String location = defect.getAttributeValue("Location", "");
					String message = defect.getTextTrim();
					String source = defect.getAttributeValue("Source", "");
					if (source.isEmpty())
						source = location;
					String file;
					String line;
					
					Matcher m = SOURCE_PATTERN.matcher(source);
					if (m.matches()) {
						file = m.group(1);
						if (m.group(3) == null)
							line = m.group(2);
						else
							line = m.group(2) + ":" + m.group(3);
					} else {
						file = source;
						line = "";
					}
					
					String language = FilenameToLanguage.detectLanguage(file);
					if (language.isEmpty())
						language = "C#"; // guess
						
					StringBuilder details = new StringBuilder();
					append(details, "Problem", problem);
					append(details, "Solution", solution);
					append(details, "Severity", severity);
					append(details, "Confidence", confidence);
					append(details, "Location", location);
					
					data.add(1, "Static analysis", language, "Gendarme", file, line, ruleName, message,
							toBuildHealthSeverity(severity), details.toString(), uri);
				}
			}
		}
	}
	
	private String toBuildHealthSeverity(String severity) {
		if ("Critical".equalsIgnoreCase(severity))
			return "High";
		if ("High".equalsIgnoreCase(severity))
			return "High";
		if ("Medium".equalsIgnoreCase(severity))
			return "Medium";
		if ("Low".equalsIgnoreCase(severity))
			return "Low";
		if ("Audit".equalsIgnoreCase(severity))
			return "Low";
		return severity;
	}
	
	private void append(StringBuilder out, String name, String text) {
		if (text.isEmpty())
			return;
		
		if (out.length() > 0)
			out.append("\n");
		
		out.append(name).append(": ").append(text);
	}
	
	private Map<String, String> findRuleFullNames(Document doc) {
		Map<String, String> result = new HashMap<String, String>();
		
		for (Element rule : findElementsXPath(doc, "/gendarme-output/rules/rule")) {
			String uri = rule.getAttributeValue("Uri", "");
			String name = rule.getTextTrim();
			
			if (name.startsWith(RULE_PREFIX))
				name = name.substring(RULE_PREFIX.length());
			
			name = name.replace('.', '/');
			
			if (!uri.isEmpty() && !name.isEmpty())
				result.put(uri, name);
		}
		
		return result;
	}
	
}