package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public interface Imake 
{
	public void addFile(Imainfile file);
	public void makeAll();
	public void makeAll(LinkedList<Imainfile>files);
	public void export();
	public String getContent();
	public LinkedList<Cfile> getMains();
}
