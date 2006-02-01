package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

import java.awt.*;
import javax.swing.border.*;
import javax.swing.UIManager;



/**
 *  J.Plaz UoZurich
 *  Like MatteBorder, but with two lines. ( dark and bright usually )
 *  The effect from the two lines is like an etched border, but one
 *  can specify :
 *  on_top,on_left,on_bottom and on_right define if it should be painted at these locations.
 */

 public class CustomEtchedBorder extends EmptyBorder
{

    protected Color lightColor;
    protected Color darkColor;

    private boolean on_top;
    private boolean on_left;
    private boolean on_bottom;
    private boolean on_right;
     
    
    boolean doUpdateColors = true; // on UI changes
       
       
   /**
    *  Version with passed constant colors.
    */
    public CustomEtchedBorder( boolean on_top, boolean on_left, boolean on_bottom, boolean on_right,
                               final Color lightColor,
                               final Color darkColor )
    {
        super( on_top    ? 2 : 0,
               on_left   ? 2 : 0,
               on_bottom ? 2 : 0,
               on_right  ? 2 : 0  );
        this.lightColor = lightColor;
        this.darkColor  = darkColor;
        this.doUpdateColors = false; // dont change these colors on UI changes
        this.on_top    = on_top;
        this.on_left   = on_left;
        this.on_bottom = on_bottom;
        this.on_right  = on_right;
    }

   
          
          
   /**
    *  Version with automatically calculated colors, which then follow UI changes.
    */
    public CustomEtchedBorder( boolean on_top, boolean on_left, boolean on_bottom, boolean on_right )
    {
        super( on_top    ? 2 : 0,
               on_left   ? 2 : 0,
               on_bottom ? 2 : 0,
               on_right  ? 2 : 0  );
        this.lightColor = UIManager.getColor("Panel.background").brighter();
        this.darkColor  = UIManager.getColor("Panel.background").darker();
        this.doUpdateColors = true; // follow on UI changes
        this.on_top    = on_top;
        this.on_left   = on_left;
        this.on_bottom = on_bottom;      
        this.on_right  = on_right;
    }



    /**
     * Paints the matte border.
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {           
      if( this.doUpdateColors )
       {
         this.lightColor = UIManager.getColor("Panel.background").brighter();
         this.darkColor  = UIManager.getColor("Panel.background").darker();              
       }

      Insets insets = getBorderInsets(c);
      Color oldColor = g.getColor();
      g.translate(x, y);

      g.setColor( this.darkColor );

      if( this.on_top    ) g.drawLine( 0,0,width-1,0  );
      if( this.on_left   ) g.drawLine( 0,0,0,height-1 );
      if( this.on_bottom ) g.drawLine( 1,height-2,width-2,height-2 );
      if( this.on_right  ) g.drawLine( width-2,1,width-2,height-2 );

      g.setColor( this.lightColor );

      if( this.on_bottom ) g.drawLine( 1,height-1,width-1,height-1 );
      if( this.on_right  ) g.drawLine( width-1,1,width-1,height-1 );
      if( this.on_top    ) g.drawLine( 1,1,width-2,1  );
      if( this.on_left   ) g.drawLine( 1,1,1,height-2 );

      g.translate(-x, -y);
      g.setColor(oldColor);
    }

    /**
     * Returns the insets of the border.
     * @param c the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c) {
        return getBorderInsets();
    }

    /** 
     * Reinitialize the insets parameter with this Border's current Insets. 
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        return computeInsets(insets);
    }

    /**
     * Returns the insets of the border.
     */
    public Insets getBorderInsets() {
        return computeInsets(new Insets(0,0,0,0));
    }


    protected Insets computeInsets(Insets insets)
    {
      insets.left = left;
      insets.top = top;
      insets.right = right;
      insets.bottom = bottom;
      return insets;
    }


    /**
     * Returns whether or not the border is opaque.
     */
    public boolean isBorderOpaque()
    { 
        return true;
    }

}

