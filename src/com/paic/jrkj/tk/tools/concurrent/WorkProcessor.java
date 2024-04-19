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

import com.paic.jrkj.tk.tools.work.WorkManager;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-3-2 10:29:13 $
 * @serial 1.0
 * @since 2016-3-2 10:29:13
 */
public class WorkProcessor {

    private final Log logger = LogFactory.getLog(WorkProcessor.class);
    private List<Worker> workerList = new Vector<Worker>();
    private String workManagerName = "default";
    private long timeout;
    private final String taskId;

    public WorkProcessor(String taskId, long timeout) {
        this.taskId = taskId;
        this.timeout = timeout;
    }

    public WorkProcessor(String taskId) {
        this(taskId, 0);
    }

    public void setWorkManagerName(String workManagerName) {
        this.workManagerName = workManagerName;
    }

    public void addWorker(Worker worker) {
        worker.setTaskId(taskId);
        this.workerList.add(worker);
    }

    public List<WorkResult> execute() {
        long t = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(workerList.size());
        for (Worker worker : workerList) {
            worker.setCountDownLatch(countDownLatch);
            WorkManager.getWorkManager(workManagerName).startWork(worker);
        }
        try {
            if (timeout > 0) {
                countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            } else {
                countDownLatch.await();
            }
        } catch (InterruptedException e) {
            logger.fatal("", e);
        }
        List<WorkResult> resultList = new Vector<WorkResult>();
        for (Worker worker : workerList) {
            resultList.add(worker.getResult());
        }
        logger.info("WorkProcessor#execute[" + taskId + "] use[" + (System.currentTimeMillis() - t) + "] ms!");
        return resultList;
    }

}