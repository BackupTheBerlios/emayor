package org.emayor.RepresentationLayer.ClientTier.Controlers;


/**
 *  Another action controler for buttons.
 *  It saves the eMayorForm xml model to disk,
 *  presenting a file save dialog for the user,
 *  where the user can enter the desired filename and
 *  location on client storage medium.
 */

import java.awt.event.*;

import org.emayor.RepresentationLayer.ClientTier.EMayorFormsClientApplet;
import org.emayor.RepresentationLayer.Shared.parser.xml.*;



public class SaveModelToDiskSubmitter implements ActionListener
{

  private XML_Node modelNode = null;
  private EMayorFormsClientApplet mainApplet;
  private String buttonParameter = null;

  
  public SaveModelToDiskSubmitter( final XML_Node _modelNode,
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
    this.mainApplet.save_EMayorForm_XML_Model_ToDisk();
   } // actionPerformed
  
  
  
} // SaveModelToDiskSubmitter





