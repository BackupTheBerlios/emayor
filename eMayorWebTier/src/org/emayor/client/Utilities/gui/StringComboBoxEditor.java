package org.emayor.client.Utilities.gui;


/**
 *  A combobox editor realized as JTextField working upon Strings.*;
 *                           
 *  This makes all textfield methods available.
 *  The original setItem() method is redirected to setText().
 *                
 *  Goal: This one allows to get the document model and attach a
 *        documentlistener for being informed about entered or 
 *        removed characters.
 *
 *  6.11.05 jpl
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class StringComboBoxEditor extends JTextField implements ComboBoxEditor
{

  public StringComboBoxEditor()
  {
    // Inside comboboxes, a bold font used per default.
    // We want the usual testfield font, so set this explicitly:
    super.setFont( UIManager.getFont("TextField.font") );
  } // Constructor
  
  /** Return the component that should be added to the tree hierarchy for
    * this editor
    */
  public Component getEditorComponent()
  {
    super.setFont( UIManager.getFont("TextField.font") );
    return this;
  }
  
  /** Set the item that should be edited. Cancel any editing if necessary **/
  public void setItem(Object anObject)
  {
     super.setText( anObject.toString() );
     super.setFont( UIManager.getFont("TextField.font") );
  }

  /** Return the edited item **/
  public Object getItem()
  {
    return this.getText();
  }


}
