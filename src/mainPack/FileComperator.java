package mainPack;

import java.util.Comparator;


public class FileComperator implements Comparator<Imainfile>{
	
	public FileComperator() {;}
	public int compare(Imainfile o1, Imainfile o2) {
		return (int)(o2.getCom()-o1.getCom());
	}

}
