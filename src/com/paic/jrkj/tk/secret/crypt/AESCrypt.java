package com.paic.jrkj.tk.secret.crypt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.SecretKey;

import com.paic.jrkj.tk.util.KeyUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-2-26 15:09:16 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-2-26 15:09:16
 */
public class AESCrypt extends SecretCrypt{

    private final Log logger = LogFactory.getLog(AESCrypt.class);

    public AESCrypt() {
        this("AES", null, null);
    }

    public AESCrypt(String algorithm, SecretKey aeskey, String provider) {
        super(algorithm, aeskey, provider);
    }

    public AESCrypt(SecretKey aeskey, String provider) {
        this("AES", aeskey, provider);
    }

    public AESCrypt(SecretKey aeskey) {
        this("AES", aeskey, null);
    }

    public AESCrypt(String algorithm) {
        this(algorithm, null, null);
    }

    public void setSecretKey(byte[] aeskey)
            throws SecurityException {
        setSecretKey(KeyUtil.readAESKey(aeskey));
    }
}
