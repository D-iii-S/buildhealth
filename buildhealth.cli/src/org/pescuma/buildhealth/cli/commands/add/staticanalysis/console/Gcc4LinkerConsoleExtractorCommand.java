package org.pescuma.buildhealth.cli.commands.add.staticanalysis.console;

import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.io.File;

import org.pescuma.buildhealth.cli.BuildHealthCliCommand;
import org.pescuma.buildhealth.extractor.PseudoFiles;
import org.pescuma.buildhealth.extractor.staticanalysis.console.Gcc4LinkerConsoleExtractor;

@Command(name = "gcc4-linker", description = "Add warnings from GNU Compiler 4 (gcc) output files")
public class Gcc4LinkerConsoleExtractorCommand extends BuildHealthCliCommand {

	@Arguments(title = "file or folder", description = "File or folder with GNU Compiler 4 (gcc) output(s)", required = true)
	public File file;

	@Override
	public void execute() {
		buildHealth.extract(new Gcc4LinkerConsoleExtractor(new PseudoFiles(file)));
	}

}
