package com.paic.jrkj.tk.tools.cache;

import java.util.Calendar;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 16:34:14 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 16:34:14
 */
public class MinTimeCache<K,V> extends FixTimeCache<K,V>{

    public MinTimeCache(){
        super(59*1000,60);
    }

    public MinTimeCache(int maxKeySize){
        super(59*1000,60,maxKeySize);
    }

    protected int getCurrentIndex() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }
}
