package com.paic.jrkj.tk.tools.work;

import com.paic.jrkj.tk.tools.core.SystemProperty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-26 11:54:15 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-26 11:54:15
 */
public class PoolWorkManager extends WorkManager {

    private ThreadPoolExecutor pool;

    protected PoolWorkManager() {
    }

    protected synchronized void init() {
        SystemProperty bundle = SystemProperty.useSystemProperty("work-manager");
        int corePoolSize = bundle.getInteger(getName()+".corePoolSize",10, 5);
        int maximumPoolSize = bundle.getInteger(getName()+".maxPoolSize",10, 10);
        long keepAliveTime = bundle.getLong(getName()+".keepAliveTime",10, 15000L);
        int queCapacity = bundle.getInteger(getName()+".queueCapacity",10, -1);
        BlockingQueue<Runnable> queue = queCapacity <= 0 ?
                new LinkedBlockingDeque<Runnable>() :
                new LinkedBlockingDeque<Runnable>(queCapacity);
        this.pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public boolean isBusy(){
        return pool.getMaximumPoolSize() <= pool.getTaskCount() - pool.getCompletedTaskCount();
    }

    public void startWork(Runnable work) {
        if(isBusy()){
            logger.warn("thread-pool["+getName()+"] is too busy!");
        }
        this.pool.execute(work);
    }

    public void shutdown() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    public String toStirng() {
        return "PoolWorkManager["+getName()+"]";
    }
}