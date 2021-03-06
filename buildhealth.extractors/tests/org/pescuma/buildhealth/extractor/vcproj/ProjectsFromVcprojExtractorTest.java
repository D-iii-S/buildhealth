package org.pescuma.buildhealth.extractor.vcproj;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.InputStream;

import org.junit.Test;
import org.pescuma.buildhealth.extractor.BaseExtractorTest;
import org.pescuma.buildhealth.extractor.PseudoFiles;
import org.pescuma.buildhealth.extractor.projects.ProjectsFromVisualStudioExtractor;

public class ProjectsFromVcprojExtractorTest extends BaseExtractorTest {
	
	@Test
	public void testSimple() {
		InputStream stream = load("vcproj.xml");
		
		ProjectsFromVisualStudioExtractor extractor = new ProjectsFromVisualStudioExtractor(new PseudoFiles(stream,
				"/devel/MyProj.vcproj"));
		
		extractor.extractTo(table, tracker);
		
		verify(tracker).onStreamProcessed();
		verify(tracker, never()).onFileProcessed(any(File.class));
		
		assertTable(3, 0, table);
	}
	
}
