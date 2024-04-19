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

import com.paic.jrkj.tk.tools.io.Property;
import com.paic.jrkj.tk.tools.io.PropertyLoader;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-2-26 15:20:40 $
 * @serial 1.0
 * @since 2015-2-26 15:20:40
 */
public class SystemProperty {

    private Property loader;

    SystemProperty(String resourceName) {
        this(resourceName, 0);
    }

    SystemProperty(String resourceName, int interval) {
        String resource = "classpath://" + resourceName + ".properties";
        loader = new PropertyLoader(resource, interval);
    }

    public String getString(String name) {
        return loader.getString(name);
    }

    public String getString(String name, String defValue) {
        return loader.getString(name, defValue);
    }

    public int getInteger(String name, int radix) {
        return loader.getInteger(name, radix);
    }

    public int getInteger(String name, int radix, int defValue) {
        return loader.getInteger(name, radix, defValue);
    }

    public long getLong(String name, int radix) {
        return loader.getLong(name, radix);
    }

    public long getLong(String name, int radix, long defValue) {
        return loader.getLong(name, radix, defValue);
    }

    public boolean getBoolean(String name) {
        return loader.getBoolean(name);
    }

    public static SystemProperty useDefault() {
        String resourceName = System.getProperty("app.resource.name", "app");
        return useSystemProperty(resourceName, 0);
    }

    public static SystemProperty useDefault(int reloadInterval) {
        String resourceName = System.getProperty("app.resource.name", "app");
        return useSystemProperty(resourceName, reloadInterval);
    }

    public static SystemProperty useSystemProperty(String resourceName, int reloadInterval) {
        return BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean(resourceName, new SystemPropertyCreator(resourceName, reloadInterval));
    }
    
    public static SystemProperty useIntervalSystemProperty(String resourceName, int reloadInterval) {
        return BeanHolder.use(BeanHolder.SCOPE_MIN_SINGLETON).getBean(resourceName, new SystemPropertyCreator(resourceName, reloadInterval));
    }

    public static SystemProperty useSystemProperty(String resourceName) {
        return useSystemProperty(resourceName, 0);
    }
}
