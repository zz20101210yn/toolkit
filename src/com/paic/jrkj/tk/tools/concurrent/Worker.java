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
package com.paic.jrkj.tk.tools.concurrent;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.concurrent.CountDownLatch;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-3-2 10:28:54 $
 * @serial 1.0
 * @since 2016-3-2 10:28:54
 */
public abstract class Worker implements Runnable {

    protected final Log logger = LogFactory.getLog(getClass());
    private volatile WorkResult result;
    private volatile CountDownLatch countDownLatch;
    private String taskId;
    private final String workId;

    protected Worker(String workId) {
        this.workId = workId;
    }

    public String getWorkerId() {
        return workId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public WorkResult getResult() {
        return result;
    }

    protected abstract WorkResult execute();

    public final void run() {
        long t = System.currentTimeMillis();
        try {
            this.result = execute();
            if(this.result!=null){
                this.result.setWorkId(workId);
            }
        } finally {
            countDownLatch.countDown();
            logger.debug(toString() + " eclipse:" + (System.currentTimeMillis() - t) + " ms!");
        }
    }

    public String toString() {
        return getClass().getName() + "[" + getWorkerId() + "@" + getTaskId() + "]";
    }

}