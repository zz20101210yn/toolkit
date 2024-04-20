/**
 * Copyright 2011-2012 www.wanlitong.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paic.jrkj.tk.secret.digest;

import com.paic.jrkj.tk.util.ByteUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-2-16 10:03:47 $
 * @serial 1.0
 * @since 2016-2-16 10:03:47
 */
public class HmacDigest implements Digest {

    private String macName;
    private SecretKey secretKey;
    private final Log logger = LogFactory.getLog(getClass());

    public HmacDigest(String macName) {
        this.macName = macName;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = new SecretKeySpec(secretKey, macName);
    }

    public byte[] digest(byte[] src)
            throws SecurityException {
        try {
            Mac mac = Mac.getInstance(macName);
            mac.init(secretKey);
            mac.update(src);
            return mac.doFinal();
        } catch (IllegalStateException e) {
            throw new SecurityException(e);
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    public byte[] digest(InputStream is)
            throws SecurityException {
        try {
            Mac mac = Mac.getInstance(macName);
            mac.init(secretKey);
            byte[] data = new byte[1024 * 4];
            int n;
            while ((n = is.read(data)) != -1) {
                mac.update(data, 0, n);
            }
            return mac.doFinal();
        } catch (IllegalStateException e) {
            throw new SecurityException(e);
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (IOException e) {
            throw new SecurityException(e);
        }
    }

    public SecretKey generateKey()
            throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(macName);
        return keyGenerator.generateKey();
    }

    public boolean verify(byte[] src, byte[] digest) {
        try {
            return ByteUtil.isEquals(digest(src), digest);
        } catch (SecurityException e) {
            logger.fatal("", e);
        }
        return false;
    }

    public boolean verify(InputStream is, byte[] digest) {
        try {
            return ByteUtil.isEquals(digest(is), digest);
        } catch (SecurityException e) {
            logger.fatal("", e);
        }
        return false;
    }

    public String toString() {
        return "HmacDigest["+macName+"]";
    }
}
