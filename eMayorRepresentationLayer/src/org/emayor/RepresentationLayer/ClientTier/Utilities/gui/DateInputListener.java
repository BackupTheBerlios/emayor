package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

  /**
   *  Used for designing an observer/observable pattern
   *  between the EditableDateTypeInputComponent and the
   *  CalendarPopupFrame.  ( .. listeners )
   */

public interface DateInputListener
{

   public void setDay( int dayValue );
   public void setMonth( int monthValue );
   public void setYear( int yearValue );

}
