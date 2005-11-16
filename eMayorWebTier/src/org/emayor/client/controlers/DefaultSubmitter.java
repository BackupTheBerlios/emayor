package org.emayor.client.controlers;

  /**
   *  The default action controler for buttons.
   *  It just submits the xml model to the host, from where
   *  the applet has been loaded.
   */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

import org.emayor.client.EMayorFormsClientApplet;
import org.emayor.client.parser.XMLParser;
import org.emayor.client.parser.xml.*;
import org.emayor.client.parser.XMLPath;



public class DefaultSubmitter implements ActionListener
{
  private XML_Node modelNode = null;
  private EMayorFormsClientApplet mainApplet;
  private String buttonParameter = null;

  
  
  public DefaultSubmitter( final XML_Node _modelNode,
                           final EMayorFormsClientApplet _mainApplet,
						   final String _buttonParameter )
  {
    this.modelNode = _modelNode;
    this.mainApplet = _mainApplet;
    this.buttonParameter = _buttonParameter;
    // Note: The modelNode and the contained tree will be changed
    //       as long as the user is entering or modifying data.
    //       Therefore do not do anything here. Any action only
    //       start in the actionPerformed() method.
  } // Constructor

  


 /**
  *  The only method from the ActionListener interface.
  *  It is called as soon as a user clicks a button,
  *  which this object is listening to.
  */
  public void actionPerformed(ActionEvent e)
  {
    // Take the xml tree and flatten it into a String.
    // Select the child of the model node, because the <model> tag
    // itself is not part of the e-document. So the first (and only)
    // childnode of the model node is the e-document, and this one
    // must exist in any case:
    if( this.modelNode.getNumberOfChildren() > 0 ) // actually it must be exactly 1
    {
      XML_Node modelContentNode = this.modelNode.getChildAt(0);
      XMLParser xmlParser = new XMLParser();
      StringBuffer xmlModelDocument = xmlParser.transformTreeToDocument( modelContentNode,true );
      boolean succeeded = this.mainApplet.postDocument( xmlModelDocument.toString(),this.buttonParameter );
      if( !succeeded )
      {
        JOptionPane.showMessageDialog(this.mainApplet,
             "Unable to post the document to the server.");
      }
    }
    else
    {
      JOptionPane.showMessageDialog(this.mainApplet,
            "Unable to post the document to the server.\nThe document is empty.");
    }
  } // actionPerformed


  

} // DefaultSubmitter





