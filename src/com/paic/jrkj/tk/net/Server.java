package com.paic.jrkj.tk.net;

import com.paic.jrkj.tk.ProcessAction;

import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-7 9:24:09 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-7 9:24:09
 */
public interface Server {

    void startup() throws IOException;

    void allowConnectClient(String client);

    void setProcessAction(ProcessAction vProcessAction);

    void shutdown();

    void setWmSelector(WMSelector wmSelector);

}
