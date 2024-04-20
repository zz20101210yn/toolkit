package com.paic.jrkj.tk.tools.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 16:10:32 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 16:10:32
 */
public abstract class FixTimeCache<K, V> implements MsgCache<K, V> {

    protected final Log logger = LogFactory.getLog(getClass());
    private final long updateInterval;
    private volatile Map<K, V>[] dataMapArray;
    private volatile long lstUpdateTime;
    private volatile int maxKeySize;

    @SuppressWarnings("unchecked")
    public FixTimeCache(long updateInterval, int size) {
        this(updateInterval, size, -1);
    }

    @SuppressWarnings("unchecked")
    public FixTimeCache(long updateInterval, int size, int maxKeySize) {
        this.updateInterval = updateInterval;
        this.dataMapArray = new HashMap[size];
        this.maxKeySize = maxKeySize;
    }

    public void setMaxKeySize(int maxKeySize) {
        this.maxKeySize = maxKeySize;
    }

    public boolean put(K key, V value) {
        int index = getCurrentIndex();
        checkUpdate(index);
        synchronized (this) {
            if (dataMapArray[index] == null) {
                dataMapArray[index] = new HashMap<K, V>();
            }
            if (maxKeySize > 0 && dataMapArray[index].size() >= maxKeySize) {
                logger.info("内存数据量超限，maxKeySize=[" + maxKeySize + "].");
                return false;
            }
            dataMapArray[index].put(key, value);
            return true;
        }
    }

    public V get(K key) {
        int index = getCurrentIndex();
        checkUpdate(index);
        V value = get(key, index, false);
        if (value != null) {
            return value;
        }
        int prevIndex = index == 0 ? dataMapArray.length - 1 : index - 1;
        return get(key, prevIndex, false);
    }

    public V pop(K key) {
        int index = getCurrentIndex();
        checkUpdate(index);
        V value = get(key, index, true);
        if (value != null) {
            return value;
        }
        int prevIndex = index == 0 ? dataMapArray.length - 1 : index - 1;
        return get(key, prevIndex, true);
    }

    private V get(K key, int index, boolean removeKey) {
        Map<K, V> map = dataMapArray[index];
        if (map != null) {
            return removeKey ? map.remove(key) : map.get(key);
        }
        return null;
    }

    private synchronized void checkUpdate(int index) {
        if (System.currentTimeMillis() - lstUpdateTime >= updateInterval) {
            if (logger.isDebugEnabled()) {
                logger.debug("update cache...");
            }
            if (System.currentTimeMillis() - lstUpdateTime >= updateInterval) {
                if (index == 0) {
                    for (int i = 1; i < dataMapArray.length - 1; i++) {
                        destory(i);
                    }
                }
                for (int i = 0; i < dataMapArray.length; i++) {
                    if (i == index || i == index - 1) {
                        continue;
                    }
                    destory(i);
                }
                lstUpdateTime = System.currentTimeMillis();
            }
        }
    }

    private void destory(int index) {
        Map map = dataMapArray[index];
        if (map != null) {
            map.clear();
            map = null;
            dataMapArray[index] = null;
        }
    }

    protected abstract int getCurrentIndex();

}
