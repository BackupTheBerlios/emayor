/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utclient;

import javax.ejb.RemoveException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocal;
import org.emayor.servicehandling.kernel.ServiceException;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.Tasks;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class UserTaskServiceClient extends UserTaskAbstractClient {
    private static Logger log = Logger.getLogger(UserTaskServiceClient.class);

    public UserTaskServiceClient() {
        super();
    }

    public Task[] getMyTasks(String asid) throws UserTaskException {
        log.debug("-> start processing ...");
        Task[] ret = new Task[0];
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            UserTaskManagerLocal utm = serviceLocator.getUserTaskManagerLocal();

            Tasks _tasks = utm.getMyTasks(asid);
            ret = _tasks.getTasks();
            if (log.isDebugEnabled())
                log.debug("got tasks from service - number = " + ret.length);
            utm.remove();
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new UserTaskException("Couldn't get ref to service locator");
        } catch (ServiceException sex) {
            log.error("caught ex: " + sex.toString());
            throw new UserTaskException("problem with user task service");
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            throw new UserTaskException("problem with user task service");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public void completeTask(String asid, Task task) throws UserTaskException {
        log.debug("-> start processing ...");
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            UserTaskManagerLocal utm = serviceLocator.getUserTaskManagerLocal();
            log.debug("try to comlpete task");
            utm.completeTask(asid, task);
            utm.remove();
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new UserTaskException("Couldn't get ref to service locator");
        } catch (ServiceException sex) {
            log.error("caught ex: " + sex.toString());
            throw new UserTaskException("problem with user task service");
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            throw new UserTaskException("problem with user task service");
        }
        log.debug("-> ... processing DONE!");
    }

    public Task lookupTask(String asid, String ssid) throws UserTaskException {
        log.debug("-> start processing ...");
        Task ret = new Task();
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            UserTaskManagerLocal utm = serviceLocator.getUserTaskManagerLocal();
            log.debug("try to lookup the required task!");
            ret = utm.lookupTaskByServiceSession(asid, ssid);
            if (ret == null) {
                log.debug("Couldn't find any tasks !!!");
            }
            utm.remove();
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new UserTaskException("Couldn't get ref to service locator");
        } catch (ServiceException sex) {
            log.error("caught ex: " + sex.toString());
            throw new UserTaskException("problem with user task service");
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            throw new UserTaskException("problem with user task service");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}