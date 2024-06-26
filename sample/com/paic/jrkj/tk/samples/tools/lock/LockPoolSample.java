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
package com.paic.jrkj.tk.samples.tools.lock;

import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.tools.lock.LockPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-4-10 16:37:45 $
 * @serial 1.0
 * @since 2015-4-10 16:37:45
 */
public class LockPoolSample {

    private static final Log logger = LogFactory.getLog(LockPoolSample.class);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int idx = i + 1;
            WorkManager.getWorkManager().startWork(new Runnable() {
                public void run() {
                    while (true) {
                        LockPool lockpool = LockPool.getDynamicLockPool("demolock", 5);
                        boolean isLocked = false;
                        try {
                            isLocked = lockpool.fetchLock(5000);
                            if(!isLocked){
                                logger.info("thread[" + idx + "] timeout");
                                break;
                            }
                            logger.info("thread[" + idx + "] runing...");
                            Thread.sleep(10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        } finally {
                            if(isLocked){
                                lockpool.releaseLock();
                            }
                        }
                    }
                }
            });
        }
    }
}
