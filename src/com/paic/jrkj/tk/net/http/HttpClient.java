package com.paic.jrkj.tk.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.net.Connection;
import com.paic.jrkj.tk.net.ConnectionListener;
import com.paic.jrkj.tk.net.SendAndReceiver;
import com.paic.jrkj.tk.util.StreamUtil;


/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 15:54:30 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 15:54:30
 */
public class HttpClient implements SendAndReceiver, Connection<HttpClient> {

    private final Log logger = LogFactory.getLog(HttpClient.class);

    private static final String PROPERTY_TYPE = "Content-Type";
    private static final String PROPERTY = "application/x-www-form-urlencoded";

    private final String url;
    private final int timeout;
    private String requestMethod = "POST";
    
    private HttpURLConnection conn;
    private InputStream is;
    private OutputStream os;

    private Map<String, String> requestPropertys = new HashMap<String, String>();
    
    public HttpClient(String url, int timeout) {
        this.url = url;
        this.timeout = timeout;
    }

    public void setRequestMethod(String method) {
        this.requestMethod = method;
    }
    
    public void addRequestProperty(String type, String property){
    	this.requestPropertys.put(type, property);
    }
    
    private void setRequestProperty(){
    	for(Map.Entry<String, String> e : requestPropertys.entrySet()){
        	conn.setRequestProperty(e.getKey(), e.getValue());
        }
    }
    
    public void connect()
            throws IOException {
        URL url = new URL(this.url);
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        System.setProperty("sun.net.client.defaultConnectTimeout", this.timeout+""); 
        System.setProperty("sun.net.client.defaultReadTimeout", this.timeout+""); 
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(this.timeout);
        conn.setReadTimeout(this.timeout); 
        conn.setRequestMethod(requestMethod);
        if("POST".equals(requestMethod)){
        	 conn.setDoOutput(true);
        }
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty(PROPERTY_TYPE, PROPERTY);
        this.setRequestProperty();
        conn.connect();
    }

    public void addConnectionListener(ConnectionListener<HttpClient> listener) {
    }

    public void disconnect() {
        StreamUtil.close(is);
        StreamUtil.close(os);
        StreamUtil.close(conn);
    }

    public byte[] received()
            throws IOException {
        if (conn.getResponseCode() == 200) {
            is = conn.getInputStream();
            byte[] bytes = StreamUtil.toByteArray(is);
            if (logger.isDebugEnabled()) {
                logger.debug(new StringBuffer("HttpClient[")
                        .append(this.url).append("] receive data:len=[")
                        .append(bytes.length).append("];content=[")
                        .append(new String(bytes)).append("]").toString());
            }
            return bytes;
        }
        logger.error("url ["+this.url+"], responseMessage ["+conn.getResponseMessage()+"] , responseCode ["+conn.getResponseCode()+"]");
        return null;
    }

    @Override
    public void send(byte[] data)
            throws IOException {
    	os = conn.getOutputStream();
        os.write(data, 0, data.length);
        os.flush();
    }

    public OutputStream getOutputStream() 
    		throws IOException{
    	return conn.getOutputStream();
    }
}
