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

import java.util.Set;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-4-3 16:52:54 $
 * @serial 1.0
 * @since 2015-4-3 16:52:54
 */
public interface Property {

    String getString(String key);

    String getString(String key, String defaultValue);

    Set<String> propertyNames();

    int getInteger(String name, int radix);

    int getInteger(String name, int radix, int defValue);

    long getLong(String name, int radix);

    long getLong(String name, int radix, long defValue);

    boolean getBoolean(String name);
}
