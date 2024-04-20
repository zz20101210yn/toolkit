package com.paic.jrkj.tk.secret.digest;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:05:03 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:05:03
 */
public class SHA1Digest extends MessageDigest{

    public SHA1Digest(String provider) {
        super("SHA-1", provider);
    }

    public SHA1Digest() {
        this(null);
    }
}
