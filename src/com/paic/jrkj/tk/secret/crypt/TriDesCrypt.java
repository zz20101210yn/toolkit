package com.paic.jrkj.tk.secret.crypt;

import com.paic.jrkj.tk.util.KeyUtil;

import javax.crypto.SecretKey;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:03:23 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:03:23
 */
public class TriDesCrypt extends SecretCrypt{

    public TriDesCrypt(String algorithm) {
        this(algorithm, null, null);
    }

    public TriDesCrypt() {
        this("DESede", null, null);
    }

    public TriDesCrypt(SecretKey deskey, String provider) {
        this("DESede", deskey, provider);
    }

    public TriDesCrypt(SecretKey deskey) {
        this("DESede", deskey, null);
    }

    public TriDesCrypt(String algorithm, SecretKey deskey, String provider) {
        super(algorithm, deskey, provider);
    }

    public void setSecretKey(byte[] deskey)
            throws SecurityException {
        setSecretKey(KeyUtil.readTriDesKey(deskey));
    }
}
