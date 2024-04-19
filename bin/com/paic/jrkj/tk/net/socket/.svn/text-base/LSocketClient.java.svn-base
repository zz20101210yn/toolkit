package com.paic.jrkj.tk.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.ProcessAction;
import com.paic.jrkj.tk.net.Connection;
import com.paic.jrkj.tk.net.ConnectionListener;
import com.paic.jrkj.tk.net.SendAndReceiver;
import com.paic.jrkj.tk.tools.msg.LVarMsgBean;
import com.paic.jrkj.tk.tools.msg.VarMsgBean;
import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.util.HexUtil;
import com.paic.jrkj.tk.util.StreamUtil;

public class LSocketClient implements SendAndReceiver, Connection<LSocketClient> {

    private final Log logger = LogFactory.getLog(LSocketClient.class);
    private long heartTime = 15 * 1000;
    private long rebindingTime = 15000;
    private SocketAddress socketAddr;
    private ProcessAction vProcessAction;
    private int packLenLen = 0;
    private volatile Socket socket;
    private volatile OutputStream os;
    private volatile InputStream is;
    private String workName = "default";
    private boolean inCluster = false;
    private volatile HeartHandler heartHandler;
    private volatile ReceivedHandler receivedHandler;
    private List<ConnectionListener<LSocketClient>> listenerList = new ArrayList<ConnectionListener<LSocketClient>>();
    private boolean needHeartResp; //need heart response
    private volatile boolean isRebinding;//isRebinging

    public LSocketClient(String ip, int port) {
        this(new InetSocketAddress(ip, port));
    }

    public LSocketClient(SocketAddress socketAddr) {
        this.socketAddr = socketAddr;
    }

    public void setProcessAction(ProcessAction vProcessAction) {
        this.vProcessAction = vProcessAction;
    }

    public void addConnectionListener(ConnectionListener<LSocketClient> listener) {
        listenerList.add(listener);
    }

    public void setPackLenLen(int packLenLen) {
        this.packLenLen = packLenLen;
    }

    public void setHeartTime(long heartTime) {
        this.heartTime = heartTime;
    }

