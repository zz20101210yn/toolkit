package com.paic.jrkj.tk.samples.net;

import com.paic.jrkj.tk.net.socket.LSocketClient;

import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-22 10:53:30 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-22 10:53:30
 */
public class LSocketClientSample {

    public static void main(String[] args) {
        LSocketClient client = new LSocketClient("127.0.0.1", 12000);
        client.setHeartTime(5000);
        client.setRebindingTime(2000);
        client.setPackLenLen(0);
        client.setWorkName("default");
        client.setProcessAction(new SampleProcessAction());
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}