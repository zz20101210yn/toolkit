/**
 * Copyright 2011-2012 www.chinatvpay.com
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

import org.apache.log4j.Logger;
import com.paic.jrkj.tk.util.SystemUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:wangql@chinatvpay.com">wang.chaos</a>
 * @version $Revision: 1.0 $ $Date: 2014-12-25 18:05:07 $
 * @serial 1.0
 * @since 2014-12-25 18:05:07
 */
public class Log4JLogger extends org.apache.commons.logging.impl.Log4JLogger{

    public Log4JLogger() {
    }

    public Log4JLogger(String name) {
        super(name);
    }

    public Log4JLogger(Logger logger) {
        super(logger);
    }

    public void debug(Object message, Throwable t) {
        if(isDebugEnabled()){
            super.debug(message, t);
        }
    }

    public void debug(Object message) {
        if(isDebugEnabled()){
            super.debug(message);
        }
    }

    public boolean isDebugEnabled() {
        return (!SystemUtil.isInProductMode()) && super.isDebugEnabled();
    }
}
