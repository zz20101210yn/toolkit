package com.paic.jrkj.tk;

import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 17:21:47 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 17:21:47
 */
public interface ProcessAction<E extends ProcessAction> {

    void setAttribute(String name,Object value);
	
    byte[] processAction(byte[] bytes) throws IOException;

    E newInstance();
}
