package org.emayor.client.Utilities.ThreadEngine;


/**
*
*  JPL Note to self: This is a simplified version
*                    without GUI-Structure-Lock capability
*                    and without progressbar
*
*   This class represents a thread which processes Runnable entries
*   sequentially (one after the other).
*
*   It is designed as Singleton - you create or get its instance
*   by getThreadEngine().
*
*   It works with threads of type EngineThread, which have inbuilt
*   mechanisms that provide cooperation with this class.
*
*/                 
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;
import java.beans.PropertyVetoException;


public class ThreadEngine
{

  // The static Singleton Instance
  private static ThreadEngine threadEngine;

  // dynamically created member attributes follow here :

  // Holds the threads, which are to be processed completely
  // in sequence :
  private Vector sequentialThreadVector = new Vector(0,1);

  // run flag :
  private boolean stayAlive = true;


  // listeners :
  private Vector listeners = new Vector(0,1);


  // default priority : one less than swing to have swing alive
  // while the thread runs :
  private final int defaultPriority =  Thread.NORM_PRIORITY-1;


  // identification number to get a unique name for each incoming thread
  private int identificationNumber = 0;
  private Vector threadsToKill = new Vector(0,1);


  // The private singleton constructor :
  private ThreadEngine()
  {
  } // constructor



  public void processNextThread( EngineThread theJustTerminatedThread )
  {
    processThreadQueue(theJustTerminatedThread,null);
  } // processThreadList




 /**
  *   Use this method to add a runnable to the ThreadEngine.
  *   It will be started as soon as possible.
  *
  */
  public void addRunnable( final Runnable theRunnable,
                           final String   theRunnableDescription )
  {
    this.identificationNumber++;
    String uniqueDescription = theRunnableDescription + " (" + this.identificationNumber + ")";
    final EngineThread newThread = new EngineThread( theRunnable,
                                                     uniqueDescription,
                                                     this,
                                                     this.defaultPriority );
    processThreadQueue( null,newThread );
  } // addRunnable



 /**
  *  This method is used in the GateWay mainly.
  *  The GateWay passes the additional attribute thePriority
  *  as Thread.MAX_PRIORITY , because it cannot allow the threads
  *  to be interrupted, as they perform sql operations etc.
  */
  public void addRunnable( Runnable theRunnable,
                           String   theRunnableDescription,
                           int      theRequiredPriority )
  {
    this.identificationNumber++;
    String uniqueDescription = theRunnableDescription + " (" + this.identificationNumber + ")";
    EngineThread newThread = new EngineThread( theRunnable,
                                               uniqueDescription,
                                               this,
                                               theRequiredPriority );
    processThreadQueue( null,newThread );
  } // addRunnable






 /**
  *   The method is synchronized, because we have a TEST-AND-SET
  *   situation, which REQUIRES not-interrupted execution.
  */
  private void processThreadQueue( EngineThread threadToRemove,
                                   EngineThread threadToAdd )
  {
    synchronized(sequentialThreadVector)
     {
      // remove this one, if it exists :
      if( threadToRemove != null )
       {

        //System.out.println("ThreadEngine: processThreadQueue: removing " +
        //                   threadToRemove.getThreadName() );

        sequentialThreadVector.remove(threadToRemove);
        // and start the next one, if there is one and if
        // this engine shouldnt stop :
        if( ( sequentialThreadVector.size() > 0 ) &&
            ( this.stayAlive ) )
         {
           EngineThread nextThread = (EngineThread)sequentialThreadVector.elementAt(0);
           nextThread.start();
         }
       }
      if( threadToAdd != null )
       {

        //System.out.println("ThreadEngine: processThreadQueue: adding " +
        //                   threadToAdd.getThreadName() );

        sequentialThreadVector.addElement(threadToAdd);
        // and start it, if it was the first thread to be entered into the queue:
        if( sequentialThreadVector.size() == 1 )
         {
           threadToAdd.start();
         }
       }
     }
  } // processThreadList





