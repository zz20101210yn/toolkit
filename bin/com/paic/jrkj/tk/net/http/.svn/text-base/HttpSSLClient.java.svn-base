package com.paic.jrkj.tk.net.http;

import com.paic.jrkj.tk.util.StreamUtil;
import com.paic.jrkj.tk.net.SendAndReceiver;
import com.paic.jrkj.tk.net.Connection;
import com.paic.jrkj.tk.net.ConnectionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p/>
 *
 * @author wang.chaos
 * @version $Revision: 1.0 $ $Date: 2011-11-23 11:54:42 $
 * @serial 1.0
 * @since JDK1.4.2 2011-11-23 11:54:42
 */
public class HttpSSLClient implements SendAndReceiver, Connection<HttpSSLClient> {

    private String url;
    private long timeout = 60000L;
    private HttpURLConnection urlCon = null;
    private String requestMethod = "POST";
    private OutputStream tOut = null;
    private InputStream is = null;
    private Map<String, String> reqProps = new HashMap<String, String>();
    private KeyManager[] keyManagers = null;
    private TrustManager[] xtmArray = new TrustManager[]{new DefaultX509TrustManager()};
    private HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();
    private final Log logger = LogFactory.getLog(HttpSSLClient.class);

    public HttpSSLClient(String httpsURL, long timeout) {
        this.url = httpsURL;
        this.timeout = timeout;
    }

    public HttpSSLClient() {
    }

    public void setTrustManagerArray(TrustManager[] xtmArray) {
        this.xtmArray = xtmArray;
    }

    public void setClientCert(String keyStoreType, String clientCertPath, String password) {
        try {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            ks.load(new FileInputStream(clientCertPath), password.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password.toCharArray());
            keyManagers = kmf.getKeyManagers();
        } catch (CertificateException e) {
            logger.fatal("secret error", e);
        } catch (KeyStoreException e) {
            logger.fatal("secret error", e);
        } catch (NoSuchAlgorithmException e) {
            logger.fatal("secret error", e);
        } catch (UnrecoverableKeyException e) {
            logger.fatal("secret error", e);
        } catch (IOException e) {
            logger.fatal("secret error", e);
        }
    }
    
    public void setClientCert(String clientCertPath, String password) {
    	setClientCert("JKS", clientCertPath, password);
    }

    public void setTrustCert(String keyStoreType, String trustCertPath, String password) {
        try {
            KeyStore tks = KeyStore.getInstance(keyStoreType);
            tks.load(new FileInputStream(trustCertPath), password.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);
            xtmArray = tmf.getTrustManagers();
        } catch (CertificateException e) {
            logger.fatal("secret error", e);
        } catch (KeyStoreException e) {
            logger.fatal("secret error", e);
        } catch (NoSuchAlgorithmException e) {
            logger.fatal("secret error", e);
        } catch (IOException e) {
            logger.fatal("secret error", e);
        }
    }
    
    public void setTrustCert( String trustCertPath, String password) {
    	setTrustCert("JKS", trustCertPath, password);
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void addConnectionListener(ConnectionListener<HttpSSLClient> listener) {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setRequestMethod(String method) {
        this.requestMethod = method;
    }

    public void connect()
            throws IOException {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, xtmArray, new java.security.SecureRandom());
        } catch (GeneralSecurityException gse) {
            logger.fatal("secret error", gse);
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        System.setProperty("sun.net.client.defaultConnectTimeout", Long.toString(timeout, 10));
        System.setProperty("sun.net.client.defaultReadTimeout", Long.toString(timeout, 10));
        urlCon = (HttpURLConnection) new URL(url).openConnection();
        urlCon.setConnectTimeout((int)this.timeout);
        urlCon.setReadTimeout((int)this.timeout); 
        urlCon.setRequestMethod(requestMethod);
        for (String key : reqProps.keySet()) {
            urlCon.setRequestProperty(key, reqProps.get(key));
        }
        if("POST".equals(requestMethod)){
        	urlCon.setDoOutput(true);
        }
        urlCon.setDoInput(true);

    }

    public void addRequestProperty(String name, String value) {
        reqProps.put(name, value);
    }

    public void send(byte[] bytes)
            throws IOException {
        tOut = urlCon.getOutputStream();
        if (logger.isInfoEnabled()) {
            logger.info(new StringBuffer("HttpSSLClient[")
                    .append(this.url).append("] send=[")
                    .append(new String(bytes)).append("]").toString());
        }
        tOut.write(bytes);
        tOut.flush();
    }

    public byte[] received()
            throws IOException {
        if (200 == urlCon.getResponseCode()) {
            is = urlCon.getInputStream();
            byte[] bytes = StreamUtil.toByteArray(is);
            if (logger.isDebugEnabled()) {
                logger.debug(new StringBuffer("HttpSSLClient[")
                        .append(this.url).append("] receive data:len=[")
                        .append(bytes.length).append("];content=[")
                        .append(new String(bytes)).append("]").toString());
            }
            return bytes;
        } else {
            throw new IOException(urlCon.getResponseMessage() + "[" + urlCon.getResponseCode() + "]");
        }
    }

    public Map<String, List<String>> getHeaderFields()
            throws IOException {
        if (200 == urlCon.getResponseCode()) {
            return urlCon.getHeaderFields();
        } else {
            throw new IOException(urlCon.getResponseMessage() + "[" + urlCon.getResponseCode() + "]");
        }
    }

    public String getHeaderField(String name)
            throws IOException {
        if (200 == urlCon.getResponseCode()) {
            return urlCon.getHeaderField(name);
        } else {
            throw new IOException(urlCon.getResponseMessage() + "[" + urlCon.getResponseCode() + "]");
        }
    }

    public void disconnect() {
        StreamUtil.close(tOut);
        tOut = null;
        StreamUtil.close(is);
        is = null;
        StreamUtil.close(urlCon);
        urlCon = null;
    }

    public String toString() {
        return "HttpSSLClient[" + url + "]";
    }
}
