package com.paic.jrkj.tk.secret.crypt;

import javax.crypto.Cipher;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;

import com.paic.jrkj.tk.tools.lock.LockPool;
import com.paic.jrkj.tk.util.KeyUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:03:07 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:03:07
 */
public class RSACrypt implements Crypt{

    private transient PublicKey pubKey;
    private transient PrivateKey priKey;
    private String cryptAlgorithm = "RSA/ECB/NoPadding";// "RSA/ECB/NoPadding";
    private String provider;

    public RSACrypt() {
        this(null);
    }

    /**
     * @param provider provider
     */
    public RSACrypt(String provider) {
        this.provider = provider;
    }

    /**
     * @param provider provider
     * @param priKey   PrivateKey
     * @param pubKey   PublicKey
     */
    public RSACrypt(String provider, PrivateKey priKey, PublicKey pubKey) {
        this.provider = provider;
        this.priKey = priKey;
        this.pubKey = pubKey;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAlgorithm(String algorithm) {
        this.cryptAlgorithm = algorithm;
    }

    public String getProvider() {
        return provider;
    }

    public PublicKey getPublicKey() {
        return pubKey;
    }

    public PrivateKey getPrivateKey() {
        return priKey;
    }

    public void setPublicKey(PublicKey pubKey) {
        this.pubKey = pubKey;
    }

    public void setPrivateKey(PrivateKey priKey) {
        this.priKey = priKey;
    }

    public void setKeyPair(KeyPair pair) {
        this.pubKey = pair.getPublic();
        this.priKey = pair.getPrivate();
    }

    public void setPublicKey(byte[] pubKey)
            throws SecurityException {
        this.pubKey = KeyUtil.readPublicKey(pubKey);
    }

    public void setPrivateKey(byte[] priKey)
            throws SecurityException {
        this.priKey = KeyUtil.readPrivateKey(priKey);
    }

    public byte[] encrypt(byte[] message)
            throws SecurityException {
        if (pubKey == null) {
            throw new SecurityException( "PublicKey is null");
        }
        LockPool lock = LockPool.getLockPool("rsa");
        try {
            lock.fetchLock();
            Cipher cipher = getProvider() == null ? Cipher.getInstance(cryptAlgorithm)
                    : Cipher.getInstance(cryptAlgorithm, getProvider());
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(message);
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new SecurityException( "ENCRYPT DATA FAIL", e);
        } finally {
            lock.releaseLock();
        }
    }

    public byte[] decrypt(byte[] encBytes)
            throws SecurityException {
        if (priKey == null) {
            throw new SecurityException( "PrivateKey is null");
        }
        LockPool lock = LockPool.getLockPool("rsa");
        try {
            lock.fetchLock();
            Cipher cipher = getProvider() == null ? Cipher.getInstance(cryptAlgorithm)
                    : Cipher.getInstance(cryptAlgorithm, getProvider());
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return cipher.doFinal(encBytes);
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new SecurityException("DECRYPT DATA FAIL", e);
        } finally {
            lock.releaseLock();
        }
    }
}