 /**
  *  Lets this thread terminate by setting the loop flag
  *  in the run method to false.
  *  Also kills any thread
  */
  public void terminate()
  {
    threadEngine = null;
  }



 /**
  *  Kills a *waiting* thread in the vector.
  */
  public boolean killThread( String nameOfTheThreadToBeKilled )
  {
    boolean succeeded = false;
    synchronized(sequentialThreadVector)
     {
      // search the thread :
      for( int i=0; i < sequentialThreadVector.size(); i++ )
       {
         EngineThread thisThread = (EngineThread)sequentialThreadVector.elementAt(i);
         if( thisThread.getThreadName().equals(nameOfTheThreadToBeKilled) )
          {
            if( !thisThread.isAlive() ) // kill only waiting threads
             {
              thisThread.interrupt(); // in any case, independant wether its running or not
              sequentialThreadVector.removeElementAt(i);
              //if the thread would have been in the list threadsToKill, remove it
              threadsToKill.removeElement(nameOfTheThreadToBeKilled);
              succeeded = true;
              break;
             }
             else
             {// otherwise add to the list of threadsToKill and hope the
              // the thread will check the flag 'shouldAbort' and terminate
              // itself as soon as possible.
               threadsToKill.add(nameOfTheThreadToBeKilled);
             }
          }
       }
     }
    return succeeded;
  } // killThread




  /** @return the actual thread names in the queue, included the executed one
  */
  public String[] getActualThreadNames()
  {
    String[] namesOfThreadsInQueue;
    synchronized(sequentialThreadVector)
    {
       int numberOfThreadsInQueue = sequentialThreadVector.size();
       namesOfThreadsInQueue = new String[numberOfThreadsInQueue];
       for( int i=0; i < numberOfThreadsInQueue; i++ )
       {
          EngineThread thisThread = (EngineThread)sequentialThreadVector.elementAt(i);
          namesOfThreadsInQueue[i] = thisThread.getThreadName();
       }
    }
    return namesOfThreadsInQueue;
  }



  public Thread getThreadWithName( String threadName )
  {
    Thread theThread = null;
    String[] namesOfThreadsInQueue;
    synchronized(sequentialThreadVector)
    {
       int numberOfThreadsInQueue = sequentialThreadVector.size();
       namesOfThreadsInQueue = new String[numberOfThreadsInQueue];
       for( int i=0; i < numberOfThreadsInQueue; i++ )
       {
          EngineThread thisThread = (EngineThread)sequentialThreadVector.elementAt(i);
          // lazy way: uniqueDescription has a number at the end,
          // so just compare the start :
          if( thisThread.getThreadName().startsWith(threadName) )
           {
             theThread = thisThread;
           }
       }
    }
    return theThread;
  }





 public boolean shouldThreadTerminate(String threadName)
 {
   for (int i=0; i< threadsToKill.size(); i++ )
   {
     if ( threadsToKill.elementAt(i).equals(threadName) )
     {
       threadsToKill.removeElement(threadName);
       return true;
     }
   }
   return false;
 }



 public boolean shouldCurrentlyRunningThreadTerminate()
 {
   String currentTaskName = new String(Thread.currentThread().getName());
   if ( shouldThreadTerminate(currentTaskName) )
    return true;
   else
    return false;
 }





 /**
  *  Terminate and kills the singlet instance
  */
  public static synchronized void TerminateInstance()
  {
   if( threadEngine != null )
    {
      threadEngine.terminate();
    }
  } // ThreadEngine




 /**
  *  Returns the instance of this singleton object.
  *  Automatically creates the object, case it doesnt exist yet
  *  on this call.
  */
  public static synchronized ThreadEngine getInstance()
  {
   if( threadEngine == null )
    {
      threadEngine = new ThreadEngine();
    }
   return threadEngine;
  } // ThreadEngine


}
