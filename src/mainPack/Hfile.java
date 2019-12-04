package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public class Hfile implements Ifile {
	
	private String name;
	
	public Hfile(String name)
	{
		this.name = name;
	}
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return new String(name);
	}
	
	public String toString()
	{
		return (name);
	}



	@Override
	public void removeDependence(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOnlyName() {
		return new String(this.name.substring(0,this.name.length()-2));
	}

}
