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

import java.util.Map;
import java.util.HashMap;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-2 11:29:49 $
 * @serial 1.0
 * @since 2015-3-2 11:29:49
 */
class ThreadBeanHolder extends BeanHolder{

    private final ThreadLocal<Map<BeanKey, Object>> threadLocal;

    public ThreadBeanHolder() {
        threadLocal = new ThreadLocal<Map<BeanKey, Object>>(){

            protected Map<BeanKey, Object> initialValue() {
                return new HashMap<BeanKey, Object>();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Object id, ObjectCreator<T> creator) {
        Map<BeanKey, Object> beanMap = threadLocal.get();
        BeanKey key = new BeanKey(id,creator.getClass().getName());
        T instance = (T) beanMap.get(key);
        if (instance == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("thread-bean [" + key + "] init...");
            }
            synchronized (beanMap) {
                instance = (T)beanMap.get(key);
                if (instance == null) {
                    instance = creator.create();
                    if(instance!=null){
                        beanMap.put(key, instance);
                    }
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T remove(Object id, ObjectCreator<T> creator) {
        Map<BeanKey, Object> beanMap = threadLocal.get();
        return (T) beanMap.remove(new BeanKey(id,creator.getClass().getName()));
    }
}
