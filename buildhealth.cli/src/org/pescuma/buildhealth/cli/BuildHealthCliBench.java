package org.pescuma.buildhealth.cli;

import io.airlift.command.*;

import java.text.*;
import java.util.*;

public class BuildHealthCliBench {
	private static final String COMMAND_DELIMITER = "@@@";
	private static final String[] STRING_ARRAY_TYPE = new String[0];
	private static final String[] RESET_COMMAND = { "new" };
	
	public static void main(String[] args) {
		int loops = Integer.parseInt(System.getProperty("benchmark.loops", "10"));
		List<String[]> commands = splitIntoCommands(new ArrayList<String>(Arrays.asList(args)));
		long[] startTimes = new long[loops];
		long[] endTimes = new long[loops];
		
		Cli<Runnable> parser = BuildHealthCli.createParser();
		
		System.err.printf("# %s - benchmark started (%d loops)\n", getCurrentTimestamp(), loops);
		
		for (int i = 0; i < loops; i++) {
			Runnable command = parser.parse(RESET_COMMAND);
			command.run();
			
			startTimes[i] = System.nanoTime();
			for (String[] com : commands) {
				command = parser.parse(com);
				command.run();
			}
			endTimes[i] = System.nanoTime();
		}
		
		for (int i = 0; i < loops; i++) {
			System.err.printf("%10d %10d\n", startTimes[i], endTimes[i]);
		}
		
		System.err.printf("# %s - benchmark completed\n", getCurrentTimestamp());
	}
	
	private static List<String[]> splitIntoCommands(List<String> args) {
		List<String[]> commands = new ArrayList<String[]>();
		List<String> current = new ArrayList<String>();
		
		/* Ensure we copy the last command to the list. */
		args.add(COMMAND_DELIMITER);
		
		for (String arg : args) {
			if (arg.equals(COMMAND_DELIMITER)) {
				if (current.size() > 0) {
					commands.add(current.toArray(STRING_ARRAY_TYPE));
				}
			} else {
				current.add(arg);
			}
		}
		
		return commands;
	}
	
	private static String getCurrentTimestamp() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
