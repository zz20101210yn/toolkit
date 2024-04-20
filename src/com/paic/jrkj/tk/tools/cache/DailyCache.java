package com.paic.jrkj.tk.tools.cache;

import java.util.Calendar;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-2-26 16:10:00 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-2-26 16:10:00
 */
public class DailyCache<K,V> extends FixTimeCache<K,V>{

     public DailyCache() {
        super(24*60*60*1000-1000, 7);
    }

    public DailyCache(int maxKeySize) {
        super(24*60*60*1000-1000, 7,maxKeySize);
    }

    protected int getCurrentIndex() {
        Calendar vCalendar = Calendar.getInstance();
        return vCalendar.get(Calendar.DAY_OF_WEEK) -1;
    }
}
