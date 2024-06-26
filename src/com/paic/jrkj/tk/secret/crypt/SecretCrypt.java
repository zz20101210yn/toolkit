package com.paic.jrkj.tk.secret.crypt;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:40:16 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:40:16
 */
public abstract class SecretCrypt implements Crypt {

    private String provider;
    private transient SecretKey key;
    /**
     * DES,DESede,Blowfish,
     * DES/ECB/NoPadding
     * TripleDES/ECB/NoPadding
     * AES/CBC/PKCS5Padding
     */
    private String algorithm;
    private byte[] ivKey;

    protected SecretCrypt(String algorithm, SecretKey key, String provider) {
        this.algorithm = algorithm;
        this.key = key;
        this.provider = provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setSecretKey(SecretKey aeskey) {
        this.key = aeskey;
    }

    public void setIVKey(byte[] key) {
        this.ivKey = key;
    }

    public byte[] encrypt(byte[] input)
            throws SecurityException {
        if (key == null) {
            throw new SecurityException( "SecretKey is null!");
        }
        try {
            Cipher c1 = getProvider() == null ? Cipher.getInstance(this.algorithm)
                    : Cipher.getInstance(this.algorithm, getProvider());
            if (ivKey == null) {
                c1.init(Cipher.ENCRYPT_MODE, key);
            } else {
                //使用CBC模式，需要一个向量iv，可增加加密算法的强度x
                IvParameterSpec iv = new IvParameterSpec(ivKey);
                c1.init(Cipher.ENCRYPT_MODE, key, iv);
            }
            return c1.doFinal(input);
        } catch (Exception e) {
            throw new SecurityException( "ENCRYPT DATA FAIL", e);
        }
    }

    public byte[] decrypt(byte[] input)
            throws SecurityException {
        if (key == null) {
            throw new SecurityException( "SecretKey is null!");
        }
        try {
            Cipher c1 = getProvider() == null ? Cipher.getInstance(this.algorithm)
                    : Cipher.getInstance(this.algorithm, getProvider());
            if (ivKey == null) {
                c1.init(Cipher.DECRYPT_MODE, key);
            } else {
                //使用CBC模式，需要一个向量iv，可增加加密算法的强度x
                IvParameterSpec iv = new IvParameterSpec(ivKey);
                c1.init(Cipher.DECRYPT_MODE, key, iv);
            }
            return c1.doFinal(input);
        } catch (Exception e) {
            throw new SecurityException("DECRYPT DATA FAIL", e);
        }
    }
}
