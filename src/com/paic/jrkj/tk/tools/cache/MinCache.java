package com.paic.jrkj.tk.tools.cache;

import java.util.Calendar;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 16:43:41 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 16:43:41
 */
public class MinCache<K,V> extends FixTimeCache<K,V>{

    public MinCache() {
        super(1*60*1000-1000, 60);
    }

    public MinCache(int maxKeySize) {
        super(1*60*1000-1000, 60,maxKeySize);
    }

    protected int getCurrentIndex() {
        Calendar vCalendar = Calendar.getInstance();
        return vCalendar.get(Calendar.MINUTE);
    }
}