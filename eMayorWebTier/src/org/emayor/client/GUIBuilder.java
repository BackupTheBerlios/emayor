package org.emayor.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import org.emayor.client.parser.xml.*;
import org.emayor.client.parser.EMayorDocumentParser;
import org.emayor.client.parser.XMLPath;
import org.emayor.client.controlers.*;
import org.emayor.client.Utilities.DataUtilities;
import org.emayor.client.Utilities.SchemaEnumeration.SchemaEnumerationMapCreator;
import org.emayor.client.Utilities.SchemaEnumeration.SchemaEnumerationMap;
import org.emayor.client.Utilities.gui.JSenseButton;
import org.emayor.client.Utilities.gui.StringComboBoxEditor;
import org.emayor.client.controlers.SchemaValidator;


import org.emayor.client.Utilities.SchemaEnumeration.*;


public class GUIBuilder
{

  // Reserved identifier names used for eMayorForm
  // attributes:
  public static final String Attribute_LayoutClass="LayoutClass";
  public static final String Attribute_LayoutParameter="LayoutParameter";
  public static final String Attribute_ModelPath="ModelPath";
  public static final String Attribute_AddParameter="AddParameter";
  public static final String Attribute_Columns="Columns";
  public static final String Attribute_Rows="Rows";
  public static final String Attribute_WrapByScrollPane="WrapByScrollPane";
  public static final String Attribute_BoxType="BoxType";
  public static final String Attribute_BoxSize="BoxSize";
  public static final String Attribute_ActionClass="ActionClass";
  public static final String Attribute_Background="Background";
  public static final String Attribute_BorderType="BorderType";
  public static final String Attribute_HorizontalBorderSize="HorizontalBorderSize";
  public static final String Attribute_VerticalBorderSize="VerticalBorderSize";
  public static final String Attribute_SubmitterParameter="SubmitterParameter";
  public static final String Attribute_IsEditable="Editable";

  // Alignments used for boxlayouts:
  public static final String Attribute_AlignmentX="AlignmentX";
  public static final String Attribute_AlignmentY="AlignmentY";

  
  private EMayorFormsClientApplet mainApplet;
  private String language = "en"; // The language used for the UI.

  private XML_Node modelNode;
  private XML_Node viewNode;
   
  private SchemaValidator schemaValidator = null;
  private SchemaEnumerationMap schemaEnumerationMap = null;
  
  private EMayorDocumentParser eMayorDocumentParser;
  
  private EnumerationProperties enumerationProperties;

  
  
