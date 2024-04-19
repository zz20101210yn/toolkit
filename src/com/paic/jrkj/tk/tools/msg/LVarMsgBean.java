package com.paic.jrkj.tk.tools.msg;

import com.paic.jrkj.tk.util.ByteUtil;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 9:16:01 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 9:16:01
 */
public class LVarMsgBean implements MsgBean<LVarMsgBean>{

    private final static Log logger = LogFactory.getLog(LVarMsgBean.class);

    private final byte[] bizdata;
    /**
     * 215000000000000000
     *
     * @param bizdata 报文内容(不含报文头及描述信息)
     */
    public LVarMsgBean(byte[] bizdata) {
        this.bizdata = bizdata;
    }

    public byte[] getBizData() {
        return bizdata;
    }

    public byte[] toByteArray() {
        int dataLen = bizdata.length;
        if(dataLen==0){
            return new byte[]{0};
        }
        byte[] dataLenLen = Integer.toString(dataLen, 10).getBytes(ByteUtil.ISO8859_1);
        return ByteUtil.join(new byte[]{(byte) dataLenLen.length}, dataLenLen, bizdata);
    }

    public static LVarMsgBean parse(byte[] lvdata) {
        byte packLenLenHead = lvdata[0];
        if(packLenLenHead==0){
             return new LVarMsgBean(new byte[0]);
        }
        byte[] packLenLen = ByteUtil.intercept(lvdata, 1, Integer.valueOf(packLenLenHead));
        int packLen = Integer.parseInt(new String(packLenLen, ByteUtil.ISO8859_1), 10);
        if (logger.isDebugEnabled()) {
            logger.debug("pack len=[" + packLen + "]");
        }
        if (lvdata.length != packLen + (int) packLenLenHead + 1) {
            throw new IllegalArgumentException("data parse error!");
        }
        byte[] data = ByteUtil.intercept(lvdata, ((int) packLenLenHead) + 1, packLen);
        return new LVarMsgBean(data);
    }

    public static LVarMsgBean parse(InputStream is)
            throws IOException {
        try {
            int packLenLenHead = is.read();
            if(packLenLenHead==0){
                return new LVarMsgBean(new byte[0]);
            }
            byte[] len = new byte[packLenLenHead];
            int readCount = 0;
            while(readCount < packLenLenHead){
            	readCount += is.read(len, readCount, packLenLenHead - readCount);
            }
            int packLen = Integer.parseInt(new String(len, ByteUtil.ISO8859_1), 10);
            if (logger.isDebugEnabled()) {
                logger.debug("pack len=[" + packLen + "]");
            }
            byte[] result = new byte[packLen];
            readCount = 0;
            while(readCount < packLen){
            	readCount += is.read(result, readCount, packLen - readCount);
            }
            return new LVarMsgBean(result);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
        	logger.fatal("", e);
            throw new IOException(e.getMessage());
        }
    }
}
