package com.paic.jrkj.tk.net;

import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-8 13:30:30 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-8 13:30:30
 */
public interface Connection<E extends Connection> {

    void connect() throws IOException;

    void addConnectionListener(ConnectionListener<E> listener);

    void disconnect();
}
