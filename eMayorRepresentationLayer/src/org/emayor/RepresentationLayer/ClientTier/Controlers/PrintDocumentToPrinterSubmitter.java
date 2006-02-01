package org.emayor.RepresentationLayer.ClientTier.Controlers;


/**
 *  Another action controler for buttons.
 *  It prints the eMayorForm xml model to an available printer,
 *  presenting a printer dialog for the user.
 */

import java.awt.event.*;


import org.emayor.RepresentationLayer.ClientTier.EMayorFormsClientApplet;
import org.emayor.RepresentationLayer.Shared.parser.xml.*;


public class PrintDocumentToPrinterSubmitter implements ActionListener
{

  private XML_Node modelNode = null;
  private EMayorFormsClientApplet mainApplet;
  private String buttonParameter = null;

  
  public PrintDocumentToPrinterSubmitter( final XML_Node _modelNode,
                                    final EMayorFormsClientApplet _mainApplet,
                                    final String _buttonParameter )
  {
    this.modelNode = _modelNode;
    this.mainApplet = _mainApplet;
    this.buttonParameter = _buttonParameter;
  } // Constructor

  
  
  /**
   *  The only method from the ActionListener interface.
   *  It is called as soon as a user clicks a button,
   *  which this object is listening to.
   */
   public void actionPerformed(ActionEvent e)
   {
     this.mainApplet.print_EMayorForm_XML_Document();
   } // actionPerformed





} // PrintModelToDiskSubmitter




