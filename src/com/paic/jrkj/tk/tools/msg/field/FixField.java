package com.paic.jrkj.tk.tools.msg.field;

import com.paic.jrkj.tk.util.ByteUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 15:15:57 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 15:15:57
 */
public class FixField extends AbstractField {

    private final int length;
    private byte[] data;

    public FixField(String name, byte[] data) {
        super(name);
        this.data = data;
        this.length = data.length;
    }

    public FixField(String name, byte[] data, String charset) {
    	this(name, data);
        this.setCharset(charset);
    }
    
    public FixField(String name, int length) {
        super(name);
        this.length = length;
    }

    public FixField(String name, int length, String charset) {
        this(name, length);
        this.setCharset(charset);
    }
    
    public byte[] getData() {
        return this.data;
    }

    public byte[] toByteArray() {
        return this.data;
    }

    public int length() {
        return length;
    }

    public int encape(int fromIndex, byte[] bytes) {
        this.data = ByteUtil.intercept(bytes, fromIndex, length);
        return fromIndex + length;
    }


}
