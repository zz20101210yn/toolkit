package com.paic.jrkj.tk.tools.work;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.tools.core.BeanHolder;
import com.paic.jrkj.tk.tools.core.ObjectCreator;
import com.paic.jrkj.tk.tools.core.SystemProperty;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-8 12:19:03 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-8 12:19:03
 */
public abstract class WorkManager {

    protected final static Log logger = LogFactory.getLog(WorkManager.class);

    private String name;

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public static WorkManager getWorkManager(final String name) {
        return BeanHolder.use(BeanHolder.SCOPE_SINGLETON).getBean(name, new ObjectCreator<WorkManager>() {
            public WorkManager create() {
                try {
                    String wmName = SystemProperty.useSystemProperty("work-manager", 5 * 60).getString("workmanager." + name);
                    if (wmName != null) {
                        WorkManager wm = (WorkManager) Class.forName(wmName).newInstance();
                        wm.setName(name);
                        wm.init();
                        return wm;
                    }
                } catch (Exception e) {
                    logger.fatal("", e);
                }
                return null;
            }
        });
    }

    public static WorkManager getWorkManager() {
        return getWorkManager("default");
    }

    public static WorkManager getWorkManagerUseLock(String lockName) {
        return new WMProxy(getWorkManager(), lockName);
    }

    protected void init() {
    }

    public abstract void startWork(Runnable t);

    public void startWork(final Runnable t, final Listener listener) {
        startWork(new Runnable() {
            public void run() {
                try {
                    t.run();
                    if (listener != null) {
                        listener.callAfterFinish();
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.callWhenException(e);
                    }
                    logger.fatal("", e);
                } finally {
                    if (listener != null) {
                        listener.callFinally();
                    }
                }
            }
        });
    }

    public boolean isBusy() {
        return false;
    }

}