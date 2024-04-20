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

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-11 16:59:33 $
 * @serial 1.0
 * @since 2015-3-11 16:59:33
 */
public class PropertyLoader implements Property {

    private final Log logger = LogFactory.getLog(PropertyLoader.class);
    private volatile Properties properties;
    private final StreamReader streamReader;

    public PropertyLoader(String path) {
        this(path, 300);
    }

    public PropertyLoader(String path, int checkInterval) {
        this.streamReader = new StreamReader(path, checkInterval*1000);
        load();
    }

    private void load() {
        try {
            Properties properties = new Properties();
            properties.load(this.streamReader.getInputStream());
            this.properties = properties;
        } catch (IOException e) {
            logger.fatal("", e);
        }
    }

    private Properties getProperties() {
        this.streamReader.checkUpdate(new UpdateProxy() {
            public void update() {
               load();
            }
        });
        return this.properties;
    }

    public String getString(String key) {
        return getProperties().getProperty(key);
    }

    public String getString(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    public Set<String> propertyNames() {
        return getProperties().stringPropertyNames();
    }

    public int getInteger(String name, int radix) {
        return getInteger(name, radix, 0);
    }

    public int getInteger(String name, int radix, int defValue) {
        String value = getString(name, null);
        return value == null ? defValue : Integer.valueOf(value, radix);
    }

    public long getLong(String name, int radix) {
        return getLong(name, radix, 0);
    }

    public long getLong(String name, int radix, long defValue) {
        String value = getString(name, null);
        return value == null ? defValue : Long.valueOf(value, radix);
    }

    public boolean getBoolean(String name) {
        String value = getString(name);
        return Boolean.parseBoolean(value);
    }
}
