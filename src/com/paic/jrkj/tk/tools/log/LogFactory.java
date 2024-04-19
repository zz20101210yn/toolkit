/**
 * Copyright 2011-2012 wang.chaos
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

import org.apache.commons.logging.Log;

@Deprecated
public class LogFactory {

    private LogFactory(){}

    public static Log getLog(Class vClass) {
        return org.apache.commons.logging.LogFactory.getLog(vClass);
    }

    public static Log getLog(String name) {
        return org.apache.commons.logging.LogFactory.getLog(name);
    }

}
