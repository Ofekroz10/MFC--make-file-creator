package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public class Cfile implements  Ifile, Imainfile
{
	protected String name;
	protected LinkedList<Ifile> dependence;

	public Cfile(String name,Hfile headerF)
	{
		this.name = name;
		this.dependence=  new LinkedList<Ifile>();
		dependence.add(headerF);
	}
	public Cfile(String name)
	{
		this.dependence=  new LinkedList<Ifile>();
		this.name = name;
	}
	public Hfile createHeader()
	{

		String hName = this.getOnlyName()+".h";
		Hfile header = new Hfile(hName);
		this.dependence.add(header);
		return header;
	}
	@Override
	public String toString()
	{
		return this.name.toString() + "Dependence on: "+this.dependence.toString();
	}
	@Override
	public void setName(String name) {
		this.name = new String(name);
		
	}
	@Override
	public String getName() {
		return new String(this.name);
	}
	

	public Iterator<Ifile> iterator() {
		return dependence.iterator();
	}

	public void addDependence(Ifile file) {
		this.dependence.add(file);
		
	}
	@Override
	public void removeDependence(String name) {
		this.dependence.remove(name);
		
	}
	@Override
	public String toMakefile() {
		return this.getOnlyName()+".o";
	}
	@Override
	public String getOnlyName() {
		return new String(this.name.substring(0,this.name.length()-2));
	}
	@Override
	public String getNamedoubleitle()
	{
		if(this.dependence.size() ==0)
			return this.getName();
		else
		{
			StringBuilder sb =new StringBuilder (this.getName()+" ");
			Iterator<Ifile> it = this.iterator();
			while(it.hasNext())
			{
				Ifile cur = it.next();
				sb.append(cur.getName()+ " ");
			}
			return sb.toString();
		}
	}
	@Override
	public String getNameToMakeFile() {
		return this.getName();
		
	}
	@Override
	public String getComment() {
		return new String("-c");
	}
	@Override
	public String getCompile() {
		return new String("gcc");
	}
	@Override
	public double getCom() {
		return 1;
	}
	
	
}
