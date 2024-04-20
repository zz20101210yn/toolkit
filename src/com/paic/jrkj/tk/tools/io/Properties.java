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

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.paic.jrkj.tk.tools.kv.NameValuePair;
import com.paic.jrkj.tk.util.StreamUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-5-25 14:26:05 $
 * @serial 1.0
 * @since 2016-5-25 14:26:05
 */
public class Properties {

    private final Log logger = LogFactory.getLog(Properties.class);
    private final StreamReader streamReader;
    private volatile Map<String, NameValuePair<String, String>> keyValueMap;

    public Properties(String path, long updateInterval) {
        this.streamReader = new StreamReader(path, updateInterval);
        load();
    }

    private void load() {
        BufferedReader reader = null;
        try {
            Map<String, NameValuePair<String, String>> keyValueMap = new HashMap<String, NameValuePair<String, String>>();
            reader = new BufferedReader(new InputStreamReader(streamReader.getInputStream()));
            String line;
            String name;
            String value;
            NameValuePair<String, String> nvp;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0 || line.startsWith("#") || line.startsWith("--")) {
                    logger.debug("ignore line:" + line);
                    continue;
                }
                int eqIdx = line.indexOf("=");
                if (eqIdx == 0 || eqIdx == line.length() - 1) {
                    logger.error("invalid line:" + line);
                    continue;
                }
                name = line.substring(0, eqIdx);
                value = line.substring(eqIdx + 1);
                nvp = keyValueMap.get(name);
                if (nvp == null) {
                    nvp = new NameValuePair<String, String>(name, value);
                } else {
                    nvp.addValue(value);
                }
                keyValueMap.put(name, nvp);
            }
            this.keyValueMap = keyValueMap;
        } catch (IOException e) {
            logger.fatal("", e);
        } finally {
            StreamUtil.close(reader);
        }
    }

    public String[] getStringArray(String name) {
        return getStringArray(name, null);
    }

    public String[] getStringArray(String name, String[] def) {
        streamReader.checkUpdate(new UpdateProxy() {
            public void update() {
                 load();
            }
        });
        NameValuePair<String, String> nvp = keyValueMap.get(name);
        if (nvp == null) {
            return def;
        }
        List<String> val = nvp.getValue();
        String[] arr = new String[val.size()];
        val.toArray(arr);
        return arr;
    }

    public String getString(String name) {
        return getString(name, null);
    }

    public String getString(String name, String def) {
        String[] val = getStringArray(name);
        return val == null || val.length == 0 ? def : val[0];
    }

    public int getInteger(String name) {
        return getInteger(name, 0);
    }

    public int getInteger(String name, int def) {
        return getInteger(name, 10, def);
    }

    public int getInteger(String name, int radix, int def) {
        try {
            String val = getString(name);
            if (val != null) {
                return Integer.parseInt(val, radix);
            }
        } catch (NumberFormatException e) {
            //
        }
        return def;
    }

    public long getLong(String name) {
        return getLong(name, 0);
    }

    public long getLong(String name, long def) {
        return getLong(name, 10, def);
    }

    public long getLong(String name, int radix, long def) {
        try {
            String val = getString(name);
            if (val != null) {
                return Long.parseLong(val, radix);
            }
        } catch (NumberFormatException e) {
            //
        }
        return def;
    }
}
