package com.paic.jrkj.tk.tools.msg.field;

import com.paic.jrkj.tk.util.ByteUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 15:16:21 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 15:16:21
 */
public class LVarField extends AbstractField {

    private byte[] data;

    public LVarField(String name, byte[] data) {
        super(name);
        this.data = data;
    }
    
    public LVarField(String name, byte[] data, String charset) {
        this(name, data);
        this.setCharset(charset);
    }

    public LVarField(String name) {
        super(name);
    }

    public LVarField(String name, String charset) {
        this(name);
        this.setCharset(charset);
    }
    
    public byte[] getData() {
        return data;
    }

    public byte[] toByteArray() {
        int dataLen = data.length;
        if (dataLen == 0) {
            return new byte[]{0};
        }
        byte[] dataLenLen = Integer.toString(dataLen, 10).getBytes(ByteUtil.ISO8859_1);
        return ByteUtil.join(new byte[]{(byte) dataLenLen.length}, dataLenLen, data);
    }

    public int length() {
        int packLen = data.length;
        if(packLen == 0){
        	return 1;
        }
        int packLenLen = String.valueOf(packLen).length();
        return 1 + packLenLen + packLen;
    }

    public int encape(int fromIndex, byte[] bytes) {
        byte packLenLenHead = bytes[fromIndex];
        if (packLenLenHead == 0) {
            data = new byte[0];
            return fromIndex + 1;
        }
        byte[] packLenLen = ByteUtil.intercept(bytes, fromIndex + 1, packLenLenHead);
        int packLen = Integer.parseInt(new String(packLenLen, ByteUtil.ISO8859_1), 10);
        if (bytes.length < fromIndex + packLen + (int) packLenLenHead + 1) {
            throw new IllegalArgumentException("data parse error!");
        }
        this.data = ByteUtil.intercept(bytes, fromIndex + ((int) packLenLenHead) + 1, packLen);
        return fromIndex + 1 + ((int) packLenLenHead) + packLen;
    }
}
