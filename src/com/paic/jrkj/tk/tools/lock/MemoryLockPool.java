/**
 * Copyright 2011-2012 www.wanlitong.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paic.jrkj.tk.tools.lock;

import com.paic.jrkj.tk.tools.core.SystemProperty;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-2 14:47:34 $
 * @serial 1.0
 * @since 2015-3-2 14:47:34
 */
class MemoryLockPool extends LockPool {

    private String name;
    private final Object releaseLock = new Object();
    private SystemProperty prop;
    private volatile int current = 0;
    private volatile int maxLockSize;

    public MemoryLockPool(String name) {
        this.name = name;
    }

    protected void setMaxLockSize(int maxLockSize) {
        this.maxLockSize = maxLockSize;
    }

    protected void setProperty(SystemProperty prop) {
        this.prop = prop;
    }

    public boolean fetchLock(long timeout)
            throws Exception {
        if (prop != null) {
            maxLockSize = prop.getInteger(name + ".maxLockSize", 10, 100);
        }
        if (Integer.MAX_VALUE == maxLockSize) {
            return true;
        }
        synchronized (this) {
            int step = 10;
            boolean hint = false;
            long count = timeout / step;
            for (int i = 0; i < count; i++) {
                if (this.current >= this.maxLockSize) {
                    if (!hint) {
                        logger.warn("lock[" + name + "] is busy!");
                        hint = true;
                    }
                    Thread.sleep(step);
                } else {
                    this.current++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fetchLock(boolean waitIfBusy)
            throws Exception {
        if (prop != null) {
            maxLockSize = prop.getInteger(name + ".maxLockSize", 10, 100);
        }
        if (Integer.MAX_VALUE == maxLockSize) {
            return true;
        }
        synchronized (this) {
            boolean hint = false;
            while (this.current >= this.maxLockSize) {
                if (!hint) {
                    logger.warn("lock[" + name + "] is busy!");
                    hint = true;
                }
                if (waitIfBusy) {
                    Thread.sleep(10);
                } else {
                    return false;
                }
            }
            this.current++;
        }
        return true;
    }

    public void fetchLock()
            throws Exception {
        fetchLock(true);
    }

    public void releaseLock() {
        if (prop != null) {
            maxLockSize = prop.getInteger(name + ".maxLockSize", 10, 100);
        }
        if (Integer.MAX_VALUE == maxLockSize) {
            return;
        }
        synchronized (releaseLock) {
            if (this.current > 0) {
                this.current--;
            }
        }
    }

    public String toString() {
        return "MemoryLockPool[" + name + "]";
    }
}
