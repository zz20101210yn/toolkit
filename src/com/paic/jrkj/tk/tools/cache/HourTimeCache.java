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
public class HourTimeCache<K,V> extends FixTimeCache<K,V>{

    public HourTimeCache() {
        super(1*60*60*1000-1000, 24);
    }

    public HourTimeCache(int maxKeySize) {
        super(1*60*60*1000-1000, 24,maxKeySize);
    }

    protected int getCurrentIndex() {
        Calendar vCalendar = Calendar.getInstance();
        return vCalendar.get(Calendar.HOUR_OF_DAY);
    }
}
