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
package com.paic.jrkj.tk.samples.tools;

import java.util.Date;
import java.util.Calendar;

import com.paic.jrkj.tk.tools.core.ObjectCreator;
import com.paic.jrkj.tk.tools.core.BeanHolder;
import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.util.RandomUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-2-26 9:59:50 $
 * @serial 1.0
 * @since 2015-2-26 9:59:50
 */
public class ObjectCreatorSample implements ObjectCreator<Calendar> {

    private static final Log logger = LogFactory.getLog(ObjectCreatorSample.class);

    private boolean created;

    public ObjectCreatorSample(boolean created) {
        this.created = created;
    }

    public Calendar create() {
        if(!created) return null;
        return Calendar.getInstance();
    }

    public static void main(String[] args)
            throws InterruptedException {
        System.out.println("zzzz"+BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean("t", new ObjectCreatorSample(false)));
        for (int i = 0; i < 3; i++) {
            final int k = i;
            WorkManager.getWorkManager("default").startWork(new Runnable() {
                public void run() {
                    try{
                        Thread.sleep(RandomUtil.getRandom(1000));
                    } catch(InterruptedException e){}
                    for (int i = 0; i < 2; i++) {
                        logger.info("singleton["+k+"-"+i+"]"+BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean("t", new ObjectCreatorSample(true)).getTimeInMillis());
                        logger.info("thread["+k+"-"+i+"]"+BeanHolder.use(BeanHolder.SCOPE_THREAD).getBean("t", new ObjectCreatorSample(true)).getTimeInMillis());
                    }
                    System.out.println("bbbbb"+BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean("t", new ObjectCreatorSample(false)));
                }
            });
        }
    }
}
