package org.pescuma.buildhealth.extractor.staticanalysis.console;

import java.io.IOException;
import java.io.Reader;

import org.pescuma.buildhealth.core.BuildData;
import org.pescuma.buildhealth.extractor.BaseBuildDataExtractor;
import org.pescuma.buildhealth.extractor.PseudoFiles;

public class GnuFortranConsoleExtractor extends BaseBuildDataExtractor {

	public GnuFortranConsoleExtractor(PseudoFiles files) {
		super(files, "txt", "out");
	}

	@Override
	protected void extract(String path, Reader input, BuildData data) throws IOException {
		WarningsHelper.extractFromParser("GNU Fortran Compiler (gfortran)", new hudson.plugins.warnings.parser.GnuFortranParser(),
				input, data);
	}

}
