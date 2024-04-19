package com.paic.jrkj.tk.tools.lock;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.util.FileUtil;

public class FileLock{

    private String path;
    private File lockFile;
    private final Log logger = LogFactory.getLog(FileLock.class);
    private boolean lockSelf;
    private boolean waitIfBusy = true;

    public FileLock(){}

    public FileLock(String path) {
        setPath(path);
    }

    public void setWaitIfBusy(boolean waitIfBusy) {
        this.waitIfBusy = waitIfBusy;
    }

    public void setPath(String path) {
        this.path = path;
        this.lockFile = new File(this.path + ".lock");
        String parent = this.lockFile.getParent();
        logger.debug("lock parent=["+parent+"]");
        if (parent != null) {
            FileUtil.makeDirs(parent);
        }
    }

    public synchronized boolean isLocked() {
        return lockFile.exists();
    }

    public boolean fetchLock()
            throws Exception {
       return fetchLock(this.waitIfBusy);
    }

    public synchronized boolean fetchLock(boolean waitIfBusy) {
        if (lockSelf) {
            throw new IllegalStateException("lock is fetched by own process!");
        }
        if (lockFile.exists()) {
            if (waitIfBusy) {
                try {
                    Thread.sleep(10);
                    return fetchLock(waitIfBusy);
                } catch (InterruptedException e) {
                    logger.fatal("", e);
                }
            }
            return false;
        }
        try {
            lockSelf = lockFile.createNewFile();
            if (lockSelf) {
                logger.info("lock [" + this.path + "] locked!");
            } else if (waitIfBusy) {
                try {
                    Thread.sleep(10);
                    return fetchLock(waitIfBusy);
                } catch (InterruptedException e) {
                    logger.fatal("", e);
                }
            }
            return lockSelf;
        } catch (IOException e) {
            logger.fatal("", e);
        }
        return false;
    }

    public synchronized void releaseLock() {
        if (lockSelf) {
            lockSelf = false;
            logger.info("lock [" + this.path + "] released!");
            if (lockFile.exists()) {
                lockFile.delete();
            }
        }
    }

}
