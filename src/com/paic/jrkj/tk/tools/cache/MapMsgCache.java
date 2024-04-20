package com.paic.jrkj.tk.tools.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-3-31 12:01:15 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-3-31 12:01:15
 */
public class MapMsgCache<K, V> implements MsgCache<K, V> {

    protected final Log logger = LogFactory.getLog(getClass());
    private Map<K, V> cache = new HashMap<K, V>();
    private int maxKeySize = -1;

    public MapMsgCache(int maxKeySize) {
        this.maxKeySize = maxKeySize;
    }

    public MapMsgCache() {
    }

    public void setMaxKeySize(int maxKeySize) {
        this.maxKeySize = maxKeySize;
    }

    public boolean put(K key, V value) {
        if (maxKeySize > 0 && cache.size() >= maxKeySize) {
            logger.info("内存数据量超限,maxKeySize=[" + maxKeySize + "].");
            return false;
        }
        cache.put(key, value);
        return true;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public V pop(K key) {
        return cache.remove(key);
    }
}