package com.paic.jrkj.tk.tools.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 16:48:15 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 16:48:15
 */
public class CacheManager {

    private final static Log logger = LogFactory.getLog(CacheManager.class);
    private static final Map<String, MsgCache> cacheMap = new HashMap<String, MsgCache>();

    @SuppressWarnings("unchecked")
    @Deprecated
    public static <T extends MsgCache> T getCache(final Class<T> typeClass, final String id) {
        final String key = id + "@" + typeClass.getName();
        try {
            T cache = (T) cacheMap.get(key);
            if (cache == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("init cache[" + key + "].");
                }
                synchronized (CacheManager.class) {
                    cache = (T) cacheMap.get(key);
                    if (cache == null) {
                        cache = typeClass.newInstance();
                    }
                    cacheMap.put(key, cache);
                }
            }
            return cache;
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("init cache [" + key + "] fail.");
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("init cache [" + key + "] fail.");
        }
    }

    @SuppressWarnings("unchecked")
    public static MsgCache getCache(final String key) {
        MsgCache cache = cacheMap.get(key);
        if (cache == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("init cache[" + key + "].");
            }
            synchronized (CacheManager.class) {
                cache = cacheMap.get(key);
                if (cache == null) {
                    cache = initCache(key);
                }
                cacheMap.put(key, cache);
            }
        }
        return cache;
    }

    private static MsgCache initCache(String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("msg-cache");
            if (bundle != null) {
                String vClass = bundle.getString(key + ".impl");
                if (vClass == null || StringUtils.isEmpty(vClass)) {
                    throw new IllegalArgumentException("msg-cache [" + key + "] implements undefined!");
                }
                MsgCache cache = (MsgCache) Class.forName(vClass).newInstance();
                String maxKeySize = bundle.getString(key + ".maxKeySize");
                int size = StringUtils.isEmpty(maxKeySize) ? -1 : Integer.parseInt(maxKeySize, 10);
                cache.setMaxKeySize(size);
                return cache;
            } else {
                throw new IllegalArgumentException("resourbundle msg-cache not found!");
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("init cache [" + key + "] fail.");
        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException("init cache [" + key + "] fail.");
        }
        catch (InstantiationException e) {
            throw new IllegalArgumentException("init cache [" + key + "] fail.");
        }
    }

}
