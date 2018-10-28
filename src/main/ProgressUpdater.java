package main;

/**
 * Shows the number of files read within the root directory 
 * and updates it once a second
 */
public class ProgressUpdater extends Thread {
	private CheckLines checkLines;
	public ProgressUpdater(CheckLines checkLines) {
		this.checkLines = checkLines;
	}
	
	@Override
	public void run() {
		System.out.println();
		
		// Print time while the Tread isn't interrupted
		while (!Thread.currentThread().isInterrupted()) {
			outputLine();
			System.out.print("\r");
			
			// Wait 1 second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
		// Output the final number in the end
		outputLine();
	}
	
	/**
	 * Outputs the Number of files that where read
	 */
	public void outputLine() {
		System.out.print(checkLines.getFilesScanned() + " files read.");
	}
}
