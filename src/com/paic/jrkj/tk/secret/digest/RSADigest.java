package com.paic.jrkj.tk.secret.digest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.Signature;

import com.paic.jrkj.tk.tools.lock.LockPool;
import com.paic.jrkj.tk.util.KeyUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:03:57 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:03:57
 */
public class RSADigest implements Digest{

    private transient PublicKey pubKey;
    private transient PrivateKey priKey;
    private String digestAlgorithm = "SHA1withRSA";//"MD5withRSA"
    private String provider;
    private final Log logger = LogFactory.getLog(RSADigest.class);

    public RSADigest() {
        this(null);
    }

    /**
     * @param provider provider
     */
    public RSADigest(String provider) {
        this.provider = provider;
    }

    /**
     * @param provider provider
     * @param priKey   PrivateKey
     * @param pubKey   PublicKey
     */
    public RSADigest(String provider, PrivateKey priKey, PublicKey pubKey) {
        this.provider = provider;
        this.priKey = priKey;
        this.pubKey = pubKey;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAlgorithm(String algorithm) {
        this.digestAlgorithm = algorithm;
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

    public byte[] digest(byte[] message)
            throws SecurityException {
        if (priKey == null) {
            throw new SecurityException( "PrivateKey is null");
        }
        LockPool lock = LockPool.getLockPool("rsa");
        try {
            lock.fetchLock();
            Signature signet = getProvider() == null ? Signature.getInstance(digestAlgorithm) :
                    Signature.getInstance(digestAlgorithm, getProvider());
            signet.initSign(priKey);
            signet.update(message);
            return signet.sign();
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new SecurityException( "BUILD SIGNATURE FAIL", e);
        } finally {
            lock.releaseLock();
        }
    }

    public boolean verify(byte[] message, byte[] chkValue) {
        if (pubKey == null) {
            logger.fatal("PublicKey is null");
            return false;
        }
        LockPool lock = LockPool.getLockPool("rsa");
        try {
            lock.fetchLock();
            Signature signet = getProvider() == null ? Signature.getInstance(digestAlgorithm) :
                    Signature.getInstance(digestAlgorithm, getProvider());
            signet.initVerify(pubKey);
            signet.update(message);
            return signet.verify(chkValue);
        } catch (Exception e) {
            logger.fatal("",e);
        } finally {
            lock.releaseLock();
        }
        return false;
    }

    public byte[] digest(InputStream is) throws SecurityException {
        if (priKey == null) {
            throw new SecurityException( "PrivateKey is null");
        }
        LockPool lock = LockPool.getLockPool("rsa");
        try {
            lock.fetchLock();
            Signature signet = getProvider() == null ? Signature.getInstance(digestAlgorithm) :
                    Signature.getInstance(digestAlgorithm, getProvider());
            signet.initSign(priKey);
            byte[] data = new byte[1024*4];
            int len;
            while((len = is.read(data))!=-1){
                signet.update(data,0,len);
            }
            return signet.sign();
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new SecurityException( "BUILD SIGNATURE FAIL", e);
        } finally {
            lock.releaseLock();
        }
    }

    public boolean verify(InputStream is, byte[] chkValue) {
        if (pubKey == null) {
            logger.fatal("PublicKey is null");
            return false;
        }
        LockPool lock = LockPool.getLockPool("rsa");
        try {
            lock.fetchLock();
            Signature signet = getProvider() == null ? Signature.getInstance(digestAlgorithm) :
                    Signature.getInstance(digestAlgorithm, getProvider());
            signet.initVerify(pubKey);
            byte[] data = new byte[1024*4];
            int len;
            while((len = is.read(data))!=-1){
                signet.update(data,0,len);
            }
            return signet.verify(chkValue);
        } catch (Exception e) {
            logger.fatal("",e);
        } finally {
            lock.releaseLock();
        }
        return false;
    }

}
