package com.paic.jrkj.tk.secret.crypt;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:00:23 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:00:23
 */
public interface Crypt {

    byte[] encrypt(byte[] src) throws SecurityException;

    byte[] decrypt(byte[] enc) throws SecurityException;
}
