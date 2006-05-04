package org.emayor.install;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MessageComposer {
	
	private DocumentParser profile_doc;
	private MessageKeys keywords;
	private ByteArrayInputStream message_in;
	private ByteArrayOutputStream message_out; 
	
	
	private String subject;
	private String message;
	private String mapping;
	private String profile;
	private String qualifier;
	
	private final String SUBJECT = "/data/subject";
	private final String MESSAGE = "/data/message";
	private final String MAPPING = "/data/mapping";
	private final String PROFILE = "/data/profile";
	private final String QUALIFIER = "/data/qualifier";
  
	private String PROFILE_ELEM = "";
	
  /*
   * request split up to important parts, given as strings
   * 
   * if profile does not contain XML-Syntax elements, we cannot parse it correctly,
   * so we use altProfile as alternative
   */
  public MessageComposer(String profile, String mapping, String message) { 
  
  
    this.profile = profile;
    
    //System.out.println(this.profile);
  
    this.mapping = mapping;
    this.message = message;
		
		ByteArrayInputStream template_in = null, mappings_in = null;
    
	if (mapping != null && message != null) { 
      template_in = new ByteArrayInputStream(message.getBytes());
      mappings_in = new ByteArrayInputStream(mapping.getBytes());				
    }
    
		/*
		 * treat "file" as an xml document and prepare for
		 * extraction of node values
		 */
		profile_doc = new DocumentParser(this.profile);
		System.out.println(profile_doc.getNodeListValue(""));
		
		keywords = new MessageKeys(mappings_in);
		
		try {
			//System.out.println(">> getting input ...");
			message_in = template_in;
			message_out = new ByteArrayOutputStream();
			//System.out.println(">> composing ...");
			compose();
			this.message = message_out.toString();
			message_in.close();
			message_out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	  
  /*
   * if the whole request is given as string
   */
	public MessageComposer(String input) { 
		
		DocumentParser parse = new DocumentParser(input);
		ByteArrayInputStream template_in = null, mappings_in = null;
    
    
		this.subject = parse.getNodeValue(SUBJECT);
		this.message = parse.getNodeValue(MESSAGE);
		this.mapping = parse.getNodeValue(MAPPING);
		this.profile = parse.getNodeListValue(PROFILE);
		this.qualifier = parse.getNodeValue(QUALIFIER);
		
    /*
    System.out.println("input:     " +input);
    System.out.println("subject:   " +subject);
    System.out.println("message:   " +message);
    System.out.println("mapping:   " +mapping);
    System.out.println("profile:   " +profile);
    System.out.println("qualifier: " +qualifier);
    */
    
		if (mapping != null && message != null) { 
      template_in = new ByteArrayInputStream(message.getBytes());
      mappings_in = new ByteArrayInputStream(mapping.getBytes());				
    }
    
		/*
		 * treat "file" as an xml document and prepare for
		 * extraction of node values
		 */
		profile_doc = new DocumentParser(profile);
		System.out.println("doc:"+profile_doc.getNodeListValue(""));
		
		keywords = new MessageKeys(mappings_in);
		
		try {
			//System.out.println(">> getting input ...");
			message_in = template_in;
			message_out = new ByteArrayOutputStream();
			//System.out.println(">> composing ...");
			compose();
			message = message_out.toString();
			message_in.close();
			message_out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param string String to trim of whitespaces
	 * @return	Returns the String without leading or trailing whitespaces
	 */
	private String trim(String string) {
		/* remove leading whitespace */
	    string = string.replaceAll("^\\s+", "");
	    /* remove trailing whitespace */
	    string = string.replaceAll("\\s+$", "");
	    return string;
	}
	
	/**
	 * Compose the message out of the XML Profile
	 */
	private void compose() {
		//System.out.println(">> getting compose in ...");
		BufferedReader in = new BufferedReader(new InputStreamReader(message_in));
		//DataInputStream  in = new DataInputStream(message_in);
		//System.out.println(">> getting compose out ...");
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(message_out));
		//DataOutputStream out = new DataOutputStream(message_out);
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
				//System.out.println(line);
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
					//System.out.println(">> found matching pattern: " + sstr + " ("+beg_idx+","+end_idx+")");
					/*
					 * search for matching xpath expression
					 */
					rstr = keywords.getKey(sstr);
          System.out.print("("+sstr+","+rstr+",");
          rstr = profile_doc.getNodeValue(rstr);
          System.out.println(rstr+")");
					if (rstr != null) {
						//System.out.println(">> key is: "+rstr);
						/*
						 * lookup value
						 */
						rstr = trim(rstr);
						//System.out.println(">> value is: "+rstr);
						/*
						 * replace all strings matching the pattern
						 */
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
	
	public String getMessage() {
		return message;
	}
	public String getQualifier() {
		return qualifier;
	}
	public String getSubject() {
		return subject;
	}
}
