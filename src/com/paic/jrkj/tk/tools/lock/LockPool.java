package com.paic.jrkj.tk.tools.lock;

import com.paic.jrkj.tk.tools.core.BeanHolder;
import com.paic.jrkj.tk.tools.core.ObjectCreator;
import com.paic.jrkj.tk.tools.core.SystemProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:21:58 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:21:58
 */
public abstract class LockPool {

    protected static final Log logger = LogFactory.getLog(LockPool.class);

    public abstract void fetchLock() throws Exception;

    public abstract boolean fetchLock(long timeout)
            throws Exception;

    public abstract boolean fetchLock(boolean waitIfBusy)
            throws Exception;

    public abstract void releaseLock();

    protected abstract void setMaxLockSize(int maxLockSize);

    public static LockPool getLockPool(String name) {
        return getLockPool(name, true);
    }

    public static LockPool getLockPool(final String name, final boolean createIfNotExist) {
        return BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean(name, new ObjectCreator<LockPool>() {
            public LockPool create() {
                SystemProperty prop = SystemProperty.useSystemProperty("lock");
                int maxLockSize = prop.getInteger(name + ".maxLockSize", 10, -1);
                
                if (maxLockSize == -1) {
                	
                    if (createIfNotExist) {
                        logger.info("LockPool[" + name + "] not found!use default pool size=[100]");
                        maxLockSize = 100;
                    } else {
                        return null;
                    }
                }
                
                LockPool lockPool = new MemoryLockPool(name);
                lockPool.setMaxLockSize(maxLockSize);
                return lockPool;
            }
        });
    }
    
    public static LockPool getMinCacheLockPool(final String name,final int updateInterval, final boolean createIfNotExist) {
        return BeanHolder.use(BeanHolder.SCOPE_MIN_SINGLETON).getBean(name, new ObjectCreator<LockPool>() {
            public LockPool create() {
                SystemProperty prop = SystemProperty.useIntervalSystemProperty("lock", updateInterval);
                int maxLockSize = prop.getInteger(name + ".maxLockSize", 10, -1);
                if (maxLockSize == -1) {
                    if (createIfNotExist) {
                        logger.info("LockPool[" + name + "] not found!use default pool size=[100]");
                        maxLockSize = 100;
                    } else {
                        return null;
                    }
                }
                LockPool lockPool = new MemoryLockPool(name);
                lockPool.setMaxLockSize(maxLockSize);
                return lockPool;
            }
        });
    }
    
    

    public static LockPool getDynamicLockPool(final String name, final int updateInterval) {
        return getDynamicLockPool(name, updateInterval, true);
    }

    public static LockPool getDynamicLockPool(final String name, final int updateInterval, final boolean createIfNotExist) {
        return BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean(name, new ObjectCreator<LockPool>() {
            public LockPool create() {
                SystemProperty prop = SystemProperty.useSystemProperty("lock", updateInterval);
                int maxLockSize = prop.getInteger(name + ".maxLockSize", 10, -1);
                if (maxLockSize == -1) {
                    if (createIfNotExist) {
                        logger.info("LockPool[" + name + "] not found!use default pool size=[100]");
                        maxLockSize = 100;
                    } else {
                        return null;
                    }
                }
                MemoryLockPool lockPool = new MemoryLockPool(name);
                lockPool.setMaxLockSize(maxLockSize);
                lockPool.setProperty(prop);
                return lockPool;
            }
        });
    }
}
