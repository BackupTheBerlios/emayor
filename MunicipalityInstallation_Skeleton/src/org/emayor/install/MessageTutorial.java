package org.emayor.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageTutorial {

	private String language = null;
	
	private final String STEP1 = "This is a tutorial through the notification system. At the end you should provide" +
	" several files to set up your notification system correctly. You may skip this step" +
	" and provide notification settings later on by performing the steps mentioned in the" +
	" installation guide or just keep English as the default language.\n" +
	"Do you want me to continue? (yes/no)";
	
	private final String STEP2 = "The notification takes care of the language used in your municipality and whether the citizen" +
	" using your services is male or female. The language as well as the gender of person can be taken" +
	" from the request. However your should provide a message template containing the message text and" +
	" special variables that can be mapped to the parameters of the request. With that information it is" +
	" possible to create a personalized message that will be send to the citizen. \nIn the next step we" +
	" will look at an example. After that you should be able to create your own message parts." +
	" At the end I will ask you for your configuration and will generate a fake request to test your" +
	" settings. Once the result is sufficient we will continue, otherwise you can start up from the" +
	" beginning.\n\nPress enter to continue.\n";
	
	private final String STEP3 = "First, let´s take a look at the final result. Here is an example of a message for a citizen:\n";
	
	private final String STEP4 = "As you can see there are several fields that seem to be addressed dynamicaly (date, salutation," +
	" municipality name).\n\nPress enter to continue.\n";
	
	private final String STEP5 = "Now let´s see what the original message template looks like:\n";
	
	private final String STEP6 = "There are several variables of the format \"${<name>}\" where <name> is some sort of qualifier." +
	"Seems there is something missing that maps those variables to the actual values. This part is" +
	" called mapping and we will take a look at it in the next step.\n" +
	"\nPress enter when you are ready to continue.\n";
	
	private final String STEP7 = "The mapping for the template we have seen before looks something like this:\n";
	
	private final String STEP8 = "Here you have the previously shown variables bound to a field within our request. The request is in XML" +
	" and the syntax used to access those fields is simply the names of the nodes (that finally lead to the" +
	" value we need) seperated by \"/\"-characters. You can think of that just like browsing through a filesystem.\n" +
	"\nPress <enter> to see the default request with some example values filled in.\n";
	
	private final String STEP9 = "This is how the reuqest looks like:\n";
	
	private final String STEP10 = "Finally we got all pieces together, so let´s summarize:\n\n" +
	"- A message depends on the gender/sex of a citizen as well as the language used.\n" +
	"- A message is created from three parts:\n" +
	"\t - The request itself containing informations about the citizen\n" +
	"\t - A message template containing variables\n" +
	"\t - A message mapping that maps those variables to the request\n" +
	"\n" +
	"Next I will ask you to create your own message templates - one for the male, another" +
	" for the female gender - and a corresponding mapping for the variables in both templates." +
	" After your specified the filenames containing templates and mappings, I will validate them" +
	" against the request and show you the message produced. Then you can choose to come back" +
	" here or leave it as it is.\n" +
	"\nIf you are ready to continue, enter \"yes\" below or \"no\" to restart from the beginning.\n";
	
	private final String STEP11 = "Please enter the absolute path to the file containing the message template for male gender:\n";
	
	private final String STEP12 = "Please enter the absolute path to the file containing the message template for female gender:\n";
	
	private final String STEP13 = "Please enter the absolute path to the file containing the message mapping:\n";
	
	private final String STEP14 = "The produces messages will look like this:\n";
	
	private final String STEP15 = "Does it look ok or do you want to change something and restart the validation? (yes/no)\n ";
	
	private final String STEP16 = "Please enter a two digit language code (e.g. \"en\", \"de\"):\n ";
	
	private final String STEP17 = "\n\nIs everything fine, so we can continue with the installation? (yes/no) \n";
	
	private final String STEP18 = "";
	
	private final String DELIMETER = "\n-------------------------------------------------------------------\n";
	
	public MessageTutorial() {
		
	}
	
	public boolean intro(String notificationSrc) {
	
		String input = "";
		boolean done = false;
		
		/*
		 * start tutorial 
		 */
		while (!done) {
			printOut(STEP1);
			
			while (! (input.toLowerCase().equals("yes") || input.toLowerCase().equals("no"))) {
				input = readIn();
			}
			
			if (input.toLowerCase().equals("no")) return false;
			
			
			printOut(STEP2);
			
			input = readIn();
			
			String mapping = null;
			String template = null;
			String profile = null;
			String message = null;
			
			try {
				mapping = Utils.fileToString("RCRequest_en.map");
				template = Utils.fileToString("RCRequest_en_f.msg");
				profile = Utils.fileToString("SampleResidenceCertificationRequestDocument.xml");
				message = new MessageComposer(profile,mapping,template).getMessage(); 
			} catch (FileNotFoundException e) {}
			
			printOut(STEP3);
			printOut(DELIMETER);
			printOut(message);
			printOut(DELIMETER);
			printOut(STEP4);
			input = readIn();
			
			printOut(STEP5);
			printOut(DELIMETER);
			printOut(template);
			printOut(DELIMETER);
			printOut(STEP6);
			input = readIn();
			
			printOut(STEP7);
			printOut(DELIMETER);
			printOut(mapping);
			printOut(DELIMETER);
			printOut(STEP8);
			input = readIn();
			
			printOut(STEP9);
			printOut(DELIMETER);
			printOut(profile);
			printOut(DELIMETER);
			printOut(STEP10);
			while (! (input.toLowerCase().equals("yes") || input.toLowerCase().equals("no"))) {
				input = readIn();
			}
			
			if (input.toLowerCase().equals("no"))  {
				break;
			}
			
			
			boolean files_done = false;
			while (!files_done) {
				String template_m = null;
				while (template_m == null) {
					printOut(STEP11);
					input = readIn();
					if (input != null) {
						File file = new File(input);
						if (file.exists()) {
							try {
								template_m = Utils.fileToString(input);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
				String template_f = null;
				while (template_f == null) {
					printOut(STEP12);
					input = readIn();
					if (input != null) {
						File file = new File(input);
						if (file.exists()) {
							try {
								template_f = Utils.fileToString(input);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
				mapping = null;
				while (mapping == null) {
					printOut(STEP13);
					input = readIn();
					if (input != null) {
						File file = new File(input);
						if (file.exists()) {
							try {
								mapping = Utils.fileToString(input);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
					
				String message_m = new MessageComposer(profile,mapping,template_m).getMessage();
				String message_f = new MessageComposer(profile,mapping,template_f).getMessage();
				
				printOut(STEP14);
				printOut("Notification for gender: male\n");
				printOut(DELIMETER);
				printOut(message_m);
				printOut(DELIMETER);
				printOut("Notification for gender: female\n");
				printOut(DELIMETER);
				printOut(message_f);
				printOut(DELIMETER);
				printOut(STEP15);
				while (! (input.toLowerCase().equals("yes") || input.toLowerCase().equals("no"))) {
					input = readIn();
				}
				
				if (input.toLowerCase().equals("no"))  {
					break;
				}
				
				/*
				 * language setting
				 */
				
				input = "";
				
				while (input.length() != 2) {
					printOut(STEP16);
					input = readIn();
				}
				this.language = input;
				
				printOut("\n\n------------------- TEMPLATE (MALE) -------------------\n");
				printOut(template_m);
				printOut("\n\n------------------- TEMPLATE (FEMALE) -------------------\n");
				printOut(template_f);
				printOut("\n\n------------------- MAPPING -------------------\n");
				printOut(mapping);
				printOut("\n\n------------------- LANGUAGE -------------------\n");
				printOut("set to: "+language);
				
				printOut(STEP17);
				while (! (input.toLowerCase().equals("yes") || input.toLowerCase().equals("no"))) {
					input = readIn();
				}
				
				if (input.toLowerCase().equals("no"))  {
					break;
				}
				
				Utils.copyStringToFile(template_m,notificationSrc+"/RCRequest_"+language+"_m.msg");
				Utils.copyStringToFile(template_f,notificationSrc+"/RCRequest_"+language+"_f.msg");
				Utils.copyStringToFile(mapping,notificationSrc+"/RCRequest_"+language+".map");
				
				files_done = true;
			}		
		}
		
		return true;
	}
	
	/*
	 * print to stdout
	 */
	public void printOut(String str) {
		System.out.print(str);
	}
	
	public String readIn() {
		String result = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			result = br.readLine();
		} catch (IOException e) {
			result = null;
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: java MessageTutorial <path-to-notification-src>");
			System.exit(0);
		}
		MessageTutorial tut = new MessageTutorial();
		tut.intro(args[0]);
	}
	
}
