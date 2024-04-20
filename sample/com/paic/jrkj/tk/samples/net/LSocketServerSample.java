package com.paic.jrkj.tk.samples.net;

import com.paic.jrkj.tk.net.socket.LSocketServer;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-22 10:53:30 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-22 10:53:30
 */
public class LSocketServerSample {

    public static void main(String[] args){
        LSocketServer server = new LSocketServer(12000,4);
        server.allowConnectClient("127.0.0.1");
        server.setProcessAction(new SampleProcessAction());
        server.setWorkName("default");
        server.startup();
    }

}
