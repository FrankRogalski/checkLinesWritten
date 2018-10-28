package main;

/**
 * Class which hold important informations about a File
 */
public class CSFile {
	/**
	 * The name of a file
	 */
	private String filename;
	
	/**
	 * the number of lines within a file
	 */
	private int lines;
	
	public CSFile() {}
	
	public CSFile(String filename, int lines) {
		this.filename = filename;
		this.lines = lines;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getLines() {
		return lines;
	}
	
	public void setLines(int lines) {
		this.lines = lines;
	}
}