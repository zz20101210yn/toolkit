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

import com.paic.jrkj.tk.tools.core.ObjectCreator;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-11 17:41:34 $
 * @serial 1.0
 * @since 2015-3-11 17:41:34
 */
public class PropertyLoaderCreator implements ObjectCreator<PropertyLoader> {

    private final String path;
    private final int updateInterval;

    public PropertyLoaderCreator(String path) {
        this(path, 5 * 60);
    }

    public PropertyLoaderCreator(String path, int updateInterval) {
        this.path = path;
        this.updateInterval = updateInterval;
    }

    public PropertyLoader create() {
        return new PropertyLoader(path, updateInterval);
    }
}
