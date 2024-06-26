package com.paic.jrkj.tk.secret.crypt;

import com.paic.jrkj.tk.util.KeyUtil;

import javax.crypto.SecretKey;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 20:14:54 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 20:14:54
 */
public class DesCrypt extends SecretCrypt{

    public DesCrypt(String algorithm) {
        this(algorithm, null, null);
    }

    public DesCrypt() {
        this("DES", null, null);
    }

    public DesCrypt(SecretKey deskey, String provider) {
        this("DES", deskey, provider);
    }

    public DesCrypt(SecretKey deskey) {
        this("DES", deskey, null);
    }

    public DesCrypt(String algorithm, SecretKey deskey, String provider) {
        super(algorithm, deskey, provider);
    }

    public void setSecretKey(byte[] deskey)
            throws SecurityException {
        setSecretKey(KeyUtil.readDesKey(deskey));
    }
}
