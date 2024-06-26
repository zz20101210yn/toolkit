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

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2015-3-2 11:45:44 $
 * @serial 1.0
 * @since 2015-3-2 11:45:44
 */
class SystemPropertyCreator implements ObjectCreator<SystemProperty> {

    private final String resourceName;
    private final int reloadInterval;

    public SystemPropertyCreator(String resourceName,int reloadInterval) {
        this.resourceName = resourceName;
        this.reloadInterval= reloadInterval;
    }

    public SystemProperty create() {
        return new SystemProperty(resourceName,reloadInterval);
    }
}
