package org.pescuma.buildhealth.cli.commands.coverage;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.io.File;

import org.pescuma.buildhealth.cli.BuildHealthCliCommand;
import org.pescuma.buildhealth.extractor.PseudoFiles;
import org.pescuma.buildhealth.extractor.dotcover.DotCoverExtractor;

@Command(name = "dotCover", description = "Add coverage information from an JaCoCo XML file")
public class DotCoverExtractorCommand extends BuildHealthCliCommand {
	
	@Arguments(title = "file or folder", description = "File or folder with JaCoCo xml output(s)", required = true)
	public File file;
	
	@Override
	public void execute() {
		buildHealth.extract(new DotCoverExtractor(new PseudoFiles(file)));
	}
	
}
