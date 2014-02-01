package org.pescuma.buildhealth.cli.commands.add.staticanalysis.console;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.io.File;

import org.pescuma.buildhealth.cli.BuildHealthCliCommand;
import org.pescuma.buildhealth.extractor.PseudoFiles;
import org.pescuma.buildhealth.extractor.staticanalysis.console.GnuMakeGccConsoleExtractor;

@Command(name = "gnu-make-gcc-console", description = "Add warnings from GNU Make + GNU C Compiler (gcc) output files")
public class GnuMakeGccConsoleExtractorCommand extends BuildHealthCliCommand {

	@Arguments(title = "file or folder", description = "File or folder with GNU Make + GNU C Compiler (gcc) output(s)", required = true)
	public File file;

	@Override
	public void execute() {
		buildHealth.extract(new GnuMakeGccConsoleExtractor(new PseudoFiles(file)));
	}

}