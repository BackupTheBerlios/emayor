/*
 * HTMLPrinter.java
 *
 * Created on 16 Φεβρουάριος 2005, 2:14 μμ
 */

package org.eMayor.ServiceHandling.PrintingUtility.ejb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.ColorSupported;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.event.PrintJobListener;
import javax.print.event.PrintServiceAttributeListener;
import javax.swing.JEditorPane;
import javax.swing.text.View;

/**
 * Prints text, HTML and RTF documents by rendered in a JEditorPane by the appropriate EditorKit.
 *
 * @author imavr
 *
 */

public class HTMLPrinter {
    public static int DEFAULT_DPI = 72;
    public static float DEFAULT_PAGE_WIDTH_INCH = 8.5f;
    public static float DEFAULT_PAGE_HEIGHT_INCH = 11f;
    int x = 100;
    int y = 80;
    GraphicsConfiguration gc;
    PrintService[] services;
    PrintService defaultService;
    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
    PrintRequestAttributeSet attributes;
    Vector pjlListeners = new Vector();
    Vector pjalListeners = new Vector();
    Vector psalListeners = new Vector();
    JEditorPane jep;
    int units;
    boolean paging;
    boolean preview;
    String chromaticity = "Monochrome";
    String copies = "1";
    String destination = "/file.out";
    String fidelity = "false";
    String jobname = "Java printing";
    String jobsheets = "standard";
    String media = "iso-a4";
    String filetypestr = null;
    String filename = "file.txt";
    String orientation = "landscape";
    String sides = "duplex";
    String mediasize = "A4";
    PrintPreviewDialog previewDialog;
    
    
    /** Creates a new instance of HTMLPrinter
     *  @param Jep The JEditorPane in which the content has been rendered
     *  @param pras The PrintRequestAttributeSet that defines the printing parameters
     *  @param defaultPrintService The system's default Print Service
     *  @param availableServices The system's available Print Services
     *  @param unit javax.print.attribute.standard.MediaSize.MM or javax.print.attribute.standard.MediaSize.INCH used in internal calculations
     *  @param pages true or false for paging function. If no paging is selected the document will be scaled to fit in one page.
     */
    public HTMLPrinter(JEditorPane Jep, PrintRequestAttributeSet pras, PrintService defaultPrintService, PrintService[] availableServices, int unit, boolean pages) {
        gc = null;
        jep = Jep;
        units = unit;
        paging = pages;
        if (pras == null)
            attributes = new HashPrintRequestAttributeSet();
        else {
            attributes = pras;
            if (attributes.get(javax.print.attribute.standard.Chromaticity.class) != null)
                chromaticity = attributes.get(javax.print.attribute.standard.Chromaticity.class).toString();
            if (attributes.get(javax.print.attribute.standard.Copies.class) != null)
                copies = attributes.get(javax.print.attribute.standard.Copies.class).toString();
            if (attributes.get(javax.print.attribute.standard.Destination.class) != null)
                destination = attributes.get(javax.print.attribute.standard.Destination.class).toString();
            if (attributes.get(javax.print.attribute.standard.Fidelity.class) != null)
                fidelity = attributes.get(javax.print.attribute.standard.Fidelity.class).toString();
            if (attributes.get(javax.print.attribute.standard.JobName.class) != null)
                jobname = attributes.get(javax.print.attribute.standard.JobName.class).toString();
            if (attributes.get(javax.print.attribute.standard.JobSheets.class) != null)
                jobsheets = attributes.get(javax.print.attribute.standard.JobSheets.class).toString();
            if (attributes.get(javax.print.attribute.standard.Media.class) != null)
                media = attributes.get(javax.print.attribute.standard.Media.class).toString();
            if (attributes.get(javax.print.attribute.standard.MediaSizeName.class) != null)
                mediasize = attributes.get(javax.print.attribute.standard.MediaSizeName.class).toString();
            if (attributes.get(javax.print.attribute.standard.OrientationRequested.class) != null)
                orientation = attributes.get(javax.print.attribute.standard.OrientationRequested.class).toString();
            if (attributes.get(javax.print.attribute.standard.Sides.class) != null)
                sides = attributes.get(javax.print.attribute.standard.Sides.class).toString();

        }
        flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        defaultService = defaultPrintService;
        services = availableServices;
        // if there is a default service, but no other services
        if (defaultService != null && (services == null || services.length == 0)) {
            services = new PrintService[1];
            services[0] = defaultService;
        }
        jep.setEditable(false);
    }
    
    /** Sets whether a print preview window will appear or not
     *  @param prv true or false for print previewing
     */
    public void setPreview(boolean prv) {
        preview = prv;
    }
    
