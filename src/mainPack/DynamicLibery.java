package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public  class DynamicLibery extends Lib 
{
	public DynamicLibery(String name)
	{
		this.dep = new LinkedList<Imainfile>();
		this.name = name;
	}
	
	@Override
	public String getComment() {
		StringBuilder s = new StringBuilder();
		s.append("-rcs "+this.getName()+" "+this.getDepAsString());
		return s.toString();
		
	}

	@Override
	public String getCompile() {
		return new String("ar");
	}

	@Override
	public String getEndToTarget() {
		return new String("");
	}



}
