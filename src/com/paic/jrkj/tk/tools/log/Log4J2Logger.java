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
package com.paic.jrkj.tk.tools.log;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

import com.paic.jrkj.tk.util.SystemUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-12-25 14:19:57 $
 * @serial 1.0
 * @since 2015-12-25 14:19:57
 */
public class Log4J2Logger implements Log, Serializable {

    public Log4J2Logger() {
        logger = null;
        name = null;
    }

    public Log4J2Logger(String name) {
        this.name = name;
        logger = getLogger();
    }

    public Log4J2Logger(Logger logger) {
        name = logger.getName();
        this.logger = logger;
    }

    public void trace(Object message) {
        if (!isProd) {
            getLogger().trace(message);
        }
    }

    public void trace(Object message, Throwable t) {
        if (!isProd) {
            getLogger().trace(message, t);
        }
    }

    public void debug(Object message) {
        if (!isProd) {
            getLogger().debug(message);
        }
    }

    public void debug(Object message, Throwable t) {
        if (!isProd) {
            getLogger().debug(message, t);
        }
    }

    public void info(Object message) {
        getLogger().info(message);
    }

    public void info(Object message, Throwable t) {
        getLogger().info(message, t);
    }

    public void warn(Object message) {
        getLogger().warn(message);

    }

    public void warn(Object message, Throwable t) {
        getLogger().warn(message, t);
    }

    public void error(Object message) {
        getLogger().error(message);
    }

    public void error(Object message, Throwable t) {
        getLogger().error(message, t);
    }

    public void fatal(Object message) {
        getLogger().fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        getLogger().fatal(message, t);
    }

    private Logger getLogger() {
        if (logger == null) {
            logger = LogManager.getLogger(name);
        }
        return logger;
    }

    public boolean isDebugEnabled() {
        if (isProd) return false;
        return getLogger().isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return getLogger().isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        return getLogger().isFatalEnabled();
    }

    public boolean isInfoEnabled() {
        return getLogger().isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        if (isProd) return false;
        return getLogger().isDebugEnabled();
    }

    public boolean isWarnEnabled() {
        return getLogger().isWarnEnabled();
    }

    private transient Logger logger;
    private String name;
    private boolean isProd = SystemUtil.isInProductMode();

}
