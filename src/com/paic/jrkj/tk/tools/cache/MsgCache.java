package com.paic.jrkj.tk.tools.cache;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 16:09:15 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 16:09:15
 */
public interface MsgCache<K,V> {

    void setMaxKeySize(int maxKeySize);

    boolean put(K key,V value);

    V get(K key);

    V pop(K key);

}
