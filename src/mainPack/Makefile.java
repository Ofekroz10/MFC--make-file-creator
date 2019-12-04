package mainPack;

import java.util.Iterator;
import java.util.LinkedList;

public class Makefile implements Imake
{
	private String name;
	private StringBuilder content;
	private LinkedList<Imainfile> files;
	private String flag = new String("-Wall -g");
	private String compiler ="gcc";
	private boolean phony = true;
	private static double tarCount=0;
	private LinkedList<Cfile> main;
	private LinkedList<String> mainss;
	private boolean libExist = false;
	
	public Makefile(String name)
	{
		this.content = new StringBuilder();
		this.name = name;
		this.files = new LinkedList<Imainfile>();
		//this.main = new LinkedList<Cfile>();
		this.mainss = new LinkedList<String>();
	}
	public Makefile()
	{
		this.content = new StringBuilder();
		this.name = new String("Makefile");
		this.files = new LinkedList<Imainfile>();
		this.mainss = new LinkedList<String>();
	}
	@Override
	public String toString()
	{
		return files.toString();
	}
	@Override
	public void addFile(Imainfile file) {
		if(file instanceof Cfile)
			this.mainss.add(file.getNameToMakeFile());
		else if(file instanceof Lib)
		{
			libExist= true;
			Iterator<Imainfile> it = ((Lib)file).iterator();
			while(it.hasNext())
			{
				Imainfile current = it.next();
				for (String mainF : mainss) {
					if(mainF.equals(current.getNameToMakeFile()))
						mainss.remove(mainF);
				}
			}
		}
		this.files.add(file);
	}

	@Override
	public void makeAll() {
		this.makeAll(this.files);
	}

	@Override
	public void makeAll(LinkedList<Imainfile> files) {
		this.files.sort(Lib._Comp);
		this.main = this.getMains();
		System.out.println(mainss);
		StringBuilder s = new StringBuilder("");
		StringBuilder targets = new StringBuilder();
		Iterator<Imainfile> it = files.iterator();
		if(it.hasNext())
			s.append("all: ");
		LinkedList<Itarget> tar = new LinkedList<Itarget>();
		
		while(it.hasNext())
		{
			Imainfile current = it.next();
			if(current instanceof Lib)
			{
				Lib current1 = (Lib)current;
				s.append(current.toMakefile()+ " ");
				tar.add(new Target("target"+this.tarCount,current1.iterator(),current1,this.mainss));
				tarCount++;
				
			}
			
			targets.append(current.toMakefile()+": "+current.getNamedoubleitle()+"\n");
			if(current instanceof Lib)
				targets.append("\t"+ current.getCompile()+ " "+current.getComment()+" "+"\n");
			else
				targets.append("\t"+ current.getCompile()+ " "+flag+" "+current.getComment()+" "+current.getNameToMakeFile()+"\n");
				
		}
		Iterator<Itarget> itTarget = tar.iterator();
		StringBuilder head = new StringBuilder();
		StringBuilder toClean = new StringBuilder();
		while(itTarget.hasNext())
		{
			Itarget cur = itTarget.next();
			String temp = "";
			for (String c : mainss) {
				c = c.replace(".c",".o");
				temp+=c+ " ";
			}
			head.append(cur.getTitle()+temp+ "\n");
			head.append("\t gcc "+flag+ " "+cur.command()+"\n");
			s.append(cur.getName()+" ");
			toClean.append(cur.toString()+" ");
			
		}
		if(!libExist)
		{
			String oFiles="";
			
			for (String str : mainss) {
				 oFiles += str.replace(".c",".o").replace("[","").replace("]","") +" ";
			}
			
			s.append("target0"+"\n");
			s.append("target0: "+oFiles+"\n");
			s.append("\t gcc -Wall -g -o target0 "+oFiles);
			
			
		}
		s.append("\n"+ head);
		s.append("\n" +targets);
		if(this.phony)
		{
			s.append("\n" +".PHONY: clean all");
		}
		s.append("\n"+"clean:"+"\n"+"\t"+"rm -f *.o *.a *.so ");
		s.append(toClean);
		
		this.content = s;
		
	}

	@Override
	public void export() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getContent() {
		return content.toString();
	}
	@Override
	public LinkedList<Cfile> getMains() {
		Iterator<Imainfile> it = files.iterator();
		LinkedList<Cfile> c = new LinkedList<Cfile>();
		while(it.hasNext())
		{
			Imainfile current = it.next();
			if(current instanceof Cfile)
			{
				c.add((Cfile)current);
			}
		}
		return c;
	}
	
}
