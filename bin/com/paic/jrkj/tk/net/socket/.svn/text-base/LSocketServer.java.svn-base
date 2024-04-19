package com.paic.jrkj.tk.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.ProcessAction;
import com.paic.jrkj.tk.net.Server;
import com.paic.jrkj.tk.net.WMSelector;
import com.paic.jrkj.tk.tools.msg.LVarMsgBean;
import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.util.HexUtil;
import com.paic.jrkj.tk.util.StreamUtil;

public class LSocketServer implements Server {

    private final Log logger = LogFactory.getLog(LSocketServer.class);
    private Set<String> allowIpArray = new HashSet<String>();
    private List<String> allowIpPrefixList = new Vector<String>();
    private int maxConnections = 50;
    private int serverPort;
    private ProcessAction vProcessAction;
    private ServerSocket server;
    private List<SocketWork> workList = new Vector<SocketWork>();
    private volatile boolean shutdown = false;
    private String workName = "default";
    private Set<String> heartRespSet = new HashSet<String>();//need heart response set
    private WMSelector wmSelector = new WMSelector.DefaultWMSelector();
    private Timer monitorTimer;//monitor client socket alive
    private final long MONITOR_DELAY_TIME = 3000;
    private final long MONITOR_PERIOD_TIME = 60000;
    
    public LSocketServer(int serverPort, int maxConnections) {
        this.serverPort = serverPort;
        this.maxConnections = maxConnections;
    }

    public LSocketServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public void needHeartResp(String ip) {
    	heartRespSet.add(ip);
    }
    
    public void setHeartRespSet(Set<String> heartRespSet){
    	this.heartRespSet = heartRespSet;
    }
    
	@Override
    public void allowConnectClient(String ip) {
        int index = ip.indexOf("*");
        if (index == -1) {
            allowIpArray.add(ip);
        } else {
            allowIpPrefixList.add(ip.substring(0, index));
        }
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public void setWmSelector(WMSelector wmSelector) {
        this.wmSelector = wmSelector;
    }

    @Override
    public void setProcessAction(ProcessAction processAction) {
        this.vProcessAction = processAction;
    }

    @Override
    public void shutdown() {
        shutdown = true;
        monitorTimer.cancel();
        StreamUtil.close(server);
    }

    @Override
    public void startup() {
        try {
            shutdown = false;
            server = new ServerSocket(this.serverPort, maxConnections);
            
            monitorTimer = new Timer();
            monitorTimer.schedule(new TimerTask(){
				@Override
				public void run() {
					for(Iterator<SocketWork> it = workList.iterator(); it.hasNext();){
						SocketWork sw = it.next();
						if(!sw.checkAlived()){
							it.remove();
						}
					}
				}}, MONITOR_DELAY_TIME, MONITOR_PERIOD_TIME);
            
            while (!shutdown) {
                doAccept();
            }
        } catch (IOException e) {
            logger.error("socketserver exception", e);
        }
    }

    private void doAccept() {
        try {
            Socket socket = server.accept();
            String ip = socket.getInetAddress().getHostAddress();
            if (allowIpArray.size() != 0 || allowIpPrefixList.size() != 0) {
                boolean allow = false;
                if (allowIpArray.contains(ip)) {
                    allow = true;
                } else {
                    for (String prefix : allowIpPrefixList) {
                        if (ip.startsWith(prefix)) {
                            allow = true;
                        }
                    }
                }
                if (!allow) {
                    logger.warn("forbind access for ip[" + ip + "]");
                    StreamUtil.close(socket);
                    return;
                }
            }
            SocketWork socketWork = new SocketWork(socket, serverPort, vProcessAction);
            WorkManager.getWorkManager(workName).startWork(socketWork);
            workList.add(socketWork);
        } catch (IOException e) {
            logger.error("socketserver exception", e);
        }
    }

    private class SocketWork implements Runnable {

        private int serverPort;
        private Socket socket;
        private final ProcessAction processAction;
        
        private final long clientMonitorTime = 3 * 60 * 1000;
        private long receiveHeartTime = System.currentTimeMillis();
        
        public SocketWork(Socket socket, int serverPort, ProcessAction processAction) {
            this.socket = socket;
            this.serverPort = serverPort;
            this.processAction = processAction;
        }

        public boolean checkAlived(){
        	if(System.currentTimeMillis() - receiveHeartTime > clientMonitorTime){
				logger.error("do not receive heart for [" + clientMonitorTime
						+ "] ms, client ip [" + socket.getInetAddress() + "], close it");
        		StreamUtil.close(socket);
        		return false;
        	}
        	return true;
        }
        
		public void run() {
            InputStream is = null;
            OutputStream out = null;
            try {
                is = socket.getInputStream();
                out = socket.getOutputStream();
                while (!shutdown) {
                    byte[] bizData = LVarMsgBean.parse(is).getBizData();
                    if (bizData == null || bizData.length == 0) {// heart
                    	if(heartRespSet.size() == 0 || heartRespSet.contains(socket.getInetAddress().getHostAddress())){//need heart response
                    		out.write(new LVarMsgBean(new byte[0]).toByteArray());//response
                        	out.flush();
                    	}
                        if (logger.isDebugEnabled()) {
                            logger.debug("receive heart message");
                        }
                        receiveHeartTime = System.currentTimeMillis();
                        continue;
                    }
                    ProcessAction processAction = this.processAction.newInstance();
                    processAction.setAttribute("socket", socket);
                    processAction.setAttribute("serverPort", Integer.valueOf(serverPort));
                    WorkManager wm = wmSelector.select(socket.getInetAddress(), workName);
                    wm.startWork(new SocketProcessWork(processAction, bizData));
                }
            } catch (IOException e) {
                logger.fatal("socket read exception ["+socket.getInetAddress()+"]");//异常堆栈就不打印了
            } finally {
                logger.debug("socket closed!");
                StreamUtil.close(is);
                StreamUtil.close(out);
                StreamUtil.close(socket);
//                workList.remove(this);
            }
        }
    }

    private class SocketProcessWork implements Runnable {

        private ProcessAction processAction;
        private byte[] data;

        public SocketProcessWork(ProcessAction processAction, byte[] data) {
            this.processAction = processAction;
            this.data = data;
        }

        public void run() {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Receive Data[" + HexUtil.encode(data) + "]");
                }
                processAction.processAction(data);
            } catch (IOException e) {
                logger.fatal("received exception", e);
            }
        }
    }
}
