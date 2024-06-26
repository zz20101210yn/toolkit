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
 * @version $Revision: 1.0 $ $Date: 2015-2-4 13:59:04 $
 * @serial 1.0
 * @since 2015-2-4 13:59:04
 */
class SingletonHolder extends BeanHolder{

    private final Map<BeanKey, Object> singletonMap = new HashMap<BeanKey, Object>();

    public SingletonHolder() {
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Object id, ObjectCreator<T> creator) {
        BeanKey key = new BeanKey(id,creator.getClass().getName());
        T instance = (T) singletonMap.get(key);
        if (instance == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("singleton [" + key + "] init...");
            }
            synchronized (singletonMap) {
                instance = (T)singletonMap.get(key);
                if (instance == null) {
                    instance = creator.create();
                    if(instance!=null){
                        singletonMap.put(key, instance);
                    }
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T remove(Object id, ObjectCreator<T> creator) {
        return (T) singletonMap.remove(new BeanKey(id,creator.getClass().getName()));
    }

}