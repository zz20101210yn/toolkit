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
package com.paic.jrkj.tk.tools.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.net.URL;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-5-25 13:59:39 $
 * @serial 1.0
 * @since 2016-5-25 13:59:39
 */
public class StreamReader {

    private final Log logger = LogFactory.getLog(Properties.class);
    private final String path;
    private final long updateInterval;
    private volatile boolean isFile;
    private volatile long lastModified;

    public StreamReader(String path, long updateInterval) {
        this.path = path;
        this.updateInterval = updateInterval;
    }

    public synchronized void checkUpdate(UpdateProxy proxy) {
        if (this.updateInterval <= 0) {
            return;
        }
        if (isFile) {
            String file = this.path.substring(7);
            File f = new File(file);
            long newLastModified = f.lastModified();
            if (this.lastModified != newLastModified) {
                if(logger.isDebugEnabled()){
                    logger.debug("reload ["+path+"]");
                }
                proxy.update();
            }
        } else {
            if(System.currentTimeMillis() - this.lastModified >= this.updateInterval){
                logger.debug("reload ["+path+"]");
                proxy.update();
            }
        }
    }

    public InputStream getInputStream()
            throws IOException {
        String path = this.path.toLowerCase();
        if (path.startsWith("classpath://")) {
            String file = this.path.substring(11);
            InputStream is = StreamReader.class.getResourceAsStream(file);
            if (is == null) {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            }
            if (is == null) {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
            }
            this.lastModified = System.currentTimeMillis();
            return is;
        } else if (path.startsWith("file://")) {
            this.isFile = true;
            String file = this.path.substring(7);
            File f = new File(file);
            this.lastModified = f.lastModified();
            return new FileInputStream(f);
        } else {
            this.lastModified = System.currentTimeMillis();
            return new URL(this.path).openStream();
        }
    }

    public String toString() {
        return path;
    }
}