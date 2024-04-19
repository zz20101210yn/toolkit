package com.paic.jrkj.tk.samples.util;

import com.paic.jrkj.tk.util.KeyUtil;
import com.paic.jrkj.tk.secret.crypt.Crypt;
import com.paic.jrkj.tk.secret.crypt.AESCrypt;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 15:00:50 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 15:00:50
 */
public class KeyUtilSample {

    public static KeyPair genRSA(String provider, int length)
            throws SecurityException {
        try {
            KeyPairGenerator kpg = provider == null ? KeyPairGenerator.getInstance("RSA")
                    : KeyPairGenerator.getInstance("RSA", provider);
            kpg.initialize(length);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("BUILD RSA KEY FAIL,NO SUCH ALGORITHM", e);
        } catch (Exception e) {
            throw new SecurityException( "BUILD RSA KEY FAIL", e);
        }
    }

    public static void main(String[] args){
        byte[] key = KeyUtil.buildAesKey();
        SecretKey sk = KeyUtil.readAESKey(key);
        Crypt aes = new AESCrypt(sk);
        byte[] enc= aes.encrypt("buildAesKey".getBytes());
        System.out.println(new String(aes.decrypt(enc)));
    }

}
