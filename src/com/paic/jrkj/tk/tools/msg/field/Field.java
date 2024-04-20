package com.paic.jrkj.tk.tools.msg.field;

import java.nio.charset.Charset;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 15:15:50 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 15:15:50
 */
public interface Field {

    String getName();

    byte[] toByteArray();

    byte[] getData();

    int length();

    int encape(int fromIndex,byte[] bytes);
    
    String toString();

    boolean isLogEnabled();

    Charset charset();
}
