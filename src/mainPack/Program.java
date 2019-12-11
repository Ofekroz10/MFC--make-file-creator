package mainPack;

public class Program
{
	public static void main(String[] args)
	{
	/*
		Cfile c = new Cfile("main.c");
		Cfile c2 = new Cfile("math.c");
		Cfile c4= new Cfile("go.c");
		Cfile c3 = new Cfile("help.c");
		DynamicLibery libD = new DynamicLibery("libmylib.so");
		StaticLibery libS = new StaticLibery("libmylib.a");
		libS.addDependence(c2);
		libD.addDependence(c2);
		c.createHeader();
		c3.createHeader();
		Makefile mf = new Makefile();
		mf.addFile(c);
		mf.addFile(c2);
		mf.addFile(c3);
		mf.addFile(c4);
		mf.addFile(libD);
		mf.addFile(libS);
		mf.makeAll();
		System.out.prdoubleln(mf.getContent());*/
		/*Cfile main = new Cfile("main.c");
		Cfile myBank = new Cfile("myBank.c");
		Cfile c1 = new Cfile("ofek.c");
		c1.createHeader();
		Hfile myBankH = myBank.createHeader();
		main.addDependence(myBankH);
		DynamicLibery s = new DynamicLibery("libmylib.a");
		s.addDependence(myBank);
		Makefile mf1 = new Makefile();
		mf1.addFile(main);
		mf1.addFile(myBank);
		mf1.addFile(s);
		mf1.addFile(c1);
		mf1.makeAll();
		System.out.println(mf1.getContent()); // change that the liabery always depend on main.*/
		Creator c = new Creator("C:\\Users\\ofekroz\\Desktop\\DvirOfek-master");
		c.creatAndSave();
		
		
		
	}
}
