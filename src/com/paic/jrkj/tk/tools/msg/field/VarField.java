package com.paic.jrkj.tk.tools.msg.field;

import com.paic.jrkj.tk.util.ByteUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 15:16:11 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 15:16:11
 */
public class VarField extends AbstractField {

    private final int packLenLen;
    private byte[] data;

    public VarField(String name, int packLenLen) {
        super(name);
        this.packLenLen = packLenLen;
    }

    public VarField(String name, int packLenLen, byte[] data) {
        super(name);
        this.packLenLen = packLenLen;
        this.data = data;
    }

    public VarField(String name, int packLenLen, byte[] data, String charset) {
        this(name, packLenLen, data);
        this.setCharset(charset);
    }
    
    public VarField(String name, int packLenLen, String charset) {
    	this(name, packLenLen);
        this.setCharset(charset);
    }
    
    public byte[] getData() {
        return data;
    }

    public byte[] toByteArray() {
        return ByteUtil.includeLength(data, packLenLen);
    }

    public int length() {
        return packLenLen + data.length;
    }

    public int encape(int fromIndex, byte[] bytes) {
        byte[] length = ByteUtil.intercept(bytes, fromIndex, packLenLen);
        int packLen = Integer.parseInt(new String(length, ByteUtil.ISO8859_1), 10);
        if (packLen + packLenLen+fromIndex > bytes.length) {
            throw new IllegalArgumentException("data packLen error!");
        }
        this.data = ByteUtil.intercept(bytes, fromIndex + packLenLen, packLen);
        return fromIndex + packLenLen + packLen;
    }
}
