package org.emayor.install;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class Main {

	/*
	 * search path for autmatic detection of JDK/JRE/JBOSS
	 * and old installation files
	 */
	private String[] JDK 				= {"/","/Work","/emayor"};
	private String[] JBOSS 				= {"/","/Work","/emayor"};
	private String[] MYSQL 				= {"/","/Programme/MySQL","/Program Files/MySQL","/Work","/emayor"};
	private String[] JBOSS_TREE 		= {"bin","bin/run.bat","server","server/default","server/default/conf","server/default/deploy"};
	private String[] JBOSS_OLD_INSTALL 	= {"server/default/deploy/eMayorApp.ear","server/default/conf/policies","server/default/conf/MunicipalityInformation","server/default/tmp/emayor"};
	private String[] UDDI_ENTRIES		= {"Aachen","Bozen","Seville","Siena"};
	
	/*
	 * root of the municipality installations (subdirectories include "Aachen","Bozen","Seville","Siena")
	 */
	private String INSTALLATION_PATH = "../../../../Installation/";
	
	/*
	 * debug messages?
	 */
	private boolean debug = true; 
	private String  error = null;
	
	/*
	 * configuration taken from municipality.properties
	 */
	private Properties config = new Properties(); 
	
	/*
	 * check whether file exists
	 */
	public boolean checkFile(String file) {
		return (new File(file)).exists();
	}
	
	private String getValueFromFile(String file, String startStr, String startStr2, String endStr) {
		String result = null;
		BufferedReader fileIn;
		try {
			fileIn = new BufferedReader(new FileReader((new File(file))));
			String line = null;
			while ((line = fileIn.readLine()) != null) {
				int beg = line.indexOf(startStr); 
				if (beg > 0) {
					beg = line.indexOf(startStr2,beg)+1;
					int end = line.indexOf(endStr,beg);
					if (beg > 0 && end > beg) {
						result = line.substring(beg,end);
						break;
					}
				}				
			}
			fileIn.close();
		} catch (Exception e) {
			//ignore
		}
		return result;
	}
	
	public void getUpgradeConfig() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		/*
		 * Oracle BPEL PM
		 */
		File obant_path = null;
		String orabpel = config.getProperty("emayor.ORABPEL");
		if (orabpel != null) {
			obant_path = new File(orabpel);
			if (!obant_path.exists()) {
				obant_path = null;	
			}
		}
		
		while (obant_path == null) {
			try {
				obant_path = new File(ClassLoader.getSystemClassLoader().getSystemResource(".").getFile()).getParentFile().getParentFile();
				orabpel = obant_path.getPath();
			} catch (Exception e) {
				orabpel = readIn("Path to Oracle BPEL PM installation (e.g. c:\\ORABPELPM)",null,false);
				if (!checkFile(orabpel)) {
					printErr("no such directory: "+orabpel+"\n");
					//System.exit(0);
					obant_path = null;
				}
				orabpel += "/integration/orabpel";
				if (!checkFile(orabpel)) {
					printErr("no such directory: "+orabpel+"\n");
					obant_path = null;
					//System.exit(0);
				} else {
					obant_path = new File(orabpel);
					if ((!obant_path.exists()) || (!obant_path.getName().equals("orabpel"))) {
						printErr("Oracle BPEL PM not found\n");
						//System.exit(1);
						obant_path = null;
					}
				}
			}
		}
		
		try {
			config.setProperty("emayor.ORABPEL",new File(orabpel).toURL().getFile().substring(1));
		} catch (Exception e1) {
			System.exit(1);
		}
		
		
		/*
		 * JBOSS
		 */
		String jboss = getJBOSS();
		if (jboss != null) {
			String acceptance = readIn("Found JBoss located under "+jboss+" - correct?" ,"yes",true);
			if (acceptance.toLowerCase().equals("no")) {
				jboss = null;
			}
		}
		while (jboss == null) {
			printOut("Path to JBoss4 installation: ");
			try {
				jboss = br.readLine();
			} catch (IOException e) {
				printErr("ERROR: Invalid input!\n");
				//e.printStackTrace();
				jboss = null;
				//System.exit(0);
			}
			if (!checkFile(jboss)) {
				printErr("no such directory: "+jboss+"\n");
				//System.exit(0);
				jboss = null;
			} else {
				for (int i = 0; i<JBOSS_TREE.length; i++) {
					String jFile = jboss+"/"+JBOSS_TREE[i];
					if (!checkFile(jFile)) {
						printErr("missing jboss file or directory "+jFile+"\n");
						//System.exit(1);
						jboss = null;
					}
				}
			}
		}
		try {
			config.setProperty("emayor.JBOSS",new File(jboss).toURL().getFile().substring(1));
		} catch (Exception e) {
			System.exit(1);
		}		
		
		try {
		
			// detect jndi port
			String jndi_port = null;
			
			
			BufferedReader fileIn;
			try {
				fileIn = new BufferedReader(new FileReader((new File(jboss+"/server/default/conf/jboss-service.xml"))));
				String line = null;
				boolean namingConfig = false;
				while ((line = fileIn.readLine()) != null) {
					int beg = line.indexOf("<mbean code=\"org.jboss.naming.NamingService\""); 
					if ( beg > 0) {
						namingConfig = true;
					} else {
						beg = line.indexOf("<attribute name=\"Port\">");
						if (beg > 0 && namingConfig) {
							beg = line.indexOf(">",beg)+1;
							int end = line.indexOf("<",beg);
							if (beg > 0 && end > beg) {
								jndi_port = line.substring(beg,end);
								break;
							}
						}				
					}
					
				}
				fileIn.close();
			} catch (FileNotFoundException e1) {
				printErr("Could not find jboss config ("+jboss+"/server/default/conf/jboss-service.xml)");
				System.exit(1);
			} catch (IOException e) {
				// ignore
			}
			
			try {
			if (Integer.parseInt(jndi_port) < 0) {
				jndi_port="1099";
			}
			} catch (NumberFormatException e) {
				jndi_port="1099";
			}
			
			
			String db_name = null;
			
			try {
				fileIn = new BufferedReader(new FileReader((new File(jboss+"server/default/deploy/mysql-ds.xml"))));
				String line = null;
				boolean namingConfig = false;
				while ((line = fileIn.readLine()) != null) {
					int beg = line.indexOf("<connection-url>jdbc:mysql://localhost");
					if (beg > 0)
						beg = line.indexOf("localhost",beg+1);
					if (beg > 0)
						beg = line.indexOf("/",beg+1)+1;
					if (beg > 0) {
						int end = line.indexOf("<",beg);
						if (beg > 0 && end > beg) {
							db_name = line.substring(beg,end);
							break;
						}
					}
				}
				fileIn.close();
			} catch (FileNotFoundException e1) {
				printErr("Could not find jboss config ("+jboss+"/server/default/conf/jboss-service.xml)");
				System.exit(1);
			} catch (IOException e) {
				// ignore
			}
			
			String municipality_name = db_name;
			String bpel_domain = db_name;
			
			if (db_name != null && db_name.equals("Bozen"))
				municipality_name = "Bolzano-Bozen";
			
			String http_port = getValueFromFile(
					jboss+"/server/default/deploy/jboss-ws4ee.sar/META-INF/jboss-service.xml",
					"<attribute name=\"WebServicePort\"",
					">",
					"<");
			try {
				if (Integer.parseInt(http_port) < 0) {
					http_port="8080";
				}
			} catch (NumberFormatException e) {
				http_port="1099";
			}
			
			
			String https_port = getValueFromFile(
					jboss+"/server/default/deploy/jboss-ws4ee.sar/META-INF/jboss-service.xml",
					"<attribute name=\"WebServiceSecurePort\">",
					">",
					"<");
			
			try {
				if (Integer.parseInt(https_port) < 0) {
					http_port="8443";
				}
			} catch (NumberFormatException e) {
				http_port="8443";
			}
			
			config.setProperty("emayor.MUNICIPALITY",municipality_name);
			config.setProperty("emayor.JBOSS_HTTP",http_port);
			config.setProperty("emayor.JBOSS_JNDI",jndi_port);
	        config.setProperty("emayor.BPEL_DOMAIN",bpel_domain);
	        config.setProperty("emayor.JBOSS_HTTPS",https_port);
        
		} catch (Exception e) {
			//ignore
		}
        
        /*
		 * Variables
		 */
		
		Properties var_desc = new Properties();
		String location = null;
		
		try {
			
		    location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/upgrade.desc";
			
			File file = new File(location);
			
			if (file.exists()) {
				var_desc.load(new FileInputStream(file));	
			} else {
				printErr("Configuration file not found: "+location);
				System.exit(1);
			}
			
		} catch (Exception e2) {}
		
		
		//printOut("Configuration of system variables:\n");
		//printOut("-------------------\n");
		
		TreeMap port_keys = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        port_keys.putAll(var_desc);
		Set s = port_keys.keySet();
		Iterator i = s.iterator();
		
		
		boolean validInput 	= true;
		String key 			= null;
		String key_prop		= null;
		String value 		= null;
		String desc			= null;
		String value_new 	= null;
		
		while (i.hasNext() || (!validInput)) {
			
			if (i.hasNext() && validInput) {
				key 		= (String) i.next();
				key_prop	= "emayor" + key.substring(key.indexOf("."));
				value 		= config.getProperty(key_prop);
				desc 		= var_desc.getProperty(key);
				value_new 	= null;
			}
			
			String acceptance = readIn(desc+" is "+value+" - correct?","yes",true);
			if (!acceptance.toLowerCase().equals("yes")) {
				value_new = null;
			} else {
				value_new = value;
			}
			
			while (value_new == null) {
				printOut(desc+": ");
				try {
					value_new = br.readLine();
					if (value_new != null && value_new.equals(""))
						value_new = null;
				} catch (Exception e3) {
					value_new = null;
				}	
			}
			config.setProperty(key_prop,value_new);
		}
        
	}
	
	/*
	 * get first file that matches a given string 
	 * from a list of directories to search
	 */
	public String getFileName(String[] dirs, String startStr) {
		String result = null;
		
		for (int i=0; i<dirs.length && result == null; i++) {
			File dir = new File(dirs[i]);
			if (dir.exists()) {
				File[] files = dir.listFiles();
				for (int j=0; j<files.length;j++) {
					if (files[j].getName().toLowerCase().startsWith(startStr)) {
						try {
							result = files[j].toURL().getFile();
						} catch (Exception e) {}
						break;
					}
				}
			}
		}
		
		//if (result == null) result = "";
		
		return result;
	}
		
	
	private String readIn(String message, String def, boolean yesno) {
		boolean done = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String readIn = null;
		String result = null;
		
		while (!done) {
			try {
				if (def == null) {
					printOut(message+" :");	
				} else
					printOut(message+" ["+def+"] :");
				readIn = br.readLine(); 
				if (readIn == null || readIn.trim().equals("")) {
					if (def == null) {
						continue;
					} else {
						result = def;
					}
				} else {
					result = readIn.trim();
				}
				
				if (yesno) {
					if (!(result.toLowerCase().equals("yes") || result.toLowerCase().equals("no"))) {
						continue;
					} else
						done = true;
				} else {
					done = true;
				}
			} catch (IOException e) {
				printErr("ERROR: Invalid input! Try again\n");
			}	
		}
		return result;
	}
	
	/*
	 * search for valid JDKs
	 */
	public String getJDKs() {
		String jdk = config.getProperty("emayor.JDK");
		if (jdk != null) {
			String jexec = jdk+"/bin/java.exe";
			if (!checkFile(jexec)) {
				jdk = null;
			}
		}
		if (jdk == null) {
			jdk = getFileName(JDK,"j2sdk1.4.2");
			if (jdk != null) {
				String jexec = jdk+"/bin/java.exe";
				if (!checkFile(jexec)) {
					jdk = null;
				}
			}
		}
		return jdk;
	}
	
	/*
	 * search for valid JREs
	 */
	public String getJREs() {
		String jre = config.getProperty("emayor.JRE_CERTS");
		if (jre != null) {
			String jexec = jre+"/bin/java.exe";
			if (!checkFile(jexec)) {
				jre = null;
			}
		}
		if (jre == null) {
			jre = config.getProperty("emayor.JDK") + "/jre";
			if (jre != null) {
				String jexec = jre + "/bin"+"/java.exe";
				if (!checkFile(jexec)) {
					jre = null;
				}
			}
		}
		return jre;
	}
	
	/*
	 * search valid JBoss installations
	 */
	public String getJBOSS() {
		String jboss = config.getProperty("emayor.JBOSS");
		if (jboss != null) {
			String jbat = jboss+"/bin/run.bat";
			if (!checkFile(jbat)) {
				jboss = null;
			}
		}
		if (jboss == null) {
			jboss = getFileName(JBOSS,"jboss");
			if (jboss != null) {
				String jbat = jboss+"/bin"+"/run.bat";
				if (!checkFile(jbat)) {
					jboss = null;
				}
			}
		}
		return jboss;
	}
	
	/*
	 * search valid MySQL installations
	 */
	public String getMySQL() {
		String mysql = config.getProperty("emayor.MYSQL");
		if (mysql != null) {
			String mysqlExe = mysql+"/bin/mysql.exe";
			if (!checkFile(mysqlExe)) {
				mysql = null;
			}
		}
		if (mysql == null) {
			mysql = getFileName(MYSQL,"mysql");
			if (mysql != null) {
				String mysqlExe = mysql+"/bin"+"/mysql.exe";
				if (!checkFile(mysqlExe)) {
					mysql = null;
				}
			}
		}
		return mysql;
	}
	
	/*
	 * print to stdout
	 */
	public void printOut(String str) {
		System.out.print(str);
	}
	
	/*
	 * print to stderr
	 */
	public void printErr(String str) {
		System.err.print(str);
	}

	public void readConfig() {
		
		/*
		 * get config
		 */
		String location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH;
		
	    try {
	    	String properties = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/municipality.properties";
			
			File fileIn = new File(properties);
			
			if (fileIn.exists()) {
				this.config.load(new FileInputStream(fileIn));
				this.config.setProperty("INSTALLATION_PATH",fileIn.getParentFile().toURL().getFile().substring(1));
			} else {
				printErr("Configuration not found: "+properties);
				System.exit(1);
			}
			
		} catch (Exception e2) {
			printErr("Error while reading configuration!");
			System.exit(1);
		}
	}
	
	/*
	 * get environment
	 */
	public void getEnvironment() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		/*
		 * Municipality ID
		 */
		String municipality = null;
		String municipality_id = null;
		String municipality_lang = null;
		boolean correctIn 	= false;
		
		printOut("\t [1]  -  Aachen\n");
		printOut("\t [2]  -  Bolzano-Bozen\n");
		printOut("\t [3]  -  Seville\n");
		printOut("\t [4]  -  Siena\n");
		printOut("--------------------------------------\n ");
		while (!correctIn) {
			printOut("Please choose your Municipality (1-4): ");	
			try {
				municipality = br.readLine();
				correctIn = true;
				if (municipality.equals("1")) {
					municipality_id = "Aachen";
					municipality = "Aachen";
					municipality_lang = "DE";
				} else
				if (municipality.equals("2")) {
					municipality_id = "Bolzano-Bozen";
					municipality = "Bozen";
					municipality_lang = "IT";
				} else
				if (municipality.equals("3")) {
					municipality_id = "Seville";
					municipality = "Seville";
					municipality_lang = "ES";
				} else
				if (municipality.equals("4")) {
					municipality_id = "Siena";
					municipality = "Siena";
					municipality_lang = "IT";
				} else 
					correctIn = false;
			} catch (IOException e) {
				printErr("ERROR: Invalid input! Try again\n");
			}
		}
		
		config.setProperty("emayor.MUNICIPALITY",municipality_id);
		config.setProperty("emayor.MUNICIPALITY_DB_NAME",municipality);
		config.setProperty("emayor.BPEL_DOMAIN",municipality_id);
		
		String e2m = config.getProperty("emayor.E2MSERVER");
		if (e2m != null && e2m.equals("placeholder")) {
			config.setProperty("emayor.E2MSERVER","//localhost:2001/"+municipality+"/E2MServer");
		}
		
		String lang = config.getProperty("emayor.LANGUAGE_TAG");
		if (lang != null && lang.equals("placeholder")) {
			config.setProperty("emayor.LANGUAGE_TAG",municipality_lang);
		}
		
		
		/*
		 * JDK
		 */
		String jdk = getJDKs();
		if (jdk != null) {
			String acceptance = readIn("Found JDK located under "+jdk+" - correct?" ,"yes",true);
			if (acceptance.toLowerCase().equals("no")) {
				jdk = null;
			}
		}
		while (jdk == null) {
			jdk = readIn("Path to JDK installation",null,false);
			if (!checkFile(jdk)) {
				printErr("no such directory: "+jdk+"\n");
				jdk = null;
				//System.exit(0);
			} else {
				String jexec = jdk+"/bin"+"/java.exe";
				if (!checkFile(jexec)) {
					printErr("java executable not available at "+jexec+"\n");
					//System.exit(1);
					jdk = null;
				}
			}
		}
		
		try {
			config.setProperty("emayor.JDK",new File(jdk).toURL().getFile().substring(1));
		} catch (Exception e) {
			System.exit(1);
		}
		
		/*
		 * JRE
		 */
		String jre = getJREs();
		if (jre != null) {
			String acceptance = readIn("Found JRE located under "+jre+" - correct?" ,"yes",true);
			if (acceptance.toLowerCase().equals("no")) {
				jre = null;
			}
		}
		while (jre == null) {
			jre = readIn("There was no JRE (1.4) found. (It should be included in you JDK installation somewhere around "+jdk+"\nPath to JRE installation",null,false);
			if (!checkFile(jre)) {
				printErr("no such directory: "+jre+"\n");
				jre = null;
				//System.exit(0);
			} else {
				String jcerts = jre+"/lib"+"/security"+"/cacerts";
				if (!checkFile(jcerts)) {
					printErr("jre certificates not available at "+jcerts+", searching in JDK path ....\n");
					
					jcerts = jdk+"/jre/lib/security/cacerts";
					
					if (!checkFile(jcerts)) {
						printErr(" failed!");
						//System.exit(1);
						jre = null;
					}
				}
			}
		}
		try {
			config.setProperty("emayor.JRE_CERTS",new File(jre).toURL().getFile().substring(1));
		} catch (Exception e) {
			System.exit(1);
		}
		
		/*
		 * Oracle BPEL PM
		 */
		File obant_path = null;
		String orabpel = config.getProperty("emayor.ORABPEL");
		if (orabpel != null) {
			obant_path = new File(orabpel);
			if (!obant_path.exists()) {
				obant_path = null;	
			}
		}
		
		while (obant_path == null) {
			try {
				obant_path = new File(ClassLoader.getSystemClassLoader().getSystemResource(".").getFile()).getParentFile().getParentFile();
				orabpel = obant_path.getPath();
			} catch (Exception e) {
				orabpel = readIn("Path to Oracle BPEL PM installation (e.g. c:\\ORABPELPM)",null,false);
				if (!checkFile(orabpel)) {
					printErr("no such directory: "+orabpel+"\n");
					//System.exit(0);
					obant_path = null;
				}
				orabpel += "/integration/orabpel";
				if (!checkFile(orabpel)) {
					printErr("no such directory: "+orabpel+"\n");
					obant_path = null;
					//System.exit(0);
				} else {
					obant_path = new File(orabpel);
					if ((!obant_path.exists()) || (!obant_path.getName().equals("orabpel"))) {
						printErr("Oracle BPEL PM not found\n");
						//System.exit(1);
						obant_path = null;
					}
				}
			}
		}
		
		try {
			config.setProperty("emayor.ORABPEL",new File(orabpel).toURL().getFile().substring(1));
		} catch (Exception e1) {
			System.exit(1);
		}
		
		
		/*
		 * JBOSS
		 */
		String jboss = getJBOSS();
		if (jboss != null) {
			String acceptance = readIn("Found JBoss located under "+jboss+" - correct?" ,"yes",true);
			if (acceptance.toLowerCase().equals("no")) {
				jboss = null;
			}
		}
		while (jboss == null) {
			printOut("Path to JBoss4 installation: ");
			try {
				jboss = br.readLine();
			} catch (IOException e) {
				printErr("ERROR: Invalid input!\n");
				//e.printStackTrace();
				jboss = null;
				//System.exit(0);
			}
			if (!checkFile(jboss)) {
				printErr("no such directory: "+jboss+"\n");
				//System.exit(0);
				jboss = null;
			} else {
				for (int i = 0; i<JBOSS_TREE.length; i++) {
					String jFile = jboss+"/"+JBOSS_TREE[i];
					if (!checkFile(jFile)) {
						printErr("missing jboss file or directory "+jFile+"\n");
						//System.exit(1);
						jboss = null;
					}
				}
			}
		}
		try {
			config.setProperty("emayor.JBOSS",new File(jboss).toURL().getFile().substring(1));
		} catch (Exception e) {
			System.exit(1);
		}		
		
		/*
		 * MySQL
		 */
		String mysql = getMySQL();
		if (mysql != null) {
			String acceptance = readIn("Found MySQL located under "+mysql+" - correct?" ,"yes",true);
			if (acceptance.toLowerCase().equals("no")) {
				mysql = null;
			}
		}
		while (mysql == null) {
			printOut("Path to MySQL installation (e.g. c:\\mysql4.1): ");
			try {
				mysql = br.readLine();
			} catch (IOException e) {
				printErr("ERROR: Invalid input!\n");
				//System.exit(0);
				mysql = null;
			}
			if (!checkFile(mysql)) {
				printErr("no such directory: "+mysql+"\n");
				//System.exit(0);
				mysql = null;
			} else {
				String mFile = mysql + "/bin/mysql.exe";
				if (!checkFile(mFile)) {
					printErr("missing mysql executable "+mFile+"\n");
					//System.exit(1);
					mysql = null;
				} 
			}
		}
		try {
			config.setProperty("emayor.MYSQL",new File(mysql).toURL().getFile().substring(1));
		} catch (Exception e) {
			System.exit(1);
		}
		
		String acceptance = "test";
		String emailMode = "test";
		acceptance = readIn("\nDo you have smartcards installed?","yes",true);
		
		if (acceptance.toLowerCase().equals("yes")) {
			config.setProperty("emayor.USESMARTCARDS","1");
			
			String language = config.getProperty("emayor.LANGUAGE_TAG");
			String languageIn = null;
			if (language == null) language = "EN";
			correctIn 	= false;
			
			printOut("What is the country of your municipality?\n You will find this on you smartcard (e.g. DE or IT) ["+language+"]: \n");
			while (!correctIn) {	
				try {
					languageIn = br.readLine();
					correctIn = true;
					if (languageIn.length() > 1) {
						config.setProperty("emayor.LANGUAGE_TAG",languageIn.toUpperCase());
					} else 
					if (languageIn.trim().equals("")) {
						config.setProperty("emayor.LANGUAGE_TAG",language.toUpperCase());
					} else
						correctIn = false;
				} catch (IOException e) {
					printErr("ERROR: Invalid input! Try again\n");
				}
			}
			
			acceptance = "test";
			acceptance = readIn("\nDo you want to use the proper email addresses (stored on the smartcards) for notification?","yes",true);
			
			if (acceptance.trim().toLowerCase().equals("yes")) {
				emailMode = "production";
			}
			
		} else {
			config.setProperty("emayor.USESMARTCARDS","0");
			config.setProperty("emayor.LANGUAGE_TAG","BE");
		}
		
		config.setProperty("emayor.EMAIL_MODE",emailMode);
		
		if (emailMode.equals("production")) {
			config.setProperty("emayor.EMAIL_TEST_ADDRESS","NOT_USED");
		} else {
			String testEmail = config.getProperty("emayor.EMAIL_TEST_ADDRESS");
			testEmail = readIn("Please enter the email address that should be used for notification",testEmail,false);
			config.setProperty("emayor.EMAIL_TEST_ADDRESS",testEmail);
		}	

		
		/*
		 * Variables
		 */
		
		Properties var_desc = new Properties();
		String location = null;
		
		try {
			
		    location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/installation.desc";
			
			File fileIn = new File(location);
			
			if (fileIn.exists()) {
				var_desc.load(new FileInputStream(fileIn));	
			} else {
				printErr("Configuration file not found: "+location);
				System.exit(1);
			}
			
		} catch (Exception e2) {}
		
		
		printOut("Configuration of system variables:\n");
		printOut("-------------------\n");
		
		TreeMap port_keys = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        port_keys.putAll(var_desc);
		Set s = port_keys.keySet();
		Iterator i = s.iterator();
		
		
		boolean validInput 	= true;
		String key 			= null;
		String key_prop		= null;
		String value 		= null;
		String desc			= null;
		String value_new 	= null;
		
		while (i.hasNext() || (!validInput)) {
			
			if (i.hasNext() && validInput) {
				key 		= (String) i.next();
				key_prop	= "emayor" + key.substring(key.indexOf("."));
				value 		= config.getProperty(key_prop);
				desc 		= var_desc.getProperty(key);
				value_new 	= null;
			}
			
			printOut("\t - "+desc+" ["+value+"]: ");
			try {
				value_new = br.readLine();
				if (value_new.trim().equals("") || value_new.equals(value)) {
					value_new = value;
					validInput = true;
				} else
				if (value_new != null) {
					validInput = true;
					config.setProperty(key_prop,value_new);
				} else {
					validInput = false;
				}
			} catch (Exception e3) {
				// <enter> pressed, use defaults -> do nothing
				value_new = value;
				validInput = true;
			}	
		}
		
		

		acceptance = "test";
		acceptance = readIn("\nDo you need to authenticate to the mail server ("+config.getProperty("emayor.MAILHOST")+")?","no",true);
		if (acceptance.toLowerCase().equals("yes")) {
			config.setProperty("emayor.EMAIL_AUTH","true");
			acceptance = readIn("\nPlease enter the username for authentication on the mailserver ("+config.getProperty("emayor.MAILHOST")+")?",config.getProperty("emayor.EMAIL_USER"),false);
			config.setProperty("emayor.EMAIL_USER",acceptance);
			acceptance = readIn("\nPlease enter the password for authentication on the mailserver ("+config.getProperty("emayor.MAILHOST")+")?",config.getProperty("emayor.EMAIL_PASS"),false);
			config.setProperty("emayor.EMAIL_PASS",acceptance);
		} else {
			config.setProperty("emayor.EMAIL_AUTH","false");
			config.setProperty("emayor.EMAIL_USER","NOTUSED");
			config.setProperty("emayor.EMAIL_PASS","NOTUSED");
		}
		
		acceptance = "test";
		acceptance = readIn("\nDo you have a local uddi server and want to configure it?","no",true);

		if (acceptance.toLowerCase().equals("yes")) {
			String host;
			try {
				host = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {
				host = "localhost";
			} 
			config.setProperty("emayor.UDDI_HOST",host);
			config.setProperty("emayor.UDDI_CONFIGURED","true");
			printOut("Configuration of uddi server:\n");
			printOut("-------------------\n");
			
			for (int j=0;j < UDDI_ENTRIES.length;j++) {
				host = config.getProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_HOST");
				String port = config.getProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_PORT");
				if (UDDI_ENTRIES[j].toUpperCase().equals(municipality.toUpperCase())) {
					try {
						host = InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e1) {
						host = null;
					} 
				}
				if (host == null) host = "localhost";
				if (port == null) port = "8443";
				printOut("\nIP Address of "+UDDI_ENTRIES[j]+"? ["+host+"]: ");
				String host_new;
				try {
					host_new = br.readLine();
				} catch (IOException e) {
					host_new = host;
				}
				if (! (host_new.trim().equals("") || host_new.equals(host))) {
					config.setProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_HOST",host_new);
				} else {
					config.setProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_HOST",host);
				}
				if (UDDI_ENTRIES[j].toUpperCase().equals(municipality.toUpperCase())) {
					config.setProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_PORT",config.getProperty("emayor.JBOSS_HTTPS"));
					continue;
				}
				printOut("\nHTTPS port of "+UDDI_ENTRIES[j]+"? ["+port+"]: ");
				String port_new;
				try {
					port_new = br.readLine();
				} catch (IOException e) {
					port_new = host;
				}
				if (! (port_new.trim().equals("") || port_new.equals(port))) {
					config.setProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_PORT",port_new);
				} else {
					config.setProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_PORT",port);
				}
			}
		} else {
			acceptance = config.getProperty("emayor.UDDI_HOST");
			acceptance = readIn("\nPlease enter the IP of the UDDI host (or just go ahead if you don´t need one)",acceptance,false);
			config.setProperty("emayor.UDDI_HOST",acceptance);
			config.setProperty("emayor.UDDI_CONFIGURED","false");
		}
		
	}					
	
	
	/*
	 * clear out previously installed files
	 */
	public void clearPreviousInstall() {
		
		String jboss_home 	= config.getProperty("emayor.JBOSS");
		String orabpel_home = config.getProperty("emayor.ORABPEL");
		String municipality = config.getProperty("emayor.MUNICIPALITY");
		
		for (int i=0; i<JBOSS_OLD_INSTALL.length; i++) {
			File temp = new File(jboss_home + File.separator + JBOSS_OLD_INSTALL[i]);
			if (temp.exists()) {
				printOut(temp.getPath()+" ... ");
				if (!debug) temp.delete();
				printOut("deleted\n");
			}
		}
		
		File temp = new File(orabpel_home + File.separator + "domains" + File.separator + municipality);
		if (temp.exists()) {
			printOut(temp.getPath()+" ... ");
			if (!debug) temp.delete();
			printOut("deleted\n");
		}
	}
	
	/*
	 * write new configuration settings to files
	 */
	private boolean writeFiles(String pathToMap) {
		BufferedReader 	in 		= null;
		try {
			
			String location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/"+pathToMap;
			
			File fileIn = new File(location);
			
			if (!fileIn.exists()) return false;
			
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));
			
		} catch (Exception e1) {
			this.error = e1.getMessage(); 
			return false;
		}
		
		String line = null;
		try {
			while ((line = in.readLine()) != null) {
				String sep = null;
				
				if (line.length() == 0) sep = "#";
				else sep = line.substring(0,1);
				
				// ignore comments (or empty lines)
				if (!sep.equals("#")) {
					String[] fields = line.split(sep);
					if (fields[2] == null || fields[2].equals(""))
						fields[2] = "0";
					
					String 	file 		= fields[1];
					
					while (file.matches("^(.*?)\\$\\{\\S*\\}(.*?)$")) {
						int beg_idx = file.indexOf("${",0);
						int end_idx = file.indexOf("}",beg_idx);
						String orig_sstr 	= file.substring(beg_idx+2,end_idx);
						if (config.containsKey(orig_sstr)) {
							file = file.replaceFirst("\\$\\{"+orig_sstr+"\\}",config.getProperty(orig_sstr));
						} else {
							file = file.replaceFirst("\\$\\{"+orig_sstr+"\\}",config.getProperty(orig_sstr));
						}
					}		
					
					int 	lineNr 		= Integer.parseInt(fields[2]);
					String 	mode 		= fields[3];
					String 	original 	= fields[4];
					
					while (original.matches("^(.*?)\\$\\{\\S*\\}(.*?)$")) {
						int beg_idx = original.indexOf("${",0);
						int end_idx = original.indexOf("}",beg_idx);
						String orig_sstr 	= original.substring(beg_idx+2,end_idx);
						if (config.containsKey(orig_sstr)) {
							original = original.replaceFirst("\\$\\{"+orig_sstr+"\\}",config.getProperty(orig_sstr));
						} else {
							original = original.replaceFirst("\\$\\{"+orig_sstr+"\\}",config.getProperty(orig_sstr));
						}
					}
										
					//printOut("file: "+file+", lineNr: " + lineNr+" ,mode: "+mode+" ,original: "+original+"\n");
					
					String	replacement = null;
					
					if (fields.length >= 6) {
						replacement = fields[5];
						while (replacement.matches("^(.*?)\\$\\{\\S*\\}(.*?)$")) {
							int beg_idx = replacement.indexOf("${",0);
							int end_idx = replacement.indexOf("}",beg_idx);
							String orig_sstr 	= replacement.substring(beg_idx+2,end_idx);
							if (config.containsKey(orig_sstr)) {
								replacement = replacement.replaceFirst("\\$\\{"+orig_sstr+"\\}",config.getProperty(orig_sstr));
							} else {
								replacement = replacement.replaceFirst("\\$\\{"+orig_sstr+"\\}",config.getProperty(orig_sstr));
							}
						}
					}		
					
					//printOut("file: "+file+", lineNr: " + lineNr+" ,mode: "+mode+" ,original: "+original+" ,replace: "+replacement+"\n");
					
					if (!original.equals(replacement)) {
						boolean result = editFile(file,lineNr,mode,original,replacement);
						//printOut("file: "+file+", lineNr: "+lineNr+", mode: "+mode+", orig: "+original+", replacement: "+replacement);
						if (!result) {
							printOut("edit " + file + "... failed.\n");
							return false;
						}
					}
				}
			}
			in.close();
		} catch (IOException e2) {
			this.error = e2.getMessage(); 
			return false;
		}
		
		return true;
	}

	
	/*
	 * small editor
	 */
	private boolean editFile(String file, int lineNr, String mode, String original, String replacement) {
		BufferedReader input = null;
		File fileIn = null;
		String line = null;
		String newLine = null;
		StringBuffer result = new StringBuffer();
		int currentLineNr = 0;
		//String location = null;
		boolean changed = false;
		
		try {
			
			//location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/" + file;
			
			fileIn = new File(file);
			
			if (!fileIn.exists()) return false;
			
			input = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));
			
			//if (debug) printOut("line: "+currentLineNr+", mode: "+mode+"\n");
			
			while ((line = input.readLine()) != null) {
				currentLineNr++;
				if (currentLineNr == lineNr) {
					//if (debug) printOut("BEFORE: "+line);
					if (mode.equals("r")) {
						newLine = original;
					} else
					if (mode.equals("e")) {
						newLine = line.replaceAll(original,replacement);
					} else
					if (mode.equals("i")) {
						newLine = original + "\n" + line;
					}
					if (!line.equals(newLine)) {
						line = newLine;
						changed = true;
					}
					//if (debug) printOut(" - AFTER: "+line+"\n");
				}
				result.append(line+"\n");
			}
			
			String out = result.toString();
			if (lineNr == 0 && mode.equals("e")) {
				out = out.replaceAll(original,replacement);
				changed = true;
			}
			
			input.close();
			
			if (changed) {
				File fileOut = new File(file);
				FileOutputStream output = new FileOutputStream(fileOut);
				output.write(out.getBytes());
				output.close();
			}
			
		} catch (IOException e) {
			this.error = e.getMessage();
			return false;
		}
		
		return true;
	}
	
	/*
	 * write out configuration to municipality.properties
	 */
	public void writeConfig() {
		
		try {
			String location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/municipality.properties";
			
			File fileOut = new File(location);
			
			StringBuffer out = new StringBuffer();
			Enumeration keys = config.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				out.append(key).append("=").append(config.getProperty(key)).append("\n");
			}
			
			FileOutputStream output = new FileOutputStream(fileOut);
			output.write(out.toString().getBytes());
		} catch (Exception e) {
			printErr("Error while writing configuration down.");
			System.exit(1);
		}
	}
	
	/*
	 * set up MySQL DB

	public void setupMySQL() {
		Connection connection = null;
	    try {
	        String driverName = "com.mysql.jdbc.Driver";
	        Class.forName(driverName);
	    
	        String serverName = "localhost";
	        String url = "jdbc:mysql://" + serverName +  "/"; 
	        String username = "root";
	        String password = "root";
	        connection = DriverManager.getConnection(url, username, password);
	        Statement stmt = connection.createStatement();
	        stmt.execute("create database testeMayor");
	        stmt.close();
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        // Could not find the database driver
	    } catch (SQLException e) {
	        // Could not connect to the database
	    }
	}
	 */
	
	/*
	 * get the whole thing running
	 */
	public static void main(String[] args) {

		if (args.length == 1 && args[0].equals("configure")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			Main main = new Main();
			
			main.printOut("Setting up environment ...\n");
			main.printOut("-------------------------------------\n");
			main.readConfig();
			main.getEnvironment();
			main.printConfig();
			
			String acceptance = "no";
			main.printOut("\nIf these settings are correct type \"yes\" below and press <enter>, otherwise <enter> aborts.\n");
			try {
				acceptance = br.readLine();
			} catch (IOException e) {}
			if (!acceptance.toLowerCase().equals("yes")) {
				main.printOut("Aborted.");
				System.exit(1);
			}
			
			main.printOut("\n\n");
			main.printOut("Writing down configuration ...\n");
			main.printOut("-------------------------------------\n");
			main.writeConfig();
			
			//main.printOut("\n\n");
			//main.printOut("Check for previous installation files\n");
			//main.printOut("-------------------------------------\n");
			//main.clearPreviousInstall();
		} else
		if (args.length == 1 && args[0].equals("editInstall")) {
			Main main = new Main();
			
			main.readConfig();
			main.printOut("\n\n");
			main.printOut("Editing files ...\n");
			main.printOut("-------------------------------------\n");
			if (!main.writeFiles("installation.map")) {
				main.printErr("Error occurred: "+main.getError());
				System.exit(1);
			}
		} else
		if (args.length == 1 && args[0].equals("editUpgrade")) {
			Main main = new Main();
			
			main.readConfig();
			main.printOut("\n\n");
			main.printOut("Editing files ...\n");
			main.printOut("-------------------------------------\n");
			if (!main.writeFiles("upgrade.map")) {
				main.printErr("Error occurred: "+main.getError());
				System.exit(1);
			}
		} else 
		if (args.length == 1 && args[0].equals("upgrade")) {
			Main main = new Main();
			main.printOut("Setting up environment ...\n");
			main.printOut("-------------------------------------\n");
			main.readConfig();
			main.getUpgradeConfig();
			main.printOut("\n\n");
			main.printOut("Writing down configuration ...\n");
			main.printOut("-------------------------------------\n");
			main.writeConfig();
		}
	}
	
	/*
	 * print out configuration setting
	 */
	private void printConfig() {
		printOut("\nConfiguration Summary:\n");
		printOut("--------------------------------------------\n");
		
		printOut("JDK 1.4.2        -\t "+config.getProperty("emayor.JDK")+"\n");
		printOut("JRE certificates -\t "+config.getProperty("emayor.JRE_CERTS")+"\n");
		printOut("JBoss 4.0.0      -\t "+config.getProperty("emayor.JBOSS")+"\n");
		printOut("Tomcat 5.x.x     -\t "+config.getProperty("emayor.TOMCAT")+"\n");
		printOut("Oracle BPEL PM   -\t "+config.getProperty("emayor.ORABPEL")+"\n");
		printOut("MySQL            -\t "+config.getProperty("emayor.MYSQL")+"\n");
		printOut("Municipality ID  -\t "+config.getProperty("emayor.MUNICIPALITY")+"\n");
		if (config.getProperty("emayor.USESMARTCARDS").equals("1")) {
			printOut("Language ID  	   -\t "+config.getProperty("emayor.LANGUAGE_TAG")+"\n");	
		} else {
			printOut("Notification Address (email) -\t "+config.getProperty("emayor.EMAIL_TEST_ADDRESS")+"\n");
		}
		if (config.getProperty("emayor.UDDI_CONFIGURED").equals("true")) {
			for (int j=0;j<UDDI_ENTRIES.length;j++) {
				String host = config.getProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_HOST");
				String port = config.getProperty("emayor.UDDI_"+UDDI_ENTRIES[j].toUpperCase()+"_PORT");
				printOut("IP Address of "+UDDI_ENTRIES[j]+" -\t "+host+"\n");
				printOut("HTTPS port of "+UDDI_ENTRIES[j]+" -\t "+port+"\n");
			}
		}
		
		if (config.getProperty("emayor.EMAIL_AUTH").equals("true")) {
			printOut("Email authentication (user) -\t "+config.getProperty("emayor.EMAIL_USER")+"\n");
			printOut("Email authentication (password) -\t "+config.getProperty("emayor.EMAIL_PASS")+"\n");
		}
		
		Properties var_desc = new Properties();
		
		String location = null;
		
		try {
			
		    location = this.getClass().getResource(".").getPath() + INSTALLATION_PATH + "/installation.desc";
			
			File fileIn = new File(location);
			
			var_desc.load(new FileInputStream(fileIn));
			
		} catch (Exception e2) {}
		
		
		TreeMap port_keys = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        port_keys.putAll(var_desc);
		Set s = port_keys.keySet();
		Iterator i = s.iterator();
		
		String key, value, desc, key_prop;
		
		while (i.hasNext()) {
			
			key 		= (String) i.next();
			key_prop	= "emayor" + key.substring(key.indexOf("."));
			value 		= config.getProperty(key_prop);
			desc 		= var_desc.getProperty(key);
			
			printOut(desc+" -\t "+value+"\n");
		}
	}
	
	/*
	 * set internal error field
	 */
	public String getError() {
		return this.error;
	}
	
}