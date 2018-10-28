package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The Main class that processes reading the files and the output on the Screen
 */
public class CheckLines {
	private final File mainFolder;
	private final String ending;
	private final boolean reverseList;
	private int filesScanned = 0;

	/**
	 * Constructor which initializes the root directory and saves the relevant file ending
	 * @param arguments Start-arguments of the application
	 */
	public CheckLines(String[] arguments) {
		// Check if the number of arguments is valid and 
		// if the list should be displayed in reversed order
		if (arguments.length < 2)
			throw new IllegalArgumentException("Geben sie bitte mindestens 2 Parameter an");
		else if (arguments.length >= 3)
			reverseList = true;
		else
			reverseList = false;

		// Create a file object if possible
		File file = new File(arguments[0]);
		if (file.exists() && file.isDirectory())
			mainFolder = file;
		else
			throw new IllegalArgumentException("Pfad ungueltig");

		this.ending = arguments[1];
	}

	/**
	 * Starts the process of counting and displaying the files in a directory
	 */
	public void start() {
		final List<CSFile> files = new LinkedList<>();

		ProgressUpdater pu = new ProgressUpdater(this);
		pu.start();
		loopFolder(mainFolder, files);
		pu.interrupt();

		outputResults(files);
	}

	/**
	 * Outputs the results obtained by the file reading
	 * @param files The list of files that will be displayed an counted
	 */
	private void outputResults(final List<CSFile> files) {
		// Sort the files by number of lines
		files.sort(new LinesComparator());
		if (reverseList)
			Collections.reverse(files);

		int lines = 0;
		int fileNumber = 0;
		DecimalFormat df = new DecimalFormat(String.valueOf(filesScanned).replaceAll(".", "0") + ": ");

		// Output all files and their line count
		System.out.println();
		System.out.println();
		for (CSFile file : files) {
			System.out.println(df.format(++fileNumber) + file.getFilename() + ": " + file.getLines() + " lines");
			lines += file.getLines();
		}

		// Output total line count
		System.out.println();
		System.out.println(lines + " lines in " + filesScanned + " files");
	}

	/**
	 * A recursive method that reads every file in every directory of the root directory
	 * @param folder The folder in which files should be read
	 * @param files The List of files that are found within the root directory
	 */
	private void loopFolder(File folder, List<CSFile> files) {
		File[] directoryListing = folder.listFiles();
		if (directoryListing != null)
			for (File child : directoryListing)
				if (child.isDirectory())
					loopFolder(child, files);
				else if (child.isFile() && getFileExtension(child).equalsIgnoreCase(ending)) {
					files.add(new CSFile(child.getName(), countLines(child)));
					filesScanned++;
				}
	}

	/**
	 * Returns the Extension of a given file
	 * @param file The file which extension is to be returned
	 * @return The extension of the file
	 */
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		int index;
		if ((index = fileName.lastIndexOf('.')) > 0)
			return fileName.substring(++index);
		else
			return "";
	}

	/**
	 * Returns the number of lines of a given file
	 * @param file The files which lines will be counted
	 * @return The number of lines within this file
	 */
	private static int countLines(File file) {
		// Open the file
		try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			
			// Loop through all lines
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				// Loop through all characters. 
				// If a character is a newline symbol, increase the line counter
				for (int i = 0; i < readChars; ++i)
					if (c[i] == '\n')
						count++;
			}
			
			return (count == 0 && !empty) ? 1 : count;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Getter of the filesScanned attribute
	 * @return The value of the attribute filesScanned
	 */
	public int getFilesScanned() {
		return filesScanned;
	}
}