  public GUIBuilder( final XML_Node _modelNode,
                     final XML_Node _viewNode,
                     final EMayorFormsClientApplet _mainApplet,
                     final LanguageProperties _languageProperties,
                     final EnumerationProperties _enumerationProperties,
                     final String _language,
                     final EMayorDocumentParser _eMayorDocumentParser )
  {
    this.modelNode = _modelNode;
    this.viewNode = _viewNode;
    this.mainApplet = _mainApplet;
    this.enumerationProperties = _enumerationProperties;
    this.language = _language;
    this.eMayorDocumentParser = _eMayorDocumentParser;
    
    System.out.println("GUIBuilder.constructor: Set tooltips to react immediately.");
    ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
    toolTipManager.setInitialDelay(99);
    toolTipManager.setDismissDelay(30000);
    toolTipManager.setReshowDelay(99);
        
    // Create and start the schemavalidator background thread:
    this.schemaValidator = new SchemaValidator( _modelNode, _eMayorDocumentParser, _mainApplet, _languageProperties );
    this.schemaValidator.start();    
  } // Constructor


  

  
 /**
  *   This is called from the DocumentProcessor, when the whole model
  *   has changed.
  *   This only occurs, when the user has loaded a stored eMayorForm
  *   from disk.
  *   Action taken should be: Update the Gui on the passed viewPanel.
  */ 
  public void fireModelChanged( JPanel viewPanel )
  {
    try
    {
      this.buildGraphicalUserInterfaceOn( viewPanel );
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
  } // fireModelChanged
  
  
  
  
  

 /**
  *  Build the GUI on the interactionPanel, based on
  *  the viewNode and modelNode.
  */
  public void buildGraphicalUserInterfaceOn( JPanel viewPanel ) throws Exception
  {
  	viewPanel.removeAll();
    // The view always has BorderLayout.
  	viewPanel.setLayout( new BorderLayout(0,0) );
    viewPanel.setBackground( new Color(210,255,210) );

    // Preparation for a special feature for JTextFields:
    // If schemas are present and a schema contains an enumeration
    // a JComboBox is used instead of a JTextField, and the
    // enumeration values automatically are added to that JComboBox.
    // This way, the user can select one of the schema conform input.
    // This requires an initial sax parser run, which returns a list of all
    // entry pathes with enumeration types found and their values.
    // Don't allow exceptions or errors to break execution, because this
    // is an optional feature.
    try
    {
      SchemaEnumerationMapCreator c = new SchemaEnumerationMapCreator();
      this.schemaEnumerationMap = c.createSchemaEnumerationMap( this.modelNode, this.eMayorDocumentParser );
    }
    catch( Exception ex )
    {
      System.out.println("*** GUIBuilder: Unable to get schema enumeration map.[This is optional]");
      System.out.println("*** Exception message= " + ex.getMessage() );
    }
    catch( Error err )
    {
      System.out.println("*** GUIBuilder: Unable to get schema enumeration map.[This is optional]");
      System.out.println("*** Error message= " + err.getMessage() );    
    }
    
    // debug:
    if( this.schemaEnumerationMap != null )
    {
      this.schemaEnumerationMap.printMap();
    }
    
    // build the UI - the method uses recursion:
    this.buildUI( viewPanel, this.viewNode );

    // Call Swing for the update:
    final JPanel finalViewPanel = viewPanel;
    // Only carry this out using invokeAndWait, if the
    // current thread is outside the EDT.
    if( EventQueue.isDispatchThread() )
    {
      finalViewPanel.updateUI(); // Direct call is allowed
    }
    else // We're in a user thread, so put it into the EDT queue, and wait for it:
    {
      try
      {
        EventQueue.invokeAndWait( new Runnable()
        {
          public void run()
          {
        	finalViewPanel.updateUI();
          }
        });
      } catch( Exception e )
      {
        System.err.println("*** init(): Couldn't display the load screen.");
      }
    } // else  
  } // buildGraphicalUserInterfaceOn





 /**
  *  Recursive worker of buildGraphicalUserInterfaceOn()
  */
  private void buildUI( final JPanel parentPanel,
                        final XML_Node parentNode ) throws Exception
  {  	
    for( int i=0; i < parentNode.getNumberOfChildren(); i++ )
    {
      XML_Node childNode = parentNode.getChildAt(i);            
      if( childNode.getTagName().equals("JPanel") )
      {
        this.buildJPanelUI( parentPanel,childNode ); // can call buildUI() again for children
      }
      else
      if( childNode.getTagName().equals("JLabel") )
      {
        this.buildJLabellUI( parentPanel,childNode ); // can call buildUI() again for children
      }
      else
        if( childNode.getTagName().equals("JTextField") )
        {
          this.buildTextInputUI( parentPanel,childNode ); // can call buildUI() again for children
        }
      else
      if( childNode.getTagName().equals("JTextArea") )
      {
        this.buildJTextAreaUI( parentPanel,childNode ); // can call buildUI() again for children
      }
      else
      if( childNode.getTagName().equals("JButton") )
      {
        this.buildJButtonUI( parentPanel,childNode ); // can call buildUI() again for children
      }
      else
      if( childNode.getTagName().equals("Box") )
      {
        this.buildBoxUI( parentPanel,childNode ); // can call buildUI() again for children
      }
      else
      {
      	System.out.println("***");
        System.out.println("*** buildUI(): Unsupported tagname: " + childNode.getTagName() );
      	System.out.println("***");
      }
    }
  } // buildUI



  
  
  
  private void buildJButtonUI( final JPanel parentPanel,
                               final XML_Node jButtonNode )
  {                                       
    String actionClass = jButtonNode.getAttributeValueForName(Attribute_ActionClass);
    String addParameter = jButtonNode.getAttributeValueForName(Attribute_AddParameter);

    String alignmentX_Parameter = jButtonNode.getAttributeValueForName(Attribute_AlignmentX);
    String alignmentY_Parameter = jButtonNode.getAttributeValueForName(Attribute_AlignmentY);

    String submitterParameter = jButtonNode.getAttributeValueForName(Attribute_SubmitterParameter);
		
    String value = jButtonNode.getTagValue();
    // Create it:
    final JSenseButton jButton = new JSenseButton(true);
    // For each jbutton, we add one actionlistener, which deactivates the button
    // for 2 seconds after it has been clicked. This prevents the user from
    // starting actions multiple times, or starting actions two times, if it is
    // doubleclicked:
    jButton.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent e )
      {
        jButton.setEnabled(false);
        Thread reenablerThread = new Thread()
        {
          public void run()
          {
            try{ Thread.sleep(1999); } catch( Exception ee ){}
            // reenabling is a swing task, so add to EDT queue:
            EventQueue.invokeLater( new Runnable()
            {
              public void run()
              {
                jButton.setEnabled(true);              
              }
            });
          }
        };
        reenablerThread.start();
      }
    });
    if( value != null ) jButton.setText( DataUtilities.TranslateUnicodeShortcutsInLine(value) );
    if( actionClass != null )
    {
        if( actionClass.equals("DefaultSubmitter") )
        {
          ImageIcon icon = this.loadIconFromResource("/pictures/applet/ok.gif");
          if( icon != null ) jButton.setIcon(icon);
          jButton.addActionListener( new DefaultSubmitter( this.modelNode, this.mainApplet,submitterParameter ) );
        }
        else
        if( actionClass.equals("XMLSignatureSubmitter") )
        {
          // The icon is dependent of the submitter parameter:
          // If none is set, use ok.gif
          // If there is one set, use ok gif, unless, the value of
          // the submitter is "Deny", in shich case invalid.gif is used:
          ImageIcon icon = null;
          if( submitterParameter != null )
          {
            if( submitterParameter.equals("Deny") )
            {
              icon = this.loadIconFromResource("/pictures/applet/invalid.gif");
            }
            else
            {
              icon = this.loadIconFromResource("/pictures/applet/ok.gif");
            }
          }
          else
          {
            icon = this.loadIconFromResource("/pictures/applet/ok.gif");
          }
          if( icon != null ) jButton.setIcon(icon);
          jButton.addActionListener( new XMLSignatureSubmitter( this.modelNode, this.mainApplet,submitterParameter ) );
        }
        else
        if( actionClass.equals("RedirectSubmitter") )
        {
          ImageIcon icon = this.loadIconFromResource("/pictures/applet/bluerightarrow.gif");
          if( icon != null ) jButton.setIcon(icon);
          jButton.addActionListener( new RedirectSubmitter( this.modelNode, this.mainApplet,submitterParameter ) );
        }
        else
        if( actionClass.equals("SaveModelToDiskSubmitter") )
        {
          ImageIcon icon = this.loadIconFromResource("/pictures/applet/save.gif");
          if( icon != null ) jButton.setIcon(icon);
          jButton.addActionListener( new SaveModelToDiskSubmitter( this.modelNode, this.mainApplet,submitterParameter ) );
        }
        else
        if( actionClass.equals("PrintFormToPrinterSubmitter") )
        {
          ImageIcon icon = this.loadIconFromResource("/pictures/applet/printicon.gif");
          if( icon != null ) jButton.setIcon(icon);
          jButton.addActionListener( new PrintFormToPrinterSubmitter( this.mainApplet,submitterParameter ) );
        }
        else
        if( actionClass.equals("PrintDocumentToPrinterSubmitter") )
        {
          ImageIcon icon = this.loadIconFromResource("/pictures/applet/printicon.gif");
          if( icon != null ) jButton.setIcon(icon);
          jButton.addActionListener( new PrintDocumentToPrinterSubmitter( this.modelNode, this.mainApplet,submitterParameter ) );
        }
        else
        {
          System.out.println("buildJButtonUI: Not supported action class: " + actionClass );
        }
    }
    else
    {
      // Without an action, this button won't do anything:
      System.out.println("buildJButtonUI: Caution: JButton without action added.");
    }
    // Add it:
    this.addComponent(parentPanel,jButton,addParameter,
    		          alignmentX_Parameter,alignmentY_Parameter );
  }



  
  
 /**
  *   Load the icon from resource with iconPath.
  *   Returns null, if the icon has not been found. 
  */ 
  private ImageIcon loadIconFromResource(String iconPath)
  {
    ImageIcon icon = null;
    try
    {
      icon = this.mainApplet.loadImageIcon( iconPath );
    }
    catch( Exception ee )
    {
       System.out.println( "*** GuiBuilder Exception: Icon not found for path= " + iconPath );
    }
    catch( Error err )
    {
       System.out.println( "*** GuiBuilder Error: Icon not found for path= " + iconPath );
    }
    return icon;
  }

  
  
  

  private void buildBoxUI( final JPanel parentPanel,
                           final XML_Node boxNode )
  {
    String boxType = boxNode.getAttributeValueForName(Attribute_BoxType);
    String boxSize = boxNode.getAttributeValueForName(Attribute_BoxSize);
    String addParameter = boxNode.getAttributeValueForName(Attribute_AddParameter);
    
    if( boxType == null ) // invalid box ui directive
    {
        System.out.println("*** ");
        System.out.println("*** Invalid Box UI directive: boxType is not specified.");
        System.out.println("*** ");
    } // if
    
    int size = -1;
    if( boxSize != null ) // optional
    {
      try{ size = Integer.parseInt(boxSize); } catch(Exception e){}
    }
    JComponent box = null;
    if( boxType.equals("createHorizontalGlue") )
    {                              
      box = (JComponent)Box.createHorizontalGlue();
    }
    else
    if( boxType.equals("createVerticalGlue") )
    {
      box = (JComponent)Box.createVerticalGlue();
    }
    else
    if( boxType.equals("createHorizontalStrut") )
    {
      if( size > 0 ) box = (JComponent)Box.createHorizontalStrut(size);
    }
    else
    if( boxType.equals("createVerticalStrut") )
    {                                       
      if( size > 0 ) box = (JComponent)Box.createVerticalStrut(size);
    }
    // Add it:
    if( box != null )
    {
      this.addComponent(parentPanel,box,addParameter,null,null);
    }                                               
    else
    {
      System.out.println("buildBoxUI: Unable to add Box with type " + boxType);
    }
  }




  private void buildJLabellUI( final JPanel parentPanel,
                               final XML_Node jLabelNode ) throws Exception
  {
    String addParameter = jLabelNode.getAttributeValueForName(Attribute_AddParameter);
    String columnsParameter = jLabelNode.getAttributeValueForName(Attribute_Columns);
    String backgroundParameter = jLabelNode.getAttributeValueForName(Attribute_Background);
    String borderTypeParameter = jLabelNode.getAttributeValueForName(Attribute_BorderType);
    String horizontalBorderSizeParameter = jLabelNode.getAttributeValueForName(Attribute_HorizontalBorderSize);
    String verticalBorderSizeParameter = jLabelNode.getAttributeValueForName(Attribute_VerticalBorderSize);

    String alignmentX_Parameter = jLabelNode.getAttributeValueForName(Attribute_AlignmentX);
    String alignmentY_Parameter = jLabelNode.getAttributeValueForName(Attribute_AlignmentY);
        
    // A value is optional, if it's not connected with the model.
    // If a modelPath is set, the value will not be used.
    String value = jLabelNode.getTagValue();
    int columns = this.getIntegerFromParameter(columnsParameter);    
    String modelPath = jLabelNode.getAttributeValueForName(Attribute_ModelPath);
    if( modelPath != null )
    {
      // The value is connected with a model xml value, get it:
      XML_Node modelValueNode = XMLPath.GetNode(modelPath,this.modelNode);
      if( modelValueNode != null )
      {
        value = modelValueNode.getTagValue();
        //System.out.println("buildJLabellUI: ..overwritten by modelpath value= " + value );
      }
      else
      {
        value = "[undefined]";
        System.out.println("*** buildJLabellUI: a JLabel model reference points to a not existing address. ");
        System.out.println("*** buildJLabellUI: The reference is: " + modelPath );      
      }
    
    }
    // Create it:
    JLabel jLabel = new JLabel();    
    this.setGraphicalAttributes( jLabel,backgroundParameter,
    		                     borderTypeParameter,
    		                     horizontalBorderSizeParameter,verticalBorderSizeParameter );
    if( value != null ) jLabel.setText( DataUtilities.TranslateUnicodeShortcutsInLine(value) );
    if( columns > 0 )
    {
      //System.out.println("buildJLabellUI: Add jLabel with number of columns set to " + columns );
      int fontSize = UIManager.getFont("Label.font").getSize();      
      int labelWidth = fontSize*columns;
      int labelHeight = (5*fontSize)/4;
      jLabel.setPreferredSize( new Dimension(labelWidth,labelHeight) );
    } // if columns    
    // Add it:
    this.addComponent( parentPanel,jLabel,addParameter,alignmentX_Parameter,alignmentY_Parameter);
    //System.out.println("Added JLabel");
  } // buildJLabellUI

  
  
  

  
  
  
 /**
  *  This is called for text fields.
  *  However this method only creates a JTextField, if the associated entry
  *  is not associated with enumeration values.
  *  If it's associated with enumeration values, it creates a JComboBox instead.
  */ 
  private void buildTextInputUI( final JPanel parentPanel,
                                 final XML_Node textInputNode ) throws Exception
  {
    
    System.out.println("buildTextInputUI called.");
    
    String isEditable_Parameter = textInputNode.getAttributeValueForName(Attribute_IsEditable);
    boolean isReadOnly = false;
    if( ( isEditable_Parameter != null ) && (isEditable_Parameter.equals("false") ) )
    {
      isReadOnly = true;
    }
    SchemaEnumerationMapValue enumerationMapValue = null;
    final String[] modelPaths = textInputNode.getAttributeValuesForNameStartingWith(Attribute_ModelPath);    
    if( modelPaths != null )
    {
      String firstPath = modelPaths[0];
      // The value is connected with a model xml value, get it.
      // If there are multiple, take the first one:
      XML_Node modelValueNode = XMLPath.GetNode( firstPath,this.modelNode );      
      if( modelValueNode != null ) // connected and valid
      {
        // See if we get an enumeration for this one from the xsd schemas:
        enumerationMapValue = this.schemaEnumerationMap.getEnumerationForNodePath(firstPath);

        boolean enumFound = ( enumerationMapValue != null );
        System.out.println("buildTextInputUI enumerationmapvalue for " + firstPath + "  found=  " +
                            enumFound );
      
      }
    }
    System.out.println("buildTextInputUI: isReadOnly= " + isReadOnly );

    if( ( enumerationMapValue == null ) || (isReadOnly) )
    {
      System.out.println("buildTextInputUI: making JTextField");    
      // No enumeration constraints -> use a simple JTextField:
      this.buildJTextFieldUI(parentPanel,textInputNode);
    }
    else
    { 
      System.out.println("buildTextInputUI: making JComboBox");      
      // We have an editable entry with enumeration constraints, 
      // so use a JComboBox instead of a simple textfield:
      this.buildEditableJComboBoxUI( parentPanel,textInputNode,enumerationMapValue );    
    }
  } // buildTextInputUI




  
  

 /**
  *  Create a JComboBox and use the values passed in enumerationMapValue as list entries.
  *  JComboBoxes only make sense for editable fields, therefore this method
  *  only should be called for editable nodes.
  */
  private void buildEditableJComboBoxUI( final JPanel parentPanel,
                                         final XML_Node jTextFieldNode,
                                         final SchemaEnumerationMapValue enumerationMapValue  ) throws Exception
  {
    String addParameter = jTextFieldNode.getAttributeValueForName(Attribute_AddParameter);
    String columnsParameter = jTextFieldNode.getAttributeValueForName(Attribute_Columns);
    int columns = this.getIntegerFromParameter(columnsParameter);

    String alignmentX_Parameter = jTextFieldNode.getAttributeValueForName(Attribute_AlignmentX);
    String alignmentY_Parameter = jTextFieldNode.getAttributeValueForName(Attribute_AlignmentY);

    // A value is optional, if it's not connected with the model.
    // If one or multiple modelPaths are set, the value will not be used.
    String value = jTextFieldNode.getTagValue();
    final String[] modelPaths = jTextFieldNode.getAttributeValuesForNameStartingWith(Attribute_ModelPath);    

    if( modelPaths != null )
    {
      // The value is connected with a model xml value, get it.
      // If there are multiple, take the first one:
      XML_Node modelValueNode = XMLPath.GetNode( modelPaths[0],this.modelNode );      
      if( modelValueNode != null )
      {
        // The value of the model will be set as first entry and become
        // the selected entry following how JComboBoxes are built.
        value = modelValueNode.getTagValue();
        // Create the String array with all values:
        final String[] enumerationValues = enumerationMapValue.getEnumerationValues();
      
        // On top of these enumerationValues from the xsd schemas, we put another map
        // which translates these to the current language set on the client.
        // We display values in the current language on the client, if a translation map
        // is found in this.enumerationProperties but use the original values for
        // writing back data to the e-document.
        // Catch any exceptions - don't allow this optional service to crash execution:
        String[] translatedEnumerationValues = new String[0];
        try
        {
          translatedEnumerationValues = 
                 this.enumerationProperties.getTranslatedEnumerationValuesFor( 
                      this.language,enumerationValues );
        }
        catch( Exception eee )
        {
          translatedEnumerationValues = new String[0]; // for security - a defined state
          System.out.println("GUIBuilder: Optional getTranslatedEnumerationValuesFor() cancelled.");
          eee.printStackTrace();
        }
        System.out.println("GUIBuilder: XX translated values for enumeration");
        for( int i=0; i < enumerationValues.length; i++ )
        {
          System.out.println("source enumeration> " + enumerationValues[i]);
        }
        for( int i=0; i < translatedEnumerationValues.length; i++ )
        {
          System.out.println("translated enumeration> " + translatedEnumerationValues[i]);
        }
        
        final boolean transformationIsDefined = ( enumerationValues.length == translatedEnumerationValues.length );
        // Create it, use the translated enumeration values, if defined:
        String[] dropDownValues = (transformationIsDefined) ? translatedEnumerationValues : enumerationValues;

        System.out.println("GUIBuilder: translationIsDefined= " + transformationIsDefined);
        
        final JComboBox jComboBox = new JComboBox(dropDownValues);
        jComboBox.setEditable(true); // always - only this makes sense
        // and the associated label for the display of schema validation error informations:
        // Must be wide enough, because geometry isn't changed after creation.
        final JLabel schemaValidationInfoLabel = new JLabel("                                                ");
        schemaValidationInfoLabel.setOpaque(false); // == transparent background
        // We use our own editor, which let's us connect to the document model:       
        final StringComboBoxEditor comboboxEditor = new StringComboBoxEditor();
        jComboBox.setEditor( comboboxEditor );

        // Set the initial value in the editor:
        String selectedInitialValue = value;
        // forwardtransform if mapping is defined:
        if( transformationIsDefined )
        {
          selectedInitialValue = enumerationProperties.forwardTransform( selectedInitialValue,translatedEnumerationValues,enumerationValues);
        }
        comboboxEditor.setText( selectedInitialValue );
        
        System.out.println("combobox initial value = $" + selectedInitialValue + "$" );
        
        if( columns > 0 ) 
        {
          // A little adaptation: Because the JComboBoxes need mor horizontal
          // space for the arrow button, we subtract 2 columns, but only
          // if we then still have some:
          if( columns > 4 ) columns -=2;
          comboboxEditor.setColumns(columns);
        }
        
        final String[] finalTranslatedEnumerationValues = translatedEnumerationValues;
        // Connect it with the model:
        DocumentListener docListener = new DocumentListener()
         {
           public void insertUpdate(DocumentEvent e){ transferTextToModel(); }
           public void removeUpdate(DocumentEvent e){ transferTextToModel(); }
           public void changedUpdate(DocumentEvent e){ transferTextToModel(); }
           private void transferTextToModel()
           {
             try
             {
               for( int i=0; i < modelPaths.length; i++ )
               {
                 XML_Node modelFieldNode = XMLPath.GetNode(modelPaths[i],modelNode);
                 String newText = (String)comboboxEditor.getText();

                 System.out.println("combobox new value = $" + newText + "$" );

                 // backtransform if mapping is defined:
                 if( transformationIsDefined )
                 {
                   newText = enumerationProperties.backTransform( newText,finalTranslatedEnumerationValues,enumerationValues);
                 }
                 
                 modelFieldNode.setTagValue( newText );
               }
               // Inform the validator:
               schemaValidator.fireContentHasChanged();
             }
             catch( Exception e )
             {
               e.printStackTrace();
             }
           }
         };
         comboboxEditor.getDocument().addDocumentListener(docListener);
         // also react on item selections:
         jComboBox.addActionListener( new ActionListener()
             {
               public void actionPerformed( ActionEvent e )
               {
                 try
                 {
                   for( int i=0; i < modelPaths.length; i++ )
                   {
                     XML_Node modelFieldNode = XMLPath.GetNode(modelPaths[i],modelNode);
                     String newText = (String)comboboxEditor.getText();
                     
                     // backtransform if mapping is defined:
                     if( transformationIsDefined )
                     {
                       newText = enumerationProperties.backTransform( newText,finalTranslatedEnumerationValues,enumerationValues);
                     }
                     
                     modelFieldNode.setTagValue( newText );
                   }
                   // Inform the validator:
                   schemaValidator.fireContentHasChanged();
                 }
                 catch( Exception ex )
                 {
                   ex.printStackTrace();
                 }
               }
             });
          
         // Add the jComboBox and the schema validation info label :
         int hGap = UIManager.getFont("Label.font").getSize();
         JPanel textInputPanel = new JPanel( new BorderLayout(hGap,0) );
         textInputPanel.setOpaque(false);
         textInputPanel.add(jComboBox, BorderLayout.CENTER );
         textInputPanel.add(schemaValidationInfoLabel, BorderLayout.EAST );
         this.addComponent(parentPanel,textInputPanel,addParameter,alignmentX_Parameter,alignmentY_Parameter);
                  
         // Inform the validator:
         this.schemaValidator.addEntry( jComboBox,schemaValidationInfoLabel,modelValueNode );         
      }
      else
      {
        String msg = "*** The [first] reference given by modelPath= " + modelPaths[0] +
                     " is not valid. Such an element does not exist in the xml model node.";
        System.out.println(msg);
        value = "Invalid reference.";
        JTextField jTextField = new JTextField(value);
        jTextField.setBackground( new Color(255,100,100) );
        JPanel textInputPanel = new JPanel( new BorderLayout() );
        textInputPanel.setOpaque(false);
        textInputPanel.add(jTextField, BorderLayout.CENTER );
        this.addComponent(parentPanel,textInputPanel,addParameter,alignmentX_Parameter,alignmentY_Parameter);
      }       
    }
    else
    {
      // Should never happen - this method must not be called, when no modelpath is set.
      System.out.println("*** JComboBox Fatal Error: modelpaths must be set for comboboxes.");    
    }
    //System.out.println("Added JComboBox");
  } // buildJComboBoxUI 

  
  
  
  
  
  private void buildJTextFieldUI( final JPanel parentPanel,
                                  final XML_Node jTextFieldNode ) throws Exception
  {
    String addParameter = jTextFieldNode.getAttributeValueForName(Attribute_AddParameter);
    String columnsParameter = jTextFieldNode.getAttributeValueForName(Attribute_Columns);
    int columns = this.getIntegerFromParameter(columnsParameter);

    String alignmentX_Parameter = jTextFieldNode.getAttributeValueForName(Attribute_AlignmentX);
    String alignmentY_Parameter = jTextFieldNode.getAttributeValueForName(Attribute_AlignmentY);
    String isEditable_Parameter = jTextFieldNode.getAttributeValueForName(Attribute_IsEditable);

    // A value is optional, if it's not connected with the model.
    // If one or multiple modelPaths are set, the value will not be used.
    String value = jTextFieldNode.getTagValue();
    final String[] modelPaths = jTextFieldNode.getAttributeValuesForNameStartingWith(Attribute_ModelPath);    

    // Create it:
    final JTextField jTextField = new JTextField();    
    // Set the textfield readonly only if the editable parameter is specified as false:
    if( ( isEditable_Parameter != null ) && (isEditable_Parameter.equals("false") ) )
    {
      jTextField.setEditable(false);
    }
    // and the associated label for the display of schema validation error informations:
    // Must be wide enough, because geometry isn't changed after creation.
    final JLabel schemaValidationInfoLabel = new JLabel("                                                ");
    schemaValidationInfoLabel.setOpaque(false); // == transparent background
    if( modelPaths != null )
    {
      // The value is connected with a model xml value, get it.
      // If there are multiple, take the first one:
      XML_Node modelValueNode = XMLPath.GetNode( modelPaths[0],this.modelNode );      
      if( modelValueNode != null )
      {	
        value = modelValueNode.getTagValue();
        // Connect it with the model:
        DocumentListener docListener = new DocumentListener()
         {
           public void insertUpdate(DocumentEvent e){ transferTextToModel(); }
           public void removeUpdate(DocumentEvent e){ transferTextToModel(); }
           public void changedUpdate(DocumentEvent e){ transferTextToModel(); }
           private void transferTextToModel()
           {
             try
             {
               for( int i=0; i < modelPaths.length; i++ )
               {
                 XML_Node modelFieldNode = XMLPath.GetNode(modelPaths[i],modelNode);
                 modelFieldNode.setTagValue( jTextField.getText() );
               }
               // Inform the validator:
               schemaValidator.fireContentHasChanged();
             }
             catch( Exception e )
             {
               e.printStackTrace();
             }
           }
         };
         jTextField.getDocument().addDocumentListener( docListener );
         // Inform the validator:
         this.schemaValidator.addEntry( jTextField,schemaValidationInfoLabel,modelValueNode );         
      }
      else
      {
      	String msg = "*** The [first] reference given by modelPath= " + modelPaths[0] +
		             " is not valid. Such an element does not exist in the xml model node.";
		System.out.println(msg);
      	value = "Invalid reference.";
      	jTextField.setBackground( new Color(255,100,100) );
      }       
    }
    if( columns > 0 ) jTextField.setColumns( columns );
    if( value != null ) jTextField.setText( DataUtilities.TranslateUnicodeShortcutsInLine(value) );
    // Add the jtextfield and the schema validation info label :
    int hGap = UIManager.getFont("Label.font").getSize();
    JPanel textInputPanel = new JPanel( new BorderLayout(hGap,0) );
    textInputPanel.setOpaque(false);
    textInputPanel.add(jTextField, BorderLayout.CENTER );
    textInputPanel.add(schemaValidationInfoLabel, BorderLayout.EAST );
    this.addComponent(parentPanel,textInputPanel,addParameter,alignmentX_Parameter,alignmentY_Parameter);
    //System.out.println("Added JTextField");
  }



  
  
  
  
  
  private void buildJTextAreaUI( final JPanel parentPanel,
                                 final XML_Node jTextAreaNode ) throws Exception
  {
    String addParameter = jTextAreaNode.getAttributeValueForName(Attribute_AddParameter);
    String columnsParameter = jTextAreaNode.getAttributeValueForName(Attribute_Columns);
    int columns = this.getIntegerFromParameter(columnsParameter);
    String rowsParameter = jTextAreaNode.getAttributeValueForName(Attribute_Rows);
    int rows = this.getIntegerFromParameter(rowsParameter);

    String alignmentX_Parameter = jTextAreaNode.getAttributeValueForName(Attribute_AlignmentX);
    String alignmentY_Parameter = jTextAreaNode.getAttributeValueForName(Attribute_AlignmentY);
    String isEditable_Parameter = jTextAreaNode.getAttributeValueForName(Attribute_IsEditable);

    // A value is optional, if it's not connected with the model.
    // If one or multiple modelPaths are set, the value will not be used.
    String value = jTextAreaNode.getTagValue();
    final String[] modelPaths = jTextAreaNode.getAttributeValuesForNameStartingWith(Attribute_ModelPath);    
    // Create it:
    final JTextArea jTextArea = new JTextArea(rows,columns);
    // set it readonly only if the editable parameter is specified as false:
    if( ( isEditable_Parameter != null ) && (isEditable_Parameter.equals("false") ) )
    {
      jTextArea.setEditable(false);
    }
    jTextArea.setBorder( BorderFactory.createLineBorder( new Color(80,80,80),1));
    // and the associated label for the display of schema validation error informations:
    final JLabel schemaValidationInfoLabel = new JLabel("                                                ");
    schemaValidationInfoLabel.setOpaque(false); // == transparent background
    if( modelPaths != null )
    {
      // The value is connected with a model xml value, get it:
      // If there are multiple, take the first one:
      XML_Node modelValueNode = XMLPath.GetNode( modelPaths[0],this.modelNode );      
      if( modelValueNode != null )
      {
        value = modelValueNode.getTagValue();
        // Connect it with the model:
        DocumentListener docListener = new DocumentListener()
        {
          public void insertUpdate(DocumentEvent e){ transferTextToModel(); }
          public void removeUpdate(DocumentEvent e){ transferTextToModel(); }
          public void changedUpdate(DocumentEvent e){ transferTextToModel(); }
          private void transferTextToModel()
          {
            try
            {
              for( int i=0; i < modelPaths.length; i++ )
              {
                XML_Node modelFieldNode = XMLPath.GetNode( modelPaths[i],modelNode );
                modelFieldNode.setTagValue( jTextArea.getText() );
              }
              // Inform the validator:
              schemaValidator.fireContentHasChanged();
            }
            catch( Exception e )
            {
              e.printStackTrace();
            }
          }
        };
        jTextArea.getDocument().addDocumentListener( docListener );
        // pass required information to the schema validator:
        this.schemaValidator.addEntry( jTextArea,schemaValidationInfoLabel,modelValueNode );         
      }
      else
      {
        String msg = "*** The [first] reference given by modelPath= " + modelPaths[0] +
                     " is not valid. Such an element does not exist in the xml model node.";
        System.out.println(msg);
        value = "Invalid reference.";
        jTextArea.setBackground( new Color(255,100,100) );
      }       
    }
    if( columns > 0 ) jTextArea.setColumns( columns );
    if( value != null ) jTextArea.setText( DataUtilities.TranslateUnicodeShortcutsInLine(value) );
    // Add the jtextfield and the schema validation info label :
    int hGap = UIManager.getFont("Label.font").getSize();
    JPanel textInputPanel = new JPanel( new BorderLayout(hGap,0) );
    textInputPanel.setOpaque(false);
    textInputPanel.add(jTextArea, BorderLayout.CENTER );
    textInputPanel.add(schemaValidationInfoLabel, BorderLayout.EAST );
    this.addComponent(parentPanel,textInputPanel,addParameter,alignmentX_Parameter,alignmentY_Parameter);
    //System.out.println("Added JTextArea");
  }
  
  
  

  
  

  private void buildJPanelUI( final JPanel parentPanel,
                              final XML_Node jPanelNode ) throws Exception
  {
    //System.out.println("Adding JPanel...");
    String addParameter = jPanelNode.getAttributeValueForName(Attribute_AddParameter);
    String layoutClass  = jPanelNode.getAttributeValueForName(Attribute_LayoutClass);
    String layoutParameter = jPanelNode.getAttributeValueForName(Attribute_LayoutParameter);
    String useScrollPane = jPanelNode.getAttributeValueForName(Attribute_WrapByScrollPane);
    String backgroundParameter = jPanelNode.getAttributeValueForName(Attribute_Background);
    String borderTypeParameter = jPanelNode.getAttributeValueForName(Attribute_BorderType);
    String horizontalBorderSizeParameter = jPanelNode.getAttributeValueForName(Attribute_HorizontalBorderSize);
    String verticalBorderSizeParameter = jPanelNode.getAttributeValueForName(Attribute_VerticalBorderSize);
        
    String alignmentX_Parameter = jPanelNode.getAttributeValueForName(Attribute_AlignmentX);
    String alignmentY_Parameter = jPanelNode.getAttributeValueForName(Attribute_AlignmentY);
    
    // Create it:
    JPanel jPanel = new JPanel();
    this.setLayout(jPanel,layoutClass,layoutParameter);
    this.setGraphicalAttributes( jPanel,backgroundParameter,
    		                     borderTypeParameter,
								 horizontalBorderSizeParameter,verticalBorderSizeParameter );
    // Add it:
    if( (useScrollPane != null) && (useScrollPane.equals("true")) )
    {
      System.out.println("JPanel shall lie inside a scrollpane.");	
      this.addComponent( parentPanel,new JScrollPane(jPanel),addParameter,
      		             alignmentX_Parameter,alignmentY_Parameter );
    }
    else
    {
      this.addComponent(parentPanel,jPanel,addParameter,alignmentX_Parameter,alignmentY_Parameter);
    }
    //System.out.println("JPanel added.");
    // Process its children:
    this.buildUI(jPanel,jPanelNode);
  } // buildJPanelUI


  
  
  
  
 /**
  *  Try to parse an integer from the passed string.
  *  Return zero, if the parameter is null or does not contain a valid integer. 
  */ 
  private int getIntegerFromParameter( final String numberParameter )
  {
    int number = 0;
    if( numberParameter != null )
    {
      try
      {
        number = Integer.parseInt(numberParameter);
      }
      catch( Exception e )
      {
      	System.out.println("*** getIntegerFromParameter(): Unable to parser the passed string. Returning 0");
        //e.printStackTrace();
      }
    }
    return number;
  }

  
  
  
  
  private void addComponent( final JComponent parentComponent,
                             final JComponent childComponent,
                             final String addParameter,
							 final String alignmentX_Parameter,
							 final String alignmentY_Parameter )
  {  	
  	//System.out.println("> addComponent called with:");
  	//System.out.println("> addParameter= " + addParameter);
  	//System.out.println("> alignmentX_Parameter= " + alignmentX_Parameter);
  	//System.out.println("> alignmentY_Parameter= " + alignmentY_Parameter);
  	
  	// Process the alignment parameters first (used for boxlayouts f.ex)
  	if( alignmentX_Parameter != null )
  	{
      float floatAlignment = (float)this.getIntegerFromParameter(alignmentX_Parameter); 		
      childComponent.setAlignmentX( floatAlignment );	
  	}
  	if( alignmentY_Parameter != null )
  	{
      float floatAlignment = (float)this.getIntegerFromParameter(alignmentY_Parameter); 		
      childComponent.setAlignmentY( floatAlignment );	
  	}
  	
    if( addParameter == null )
    {
      parentComponent.add( childComponent );
      //System.out.println("addComponent: done without layout parameter");
    }
    else
    {
      // Search string based constraints first:
      String stringConstraint = null;
      if( addParameter.equals("BorderLayout.CENTER") )
      {
        stringConstraint = BorderLayout.CENTER;
      }
      else                    
      if( addParameter.equals("BorderLayout.EAST") )
      {
        stringConstraint = BorderLayout.EAST;
      }
      else                    
      if( addParameter.equals("BorderLayout.WEST") )
      {
        stringConstraint = BorderLayout.WEST;
      }
      else                    
      if( addParameter.equals("BorderLayout.SOUTH") )
      {
        stringConstraint = BorderLayout.SOUTH;
      }
      else                    
      if( addParameter.equals("BorderLayout.NORTH") )
      {
        stringConstraint = BorderLayout.NORTH;
      }
      // now set it, if a string constraint has been found:
      if( stringConstraint != null )
      {
        parentComponent.add( childComponent,stringConstraint );
        //System.out.println("addComponent: done with string layout parameter: " + stringConstraint );
      }
      else
      {
        // Search an int constraint:
        int intConstraint = -1;
        if( addParameter.equals("FlowLayout.LEFT") )
        {
          intConstraint = FlowLayout.LEFT;
        }
        else
        if( addParameter.equals("FlowLayout.RIGHT") )
        {
          intConstraint = FlowLayout.RIGHT;
        }
        else
        if( addParameter.equals("FlowLayout.CENTER") )
        {
          intConstraint = FlowLayout.CENTER;
        }
        // now set it, if an int constraint has been found:
        if( intConstraint >= 0 )
        {
          parentComponent.add( childComponent,intConstraint );
          //System.out.println("addComponent: done with int layout parameter: " + intConstraint );
        }
        else
        {
          System.out.println("***");	
          System.out.println("*** addComponent: constraint "+ addParameter +" not supported");
          System.out.println("***");	
          parentComponent.add( childComponent );
        }
      } // else
    }  
  }


  
  
  private void setLayout( final JPanel panel,
                          final String layoutClass,
                          final String layoutParameter )
  {
  	//System.out.println(">>setLayout starts...");
    if( layoutClass != null )
    {                                          
      if( layoutClass.equals("BorderLayout") )
      {
      	//System.out.println(">>Set BorderLayout");
        panel.setLayout( new BorderLayout() );
      }
      else
      if( layoutClass.equals("FlowLayout") )
      {
      	//System.out.println(">>Set FlowLayout");
        if( layoutParameter != null )
        {
          if( layoutParameter.equals("FlowLayout.LEFT") )
          {
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
          }
          else
          if( layoutParameter.equals("FlowLayout.RIGHT") )
          {
            panel.setLayout( new FlowLayout(FlowLayout.RIGHT) );
          }
          else
          if( layoutParameter.equals("FlowLayout.CENTER") )
          {
            panel.setLayout( new FlowLayout(FlowLayout.CENTER) );
          }
          else
          {
            System.out.println("Unsupported FlowLayout parameter: " + layoutParameter );
            panel.setLayout( new FlowLayout() );
          }
        }
        else
        {
          panel.setLayout( new FlowLayout() );
        }
      }
      else
      if( layoutClass.equals("BoxLayout") )
      {
      	//System.out.println(">>Set BoxLayout");
        int axis = 0; // x-axis
        if( (layoutParameter != null) &&
            (layoutParameter.equals("BoxLayout.Y_AXIS") ) )
        {
          axis = 1;
        }
        BoxLayout layout = new BoxLayout(panel,axis);
        panel.setLayout( layout );
      }
    }
  	//System.out.println(">>setLayout has ended.");
  }

  

  
  
  
  
  private void setGraphicalAttributes( JComponent component ,
  		                               final String backgroundParameter,
									   final String borderTypeParameter,
									   final String horizontalBorderSizeParameter,
									   final String verticalBorderSizeParameter)
  {
  	// backgroundParameter holds decimal commaseparated rgb values
  	if( backgroundParameter != null )
  	{      
      //System.out.println("backgroundParameter= " + backgroundParameter);
      
  	  StringTokenizer tok = new StringTokenizer(backgroundParameter,",");
      int numTokens = tok.countTokens();
  	  if( numTokens == 3 )
      {
      	try
		{
          String rs = tok.nextToken().trim(); //System.out.println("rs=$" + rs + "$");   		 
          String gs = tok.nextToken().trim(); //System.out.println("gs=$" + gs + "$");   		 
          String bs = tok.nextToken().trim(); //System.out.println("bs=$" + bs + "$");   		 
          int r = Integer.parseInt( rs );      		
          int g = Integer.parseInt( gs );      		
          int b = Integer.parseInt( bs );
          component.setBackground( new Color(r,g,b) );
		}
      	catch( Exception e )
		{
          System.out.println("*** GUIBuilder[1]: Invalid comma separated rgb data for background detected.");
		}
      } else
      {
      	System.out.println("*** GUIBuilder[2]: Invalid comma separated rgb data for background detected:" +
      			           numTokens + " number of tokens.");
      }
  	  
  	} // else this parameter has not been set
  	if( borderTypeParameter != null )
  	{
  	  if( borderTypeParameter.equals("EtchedBorder") )
  	  {
  	    component.setBorder( BorderFactory.createEtchedBorder() );	
  	  }
  	  else
      if( borderTypeParameter.equals("EmptyBorder") )
      {
       	int hsize = 0;
       	int vsize = 0;
        // Use the size parameters too, if they exists:
      	if( ( horizontalBorderSizeParameter != null ) && ( verticalBorderSizeParameter != null ) )
      	{
      	  try
		  {
      	  	hsize = Integer.parseInt(horizontalBorderSizeParameter);
      	  	vsize = Integer.parseInt(verticalBorderSizeParameter);
		  }
      	  catch( Exception e)
		  {
      	  	System.out.println("*** setGraphicalAttributes: Invalid border size parameters.");
		  }
      	}
        component.setBorder( BorderFactory.createEmptyBorder(vsize,hsize,vsize,hsize) );	
      }      
  	  else
      if( borderTypeParameter.equals("LoweredBevelBorder") )
      {
        component.setBorder( BorderFactory.createLoweredBevelBorder() );	
      }
      else
      if( borderTypeParameter.equals("RaisedBevelBorder") )
      {
        component.setBorder( BorderFactory.createRaisedBevelBorder() );	
      }
      else
      {
      	System.out.println("*** setGraphicalAttributes: Unknown border type: " + borderTypeParameter );
      }
  	}
  } // setGraphicalAttributes


  
  
  
  
  /**
   *  Called when the applet terminates.
   */
   public void stop()
   {
     this.schemaValidator.terminateThread();
   }


   
   
   
  
} // GUIBuilder




    
