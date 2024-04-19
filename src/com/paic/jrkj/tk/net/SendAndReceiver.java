package com.paic.jrkj.tk.net;

import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 15:52:49 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 15:52:49
 */
public interface SendAndReceiver {

    void send(byte[] data)throws IOException;

    byte[] received()throws IOException;

}
