package org.pescuma.buildhealth.core.listener;

import java.io.File;

import org.pescuma.buildhealth.extractor.BuildDataExtractor;

public interface BuildHealthListener {
	
	void onFileExtracted(BuildDataExtractor extractor, File file);
	
	void onStreamExtracted(BuildDataExtractor extractor);
	
}