    public void setRebindingTime(long rebindingTime) {
        this.rebindingTime = rebindingTime;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public void setInCluster(boolean inCluster) {
        this.inCluster = inCluster;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public SocketAddress getSocketAddress(){
    	return socketAddr;
    }

	public void setNeedHeartResp(boolean needHeartResp) {
		this.needHeartResp = needHeartResp;
	}

	public void connect()
            throws IOException {
        boolean isConnected = false;
        while (!isConnected) {
            try {
                bind();
                isConnected = true;
                logger.info("connect [" + socketAddr + "] Success..........");
            } catch (IOException e) {
                try {
                    Thread.sleep(rebindingTime);
                } catch (InterruptedException e1) {
                    logger.fatal("thread sleep exception", e1);
                }
            }
        }
    }

    private void bind()
            throws IOException {
        socket = new Socket();
        socket.setSoTimeout((int) heartTime*2);
        socket.connect(socketAddr);
        is = socket.getInputStream();
        os = socket.getOutputStream();
        if(receivedHandler == null || receivedHandler.isStop()){
        	receivedHandler = new ReceivedHandler(vProcessAction);
        	WorkManager.getWorkManager(workName).startWork(receivedHandler);
        }
        if(heartHandler == null || heartHandler.isStop()){
        	heartHandler = new HeartHandler();
        	WorkManager.getWorkManager(workName).startWork(heartHandler);
        }
        if (logger.isInfoEnabled()) {
            logger.info("connect [" + socketAddr + "] successed!");
        }
        for (ConnectionListener<LSocketClient> listener : listenerList) {
            listener.callAfterConnected(this);
        }
    }

    private synchronized void rebinding() {
    	if(isRebinding){//client is rebinding
    		logger.warn("client is rebinding");
    		return;
    	}
    	isRebinding = true;
        boolean rebind = false;
        unbinding();//before bind do unbind
        do {
            try {
                bind();
                rebind = true;
                logger.info("reconnect [" + socketAddr + "] Success..........");
            } catch (IOException e) {
            	unbinding();//bind exception, so unbinding
                try {
                    Thread.sleep(rebindingTime);
                } catch (InterruptedException e1) {
                    logger.fatal("thread sleep exception", e1);
                }
            }
        } while (!rebind && !inCluster);
        isRebinding = false;
    }

    private void unbinding(){
    	 for (ConnectionListener<LSocketClient> listener : listenerList) {
             listener.callBeforeDisconnected(this);
         }
         receivedHandler.stop();
         StreamUtil.close(is);
         StreamUtil.close(os);
         StreamUtil.close(socket);
         logger.info("client unbinded");
    }
    
    public void disconnect() {
    	heartHandler.stop();
    	unbinding();
    }

    public byte[] received()
            throws IOException {
        return null;
    }

    public synchronized void send(byte[] bizdata)
            throws IOException {
        if(socket==null || !socket.isConnected()){
            if(bizdata.length==0){
                rebinding();
            }
            throw new IOException("socket not connected!");
        }
        byte[] bytes = bizdata;
        if (packLenLen == 0) {
            bytes = new LVarMsgBean(bytes).toByteArray();
        } else if (packLenLen > 0) {
            bytes = new VarMsgBean(packLenLen, bytes).toByteArray();
        }
        try {
            os.write(bytes);
            os.flush();
            if (logger.isDebugEnabled()) {
                logger.debug("send[" + HexUtil.encode(bytes) + "]success!");
            }
        } catch (IOException e) {
            rebinding();
            try {
                os.write(bytes);
                os.flush();
                if (logger.isDebugEnabled()) {
                    logger.debug("send[" + HexUtil.encode(bytes) + "]success!");
                }
            } catch (IOException ex) {
                throw new IOException("send[" + HexUtil.encode(bytes) + "] fail!",ex);
            } catch (Exception exception) {
                throw new IOException("send[" + HexUtil.encode(bytes) + "] error!",exception);
            }
        }
    }

    public String toString() {
        return "LSocketClient[" + socketAddr + "]";
    }

    private class HeartHandler implements Runnable {

    	private boolean stop = false;
    	private long heartRespTime = System.currentTimeMillis();// heart response time
    	
        public void run() {
            while (!stop) {
                try {
                	send(new byte[0]);
                    Thread.sleep(heartTime);
                    if(needHeartResp){//need heart response
                    	 if((heartRespTime + 3 * heartTime) < System.currentTimeMillis()){//has not receive heart response 
                         	logger.error("no heart response");
                         	if(!stop){
                         		logger.info("HeartHandler rebinding ....");
                         		heartRespTime = System.currentTimeMillis();
                         		rebinding();
                         	}
                         }
                    }
                } catch (InterruptedException e) {
                    logger.fatal("send heart message fail", e);
                } catch (IOException e) {
                    logger.fatal("send heart message fail", e);
                }
            }
            logger.info("HeartHandler stopped");
        }
        
        public void stop(){
        	stop = true;
        }
        
        public boolean isStop(){
        	return stop;
        }

		public void setHeartRespTime(long heartRespTime) {
			this.heartRespTime = heartRespTime;
		}

    }

    private class ReceivedHandler implements Runnable {

    	private ProcessAction processAction;
    	
    	private boolean stop;
    	
    	public ReceivedHandler(ProcessAction processAction){
    		this.processAction = processAction;
    	}
    	
		public boolean isStop() {
			return stop;
		}

		public void stop() {
			stop = true;
		}

		public void run() {
            try {
                while (!stop) {
                    byte[] msg;
                    if (packLenLen == 0) {
                        msg = LVarMsgBean.parse(is).getBizData();
                    } else if (packLenLen > 0) {
                        msg = VarMsgBean.parse(is, packLenLen).getBizData();
                    } else {
                        msg = new byte[0];
                    }
                    if(msg == null || msg.length == 0){//heart response
                    	heartHandler.setHeartRespTime(System.currentTimeMillis());
                    	if(logger.isDebugEnabled()){
                    		logger.debug("heart response");
                    	}
                    	continue;
                    }
                    WorkManager.getWorkManager(workName).startWork(new ProcessDataThread(processAction.newInstance(), msg));
                }
            } catch (IOException e) {
                logger.fatal("received exception", e);
            }
            if(!stop){
            	logger.info("ReceivedHandler, rebinding....");
            	rebinding();
        	}else{
        		logger.info("ReceivedHandler stopped");
        	}
        }
    }

    private class ProcessDataThread implements Runnable {
        private ProcessAction processAction;
        private byte[] data;

        public ProcessDataThread(ProcessAction processAction, byte[] data) {
            this.processAction = processAction;
            this.data = data;
        }

        public void run() {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Receive Data[" + HexUtil.encode(data) + "]");
                }
                processAction.setAttribute("socketAddr", socketAddr);
                processAction.processAction(data);
            } catch (IOException e) {
                logger.fatal("received exception", e);
            }
        }
    }
}
