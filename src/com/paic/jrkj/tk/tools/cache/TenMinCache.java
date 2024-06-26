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
package com.paic.jrkj.tk.tools.cache;

import java.util.Calendar;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-1-7 14:09:46 $
 * @serial 1.0
 * @since 2016-1-7 14:09:46
 */
public class TenMinCache<K,V> extends FixTimeCache<K,V>{

    public TenMinCache() {
        super(10*60*1000-1000, 6);
    }

    public TenMinCache(int maxKeySize) {
        super(10*60*1000-1000, 6,maxKeySize);
    }

    protected int getCurrentIndex() {
        Calendar vCalendar = Calendar.getInstance();
        return vCalendar.get(Calendar.MINUTE)/10;
    }
}