package main;

import java.util.Comparator;

/**
 * Comparator to indicate which file has more lines 
 */
public class LinesComparator implements Comparator<CSFile>{
	@Override
	public int compare(CSFile file1, CSFile file2) {
		if (file1.getLines() > file2.getLines())
			return 1;
		else if (file1.getLines() < file2.getLines())
			return -1;
		else
			return 0;
	}
}