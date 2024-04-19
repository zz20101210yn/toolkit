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
public class HalfHourTimeCache<K, V> extends FixTimeCache<K, V> {

    public HalfHourTimeCache() {
        super(30 * 60 * 1000 - 1000, 48);
    }

    public HalfHourTimeCache(int maxKeySize) {
        super(30 * 60 * 1000 - 1000, 48, maxKeySize);
    }

    protected int getCurrentIndex() {
        Calendar vCalendar = Calendar.getInstance();
        return vCalendar.get(Calendar.HOUR_OF_DAY) * 2 + vCalendar.get(Calendar.MINUTE) / 30;
    }
}