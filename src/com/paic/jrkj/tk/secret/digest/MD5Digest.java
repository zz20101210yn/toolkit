package com.paic.jrkj.tk.secret.digest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-3-25 9:34:21 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-3-25 9:34:21
 */
public class MD5Digest extends MessageDigest{

    public MD5Digest(String provider) {
        super("MD5", provider);
    }

    public MD5Digest() {
        this(null);
    }
}
