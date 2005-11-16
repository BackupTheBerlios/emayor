package org.emayor.client.Utilities;

import java.awt.*;
import javax.swing.*;


public class TextFrame extends JFrame
{


  public TextFrame( String frameTitle,
                    String frameText )
  {
    super(frameTitle);
    this.setSize(800,200);
    this.setLocation(100,100);
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTextArea textArea = new JTextArea(frameText);
    
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
 
    this.setVisible(true);
  } // Constructor
  
  

} // TextFrame
