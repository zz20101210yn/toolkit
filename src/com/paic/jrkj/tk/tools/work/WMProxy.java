/**
 * Copyright@2016 www.wanlitong.com
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
package com.paic.jrkj.tk.tools.work;

import com.paic.jrkj.tk.tools.lock.LockPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-6-6 10:18:47 $
 * @serial 1.0
 * @since 2016-6-6 10:18:47
 */
final class WMProxy extends WorkManager {

    private final WorkManager wm;
    private final String lockName;
    private final Log logger = LogFactory.getLog(WMProxy.class);

    public WMProxy(WorkManager wm, String lockName) {
        this.wm = wm;
        this.lockName = lockName;
    }


    public void startWork(Runnable t) {
        final LockPool lockPool = LockPool.getDynamicLockPool(lockName, 300, false);   //5min
        if (lockPool == null) {
            wm.startWork(t);
            return;
        }
        try {
            lockPool.fetchLock(true);
            wm.startWork(t, new DefaultListener() {
                public void callFinally() {
                    lockPool.releaseLock();
                }
            });
        } catch (Exception e) {
            logger.fatal("", e);
        }
    }
}
