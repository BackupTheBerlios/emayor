package org.eMayor.ServiceHandling.PrintingUtility.ejb;

import java.awt.Component;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;
import javax.swing.text.html.StyleSheet;

/**
 *
 * Subclasses javax.swing.text.html.HTMLEditorKit to allow for
 * <br/>
 * complete rendering of an HTMLDocument's images
 * <br/>
 * by using <a href="MyHTMLEditorKit.MyHTMLFactory.html">MyHTMLEditorKit.MyHTMLFactory</a>
 *
 */

public class PrinterHTMLEditorKit extends HTMLEditorKit {
    public String url = null;
    public Component comp = null;
    public PrinterHTMLEditorKit(String Url, Component cmp) {
        super();
        url = Url;
        comp = cmp;
    }
    
    public Document createDefaultDocument() {
        StyleSheet styles = getStyleSheet();
        StyleSheet ss = new StyleSheet();
        
        ss.addStyleSheet(styles);
        
        HTMLDocument doc = (HTMLDocument)( super.createDefaultDocument() );
        doc.setParser(getParser());
        doc.setAsynchronousLoadPriority(-1);
        doc.setTokenThreshold(100);
        return doc;
    }
    
    public ViewFactory getViewFactory() {
        return new MyHTMLFactory(url, comp);
    }
    
    
/**
 *
 * Subclasses javax.swing.text.html.HTMLEditorKit.HTMLFactory to allow for
 * <br/>
 * complete rendering of an HTMLDocument's images
 * <br/>
 * by using <a href="MyImageView.html">MyImageView</a>
 *
 */
    public static class MyHTMLFactory extends HTMLFactory
            implements ViewFactory {
        public String url = null;
        public Component comp = null;
        public MyHTMLFactory(String Url, Component cmp) {
            super();
            url = Url;
            //        System.out.println("MyHTMLFactory(url): "+url);
            comp = cmp;
        }
        
        public View create(Element elem) {
            //        System.out.println("MyHTMLFactory.create - elem: "+elem.getName());
            Object o =
                    elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
            if (o instanceof HTML.Tag) {
                HTML.Tag kind = (HTML.Tag) o;
                if (kind == HTML.Tag.IMG)
                    //        System.out.println("new MyImageView(elem, url): "+url);
                    return new PrinterImageView(elem, url, comp);
                //          return new MyImageView(elem);
            }
            return super.create( elem );
        }
    }
}










