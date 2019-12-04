package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public interface Ifile
{
	public void setName(String name);
	public String getName();
	public void removeDependence(String name);
	public String getOnlyName();
}