    /** Sets the PrintRequestAttributeSet that defines the printing parameters
     *  @param attribs The PrintRequestAttributeSet that defines the printing parameters
     */
    public void setPrintRequestAttributes(PrintRequestAttributeSet attribs) {
        attributes = attribs;
    }
    
    /** Sets the Print Dialog location (when used)
     *  @param x The print dialog's top left point position along the screen's horizontal axis
     *  @param y The print dialog's top left point position along the screen's vertical axis
     */
    public void setPrintDialogLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /** Adds a <a href="PrintServiceJobListener.html">PrintServiceJobListener</a> to the <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s vector that will monitor a PrintJob
     *  @param pjl a <a href="PrintServiceJobListener.html">PrintServiceJobListener</a> to add to the vector of <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s to be used for monitoring a PrintJob
     */
    public void addPrintJobListener(PrintJobListener pjl) {
        pjlListeners.addElement(pjl);
    }
    
    /** Removes a <a href="PrintServiceJobListener.html">PrintServiceJobListener</a> from the <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s vector that will monitor a PrintJob
     *  @param pjl The <a href="PrintServiceJobListener.html">PrintServiceJobListener</a> to remove from the vector of <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s to be used for monitoring a PrintJob
     */
    public void removePrintJobListener(PrintJobListener pjl) {
        pjlListeners.removeElement(pjl);
    }
    
    /** Adds a <a href="PrintAttributeListener.html">PrintAttributeListener</a> to the <a href="PrintAttributeListener.html">PrintAttributeListener</a>s vector that will monitor the selected Print Service's attributes
     *  @param psal a <a href="PrintAttributeListener.html">PrintAttributeListener</a> to add to the vector of <a href="PrintAttributeListener.html">PrintAttributeListener</a>s to be used for monitoring the selected Print Service's attributes
     */
    public void addPrintServiceAttributeListener(PrintServiceAttributeListener psal) {
        psalListeners.addElement(psal);
    }
    
    /** Removes a <a href="PrintAttributeListener.html">PrintAttributeListener</a> from the <a href="PrintAttributeListener.html">PrintAttributeListener</a>s vector that will monitor the selected Print Service's attributes
     *  @param psal The <a href="PrintAttributeListener.html">PrintAttributeListener</a> to remove from the vector of <a href="PrintAttributeListener.html">PrintAttributeListener</a>s to be used for monitoring the selected Print Service's attributes
     */
    public void removePrintServiceAttributeListener(PrintServiceAttributeListener psal) {
        psalListeners.removeElement(psal);
    }
    
    /** Instantiates a <a href="PrintPreviewDialog.html">PrintPreviewDialog</a> to preview the JEditorPane's rendering before printing (the printing may be cancelled by the user though the PrintPreviewDialog)
     *  @param w The width of the PrintPreviewDialog. Determined by the JEditorPane's size and reflects the printing parameters
     *  @param h The height of the PrintPreviewDialog. Determined by the JEditorPane's size and reflects the printing parameters
     */
    public void preview(int w, int h) throws PrintException {
        PrintPreviewDialog ppd = new PrintPreviewDialog(jep, w, h);
        previewDialog = ppd;
        ppd.show();
        if (!ppd.isPrintConfirmed()) throw new PrintException("Printing aborted on preview");
    }
    
    /** Gets the PringPreviewDialog object
     * @return this PrintPreviewDialog object
     */    
    public PrintPreviewDialog getPrintPreviewDialog() {
        return previewDialog;
    }
    
