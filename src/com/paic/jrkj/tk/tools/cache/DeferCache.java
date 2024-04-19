package com.paic.jrkj.tk.tools.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-3-31 11:48:29 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-3-31 11:48:29
 */
public abstract class DeferCache<K, V> {

    private MsgCache<K, V> cache;
    protected final Log logger = LogFactory.getLog(getClass());

    protected DeferCache(MsgCache<K, V> cache) {
        this.cache = cache;
    }

    protected abstract V create(K key);

    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            logger.debug("value for [" + key + "] not found! create it!");
            synchronized (this) {
                value = cache.get(key);
                if (value == null) {
                    value = create(key);
                    cache.put(key, value);
                }
            }
        }
        return value;
    }
}
