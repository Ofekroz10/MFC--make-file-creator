package mainPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;

import javax.swing.ListModel;


public class Creator implements Icreator
{
	private String fileName;
	private LinkedList<Cfile> cFiles;
	private Makefile make;
	private File[] allFiles;

	
	public Creator(String fileName)
	{
		this.fileName= fileName;
		this.cFiles = new LinkedList<Cfile>();
			
		make = new Makefile();
		
	}
	public File[] getAllFiles()
	{
		return allFiles.clone();
	}
	public String getFileName() {
		return fileName;
	}

	@Override
	public void creatAndSave(String s) {
		try {
			initCfiles();
			for (Cfile cfile : cFiles) {
				make.addFile(cfile);
			}
			makeLibs();
			make.makeAll();
			System.out.println("----");
			System.out.println(make.getContent());
			saveToFile(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
	private void saveToFile(String s) throws IOException {
		
		try (FileWriter writer = new FileWriter(s+"//"+"makefile");
				 BufferedWriter bw = new BufferedWriter(writer)) {

				bw.write(make.getContent());
				bw.close();

		}
				
		catch(FileNotFoundException  e)
		{
			throw new  FileNotFoundException ("the file is not writble");
		}
	}
	@Override 
	public String toString()
	{
		return cFiles.toString();
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
	 				throw e;
	 				//System.out.println("Wrong .make file!!");
	 				//return;
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

	public void initCfiles() throws IOException
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
    		File[] listOfFiles1 = folder.listFiles(new FilenameFilter() {
    	 	    @Override
    	 	    public boolean accept(File dir, String name) {
    	 	        return name.endsWith(".c") || name.endsWith(".h") || name.equals(".make");
    	 	    }

    	 	   
    	 	});
    		this.allFiles = listOfFiles1;
    		
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
	public int getCfilesLen() {
		return this.cFiles.size();
	}
	public String[] getListF() {
		String[] filesAsStr = new String[allFiles.length];
		int  i=0;
		for (File f : allFiles) {
			filesAsStr[i] = f.getName();
			i++;
		}
		return filesAsStr; 
	}
	
	
	
	
}
