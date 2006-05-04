package org.emayor.install;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

public class Utils {

	
	public static String fileToString(String file) throws FileNotFoundException 
	  {
		Utils utils = new Utils();
		return fileToString(utils.getClass().getResourceAsStream(file));			
		
	  }

	public static String fileToString(InputStream in) 
	  {
	    BufferedReader reader = null;
	    
	    String current = null;
	    String result = new String();
	    
	    try
	    {
	    
	    	reader = new BufferedReader(new InputStreamReader(in));
	    	
	    	while ((current = reader.readLine()) != null) 
	    	{
	    		result+=current+"\n";
	    	}
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    
	    return result;
	  }
	
	public static boolean copyStringToFile(String string, String file) {
		
		boolean result = false;
		
		BufferedWriter out;
		
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			out.write(string);
			result = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
}
