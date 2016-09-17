package org.usfirst.frc.team4488.robot.testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class TestLogger {

	private FileWriter fileOut;
	private String loggingDir = "/home/lvuser/rioLoggingData/";
	private String loggingFile = loggingDir + "SelfDiagLog-" + LocalDateTime.now().toString();

	/*
	 * Constructor takes care of setting up the log file and printing to both
	 * log and console a pretty welcome message. text that goes to file should
	 * be parsible to ease exporting of data for analysis.
	 */
	public TestLogger() {
		initLogFile();
		initLogToConsole();
	}

	/*
	 * Log to both console and file THIS IS THE FUNCTION YOU WANT!
	 */
	public void log(String msg) {
		logToFile(msg);
		logToConsole(msg);
	}

	/*
	 * Because java doesn't have a destructor.... Guess I'll just clean up
	 * myself
	 */
	public void closeLogTestManager() {
		closeLogToFile();
		closeLogToConsole();
	}

	/*
	 * Init function prior to logging output to console
	 */
	private void initLogToConsole() {
		// Display welcome msg
		// date & time
		// List of all functions to be run
		System.out.println("Welcome to the self diagnostics mode\n"
				+ "Please follow the following prompts to guide you through the automated & manual testing suite.\n"
				+ LocalDateTime.now() + "\n" + "LIST OF TESTS:\n");
	}

	/*
	 * This function will format text and send directly to the NI robot console
	 */
	private void logToConsole(String msg) {
		// TODO format text
		System.out.println(msg);
	}

	/*
	 * destructor function for logging to console
	 */
	private void closeLogToConsole() {
		// Check if we ended early
		// Log end time & date
		// Log "Ending Log"
		System.out.println("Ending Log at " + LocalDateTime.now());

	}

	/*
	 * create new file based upon time & date -- mmddyy-mm:hh (082116-1534)
	 */
	private void initLogFile() {

		// TODO scrub filename
		try {
			// Check if we need to create the directory
			if (!Files.isDirectory(Paths.get(loggingDir))) {
				// Create directory
				new File(loggingDir).mkdirs();
			}
			// Check that file doesn't exist
			int x = 0;
			String tempLoggingFile = loggingFile;
			while (Files.isRegularFile(Paths.get(tempLoggingFile), LinkOption.NOFOLLOW_LINKS)) {
				x++;
				System.out.println("DAVE -- File existed");
				tempLoggingFile = loggingFile + "(" + Integer.toString(x) + ")";
			}
			if (x > 0) {
				loggingFile = tempLoggingFile;
			}
			// open file for writing
			fileOut = new FileWriter(loggingFile, true);

			// Print initialization statement - date, time, etc.

			fileOut.write("Welcome to the self diagnostics mode\n"
					+ "Please follow the following promgts to guide you through the automated & manual testing suite.\n"
					+ LocalDateTime.now() + "\n" + "LIST OF TESTS:\n");

			fileOut.close();

		} catch (IOException ex) {
			System.out.println("Failed to open the file");
			System.out.println(ex.getMessage());

		}

	}

	/*
	 * This function will open a file and log message
	 */
	private void logToFile(String msg) {
		// check that fileOut exists
		if (fileOut != null) {
			// format string
			// log it - JSON, CSV, TEXT?
			try {
				fileOut.write(msg);
				fileOut.close();
			} catch (IOException ex) {
				System.out.println(msg);
			}
		}
	}

	/*
	 * Destructor function for log files
	 */
	private void closeLogToFile() {
		// Check if we ended early
		// Log end time & date
		// Log "Ending Log"
		logToFile("Ending Log at " + LocalDateTime.now());

	}

}
