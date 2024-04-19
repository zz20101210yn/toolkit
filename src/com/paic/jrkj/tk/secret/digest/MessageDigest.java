package com.paic.jrkj.tk.secret.digest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.paic.jrkj.tk.util.ByteUtil;

import java.io.InputStream;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:10:52 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:10:52
 */
public class MessageDigest implements Digest{

    private byte[] prevKey;
    private byte[] nextKey;
    private String algorithm;
    private String provider;
    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * @param algorithm algorithm
     * @param provider  provider
     */
    public MessageDigest(String algorithm, String provider) {
        this.algorithm = algorithm;
        this.provider = provider;
    }

    /**
     * @param algorithm algorithm
     */
    public MessageDigest(String algorithm) {
        this(algorithm, null);
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setPrevKey(byte[] prevKey) {
        this.prevKey = prevKey;
    }

    public void setNextKey(byte[] nextKey) {
        this.nextKey = nextKey;
    }

    public byte[] digest(byte[] bytes)
            throws SecurityException {
        try {
            java.security.MessageDigest digit = provider == null ? java.security.MessageDigest.getInstance(algorithm)
                    : java.security.MessageDigest.getInstance(algorithm, provider);
            if(this.prevKey==null && this.nextKey==null){
                digit.update(bytes);
            }else{
                digit.update(ByteUtil.join(this.prevKey,bytes,this.nextKey));
            }
            return digit.digest();
        } catch (Exception e) {
            throw new SecurityException( "BUILD SIGNATURE FAIL", e);
        }
    }

    public byte[] digest(InputStream is)
            throws SecurityException {
        try {
            java.security.MessageDigest digit = provider == null ? java.security.MessageDigest.getInstance(algorithm)
                    : java.security.MessageDigest.getInstance(algorithm, provider);
            if(this.prevKey!=null){
                digit.update(this.prevKey);
            }
            byte[] data = new byte[1024*4];
            int n;
            while((n = is.read(data))!=-1){
                digit.update(data,0,n);
            }
            if(this.nextKey!=null){
                digit.update(this.nextKey);
            }

            return digit.digest();
        } catch (Exception e) {
            throw new SecurityException( "BUILD SIGNATURE FAIL", e);
        }
    }

    public boolean verify(byte[] bytes, byte[] digest) {
        try {
            return ByteUtil.isEquals(digest(bytes), digest);
        } catch (SecurityException e) {
            logger.fatal("",e);
        }
        return false;
    }


    public boolean verify(InputStream is, byte[] digest) {
        try {
            return ByteUtil.isEquals(digest(is), digest);
        } catch (SecurityException e) {
            logger.fatal("",e);
        }
        return false;
    }
}
