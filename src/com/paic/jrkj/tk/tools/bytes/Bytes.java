package com.paic.jrkj.tk.tools.bytes;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-3-25 10:12:24 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-3-25 10:12:24
 */
public final class Bytes {

    private final byte[] bytes;

    public Bytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int size() {
        return bytes.length;
    }

    public byte[] getByteArray() {
        return this.bytes;
    }
}
