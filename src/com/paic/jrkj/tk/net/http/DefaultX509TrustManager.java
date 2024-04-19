package com.paic.jrkj.tk.net.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <p/>
 *
 * @author <a href="mailto:chaos.wql@ustc.edu">wang.chaos</a>
 * @version $Revision: 1.0 $ $Date: 2012-6-14 14:36:52 $
 * @serial 1.0
 * @since JDK1.6.0_27 2012-6-14 14:36:52
 */
public class DefaultX509TrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
            throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
