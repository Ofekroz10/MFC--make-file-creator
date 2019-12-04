package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public class Target implements Itarget
{
	private String name;
	private Iterator<Imainfile> dep;
	private Lib represent;
	private LinkedList<String> main;
	
	public Target(String name,Iterator<Imainfile> dep,Lib represent,LinkedList<String> main)
	{
		this.name = name;
		this.dep = dep;
		this.represent = represent;
		this.main = main;
	}
	@Override
	public String getName() {
		return new String(name);
	}
	@Override
	public Iterator<Imainfile> iterator() {
		return this.dep;
	}
	@Override
	public Lib getRepresent() {
		return this.represent;
	}
	@Override
	public String command() {
		String mains = "";
		for (String cfile : main) {
			mains+= cfile+ " ";
		}
		return new String("-o "+this.name+ " "+mains+" "+ this.represent.getEndToTarget()+this.represent.getName());
	}
	@Override
	public String depAsString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Imainfile> it = this.iterator();
		while(it.hasNext())
		{
			Imainfile current = it.next();
			sb.append(current.toMakefile()+" ");
		}
		return sb.toString();
	}
	@Override
	public String getTitle() {
		return new String(this.name+ ": ");
	}
	@Override
	public String toString()
	{
		return new String(this.name);
	}
	
	
}
