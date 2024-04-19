package com.paic.jrkj.tk.secret.digest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014/6/24 10:34 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014/6/24 10:34
 */
public abstract class DigestFactory {

    public static final int DIGEST_MD5 = 0;
    public static final int DIGEST_SHA1 = 1;
    public static final int DIGEST_RSA_WITH_MD5 = 2;
    public static final int DIGEST_RSA_WITH_SHA1 = 3;

    protected final Log logger = LogFactory.getLog(getClass());

    private Map<String,Digest> digestMap;
    private int updateInterval = 60;
    private long lstUpdateTime = 0L;

    public DigestFactory() {}

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    protected abstract Map<String,Digest> loadDigestMap();

    private synchronized void checkUpdate(){
        long t = System.currentTimeMillis();
        if(t - lstUpdateTime > updateInterval * 60 *1000L){
            logger.info("load digest.....");
            Map<String,Digest> digestMap = loadDigestMap();
            this.digestMap = digestMap;
            this.lstUpdateTime = t;
        }
    }

    protected String buildDigestMapKey(String id,String idx){
        return id+"|"+idx;
    }

    protected Digest buildDigest(int type,byte[] lkey,byte[] rkey){
//        String name = id+"|"+idx;
//        if(digestMap.containsKey(name)){
//            throw new IllegalArgumentException("digest["+id+","+idx+"] exist!");
//        }
        Digest vDigest;
        switch(type){
            case DIGEST_MD5:
                vDigest = new MD5Digest();
                ((MD5Digest)vDigest).setPrevKey(lkey);
                ((MD5Digest)vDigest).setNextKey(rkey);
                break;
            case DIGEST_SHA1:
                vDigest = new SHA1Digest();
                ((SHA1Digest)vDigest).setPrevKey(lkey);
                ((SHA1Digest)vDigest).setNextKey(rkey);
                break;
            case DIGEST_RSA_WITH_MD5:
                vDigest = new RSADigest();
                ((RSADigest) vDigest).setAlgorithm("MD5withRSA");
                if(lkey!=null){
                    ((RSADigest) vDigest).setPublicKey(lkey);
                }
                if(rkey!=null){
                    ((RSADigest) vDigest).setPrivateKey(rkey);
                }
                break;
            case DIGEST_RSA_WITH_SHA1:
                vDigest = new RSADigest();
                ((RSADigest) vDigest).setAlgorithm("SHA1withRSA");
                if(lkey!=null){
                    ((RSADigest) vDigest).setPublicKey(lkey);
                }
                if(rkey!=null){
                    ((RSADigest) vDigest).setPrivateKey(rkey);
                }
                break;
            default:
                throw new IllegalArgumentException("unknow digest type["+type+"]");
        }
       return vDigest;
    }

    public byte[] digest(String id,String idx,byte[] data){
        checkUpdate();
        String key = buildDigestMapKey(id, idx);
        Digest vDigest = digestMap.get(key);
        if(vDigest==null){
            throw new NullPointerException("digest for ["+id+","+idx+"] not found!");
        }
        return vDigest.digest(data);
    }

    public boolean verify(String id,String idx,byte[] data,byte[] signature){
        checkUpdate();
        String key = buildDigestMapKey(id, idx);
        Digest vDigest = digestMap.get(key);
        if(vDigest==null){
            throw new NullPointerException("digest for ["+id+","+idx+"] not found!");
        }
        return vDigest.verify(data, signature);
    }
}
