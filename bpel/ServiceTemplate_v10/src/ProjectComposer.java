import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class ProjectComposer {
	
	private String resourcePrefix = File.separator+".."+File.separator;
	
	/*
	 * existing template name
	 */
	private String srcTemplateName = "ServiceTemplate_v10";
	/*
	 * location of template
	 */
	private String srcDir = "BPEL-Project"+File.separator+srcTemplateName;
	/*
	 * parent location of new project
	 */
	private String dstDir = "BPEL-Project";
	/*
	 * version of service
	 */
	private String version = "1.0";
	/*
	 * domain of service
	 */
	private String domain = "default";
	
	/*
	 * example values, never used
	 */
	private String processName = "ServiceExample_v10";
	private String nameSpace = "http://www.emayor.org/"+processName;
	
	/*
	 * values to replace (see initKeys())
	 */
	private HashMap keywords;
	
	/*
	 * create a new process from a template
	 */
	public ProjectComposer(String processName, String nameSpace, String version, String domain, String dstDir) {
		this.processName = processName;
		this.nameSpace = nameSpace;
		this.version = version;
		this.domain = domain;
		if (dstDir != null) {
			this.dstDir = dstDir;
			File dst = new File(dstDir);
			this.dstDir = dst.getPath();
		}
		dstDir += File.separator+processName;
		
		initKeys();
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
			copyFiles(currentDir+resourcePrefix+srcDir,currentDir+resourcePrefix+dstDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * initialize keyword mappings
	 */
	private void initKeys() {
		keywords = new HashMap();
		keywords.put("NAMESPACE",nameSpace);
		keywords.put("PROCESSNAME",processName);
		keywords.put("VERSION",version);
		keywords.put("DOMAIN",domain);
	}
	
	/*
	 * recursive copy (files + directories)
	 * rename files
	 * edit files and replace patterns
	 */
	private void copyFiles(String src, String dst) {
		
		File srcDir = new File(src);
		File dstDir = new File(dst);
		if (! dstDir.exists()) dstDir.mkdir();
		System.out.println(srcDir.getPath()+"->"+dstDir.getPath()+"");
		
		File[] srcFiles = srcDir.listFiles();
		File dstFile;
		int i;
		for (i=0; i<srcFiles.length;i++) {
			if (srcFiles[i].getName().matches("^(.*?)"+srcTemplateName+"(.*?)$")) {
				dstFile = new File(dstDir + File.separator+ srcFiles[i].getName().replaceAll(srcTemplateName,processName));
			} else {
				dstFile = new File(dstDir + File.separator+ srcFiles[i].getName());
			}
			if (srcFiles[i].isDirectory()) {
				copyFiles(srcFiles[i].getPath(),dstFile.getPath());
			} else {
				rewriteFile(srcFiles[i],dstFile);
			}
		}
	}
	
	private void rewriteFile(File file_in, File file_out) {
		FileInputStream message_in = null;
		FileOutputStream message_out = null;
		try {
			message_in = new FileInputStream(file_in);
			message_out = new FileOutputStream(file_out);
		} catch (FileNotFoundException e) {
			/*
			 * should not happen
			 */
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(message_in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(message_out));
		String line, sstr, rstr;
		int beg_idx = 1, end_idx = 0;
		try {
			while ((line = in.readLine()) != null) {
				if (beg_idx == 0) {
					/*
					 * last line finished, new line avaiable
					 * therefore last line needs newline character
					 */
					out.newLine();
				}
				/*
				 * matching every <word> of the form "${<word>}"
				 * where word is an non-whitespace character 
				 */
				while (line.matches("^(.*?)\\$\\{\\S*\\}(.*?)$")) {
					/*
					 * line matched, now find out which key is present
					 */
					beg_idx = line.indexOf("${",end_idx);
					end_idx = line.indexOf("}",beg_idx);
					sstr = line.substring(beg_idx+2,end_idx);
					rstr = (String) keywords.get(sstr);
					if (rstr != null) {
						out.write(line.substring(0,beg_idx));
						out.write(rstr);
						line = line.substring(end_idx+1,line.length());
					} else {
						/*
						 * replace nothing, just write out
						 */
						out.write(line.substring(0,end_idx+1));
						line = line.substring(end_idx+1,line.length());
					}
					/*
					 * set indices back to begin
					 */
					beg_idx = 0;
					end_idx = 0;
				}
				/*
				 * write out rest of line 
				 */
				out.write(line);
			}
			/*
			 * close streams
			 */
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		/*
		 * args[0] == Process Name
		 * args[1] == Namespace
		 */
		if (args.length != 5) {
			System.out.println("syntax:\n" +
					"ProjectComposer <projectname> <namespace> <version> <domain> <project parent directory>");
			System.exit(1);
		}
		ProjectComposer composer = new ProjectComposer(args[0],args[1],args[2],args[3],args[4]);
	}
}
