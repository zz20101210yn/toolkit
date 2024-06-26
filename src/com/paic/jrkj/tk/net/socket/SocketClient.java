package com.paic.jrkj.tk.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.tools.msg.LVarMsgBean;
import com.paic.jrkj.tk.tools.msg.VarMsgBean;
import com.paic.jrkj.tk.util.StreamUtil;
import com.paic.jrkj.tk.net.SendAndReceiver;
import com.paic.jrkj.tk.net.Connection;
import com.paic.jrkj.tk.net.ConnectionListener;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-7 11:03:55 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-7 11:03:55
 */
public class SocketClient implements SendAndReceiver, Connection<SocketClient> {

    private Log logger = LogFactory.getLog(SocketClient.class);
    private List<ConnectionListener<SocketClient>> listenerList = new Vector<ConnectionListener<SocketClient>>();
    private int packLenLen;
    private Socket socket;
    private OutputStream os;
    private InputStream is;

    public SocketClient(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            logger.fatal("connect fail!", e);
        }
    }

    public SocketClient(Socket socket) {
        this.socket = socket;
    }

    public void connect() throws IOException {
        os = socket.getOutputStream();
        is = socket.getInputStream();
        for(ConnectionListener<SocketClient> listener:listenerList){
            listener.callAfterConnected(this);
        }
    }

    public void addConnectionListener(ConnectionListener<SocketClient> listener) {
        listenerList.add(listener);
    }

    public void setPackLenLen(int packLenLen) {
        this.packLenLen = packLenLen;
    }

    public void setSocket(Socket socket)
            throws IOException {
        this.socket = socket;
        os = socket.getOutputStream();
        is = socket.getInputStream();
    }

    public synchronized void send(byte[] data)
            throws IOException {
        if (packLenLen == 0) {
            os.write(new LVarMsgBean(data).toByteArray());
        } else if (packLenLen > 0) {
            os.write(new VarMsgBean(packLenLen, data).toByteArray());
        } else {
            os.write(data);
        }
        os.flush();
    }

    public byte[] received()
            throws IOException {
        if (packLenLen == 0) {
            return LVarMsgBean.parse(is).getBizData();
        } else if (packLenLen > 0) {
            return VarMsgBean.parse(is, packLenLen).getBizData();
        }
        return null;
    }

    public void disconnect() {
        for(ConnectionListener<SocketClient> listener:listenerList){
            listener.callBeforeDisconnected(this);
        }
        StreamUtil.close(is);
        StreamUtil.close(os);
        StreamUtil.close(socket);
    }
    
    public boolean isConnected(){
    	if(socket != null){
    		return !socket.isClosed() && socket.isConnected();
    	}
    	return false;
    }
    
    public String getIp(){
    	return socket.getInetAddress().getHostAddress();
    }
    
    public int getPort(){
    	return socket.getPort();
    }
}
