/*
 * PrintingUtlity.java
 *
 * Created on 14 Φεβρουάριος 2005, 11:29 πμ
 */

package org.eMayor.ServiceHandling.PrintingUtility.ejb;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.Fidelity;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.JobSheets;
import javax.print.attribute.standard.MediaName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.log4j.Logger;
/**
 * Prints TEXT, HTML, RTF, GIF, JPEG, PCL, PDF, POSTSCRIPT
 * <br/>
 * TEXT, HTML, RTF are printed as rendered by JEditorPane
 *
 * @author imavr
 */

public class PrintingUtility {
	
	private static final Logger log = Logger.getLogger(PrintingUtility.class);
    String chromaticity = "Monochrome";
    String copies = "1";
    String destination = null;
    String fidelity = "false";
    String jobname = "JPS Printing";
    String jobsheets = "standard";
    String media = "iso-a4";
    String filetypestr = null;
    String filename = "file.txt";
    String orientation = "landscape";
    String sides = "ONE_SIDED";
    String mediasize = "A4";
    float topMargin = new Float(25.4).floatValue();
    float bottomMargin = new Float(25.4).floatValue();
    float leftMargin = new Float(25.4).floatValue();
    float rightMargin = new Float(25.4).floatValue();
    int units = MediaSize.MM;
    String unitsName = "MM";
    static boolean useServiceFormat = false;
    boolean printable = false;
    boolean pageable = false;
    boolean renderable_image = false;
    Properties props = new Properties();
    //String propsFile;
    InputStream _propsIS;
    String compromised[] = new String[10];
    PrintRequestAttributeSet aset;
    PrintService[] services;
    PrintService service;
    DocFlavor myFlavor;
    InputStream is;
    String url;
    FileInputStream fin;
    Doc myDoc;
    boolean useDialog = false;
    JEditorPane jep = null;
    boolean paging = false;
    DocAttributeSet das;
    boolean preview = false;
    //PrintPreviewDialog previewDialog;
    
    
    /** Creates a new instance of PrintingUtlity
     *  @param Url The URL to be printed.
     *  <br/>For printing a local html file the URL must be formated as: "file:///path/filename"
     *  @param propsIS An InputStream to the printing properties file
     *  @param filetypeName The filetype that determines how the InputStream will be rendered
     *  <br/> can be: <br/>
     *  "unknown" (uses PAGEABLE, PRINTABLE or RENDERABLE_IMAGE DocFlavor) <br/>
     *  "text" (attemps to print plain text - no control over page layout is applied) <br/>
     *  "text/plain" (attemps to print text rendered in JEditorPane - control over page layout is applied) <br/>
     *  "html" (attemps to print html rendered in JEditorPane - control over page layout is applied) <br/>
     *  "text/html" (attemps to print html rendered in JEditorPane - control over page layout is applied) <br/>
     *  "rtf" (attemps to print rtf rendered in JEditorPane - control over page layout is applied) <br/>
     *  "text/rtf" (attemps to print rtf rendered in JEditorPane - control over page layout is applied) <br/>
     *  "GIF" (uses GIF DocFlavor) <br/>
     *  "JPEG" (uses JPEG DocFlavor) <br/>
     *  "PCL" (uses PCL DocFlavor) <br/>
     *  "PDF" (uses PDF DocFlavor) <br/>
     *  "POSTSCRIPT" (uses POSTSCRIPT DocFlavor)
     */
    public PrintingUtility(String Url, InputStream propsIS, String filetypeName) throws PrintException {
        filetypestr = filetypeName;
        _propsIS = propsIS;
        url = Url;
        services = PrintServiceLookup.lookupPrintServices( DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
        aset = new HashPrintRequestAttributeSet();
        setDocProperies(_propsIS);
    }

    
    /**
     * Prints the content defined in the constructor according to its filetype. <br/>
     * The Print Dialog can be used is the "USEDIALOG" property is set to "true" in the properties file. <br/>
     * For ""text/plain", "html", "text/html", "rtf" and "text/rtf" file types <a href="HTMLPrinter.html">HTMLPrinter</a> is used.
     */
    public void print() throws PrintException {
        setRequest();
        service = null;
        if (useDialog) {
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int defaultPSidx = 0;
            PrintService dps = PrintServiceLookup.lookupDefaultPrintService();
            for (int i=0; i<services.length; i++) {
                if (dps != null) {
                    if (dps.equals(services[i])) defaultPSidx = i;
                }
            }
            service =  ServiceUI.printDialog(null, (screen.width/2)-220, (screen.height/2)-220, services, services[defaultPSidx], null, aset);
            try {
                String serviceName = service.getName();
            } catch (NullPointerException npe) {
                throw new PrintException("no printer selected - printing cancelled", npe);
            }
        } else {
            service = PrintServiceLookup.lookupDefaultPrintService();
        }
        if(!(isSupportedFileType(filetypestr))) {
            throw new PrintException("File Type "+filetypestr+" is not supported.\n" +
                    "Supported filetypes (case insensitive):\n" +
                    "\tunknown\n" +
                    "\ttext\n" +
                    "\ttext/plain (rendered by JEditorPane)\n" +
                    "\tHTML (rendered by JEditorPane)\n" +
                    "\ttext/html (rendered by JEditorPane)\n" +
                    "\tRTF (rendered by JEditorPane)\n" +
                    "\ttext/rtf (rendered by JEditorPane)\n" +
                    "\tGIF\n" +
                    "\tJPEG\n" +
                    "\tPCL\n" +
                    "\tPDF\n" +
                    "\tPOSTSCRIPT");
        }
        setDocFlavor(); //Calling this method sets the appropriate DocFlavor
        //addPrintAListener(service);
        DocPrintJob pj = createDocPrintJob(service);
        //addPrintJListener(pj);
        
        log.info("File being printed: " + url);
        if (filetypestr.equalsIgnoreCase("html") || filetypestr.equalsIgnoreCase("text/html") || filetypestr.equalsIgnoreCase("rtf") || filetypestr.equalsIgnoreCase("text/rtf") || filetypestr.equalsIgnoreCase("text/plain")) {
            printHtmlJob();
        } else {
            Doc d = createFinalDoc(is);
            printJob(pj,d);
        }
        
    }
    
    /**
     * Reads the properties file (declared in the constructor) <br/>
     * @param propsFilename the properties path/file (being called by the constructor, it is passed the filename set in it)
     * @return the properties read from the properties file
     */
    public Properties setDocProperies(InputStream propsIS) throws PrintException {
        //propsFile = propsFilename;
    	
        try {
            //FileInputStream in = new FileInputStream(propsFilename);
        	//FileInputStream in = new FileInputStream(propsIS);
            props = new Properties();
            props.load(propsIS);
            if (props.getProperty("CHROMATICITY") == null) throw new PrintException("property \"CHROMATICITY\" does not exist in properties file");
            if (props.getProperty("CHROMATICITY").equals("")) throw new PrintException("property \"CHROMATICITY\" is empty in properties file");
            if (!(props.getProperty("CHROMATICITY").equalsIgnoreCase("COLOR") || props.getProperty("CHROMATICITY").equalsIgnoreCase("MONOCHROME"))) throw new PrintException("property \"CHROMATICITY\" should be COLOR or MONOCHROME in properties file");
            chromaticity = props.getProperty("CHROMATICITY");
            if (props.getProperty("COPIES") == null) throw new PrintException("property \"COPIES\" does not exist in properties file");
            if (props.getProperty("COPIES").equals("")) throw new PrintException("property \"COPIES\" is empty in properties file");
            copies = props.getProperty("COPIES");
            destination = props.getProperty("DESTINATION");
            if (props.getProperty("FIDELITY") == null) throw new PrintException("property \"FIDELITY\" does not exist in properties file");
            if (props.getProperty("FIDELITY").equals("")) throw new PrintException("property \"FIDELITY\" is empty in properties file");
            if (!(props.getProperty("FIDELITY").equalsIgnoreCase("TRUE") || props.getProperty("FIDELITY").equalsIgnoreCase("FALSE"))) throw new PrintException("property \"FIDELITY\" should be TRUE or FALSE in properties file");
            fidelity = props.getProperty("FIDELITY");
            jobname = props.getProperty("JOBNAME");
            if (props.getProperty("JOBSHEETS") == null) throw new PrintException("property \"JOBSHEETS\" does not exist in properties file");
            if (props.getProperty("JOBSHEETS").equals("")) throw new PrintException("property \"MEDIA\" is empty in properties file");
            if (!(props.getProperty("JOBSHEETS").equalsIgnoreCase("NONE") || props.getProperty("JOBSHEETS").equalsIgnoreCase("STANDARD"))) throw new PrintException("property \"JOBSHEETS\" should be NONE or STANDARD in properties file");
            jobsheets = props.getProperty("JOBSHEETS");
            if (props.getProperty("MEDIA") == null) throw new PrintException("property \"MEDIA\" does not exist in properties file");
            if (props.getProperty("MEDIA").equals("")) throw new PrintException("property \"MEDIA\" is empty in properties file");
            if (!(props.getProperty("MEDIA").equalsIgnoreCase("ISO-A4") || props.getProperty("MEDIA").equalsIgnoreCase("TRANSPARENT") || props.getProperty("MEDIA").equalsIgnoreCase("LETTER"))) throw new PrintException("property \"MEDIA\" should be ISO-A4 or TRANSPARENT or LETTER in properties file");
            media = props.getProperty("MEDIA");
            if (props.getProperty("MEDIASIZE") == null) throw new PrintException("property \"MEDIASIZE\" does not exist in properties file");
            if (props.getProperty("MEDIASIZE").equals("")) throw new PrintException("property \"MEDIASIZE\" is empty in properties file");
            if (!(props.getProperty("MEDIASIZE").equalsIgnoreCase("A3") || props.getProperty("MEDIASIZE").equalsIgnoreCase("A4"))) throw new PrintException("property \"MEDIASIZE\" should be A3 or A4 in properties file");
            mediasize = props.getProperty("MEDIASIZE");
            if (filetypestr == null) {
                filetypestr = props.getProperty("FILETYPE");
                if (filetypestr == null) throw new PrintException("property \"FILETYPE\" does not exist in properties file");
            }
            if (props.getProperty("ORIENTATION") == null) throw new PrintException("property \"ORIENTATION\" does not exist in properties file");
            if (props.getProperty("ORIENTATION").equals("")) throw new PrintException("property \"ORIENTATION\" is empty in properties file");
            if (!(props.getProperty("ORIENTATION").equalsIgnoreCase("Portrait") || props.getProperty("ORIENTATION").equalsIgnoreCase("Landscape") || props.getProperty("ORIENTATION").equalsIgnoreCase("Reverse_Portrait") || props.getProperty("ORIENTATION").equalsIgnoreCase("Reverse_Landscape"))) throw new PrintException("property \"ORIENTATION\" should be PORTRAIT or LANDSCAPE or REVERSE_PORTRAIT or REVERSE_LANDSCAPE in properties file");
            orientation = props.getProperty("ORIENTATION");
            if (props.getProperty("SIDES") == null) throw new PrintException("property \"SIDES\" does not exist in properties file");
            if (props.getProperty("SIDES").equals("")) throw new PrintException("property \"SIDES\" is empty in properties file");
            if (!(props.getProperty("SIDES").equalsIgnoreCase("ONE_SIDED") || props.getProperty("SIDES").equalsIgnoreCase("DUPLEX") || props.getProperty("SIDES").equalsIgnoreCase("TUMBLE"))) throw new PrintException("property \"SIDES\" should be ONE_SIDED or DUPLEX or TUMBLE in properties file");
            sides = props.getProperty("SIDES");
            try {
                topMargin = Float.parseFloat(props.getProperty("TOPMARGIN"));
            } catch (NullPointerException npe) {
                throw new PrintException("property \"TOPMARGIN\" does not exist in properties file", npe);
            } catch (NumberFormatException nfe) {
                throw new PrintException("property \"TOPMARGIN\" is not a Float parsable value", nfe);
            }
            try {
                bottomMargin = Float.parseFloat(props.getProperty("BOTTOMMARGIN"));
            } catch (NullPointerException npe) {
                throw new PrintException("property \"BOTTOMMARGIN\" does not exist in properties file", npe);
            } catch (NumberFormatException nfe) {
                throw new PrintException("property \"BOTTOMMARGIN\" is not a Float parsable value", nfe);
            }
            try {
                leftMargin = Float.parseFloat(props.getProperty("LEFTMARGIN"));
            } catch (NullPointerException npe) {
                throw new PrintException("property \"LEFTMARGIN\" does not exist in properties file", npe);
            } catch (NumberFormatException nfe) {
                throw new PrintException("property \"LEFTMARGIN\" is not a Float parsable value", nfe);
            }
            try {
                rightMargin = Float.parseFloat(props.getProperty("RIGHTMARGIN"));
            } catch (NullPointerException npe) {
                throw new PrintException("property \"RIGHTMARGIN\" does not exist in properties file", npe);
            } catch (NumberFormatException nfe) {
                throw new PrintException("property \"RIGHTMARGIN\" is not a Float parsable value", nfe);
            }
            if (props.getProperty("UNITS") == null) throw new PrintException("property \"UNITS\" does not exist in properties file");
            if (props.getProperty("UNITS").equals("")) throw new PrintException("property \"UNITS\" is empty in properties file");
            if (!(props.getProperty("UNITS").equalsIgnoreCase("MM") || props.getProperty("UNITS").equalsIgnoreCase("INCH"))) throw new PrintException("property \"UNITS\" should be MM or INCH in properties file");
            unitsName = props.getProperty("UNITS");
            if (props.getProperty("PAGING") == null) throw new PrintException("property \"PAGING\" does not exist in properties file");
            if (props.getProperty("PAGING").equals("")) throw new PrintException("property \"PAGING\" is empty in properties file");
            if (!(props.getProperty("PAGING").equalsIgnoreCase("TRUE") || props.getProperty("PAGING").equalsIgnoreCase("FALSE"))) throw new PrintException("property \"PAGING\" should be TRUE or FALSE in properties file");
            paging  = Boolean.parseBoolean(props.getProperty("PAGING"));
            if (props.getProperty("USEDIALOG") == null) throw new PrintException("property \"USEDIALOG\" does not exist in properties file");
            if (props.getProperty("USEDIALOG").equals("")) throw new PrintException("property \"USEDIALOG\" is empty in properties file");
            if (!(props.getProperty("USEDIALOG").equalsIgnoreCase("TRUE") || props.getProperty("USEDIALOG").equalsIgnoreCase("FALSE"))) throw new PrintException("property \"USEDIALOG\" should be TRUE or FALSE in properties file");
            useDialog  = Boolean.parseBoolean(props.getProperty("USEDIALOG"));
            if (props.getProperty("PREVIEW") == null) throw new PrintException("property \"PREVIEW\" does not exist in properties file");
            if (props.getProperty("PREVIEW").equals("")) throw new PrintException("property \"PREVIEW\" is empty in properties file");
            if (!(props.getProperty("PREVIEW").equalsIgnoreCase("TRUE") || props.getProperty("PREVIEW").equalsIgnoreCase("FALSE"))) throw new PrintException("property \"PREVIEW\" should be TRUE or FALSE in properties file");
            preview  = Boolean.parseBoolean(props.getProperty("PREVIEW"));
            
        } catch(FileNotFoundException fnne) {
            throw new PrintException("Properties file not found", fnne);
        } catch(IOException ioe) {
            throw new PrintException("Properties file could not be loaded", ioe);
        }
        return props;
    }
    
    /**
     * Check for the supported file types <br/>
     * File type is important for proper printing. <br/>
     * supported filetypes are: <br/>
     *  "unknown" (uses PAGEABLE, PRINTABLE or RENDERABLE_IMAGE DocFlavor) <br/>
     *  "text" (attemps to print plain text - no control over page layout is applied) <br/>
     *  "text/plain" (attemps to print text rendered in JEditorPane - control over page layout is applied) <br/>
     *  "html" (attemps to print html rendered in JEditorPane - control over page layout is applied) <br/>
     *  "text/html" (attemps to print html rendered in JEditorPane - control over page layout is applied) <br/>
     *  "rtf" (attemps to print rtf rendered in JEditorPane - control over page layout is applied) <br/>
     *  "text/rtf" (attemps to print rtf rendered in JEditorPane - control over page layout is applied) <br/>
     *  "GIF" (uses GIF DocFlavor) <br/>
     *  "JPEG" (uses JPEG DocFlavor) <br/>
     *  "PCL" (uses PCL DocFlavor) <br/>
     *  "PDF" (uses PDF DocFlavor) <br/>
     *  "POSTSCRIPT" (uses POSTSCRIPT DocFlavor)
     * @param lfiletypestr the declared filetype
     * @return true or false
     */
    public boolean isSupportedFileType(String lfiletypestr) {
        filetypestr = lfiletypestr;
        if (filetypestr != null ) {
            if(filetypestr.equalsIgnoreCase("unknown")) {
                useServiceFormat = true;
                return true;
            }
            if(filetypestr.equalsIgnoreCase( "text") || filetypestr.equalsIgnoreCase( "text/plain") || filetypestr.equalsIgnoreCase( "GIF") || filetypestr.equalsIgnoreCase( "JPEG") ||
                    filetypestr.equalsIgnoreCase( "PCL") || filetypestr.equalsIgnoreCase( "PDF") || filetypestr.equalsIgnoreCase( "POSTSCRIPT") || filetypestr.equalsIgnoreCase( "HTML") || filetypestr.equalsIgnoreCase( "text/html") || filetypestr.equalsIgnoreCase( "RTF") || filetypestr.equalsIgnoreCase( "text/rtf") ) {
                useServiceFormat = false;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Discovers the default Print Service
     * @return the default Print Service
     */
    public PrintService getDefaultPrintService() {
        PrintService defservices = PrintServiceLookup.lookupDefaultPrintService();
        return defservices;
    }
    
    
    /**
     * Discovers the printers that can print the requested DocFlavor <br/>
     * with the requested PrintRequestAttribyteSet (as defined in the properties file of by the PrintDialog)
     * @return The available Print Services (that are compatible with the requested DocFlavor and PrintRequestAttribyteSet)
     */
    public PrintService[] getAvailablePrintServices() {
        services = PrintServiceLookup.lookupPrintServices(myFlavor, aset);
        return services;
    }
    
    
    /**
     * Discovers the printers that can print with the requested PrintRequestAttribyteSet, compromising attributes if necessary
     * @return The available Print Services (that are compatible with the requested PrintRequestAttribyteSet - compromising attributes if necessary)
     */
    public PrintService[] getPrintServices() throws PrintException {
        
        //Before sending the request for lookup, list the Attribute set here
        Attribute[] nattr = aset.toArray();
        int loop = nattr.length;
             
        for(int i = 0; i <= loop; i++ ) {
            nattr = aset.toArray();

            services = PrintServiceLookup.lookupPrintServices(myFlavor, aset);
            if(services.length > 0 ) {
                break;
            }else {
                compromiseAttribute(i); //remove one attribute
            }
        }
        
        for (int ck = 0; ck < compromised.length; ck++ ) {
            if (compromised[ck] != null ) {
            	//	System.out.println(compromised[ck]);
            }
        }
        // Get the supported PrintServiceatribute categories that we can specify
        for(int j = 0; j < services.length; j++ ) {
            Class [] psattribute = services[j].getSupportedAttributeCategories();
 
            for(int i = 0; i < psattribute.length; i++ ) {
 
                Object value = null;
                try {
                    value =  services[j].getSupportedAttributeValues(psattribute[i],null,null);
                } catch(Exception e) {
                    throw new PrintException(e);
                }
              
                if(value == null) {
                    break;
                }
                if (value.getClass().isArray()) {
                    Object[] v = (Object[])value;
                    /*
                    for (int k=0; k<v.length; k++) {
                        if (k > 0) {
                            System.out.print(", ");
                            System.out.print(v[k]);
                            
                        }
                    }*/
                } else {
                    //	System.out.print(value);
                    //	System.out.println();
                }
            }   
        }
        
        return services;
    }
    
    /**
     * Creates a Print Job from the specified PrintService
     * @param ps the selected Print Service (usually returned from <a href="#getPrintServices()">getPrintServices()</a>)
     * @return DocPrintJob
     */
    public DocPrintJob createDocPrintJob(PrintService ps) {
        DocPrintJob job = ps.createPrintJob();
        return job;
    }
    
    /**
     * Prints the Print Job to the specified printService according to the specified PrintRequestAttributeSet
     * @param dpj The created DocPrintJob
     * @param d The Doc to be printed
     */
    public void printJob(DocPrintJob dpj, Doc d) throws PrintException {
        DocPrintJob ljob = dpj;
        Doc ld = d;
 
        ljob.print(ld,aset);
    }
    
    
    /**
     * Creates the PrintRequestAttributeSet for the Print Service with the attributes specified in the properties file <br/>
     * and filters its attibutes to create the document-specific DocAttributeSet <br/>
     * by calling the <a href="#PrintRequestAttributeSet2DocAttributeSet(javax.print.attribute.PrintRequestAttributeSet)">PrintRequestAttributeSet2DocAttributeSet</a> method.
     */
    public void setRequest() throws PrintException {
        aset = new HashPrintRequestAttributeSet();
        
        /* Set Chromaticity */
        if (chromaticity != null ) {
            if(chromaticity.equalsIgnoreCase( "monochrome")) {
                aset.add(Chromaticity.MONOCHROME);
            }
            if(chromaticity.equalsIgnoreCase( "color")) {
                aset.add(Chromaticity.COLOR);
            }
        } else {
            aset.add(Chromaticity.MONOCHROME);
        }
        
        /* Set number of Copies */
        if(copies == null ) {
            aset.add(new Copies(1));
        }
        if (copies != null ) {
            try {
                aset.add(new Copies(Integer.parseInt(copies)));
            } catch (NumberFormatException nfe) {
                throw new PrintException("property \"COPIES\" is not an Integer parsable value", nfe);
            }
        }
        
        /* Set Fidelity */
        if(fidelity !=  null ) {
            
            if (fidelity.equalsIgnoreCase("false")) {
                aset.add(Fidelity.FIDELITY_FALSE);
            }
            if(fidelity.equalsIgnoreCase("true")) {
                aset.add(Fidelity.FIDELITY_TRUE);
            }
        }
        
        if(jobsheets != null ) {
            if (jobsheets.equalsIgnoreCase("NONE")) {
                aset.add(JobSheets.NONE);
            }
            if(jobsheets.equalsIgnoreCase("STANDARD")) {
                aset.add(JobSheets.STANDARD);
            }
        }
        
        /* Set number of sides of printing */
        if (sides != null ) {
            if(sides.equalsIgnoreCase("duplex")) {
                aset.add(Sides.DUPLEX);
            }
            if(sides.equalsIgnoreCase("one_sided")) {
                aset.add(Sides.ONE_SIDED);
            }
            if(sides.equalsIgnoreCase("tumble")) {
                aset.add(Sides.TUMBLE);
            }
        } else {
            aset.add(Sides.ONE_SIDED);
        }
        
        /* Set Media size, A4, letter or Transparency */
        if (media != null) {
            if (media.equalsIgnoreCase( "iso-A4" )) {
                aset.add(MediaName.ISO_A4_WHITE);
            }
            if(media.equalsIgnoreCase( "Transparent")) {
                aset.add(MediaName.ISO_A4_TRANSPARENT);
            }
            if(media.equalsIgnoreCase( "Letter")) {
                aset.add(MediaName.NA_LETTER_WHITE);
            }
        }
        
        /* Set Media size, A4, letter or Transparency */
        if (mediasize != null) {
            if (mediasize.equalsIgnoreCase( "A4" )) {
                aset.add(MediaSizeName.ISO_A4);
            }
            if(mediasize.equalsIgnoreCase( "A3")) {
                aset.add(MediaSizeName.ISO_A3);
            }
        }
        
        /* Set Orientation - portrait or landscape or reverse_landscape */
        if(orientation != null ) {
            if(orientation.equalsIgnoreCase( "Portrait")) {
                aset.add(OrientationRequested.PORTRAIT);
            }
            if (orientation.equalsIgnoreCase( "landscape")) {
                aset.add(OrientationRequested.LANDSCAPE);
            }
            if(orientation.equalsIgnoreCase( "reverse_portrait")) {
                aset.add(OrientationRequested.REVERSE_LANDSCAPE);
            }
            if(orientation.equalsIgnoreCase( "reverse_landscape")) {
                aset.add(OrientationRequested.REVERSE_LANDSCAPE);
            }
        }
        
        if(unitsName != null ) {
            if (unitsName.equalsIgnoreCase("MM")) {
                units = MediaSize.MM;
            }
            if(unitsName.equalsIgnoreCase("INCH")) {
                units = MediaSize.INCH;
            }
        }
        
        aset.add(deriveMediaPrintableArea());
        
        if(jobname == null) {
            aset.add(new JobName("JPS Printing",null));
        }
        if (jobname != null ) {
            aset.add(new JobName(jobname,null));
        }
        
        if (destination != null && !destination.equals("")) {
            try {
                aset.add(new Destination(new URI(destination)));
            } catch (URISyntaxException URIse) {
                throw new PrintException("invalid \"print-to-file\" destination", URIse);
            }
        }
        
        das = PrintRequestAttributeSet2DocAttributeSet(aset);
    }
    
    /**
     * Filters the attibutes of a PrintRequestAttributeSet to create the document-specific DocAttributeSet
     * @param pras The PrintRequestAttributeSet to be converted to DocAttributeSet
     * @return The DocAttributeSet converted from the specified PrintRequestAttributeSet
     */
    public DocAttributeSet PrintRequestAttributeSet2DocAttributeSet(PrintRequestAttributeSet pras) {
        DocAttributeSet daset = new HashDocAttributeSet();
        Attribute[] atts = pras.toArray();
        for (int j=0; j<atts.length; j++) {
            String attName = atts[j].getName();
            String attCategory = atts[j].getCategory().toString();
            if (atts[j] instanceof DocAttribute) {
                daset.add(atts[j]);
            }
        }
        return daset;
    }
    
    /**
     * Calculates the media printable area according to the size, orientation and margins set in the properties file
     * @return The MediaPrintableArea
     */
    public MediaPrintableArea deriveMediaPrintableArea() {
        float[] paperSizeDims = new float[2];
        if (mediasize.equalsIgnoreCase("A4")) {
            paperSizeDims = MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4).getSize(units);
        } else if (mediasize.equalsIgnoreCase("A3")) {
            paperSizeDims = MediaSize.getMediaSizeForName(MediaSizeName.ISO_A3).getSize(units);
        } else {
            paperSizeDims = MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4).getSize(units);
        }
        for (int i=0; i<paperSizeDims.length; i++) {
        }
        float x = 0;
        float y = 0;
        if (orientation.equalsIgnoreCase("landscape") || orientation.equalsIgnoreCase("reverse_landscape")) {
            x = Math.max(paperSizeDims[0], paperSizeDims[1]);
            y = Math.min(paperSizeDims[0], paperSizeDims[1]);
        } else if (orientation.equalsIgnoreCase("portrait") || orientation.equalsIgnoreCase("reverse_portrait")) {
            y = Math.max(paperSizeDims[0], paperSizeDims[1]);
            x = Math.min(paperSizeDims[0], paperSizeDims[1]);
        }
        float w = x-(leftMargin + topMargin);
        float h = y-(topMargin + bottomMargin);
        MediaPrintableArea mpa = new MediaPrintableArea(leftMargin, topMargin, w, h, units);
        return mpa;
    }
    
    /**
     * Sets Document Flavor Type (DocFlavor) which will be used by
     * PrintServiceLookup and also to create a Doc for Print Service.
     */
    public void setDocFlavor() {
        if(useServiceFormat) {
            if(pageable) {
                myFlavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
            }
            if(printable) {
                myFlavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            }
            if(renderable_image) {
                myFlavor = DocFlavor.SERVICE_FORMATTED.RENDERABLE_IMAGE;
            }
        }
        if(filetypestr.equalsIgnoreCase( "text") || filetypestr.equalsIgnoreCase( "txt") || filetypestr.equalsIgnoreCase( "text/plain")) {
            myFlavor = new DocFlavor("application/octet-stream", "java.io.InputStream");
        }
        if(filetypestr.equalsIgnoreCase( "html") || filetypestr.equalsIgnoreCase( "text/html")) {
            //            System.out.println("setDocFlaovr is setting HTML....");
            myFlavor = new DocFlavor("application/octet-stream", "java.io.InputStream");
        }
        if(filetypestr.equalsIgnoreCase( "rtf") || filetypestr.equalsIgnoreCase( "text/rtf")) {
            //            System.out.println("setDocFlaovr is setting RTF....");
            myFlavor = new DocFlavor("application/octet-stream", "java.io.InputStream");
        }
        if(filetypestr.equalsIgnoreCase(  "GIF")) {
            myFlavor = DocFlavor.INPUT_STREAM.GIF;
        }
        if(filetypestr.equalsIgnoreCase( "JPEG")) {
            myFlavor = DocFlavor.INPUT_STREAM.JPEG;
        }
        if(filetypestr.equalsIgnoreCase( "PCL")) {
            myFlavor = DocFlavor.INPUT_STREAM.PCL;
        }
        if(filetypestr.equalsIgnoreCase( "PDF")) {
            //            System.out.println("setDocFlaovr is setting PDF....");
            myFlavor = DocFlavor.INPUT_STREAM.PDF;
        }
        if(filetypestr.equalsIgnoreCase( "POSTSCRIPT")) {
            //            System.out.println("setDocFlaovr is setting POSTSCRIPT....");
            myFlavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
        }
    }
    
    /**
     * Uses <a href="HTMLPrinter.html">HTMLPrinter</a> to print "text/plain", "html" ("text/html") and rtf ("text/rtf") content
     */
    public void printHtmlJob() throws PrintException {
        HTMLPrinter htmlPrinter = null;
        jep = new JEditorPane();
        if (filetypestr.equalsIgnoreCase("text/html")) {
            jep.setContentType("text/html");
            jep.setEditable(false);
            jep.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        JEditorPane pane = (JEditorPane) e.getSource();
                        if (e instanceof HTMLFrameHyperlinkEvent) {
                            HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                            HTMLDocument doc = (HTMLDocument)pane.getDocument();
                            doc.processHTMLFrameHyperlinkEvent(evt);
                        } else {
                            try {
                                pane.setPage(e.getURL());
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                }
            });
            jep.setDocument(new HTMLDocument());
            MyHTMLEditorKit kit1 = new MyHTMLEditorKit(url, jep);
            jep.setEditorKit(kit1);
            if (url == null) {
                try {
                    jep.read(is, kit1);
                } catch (IOException ioe) {
                    throw new PrintException("Error in reading the html InputStream by the JEditorPane", ioe);
                }
            } else {
                try {
                    jep.setPage(url);
                } catch (IOException ioe) {
                    throw new PrintException("Error in setting the URL to the JEditorPane", ioe);
                }
            }
            htmlPrinter = new HTMLPrinter(jep, aset, service, services, units, paging);
            htmlPrinter.setPreview(preview);
            htmlPrinter.setPrintRequestAttributes(aset);
            htmlPrinter.addPrintJobListener(new PrintServiceJobListener());
            htmlPrinter.addPrintServiceAttributeListener(new PrintAttributeListener());

        }
        if (filetypestr.equalsIgnoreCase("text/rtf")) {
            jep.setContentType("text/rtf");
            jep.setEditable(false);

            try {
                jep.read(is, new RTFEditorKit());
            } catch (IOException ioe) {
                throw new PrintException("Error in reading the rtf InputStream by the JEditorPane", ioe);
            }
            htmlPrinter = new HTMLPrinter(jep, aset, service, services, units, paging);
            htmlPrinter.setPreview(preview);
            htmlPrinter.setPrintRequestAttributes(aset);
            htmlPrinter.addPrintJobListener(new PrintServiceJobListener());
            htmlPrinter.addPrintServiceAttributeListener(new PrintAttributeListener());

        }
        if (filetypestr.equalsIgnoreCase("text/plain")) {
            jep.setContentType("text/plain");
            jep.setEditable(false);
            try {
                jep.read(is, new DefaultEditorKit());
            } catch (IOException ioe) {
                throw new PrintException("Error in reading the text InputStream by the JEditorPane", ioe);
            }
            htmlPrinter = new HTMLPrinter(jep, aset, service, services, units, paging);
            htmlPrinter.setPreview(preview);
            htmlPrinter.setPrintRequestAttributes(aset);
            htmlPrinter.addPrintJobListener(new PrintServiceJobListener());
            htmlPrinter.addPrintServiceAttributeListener(new PrintAttributeListener());

        }
        htmlPrinter.printEditorPane(false);
    }
    
    /** Gets the PringPreviewDialog object
     * @return this PrintPreviewDialog object
     */    
    /*public PrintPreviewDialog getPrintPreviewDialog() {
        return previewDialog;
    }*/
    
    /**
     * Creates a Doc for printing. SimpleDoc is used.
     * @param inStr the IputStream to be printed
     * @return The Doc to be printed
     */
    public Doc createFinalDoc(InputStream inStr) {
        myDoc = new SimpleDoc(inStr, myFlavor, das);
        return myDoc;
    }
    
    /**
     * Add <a href="PrintAttributeListener.html">PrintAttributeListener</a> to the specified Print Service
     * This monitors the changes in Print Service Attributes
     * @param service The Print Service to be monitored
     */
    public void addPrintAListener(PrintService service) {
        PrintAttributeListener pAListener = new PrintAttributeListener();
        service.addPrintServiceAttributeListener(pAListener);
    }
    
    /**
     * Add <a href="PrintAttributeListener.html">PrintAttributeListener</a> to all available Print Services
     * This monitors the changes in Print Service Attributes
     * @param services An array of  Print Services
     */
    public void addPrintAListener(PrintService[] services) {
        PrintAttributeListener pAListener = new PrintAttributeListener();
        for(int i = 0; i < services.length; i++) {
            services[i].addPrintServiceAttributeListener(pAListener);
        }
    }
    
    /**
     * Add <a href="PrintServiceJobListener.html">PrintServiceJobListener</a> to monitor the Print Job status
     * This monitors the changes in Print Job status
     * @param job The DocPrintJob to be monitored
     */
    public void addPrintJListener(DocPrintJob job) {
        PrintServiceJobListener pjListener = new PrintServiceJobListener();
        job.addPrintJobListener(pjListener);
    }
    
    
    /**
     * Removes the requested attributes from the set to get atleast one Print Service
     * @param n The attribute to be compromised
     */
    public void compromiseAttribute(int n) {
        int i = 0;
        Attribute[] newattr = aset.toArray();
        if (newattr != null && newattr.length > 0 ) {

            Class compromiseCategory = newattr[i].getCategory();

            compromised[i] = compromiseCategory.getName();
            aset.remove(newattr[i]);
        }
    }
    
    /**
     * Returns the names of compromised attributes
     * @return	A String array containing the categories (class names) of the compromised attributes
     */
    public String[] getCompromised() {
        if (compromised != null ) 	{
            return compromised;
        } else return null;
    }
    
    /**
     * Returns the discovered Print Services
     * @return The discovered Print Services
     */
    public PrintService[] getServices() {
        return services;
    }
}
