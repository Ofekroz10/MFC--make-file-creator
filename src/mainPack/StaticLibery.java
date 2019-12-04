package mainPack;

import java.util.LinkedList;

public class StaticLibery extends Lib {

	public StaticLibery(String name) {
		this.name = name;
		this.dep = new LinkedList<Imainfile>();

	}
	@Override
	public String getComment() {
		StringBuilder s = new StringBuilder();
		s.append("-shared -o "+this.getName()+" "+this.getDepAsString());
		return s.toString();
	}
	
	@Override
	public String getCompile()
	{
		return new String("gcc");
	}
	@Override
	public String getEndToTarget() {
		return new String("./");
	}
}
