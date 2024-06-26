package com.paic.jrkj.tk.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.DESKeySpec;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.KeySpec;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:00:54 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:00:54
 */
public class KeyUtil {

    private KeyUtil() {
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static final String PROVIDER_RSA = "BC";
    public static final String PROVIDER_TRIDES = "BC";
    public static final String PROVIDER_DES = "BC";
    public static final String PROVIDER_SHA1 = "BC";
    public static final String PROVIDER_MD5 = "BC";
    public static final String PROVIDER_AES = "BC";

    private static SecretKey genSecretKey(String alg, String provider)
            throws SecurityException {
        try {
            KeyGenerator keygen = provider == null ? KeyGenerator.getInstance(alg)
                    : KeyGenerator.getInstance(alg, provider);
            return keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("BUILD SECRET KEY FAIL", e);
        } catch (NoSuchProviderException e) {
            throw new SecurityException("BUILD SECRET KEY FAIL", e);
        }
    }

    public static byte[] buildDesKey()
            throws SecurityException {
        return genSecretKey("DES", PROVIDER_TRIDES).getEncoded();
    }

    public static byte[] buildTriDesKey()
            throws SecurityException {
        return genSecretKey("DESede", PROVIDER_TRIDES).getEncoded();
    }

    public static byte[] buildAesKey()
            throws SecurityException {
        return genSecretKey("AES", PROVIDER_AES).getEncoded();
    }

    public static KeyPair buildRSAKey() {
        return buildRSAKey(1024);
    }

    public static KeyPair buildRSAKey(int length) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", PROVIDER_RSA);
            kpg.initialize(length);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("BUILD RSA KEY FAIL,NO SUCH ALGORITHM", e);
        } catch (Exception e) {
            throw new SecurityException("BUILD RSA KEY FAIL", e);
        }
    }

    public static SecretKey readDesKey(byte[] key)
            throws SecurityException{
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES", PROVIDER_DES);
            KeySpec spec = new DESKeySpec(key);
            return factory.generateSecret(spec);
        } catch (Exception e) {
            throw new SecurityException( "READ DES KEY FAIL", e);
        }
    }

    public static SecretKey readTriDesKey(byte[] key)
            throws SecurityException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("DESede");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);
            kgen.init(112, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, "DESede");
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("READ TRI-DES KEY FAIL", e);
        }
    }

    public static SecretKey readTriDesKey2(byte[] key)
            throws SecurityException {
        return new SecretKeySpec(key, "DESede");
    }

    public static PublicKey readPublicKey(byte[] key)
            throws SecurityException {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA", PROVIDER_RSA);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(key);
            return factory.generatePublic(spec);
        } catch (NoSuchProviderException e) {
            throw new SecurityException("READ PUBLIC KEY FAIL", e);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("READ PUBLIC KEY FAIL", e);
        } catch (InvalidKeySpecException e) {
            throw new SecurityException("READ PUBLIC KEY FAIL", e);
        }
    }

    public static PrivateKey readPrivateKey(byte[] key)
            throws SecurityException {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA", PROVIDER_RSA);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
            return factory.generatePrivate(spec);
        } catch (NoSuchProviderException e) {
            throw new SecurityException("READ PRIVATE KEY FAIL", e);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("READ PRIVATE KEY FAIL", e);
        } catch (InvalidKeySpecException e) {
            throw new SecurityException("READ PRIVATE KEY FAIL", e);
        }
    }

    public static SecretKey readAESKey(byte[] encoded)
            throws SecurityException {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES",PROVIDER_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encoded);
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, "AES");
        } catch (Exception e) {
            throw new SecurityException( "READ AES KEY FAIL", e);
        }
    }

    public static SecretKey readAESKey2(byte[] encoded)
            throws SecurityException {
        return new SecretKeySpec(encoded,"AES");
    }
}
