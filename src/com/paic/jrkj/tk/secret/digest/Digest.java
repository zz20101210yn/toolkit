package com.paic.jrkj.tk.secret.digest;

import java.io.InputStream;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:00:29 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:00:29
 */
public interface Digest {

    byte[] digest(byte[] src) throws SecurityException;

    byte[] digest(InputStream is)
            throws SecurityException;

    boolean verify(byte[] src,byte[] degist);

    boolean verify(InputStream is, byte[] digest);
}