    /** Prints the contents of the JEditorPane using the specified Print Service <br/>
     * Calculates the number of pages (if paging is enabled) according to the printing parameters (size, orientation, etc). <br/>
     * The contents of the JEditorPane are bufferred as a PNG image which is used to create the print document (SimpleDoc). <br/>
     * If paging is enabled the PNG image is split to the nubmer of pages required to fit the image (according to the size and orientation specified in the printing parameters)
     * @param ps The Print Service to be used for printing the contents of the JEditorPane
     * @return true if printing is successful or false if printing is not successful
     */
    public boolean printJEditorPane(PrintService ps) throws PrintException {
        if (ps == null || jep == null) {
            throw new PrintException("Printing cancelled.");
        }
        
        Attribute mpaAtt = attributes.get(javax.print.attribute.standard.MediaPrintableArea.class);
        float mpaX = ((MediaPrintableArea)mpaAtt).getX(units);
        float mpaY = ((MediaPrintableArea)mpaAtt).getY(units);
        float mpaW = ((MediaPrintableArea)mpaAtt).getWidth(units);
        float mpaH = ((MediaPrintableArea)mpaAtt).getHeight(units);
        
        int dpi = 0;

        dpi = DEFAULT_DPI;

        float pageX, pageY;
        if (mpaAtt != null) {
            if (units == MediaSize.INCH) {
                pageX = mpaW;
                pageY = mpaH;
            } else {
                // 1 millimeter = 0.0393700787 inch
                pageX = new Float(mpaW*0.0393700787).floatValue();
                pageY = new Float(mpaH*0.0393700787).floatValue();
            }
        } else {
            pageX = DEFAULT_PAGE_WIDTH_INCH;
            pageY = DEFAULT_PAGE_HEIGHT_INCH;
        }

        int pixelsPerPageY = (int) (dpi * pageY);
        
        // Add 'pixelsPerPageX' and 'minY'; used in place of 'x' and 'y', respectively, in the remainder of the method
        int pixelsPerPageX = (int) (dpi * pageX); // mod: needed to correctly scale images with lines pre-wrapped longer than page width
        
        Dimension jepSize = jep.getPreferredSize();
        View rv = jep.getUI().getRootView(jep);
        
        // find out if the print has been set to colour mode
        DocPrintJob dpj = ps.createPrintJob();
        PrintJobAttributeSet pjas = dpj.getAttributes();
        // make colour true if the user has selected colour, and the PrintService can support colour
        boolean colour = pjas.containsValue(Chromaticity.COLOR);
        colour = colour & (ps.getAttribute(ColorSupported.class) == ColorSupported.SUPPORTED);
        
        // create a BufferedImage to draw on
        int imgMode;
        if (colour)
            imgMode = BufferedImage.TYPE_3BYTE_BGR;
        else
            imgMode = BufferedImage.TYPE_BYTE_GRAY;

        BufferedImage tempImg = new BufferedImage(pixelsPerPageX, pixelsPerPageY, imgMode);
        Graphics myTempGraphics = tempImg.getGraphics();
        myTempGraphics.setClip(0, 0, pixelsPerPageX, pixelsPerPageY);
        myTempGraphics.setColor(Color.WHITE);
        myTempGraphics.fillRect(0, 0, pixelsPerPageX, pixelsPerPageY);
        
        javax.swing.SwingUtilities.paintComponent(myTempGraphics, jep, new javax.swing.JScrollPane(), 0, 0, pixelsPerPageX, pixelsPerPageY) ;
        
        
        // get the size of the JEditorPane's view
        int x = jepSize.width;
        x = jep.getWidth();

        if (x == 0 || x != pixelsPerPageX) x = pixelsPerPageX;
        int y = 0;

        y = (int) rv.getPreferredSpan(View.Y_AXIS);
        if (y == 0 || y < x) y = x;

        
        // get the number of pages we need to print this image
        int numberOfPages = (int) Math.ceil(y / (double) pixelsPerPageY);
        int minY = 0;

        if (paging)
            minY = Math.max((numberOfPages*pixelsPerPageY), y); // needed to print multiple page documents
        else
            minY = Math.max(pixelsPerPageY, y); // needed to print one page documents
        
        BufferedImage img = new BufferedImage(pixelsPerPageX, minY, imgMode);
        Graphics myGraphics = img.getGraphics();
        myGraphics.setClip(0, 0, pixelsPerPageX, minY);
        myGraphics.setColor(Color.WHITE);
        myGraphics.fillRect(0, 0, pixelsPerPageX, minY);
        
    
        
        // call rootView.paint( myGraphics, rect ) to paint the whole image on myGraphics
        rv.paint(myGraphics, new Rectangle(0, 0, pixelsPerPageX, minY));
      
        numberOfPages = (int) Math.ceil(rv.getPreferredSpan(View.Y_AXIS) / (double) pixelsPerPageY);
        
        try {
            // write the image as a JPEG to the ByteArray so it can be printed
            Iterator writers = ImageIO.getImageWritersByFormatName("png");
   
            ImageWriter writer = (ImageWriter) writers.next();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);
            
            int imageHeight = img.getHeight();
            
            if (preview) preview(x, y);
            
            if (paging) {
    
                // print each page
                for (int i = 0; i < numberOfPages; i++) {
                    int startY = i * pixelsPerPageY;
                    
                    // get a subimage which is exactly the size of one page
                    BufferedImage subImg = null;
                    subImg = img.getSubimage( 0, startY, pixelsPerPageX, pixelsPerPageY ); //OK---------
 
                    writer.write(subImg); //OK----------------
                    SimpleDoc sd = new SimpleDoc(bufferedImagetoByteArray(subImg), DocFlavor.BYTE_ARRAY.PNG, null); //OK-------------
                    printDocument(sd, ps);
                    // reset the ByteArray so we can start the next page
                    out.reset();
                }
            } else {
                SimpleDoc sd = new SimpleDoc(bufferedImagetoByteArray(img), DocFlavor.BYTE_ARRAY.PNG, null); //OK-------------
                if (preview) preview(x, y);
                printDocument(sd, ps);
            }

        } catch (IOException ioe) {
            throw new PrintException("Error creating ImageOutputStream or writing to it.", ioe);
        }
        return true;
    }
    
    /** Converts a java.awt.image.BufferedImage to a byte array <br/>
     * The method is declared as synchronized to allow for the full image to be converted before proceeding to the printing operation <br/>
     * @param o The java.awt.image.BufferedImage to be converted to byte array
     * @return The byte array containing the BufferedImage's content. <br/>
     * If the BufferedImage is null the byte array will be empty (zero-length)
     */
    public synchronized byte[] bufferedImagetoByteArray(BufferedImage o) throws IOException {
        if(o != null) {
            BufferedImage image = (BufferedImage) o;
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

            ImageIO.write(image, "png", baos);

            byte[] b = baos.toByteArray();
            return b;
        }
        return new byte[0];
    }
    
    
    /**
     * Prints the document to the specified Print Service <br/>
     * This method cannot tell if the printing was successful. You must register
     * a PrintJobListener <br/>
     * Registers the added <a href="PrintAttributeListener.html">PrintAttributeListener</a>s to the specified Print Service <br/>
     * Registers the added <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s to the created DocPrintJob
     * @param doc The javax.print.Doc to be printed
     * @param ps The javax.print.PrintService to be used for printing
     * @return true if a PrintService is selected and no Exception is thrown during printing
     */
    public boolean printDocument(Doc doc, PrintService ps) throws PrintException {
        if (ps == null) throw new PrintException("PrintService is null.");
        addAllPrintServiceAttributeListeners(ps);
        DocPrintJob dpj = ps.createPrintJob();
        addAllPrintJobListeners(dpj);
        
        dpj.print(doc, attributes);
        return true;
    }
    
    /**
     * Shows a Print Dialog initiatied by the specified printing parameters (PrintRequestAttributeSet) allowing the user to alter them or cancel the printing. <br/>
     * If more than one Print Service are available to be selected, the default Print Service is initially selected.
     * @param atset The javax.print.attribute.PrintRequestAttributeSet containing the applying printing parameters. <br/>
     * These are reflected in the Print Dialog allowing the user to review or alter them. <br/>
     * @return The Print Service selected by the Print Dialog
     */
    public PrintService showPrintDialog(PrintRequestAttributeSet atset) {

        return ServiceUI.printDialog(gc, x, y, services, defaultService, flavor, atset);
    }
    
    /**
     * Registers all <a href="PrintAttributeListener.html">PrintAttributeListener</a>s that have been added to the <a href="PrintAttributeListener.html">PrintAttributeListener</a>s' vector, to the specified Print Service
     * @param ps The javax.print.PrintService used for printing
     */
    private void addAllPrintServiceAttributeListeners(PrintService ps) {
        // add all listeners that are currently added to this object
        for (int i = 0; i < psalListeners.size(); i++) {
            PrintServiceAttributeListener p = (PrintServiceAttributeListener) psalListeners.get(i);
            ps.addPrintServiceAttributeListener(p);
        }
    }
    
    /**
     * Registers all <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s that have been added to the <a href="PrintServiceJobListener.html">PrintServiceJobListener</a>s' vector, to the specified javax.print.DocPrintJob
     * @param dpj The javax.print.DocPrintJob to be printed
     */
    private void addAllPrintJobListeners(DocPrintJob dpj) {
        // add all listeners that are currently added to this object
        for (int i = 0; i < pjlListeners.size(); i++) {
            PrintJobListener p = (PrintJobListener) pjlListeners.get(i);
            dpj.addPrintJobListener(p);
        }
    }
    
    /**
     * Prints the contents of the JEditorPane either to the Print Service selected by a Print Dialog or to the default Print Service
     * @param showPrintDialog true for showing the Print Dialog, false for printing to the default Print Service (without altering the PrintRequestAttributeSet)
     */
    public void printEditorPane(boolean showPrintDialog) throws PrintException {
        if (showPrintDialog) {
            printJEditorPane(showPrintDialog(attributes));
        } else {
            printJEditorPane(defaultService);
        }
    }
}
