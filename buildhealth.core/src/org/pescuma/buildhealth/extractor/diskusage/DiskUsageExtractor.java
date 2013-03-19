package org.pescuma.buildhealth.extractor.diskusage;

import static com.google.common.base.Objects.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.pescuma.buildhealth.core.BuildData;
import org.pescuma.buildhealth.core.BuildDataExtractorTracker;
import org.pescuma.buildhealth.extractor.BuildDataExtractor;
import org.pescuma.buildhealth.extractor.BuildDataExtractorException;
import org.pescuma.buildhealth.extractor.PseudoFiles;

import com.google.common.base.Strings;

public class DiskUsageExtractor implements BuildDataExtractor {
	
	private final PseudoFiles files;
	private final String tag;
	
	public DiskUsageExtractor(PseudoFiles file) {
		this(file, null);
	}
	
	public DiskUsageExtractor(PseudoFiles file, String tag) {
		this.files = file;
		this.tag = firstNonNull(tag, "");
	}
	
	@Override
	public void extractTo(BuildData data, BuildDataExtractorTracker tracker) {
		long total = 0;
		
		if (files.isStream()) {
			InputStream stream = files.getStream();
			try {
				while (stream.read() != -1)
					total++;
			} catch (IOException e) {
				throw new BuildDataExtractorException(e);
			}
			
		} else {
			for (File file : files.getFiles())
				total += file.length();
		}
		
		if (Strings.isNullOrEmpty(tag))
			data.add(total, "Disk usage");
		else
			data.add(total, "Disk usage", tag);
	}
	
}