package com.paic.jrkj.tk.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.ProcessAction;
import com.paic.jrkj.tk.net.Server;
import com.paic.jrkj.tk.net.WMSelector;
import com.paic.jrkj.tk.tools.msg.LVarMsgBean;
import com.paic.jrkj.tk.tools.msg.VarMsgBean;
import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.util.StreamUtil;

/**
 * <p/>
 * 
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-7 9:24:52 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-7 9:24:52
 */
public class SocketServer implements Server {

	private Log logger = LogFactory.getLog(SocketServer.class);
	private List<String> allowIpArray = new Vector<String>();
	private List<String> allowIpPrefixList = new Vector<String>();
	private ProcessAction vProcessAction;
    private String workName = "default";
	private ServerSocket server;
    private int packLenLen;
	private int port;
	private boolean shutdown = false;
    private WMSelector wmSelector = new WMSelector.DefaultWMSelector();
    private int maxConnectNum = 30;

	public SocketServer(int port) {
		this.port = port;
	}

    public void setWorkName(String workName) {
        this.workName = workName;
    }

	public void setMaxConnectNum(int maxConnectNum) {
		this.maxConnectNum = maxConnectNum;
	}

    public void setPackLenLen(int packLenLen) {
        this.packLenLen = packLenLen;
    }

    public void shutdown() {
		shutdown = true;
		StreamUtil.close(server);
	}

	public void setProcessAction(ProcessAction vProcessAction) {
		this.vProcessAction = vProcessAction;
	}

    public void setWmSelector(WMSelector wmSelector) {
        this.wmSelector = wmSelector;
    }

    public void allowConnectClient(String clientIp) {
        logger.info("allow client["+clientIp+"]");
        int index = clientIp.indexOf("*");
        if(index ==-1){
            allowIpArray.add(clientIp);
        }else{
            allowIpPrefixList.add(clientIp.substring(0,index));
        }
	}

	public void startup() throws IOException {
		try {
			server = new ServerSocket(this.port, maxConnectNum);
			do{
				try{
					final Socket socket = server.accept();
					String ip = socket.getInetAddress().getHostAddress();
					boolean allow = false;
                    if(allowIpArray.contains(ip)){
                        allow = true;
                    } else{
                        for(String prefix :allowIpPrefixList){
                            if(ip.startsWith(prefix)) {
                                allow = true;
                                break;
                            }
                        }
                    }
                    if(!allow){
                        logger.warn("forbind access for ip["+ip+"]");
                        StreamUtil.close(socket);
                        continue;
                    }
					WorkManager wm = wmSelector.select(socket.getInetAddress(), workName);
					wm.startWork(new SocketProcessThread(socket,vProcessAction.newInstance()));
				}catch(Exception e){
					logger.fatal("socketserver exception", e);
				}
			}while(!shutdown);
		} catch (IOException e) {
			logger.fatal("socketserver exception", e);
		}
	}
	
	private class SocketProcessThread implements  Runnable {
		
		private Socket socket;
		private final ProcessAction process;
		
		public SocketProcessThread(Socket socket,ProcessAction process){
			this.socket = socket;
			this.process = process;
		}
		
		public void run() {
			 InputStream is = null;
			 OutputStream os = null;
			try {
				is = socket.getInputStream();
                byte[] bizData;
                if(packLenLen==0){
                    bizData = LVarMsgBean.parse(is).getBizData();
                }else{
                    bizData = VarMsgBean.parse(is,packLenLen).getBizData();
                }
				byte[] ret = process.processAction(bizData);
                if(packLenLen==0){
                    ret = new LVarMsgBean(ret).toByteArray();
                }else{
                    ret = new VarMsgBean(packLenLen,ret).toByteArray();
                }
                os = socket.getOutputStream();
				os.write(ret);
				os.flush();
			} catch (IOException e) {
				logger.error("socket exception", e);
			} finally {
                StreamUtil.close(is);
                StreamUtil.close(os);
                StreamUtil.close(socket);
			}
		}
	}
}
