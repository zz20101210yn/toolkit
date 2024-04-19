package com.paic.jrkj.tk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ResourceBundle;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-26 12:01:17 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-26 12:01:17
 */
@Deprecated
public class ResourceBundleProxy {

    private final Log logger = LogFactory.getLog(ResourceBundleProxy.class);

    private ResourceBundle resource;

    public ResourceBundleProxy(String bundleName) {
        this.resource = ResourceBundle.getBundle(bundleName);
    }

    public String getString(String key) {
        return resource.getString(key);
    }

    public int getInteger(String key) {
        return getInteger(key, 0);
    }

    public int getInteger(String key, int defaultInt) {
        try {
            String s = resource.getString(key);
            return Integer.parseInt(s, 10);
        } catch (NumberFormatException e) {
           logger.fatal("",e);
        } catch(Throwable t ){
           logger.fatal("",t);
        }
        return defaultInt;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultLong) {
        try {
            String s = resource.getString(key);
            return Integer.parseInt(s, 10);
        } catch (NumberFormatException e) {
           logger.fatal("",e);
        }catch(Throwable t ){
           logger.fatal("",t);
        }
        return defaultLong;
    }
}
