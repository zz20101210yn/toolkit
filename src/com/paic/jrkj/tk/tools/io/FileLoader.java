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
package com.paic.jrkj.tk.tools.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-11 16:41:14 $
 * @serial 1.0
 * @since 2015-3-11 16:41:14
 * @deprecated
 */
public abstract class FileLoader {

    protected final Log logger = LogFactory.getLog(getClass());
    private final String path;
    private long checkInterval;
    private long lstCheckTime;

    protected FileLoader(String path, int checkInterval)
            throws IOException {
        this.path = path;
        this.checkInterval = checkInterval * 1000;
    }

    protected final synchronized void checkUpdate() {
        long now = System.currentTimeMillis();
        if (now - lstCheckTime < checkInterval) {
            return;
        }
        try {
            init();
            lstCheckTime = now;
        } catch (IOException e) {
            logger.fatal("load [" + path + "] fail!", e);
        }
    }

    protected abstract void init()
            throws IOException;


    protected InputStream getInputStream()
            throws IOException {
        if (path.startsWith("classpath://")) {
            String file = path.substring(11);
            InputStream is = FileLoader.class.getResourceAsStream(file);
            if (is == null) {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            }
            return is;
        } else if (path.startsWith("file://")) {
            return new FileInputStream(new File(path.substring(7)));
        } else {
            URL url = new URL(path);
            return url.openStream();
        }
    }
}
