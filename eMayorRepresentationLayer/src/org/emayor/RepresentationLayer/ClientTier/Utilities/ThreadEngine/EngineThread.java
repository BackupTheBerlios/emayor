package org.emayor.RepresentationLayer.ClientTier.Utilities.ThreadEngine;

/**
*
*  JPL Note to self: This is a simplified version.
*                    without GUI-Structure-Lock capability
*                    and without progressbar
*
*  This basis class is designed to be used
*  *exclusively* by the ThreadEngine
*  to wrap the passed Runnables for adding the
*  needed cooperation properties.
*
*  Do not use it for threads, which you want to
*  add to the ThreadEngine. Just use a Runnable
*  for that job, and pass it to the method
*  addRunnable() of the ThreadEngine.
*
*/


public class EngineThread extends Thread
{


  private ThreadEngine threadEngine;

  private String threadName;


  private int priority; // the priority under which this thread will run
                        // passed in constructor



  public EngineThread( final Runnable theTarget,
                       final String   theName,
                       final ThreadEngine theThreadEngine,
                       final int theRequiredPriority )
  {
   super(theTarget,theName);
   this.threadEngine = theThreadEngine;
   this.threadName   = theName;
   this.priority     = theRequiredPriority;
   this.setName( theName );
   this.setDaemon( true );
  } // constructor




  public void run()
  {
   // Note : The priority of the engineThreads HAS to be lower, than
   //        the one from the ThreadEngine - otherwise it will not
   //        work correctly.
   super.setPriority( this.priority );
   
   // this try catch block is to prevent AMADEUS from hanging threads in the
   // thread queue, which will occure because without this try catch-clause
   // the termination of the EngineThread will
   try
     {
         super.run();
     }
   catch (Exception e)
     {
         e.printStackTrace();
     }
    finally
     {
       // before the run method ends, this Thread has to call
       // the ThreadEngine, so the mechanism keeps going...
       threadEngine.processNextThread(this);
     }
  }


  public String getThreadName()
  {
    return this.threadName;
  }




}
