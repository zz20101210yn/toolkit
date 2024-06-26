package com.paic.jrkj.tk.tools.msg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.paic.jrkj.tk.util.ByteUtil;
import com.paic.jrkj.tk.exception.DataOutOfLengthException;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 9:16:11 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 9:16:11
 */
public class VarMsgBean implements MsgBean<VarMsgBean>{

    private final static Log logger = LogFactory.getLog(VarMsgBean.class);

    private final int dataLenLen;
    private final byte[] bizdata;

    /**
     * @param dataLenLen 报文长度描述位长度
     * @param bizdata       报文内容(不含头信息)
     */
    public VarMsgBean(int dataLenLen, byte[] bizdata) {
        this.dataLenLen = dataLenLen;
        this.bizdata = bizdata;
        String dataLen = Integer.toString(this.bizdata.length, 10);
        if (dataLen.length() > this.dataLenLen) {
            throw new DataOutOfLengthException("data length [" + dataLen + "] overflow..");
        }
    }

    public int getDataLenLen() {
        return dataLenLen;
    }

    public byte[] getBizData() {
        return bizdata;
    }

    public byte[] toByteArray() {
        return ByteUtil.includeLength(bizdata, dataLenLen);
    }

    public static VarMsgBean parse(byte[] vdata, int dataLenLen) {
        byte[] length = ByteUtil.intercept(vdata, 0, dataLenLen);
        int packLen = Integer.parseInt(new String(length, ByteUtil.ISO8859_1), 10);
        if (logger.isDebugEnabled()) {
            logger.debug("pack len=[" + packLen + "]");
        }
        if (packLen + dataLenLen != vdata.length) {
            throw new IllegalArgumentException("data packLen error!");
        }
        return new VarMsgBean(dataLenLen, ByteUtil.intercept(vdata, dataLenLen, packLen));
    }

    public static VarMsgBean parse(InputStream is, int dataLenLen)
            throws IOException {
        if (dataLenLen <= 0) {
            throw new IllegalArgumentException("dataLenLen Error!");
        }
        try {
            byte[] len = new byte[dataLenLen];
            if (dataLenLen == is.read(len)) {
                int packLen = Integer.parseInt(new String(len), 10);
                if (logger.isDebugEnabled()) {
                    logger.debug("pack len=[" + packLen + "]");
                }
                byte[] result = new byte[packLen];
                is.read(result);
                return new VarMsgBean(dataLenLen, result);
            } else {
                throw new IOException("read pack length error!");
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
