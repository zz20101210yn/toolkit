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
package com.paic.jrkj.tk.tools.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-2 11:29:20 $
 * @serial 1.0
 * @since 2015-3-2 11:29:20
 */
public abstract class BeanHolder {

    public static final int SCOPE_SINGLETON = 0;
    public static final int SCOPE_THREAD = 1;
    public static final int SCOPE_MIN_SINGLETON =2;

    private static final BeanHolder singletonHolder = new SingletonHolder();
    private static final BeanHolder threadBeanHolder = new ThreadBeanHolder();
    private static final BeanHolder minSingletonHolder = new MinSingletonHolder();
    

    protected final Log logger = LogFactory.getLog(getClass());
    
    

    public static BeanHolder use(int scope) {
        switch (scope) {
            case SCOPE_SINGLETON:
                return singletonHolder;
            case SCOPE_THREAD:
                return threadBeanHolder;
            case SCOPE_MIN_SINGLETON:
            	return minSingletonHolder;
            default:
                throw new IllegalArgumentException("argument error! use [0,1]...");
        }
    }

    public abstract <T> T getBean(Object id, ObjectCreator<T> creator);

    public abstract <T> T remove(Object id, ObjectCreator<T> creator);
}
