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
package com.paic.jrkj.tk.samples.tools.io;

import java.io.File;

import com.paic.jrkj.tk.tools.io.PropertyLoader;
import com.paic.jrkj.tk.tools.io.PropertyLoaderCreator;
import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.tools.core.BeanHolder;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-11 17:39:24 $
 * @serial 1.0
 * @since 2015-3-11 17:39:24
 */
public class PropertyLoaderSample {

    public static void main(String args[])
            throws Exception {
        for (int i = 0; i < 2; i++) {
            final int idx = i;
            WorkManager.getWorkManager().startWork(new Runnable() {
                public void run() {
                    PropertyLoader loader = BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean("classpath://lock.properties", new PropertyLoaderCreator("classpath://lock.properties",3));
                    while (true) {
                        System.out.println("classpath_"+idx+"="+loader.getString("rsa.maxLockSize"));
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        for (int i = 2; i < 4; i++) {
            final int idx = i;
            WorkManager.getWorkManager().startWork(new Runnable() {
                public void run() {
                    PropertyLoader loader = BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean("file://D:/svn/toolkit/branches/toolkit/toolkit_dev_1.3.0/config/lock.properties", new PropertyLoaderCreator("file://D:/svn/toolkit/branches/toolkit/toolkit_dev_1.3.0/config/lock.properties",3));
                    while (true) {
                        System.out.println("file_"+idx+"="+loader.getString("rsa.maxLockSize"));
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
