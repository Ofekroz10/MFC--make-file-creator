package mainPack;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;



public abstract class Lib implements Ifile,Imainfile
{
	protected LinkedList<Imainfile> dep;
	protected String name;
	public static final Comparator<Imainfile> _Comp = new FileComperator();
	public abstract String getEndToTarget();
	@Override
	public double getCom()
	{
		return 2;
	}
	@Override
	public abstract String getComment();
	
	@Override
	public abstract String getCompile();

	@Override
	public String toMakefile() {
		return this.getName();
	}

	@Override
	public String getNamedoubleitle() {
		return this.getDepAsString();
	}
	

	@Override
	public String getNameToMakeFile() {
		return this.getName();
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public String getName() {
		return new String(this.name);
	}

	
	public Iterator<Imainfile> iterator() {
		return this.dep.iterator();
	}

	public void addDependence(Imainfile mainFile) {
		this.dep.add(mainFile);
	}

	@Override
	public void removeDependence(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOnlyName() {
		return new String(this.name.substring(0,this.name.length()-3));
	}


	public String getDepAsString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Imainfile> it = this.iterator();
		while(it.hasNext())
		{
			Imainfile current = it.next();
			sb.append(current.toMakefile()+" ");
		}
		return sb.toString();
	}
	

}
