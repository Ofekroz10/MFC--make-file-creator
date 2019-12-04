package mainPack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;


public class Creator implements Icreator
{
	private String fileName;
	public LinkedList<Cfile> cFiles;
	private Makefile make;

	
	public Creator(String fileName)
	{
		this.fileName= fileName;
		this.cFiles = new LinkedList<Cfile>();
		
		make = new Makefile();
		
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public void creatAndSave() {
		try {
			initCfiles();
			for (Cfile cfile : cFiles) {
				make.addFile(cfile);
			}
			makeLibs();
			make.makeAll();
			System.out.println("----");
			System.out.println(make.getContent());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
	private void makeLibs() {
	
		try {
			String path = this.fileName +"\\.make";
			String content = this.readFile(path,Charset.defaultCharset());
	 		BufferedReader bufReader = new BufferedReader(new StringReader(content));
	 		String line=null;
	 		while( (line=bufReader.readLine()) != null )
	 		{
	 			try {
		 			if(line.substring(0,10).equals("make lib -"))
		 			{
		 				char type = line.charAt(10);
		 				String restComm  = line.substring(12,line.length());
		 				String[] split = restComm.split(":");
		 				
		 				switch(type) {
		 					case 'd':
		 						DynamicLibery dLib = new DynamicLibery(split[0]);
		 						for(int i=0; i<split.length-1;i++)
				 				{
			 						Cfile c = getCfile(split[1+i]);
			 						if(c== null)
			 						{
			 							System.out.println("Wrong type of lib, eror in this line: "+line);
			 							break;
			 						}
			 						else
			 						{
			 							dLib.addDependence(c);
			 							
			 						}
				 				}
		 						this.make.addFile(dLib);
		 						break;
		 						
		 					case 's':
		 						StaticLibery sLib = new StaticLibery(split[0]);
		 						for(int i=0; i<split.length-1;i++)
				 				{
			 						Cfile c1 = getCfile(split[i+1]);
			 						if(c1== null)
			 						{
			 							System.out.println("Wrong type of lib, eror in this line: "+line);
			 							break;
			 						}
			 						else
			 						{
			 							sLib.addDependence(c1);
			 							
			 						}
				 				}
		 						this.make.addFile(sLib);
		 						break;
		 					default:
		 						System.out.println("Wrong type of lib, eror in this line: "+line);
		 						return;
		 				}
		 			}}
		 		
	 		
	 			catch(Exception e)
	 			{
	 				System.out.println("Wrong .make file!!");
	 				return;
	 			}
	 			
	 		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private Cfile getCfile(String s) {
		for (Cfile cfile : cFiles) {
			if(cfile.getName().equals(s))
				return cfile;
		}
		return null;
	}

	private void initCfiles() throws IOException
	{
		try
        {
    	 	String[] paths;
    	 	File folder = new File(this.fileName);
    	 	File[] listOfFiles = folder.listFiles(new FilenameFilter() {
    	 	    @Override
    	 	    public boolean accept(File dir, String name) {
    	 	        return name.endsWith(".c");
    	 	    }
    	 	});
    	 	for (File file : listOfFiles) {
    	 		Path p = Paths.get(this.fileName,file.getName());
    	 		System.out.println(file.getName());
    	 		String content = this.readFile(p.toString(),Charset.defaultCharset());
    	 		BufferedReader bufReader = new BufferedReader(new StringReader(content));
    	 		String line=null;
    	 		Cfile c = new Cfile(file.getName());
    	 		while( (line=bufReader.readLine()) != null )
    	 		{
    	 			if(line.contains("#include") && !line.contains("<"))
    	 			{
    	 				line =line.replace("#include","");
    	 				System.out.println(line);
    	 				line =line.replaceAll("\\s+","");
    	 				if(line.contains(","))
    	 				{
    	 					String[] dep = line.split(",");
    	 					for (String d : dep) 
    	 					{
								String fName = d.substring(1,d.length()-1);
								switch(fName.charAt(fName.length()-1))
								{
									case('h'):
										c.addDependence(new Hfile(fName));
										break;
									case('c'):
										c.addDependence(new Cfile(fName));
										break;
								}
							}
    	 				}
    	 				else
    	 				{
    	 					String fName = line.substring(1,line.length()-1);
    	 					
    	 					switch(fName.charAt(fName.length()-1))
							{
								case('h'):
									c.addDependence(new Hfile(fName));
									break;
								case('c'):
									c.addDependence(new Cfile(fName));
									break;
							}
    	 				}
    	 			}
    	 		}
    	 		this.cFiles.add(c);
    	 		
			}
    	 	
    	 	
    	 	
            
            
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
     	
		
	}
	private String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	
	
}
