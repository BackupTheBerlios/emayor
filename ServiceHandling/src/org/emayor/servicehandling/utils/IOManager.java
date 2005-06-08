/*
 * Created on Jun 9, 2005
 */
package org.emayor.servicehandling.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class IOManager {
    private static final Logger log = Logger.getLogger(IOManager.class);

    private static IOManager _self = null;

    private String JBOSS_HOME_DIR = "";

    /**
     *  
     */
    private IOManager() throws IOManagerException {
        log.debug("-> start processing ...");
        this.JBOSS_HOME_DIR = System.getProperty("jboss.server.home.dir");
        if (log.isDebugEnabled())
            log.debug("JBOSS_HOME_DIR = " + this.JBOSS_HOME_DIR);
        if (this.JBOSS_HOME_DIR == null || this.JBOSS_HOME_DIR.length() == 0) {
            log.error("Undefined JBOSS_HOME_DIR variable!");
            throw new IOManagerException("The jboss home directory is not set!");
        }
        log.debug("-> ... processing DONE!");
    }

    public synchronized static IOManager getInstance()
            throws IOManagerException {
        log.debug("-> start processing ...");
        if (_self == null)
            _self = new IOManager();
        return _self;
    }

    public synchronized String readTextFile(String relPath, String fileName)
            throws IOManagerException {
        log.debug("-> start processing ...");
        StringBuffer b = new StringBuffer();
        String path = this.buildPath(relPath, fileName);
        if (log.isDebugEnabled())
            log.debug("Try to read content from the file: " + path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    new File(path)));
            String line = null;
            while ((line = br.readLine()) != null) {
                b.append(line.trim());
            }
            br.close();
        } catch (IOException ex) {
            log.error("caught ex: " + ex.toString());
            throw new IOManagerException("Couldn't read the file: " + path);
        }
        log.debug("-> ... processing DONE!");
        return b.toString();
    }

    public synchronized void writeTextFile(String relPath, String fileName,
            String content) throws IOManagerException {
        log.debug("-> start processing ...");
        if (content == null) {
            log.error("the content of the text file is undefined");
            throw new IOManagerException(
                    "The content of the text file to be written is undefined!");
        }
        String path = this.buildPath(relPath, fileName);
        if (log.isDebugEnabled())
            log.debug("Try to write content to the file: " + path);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(path)));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            log.error("caught ex: " + ex.toString());
            throw new IOManagerException("Couldn't write into file: " + path);
        }
        log.debug("-> ... processing DONE!");
    }

    public synchronized byte[] readBinaryFile(String relPath, String fileName)
            throws IOManagerException {
        log.debug("-> start processing ...");
        byte[] ret = new byte[0];
        String path = this.buildPath(relPath, fileName);
        if (log.isDebugEnabled())
            log.debug("Try to read binary content from the file: " + path);
        try {
            File file = new File(path);
            long l = file.length();
            ret = new byte[(int) l];
            FileInputStream fis = new FileInputStream(file);
            int i = fis.read(ret);
            fis.close();
            if (log.isDebugEnabled()) {
                log.debug("the length of the file is " + l);
                log.debug("read bytes from the file  " + i);
            }
        } catch (IOException ex) {
            log.error("caught ex: " + ex.toString());
            throw new IOManagerException("Couldn't read from file: " + path);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized void writeBinaryFile(String relPath, String fileName,
            byte[] content) throws IOManagerException {
        log.debug("-> start processing ...");
        if (content == null) {
            log.error("the content of the binary file is undefined");
            throw new IOManagerException(
                    "The content of the binary file to be written is undefined!");
        }
        String path = this.buildPath(relPath, fileName);
        if (log.isDebugEnabled())
            log.debug("Try to write content to the file: " + path);
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(content);
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            log.error("caught ex: " + ex.toString());
            throw new IOManagerException("Couldn't write into file: " + path);
        }
        log.debug("-> ... processing DONE!");
    }

    public synchronized void deleteFile(String relPath, String fileName)
            throws IOManagerException {
        log.debug("-> start processing ...");
        String path = this.buildPath(relPath, fileName);
        if (log.isDebugEnabled())
            log.debug("Try to read binary content from the file: " + path);
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                log.debug("the file has been successful deleted!");
            }
            else {
                log.error("Couldn't delete following file: " + path);
            }
        }
        log.debug("-> ... processing DONE!");
    }

    public synchronized void createDirs(String relPath, String dirs)
            throws IOManagerException {
        log.debug("-> start processing ...");
        String path = this.buildPath(relPath, dirs);
        if (log.isDebugEnabled())
            log.debug("work with following relPath: " + relPath);
        if (log.isDebugEnabled())
            log.debug("built this dir path: " + path);
        File file = new File(path);
        if (!file.exists())
            if (!file.mkdirs()) {
                log.error("dirs creation failed!");
            }
        log.debug("-> ... processing DONE!");
    }

    private String buildPath(String relPath, String fileName)
            throws IOManagerException {
        log.debug("-> start processing ...");
        if (fileName == null || fileName.length() == 0) {
            log.error("BAD file name: " + fileName);
            throw new IOManagerException("Bad file name: " + fileName);
        }
        String rp = relPath;
        if (relPath == null || relPath.length() == 0)
            rp = ".";
        StringBuffer b = new StringBuffer(this.JBOSS_HOME_DIR);
        b.append(File.separator).append(rp).append(File.separator);
        b.append(fileName);
        log.debug("-> ... processing DONE!");
        return b.toString();
    }

    private String buildPath(String relPath) throws IOManagerException {
        log.debug("-> start processing ...");
        String rp = relPath;
        if (relPath == null || relPath.length() == 0)
            rp = ".";
        StringBuffer b = new StringBuffer(this.JBOSS_HOME_DIR);
        b.append(File.separator).append(rp);
        log.debug("-> ... processing DONE!");
        return b.toString();
    }
}