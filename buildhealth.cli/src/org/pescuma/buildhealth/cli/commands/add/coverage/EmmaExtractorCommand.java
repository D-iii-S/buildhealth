package org.pescuma.buildhealth.cli.commands.add.coverage;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.io.File;

import org.pescuma.buildhealth.cli.BuildHealthCliCommand;
import org.pescuma.buildhealth.extractor.PseudoFiles;
import org.pescuma.buildhealth.extractor.coverage.EmmaExtractor;

@Command(name = "emma", description = "Add coverage information from an Emma XML file")
public class EmmaExtractorCommand extends BuildHealthCliCommand {
	
	@Arguments(title = "file or folder", description = "File or folder with Emma XML output(s)", required = true)
	public File file;
	
	@Override
	public void execute() {
		buildHealth.extract(new EmmaExtractor(new PseudoFiles(file)));
	}
	
}