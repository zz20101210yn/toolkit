package com.paic.jrkj.tk.net.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * <p/>
 *
 * @author <a href="mailto:chaos.wql@ustc.edu">wang.chaos</a>
 * @version $Revision: 1.0 $ $Date: 2012-6-14 14:37:49 $
 * @serial 1.0
 * @since JDK1.6.0_27 2012-6-14 14:37:49
 */
public class DefaultHostnameVerifier implements HostnameVerifier {

    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
