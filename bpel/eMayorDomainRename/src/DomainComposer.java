
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/*
 * Created on 30.06.2005
 */

/**
 * @author mxs
 *
 */
public class DomainComposer {
	
	/*
	 * where to start
	 */
	private String srcDir = "../";
	
	/*
	 * prefix for working from <project>/bin/
	 */
	private String resourcePrefix = File.separator+".."+File.separator;
	
	/*
	 * string to search
	 */
	private String searchStr = "http://localhost:9700/orabpel/";
	private String searchPat = "^(.*?)"+searchStr+"(.*?)$";

	/*
	 * searchStr will be searched an replaced by searchStr + domainName
	 */
	private String domainName = "Aachen";
	
	
	/*
	 * values to replace (see initKeys())
	 */
	private HashMap keywords;
	
	/*
	 * create a new process from a template
	 */
	public DomainComposer(String action, String deploy) {
	
		this.domainName = deploy;
		
		
		try {
			/*
			 * where are we?
			 */
			String currentDir = this.getClass().getClassLoader().getResource(".").getPath();
			/*
			 * where will we go?
			 */
			/*
			 * big copy/rename/edit part
			 */
			
			if (action.equals("rename")) {
				editFiles(currentDir+resourcePrefix+srcDir,1);
			} else if (action.equals("reset")) {
				resetFiles(currentDir+resourcePrefix+srcDir,1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * recursive copy (files + directories)
	 * rename files
	 * edit files and replace patterns
	 */
	private void editFiles(String src, int depth) {
		
		if (depth < 0) return;
		
		File srcDir = new File(src);
		
		long lastModified = System.currentTimeMillis();
		
		File backup = null;
		File[] srcFiles = srcDir.listFiles();
		int i;
		for (i=0; i<srcFiles.length;i++) {
			//System.out.println(srcFiles[i].getName());
			if (srcFiles[i].getName().matches("bpel.xml")) {
				// get time of old file
				lastModified = srcFiles[i].lastModified();
				// create backup file
				backup = new File(srcDir.getPath()+File.separator+"bpel_old.xml");
				if (backup.exists()) {
					// something went wrong last time, so
					// keep backup, but copy it back to original
					// to prevent errors
					copyFile(backup,srcFiles[i]);
				} else {
					// copy to backup
					copyFile(srcFiles[i],backup);
					// set backup time to old time
					backup.setLastModified(lastModified);
				}
				// edit & change original file
				editFile(srcDir + File.separator+ srcFiles[i].getName());
				// set old time on modified version
				srcFiles[i].setLastModified(lastModified);
			}
			if (srcFiles[i].isDirectory()) {
				editFiles(srcFiles[i].getPath(),depth-1);
			}
		}
	}
	
	private void resetFiles(String src, int depth) {
		
		
		if (depth < 0) return;
		
		File srcDir = new File(src);
		
		long lastModified = System.currentTimeMillis();
		File original = null;
		
		File[] srcFiles = srcDir.listFiles();
		int i;
		for (i=0; i<srcFiles.length;i++) {
			// delete if backup available
			if (srcFiles[i].getName().matches("bpel.xml") && new File(srcDir.getPath()+File.separator+"bpel_old.xml").exists()) {
				srcFiles[i].delete();
			} else
			if (srcFiles[i].getName().matches("bpel_old.xml")) {
				lastModified = srcFiles[i].lastModified();
				original = new File(srcDir.getPath()+File.separator+"bpel.xml");
				srcFiles[i].renameTo(original);
				original.setLastModified(lastModified);
			} else
			if (srcFiles[i].isDirectory()) {
				resetFiles(srcFiles[i].getPath(),depth-1);
			}
		}
	}
	
	public void copyFile(File in, File out) {
		try {
			FileInputStream fis  = new FileInputStream(in);
			FileOutputStream fos = new FileOutputStream(out);
			byte[] buf = new byte[1024];
			int i = 0;
			while((i=fis.read(buf))!=-1) {
				fos.write(buf, 0, i);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {}
    }
	  
	private void editFile(String file) {
		FileInputStream message_in = null;
		FileOutputStream message_out = null;
		BufferedReader in = null;
		BufferedWriter out = null;
		String line = null;
		String newLine = null;
		String fileAsString = null;
		boolean changed = false;
		
		try {
			message_in = new FileInputStream(new File(file));
			in = new BufferedReader(new InputStreamReader(message_in));
			fileAsString = new String();
			while ((line = in.readLine()) != null) {
				newLine = line;

				if (line.matches(searchPat)) {
					//System.out.println("edit line in file: "+file);
					newLine = editLine(line);
				}
				
				fileAsString += newLine + "\n";
				
				if (! line.equals(newLine)) {
					changed = true;
				}
			}
			in.close();
			message_in.close();
			
			//System.out.println(fileAsString);
			
			if (changed) {
				message_out = new FileOutputStream(new File(file));
				out = new BufferedWriter(new OutputStreamWriter(message_out));
				out.write(fileAsString);
				out.close();
				message_out.close();
				System.out.println(file+ ":CHANGED");
			}
		} catch (Exception e) {
			/*
			 * should not happen
			 */
			e.printStackTrace();
		}
	}
	
	private String editLine(String line) {
		
		boolean changed = false;
		
		String result = new String();
		int beg_idx = 1, end_idx = 0;
		while (beg_idx != -1) {
			/*
			 * line matched, now find out which key is present
			 */
			beg_idx = line.indexOf(searchStr,end_idx);
			if (beg_idx < 0) continue;
			
			result += line.substring(end_idx,beg_idx) + searchStr + domainName;
			
			beg_idx += searchStr.length();
			end_idx = line.indexOf("/",beg_idx);
			

			
			/*
			 * if domainame is wrong bad things can happen
			 * - do this dirty workaround, so nothing will
			 * be destroyed
			 * 
			 */
			if (end_idx <= 0 || line.substring(beg_idx,end_idx).equals(domainName)) {
				beg_idx = -1;
			} else {
				result += line.substring(end_idx,line.length());
				line = result;
			}
		}
		
		return line;
	}
	
	public static void main(String[] args) {
		/*
		 * args[0] == Process Name
		 * args[1] == Namespace
		 */
		String args0 = null;
		String args1 = null;
		if (args.length == 1) {
			if (args[0].equals("reset")) args0 = args[0];
		} else
		if (args.length == 2) {
			if (args[0].equals("rename") && (!args[1].trim().equals(""))) {
				args0 = args[0];
				args1 = args[1];
			}
		}
		if (args0 == null) {
			System.out.println("syntax:\n" +
					"DomainComposer <action> <domain>\n" +
					"where <action> is one of" +
					"rename: \t - rename to <domain>" +
					"reset : \t - reset to previous");
			System.exit(1);
		} else {
			//if (args.length == 2) DomainComposer composer = new DomainComposer(args[0],args[1]);
			//else DomainComposer composer = new DomainComposer(args[0],args[1]);
			DomainComposer composer = new DomainComposer(args0,args1);
		}
	}
}
