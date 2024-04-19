package com.paic.jrkj.tk.util;

import com.paic.jrkj.tk.tools.msg.field.Field;
import com.paic.jrkj.tk.tools.bytes.Bytes;
import com.paic.jrkj.tk.exception.DataOutOfLengthException;

import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:12:57 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:12:57
 */
public class ByteUtil {

    public static final Charset ISO8859_1=Charset.forName("8859_1");

    public static final byte BLANK = 0x20;
    public static final byte ZERO = 0x30;

    private ByteUtil() {
    }

    public static boolean isEquals(byte[] src1, byte[] src2) {
        if (src1 == null && src2 == null) {
            return true;
        }
        if (src1 == null || src2 == null) {
            return false;
        }
        if (src1.length != src2.length) {
            return false;
        }
        for (int i = src1.length - 1; i >= 0; i--) {
            if (src1[i] != src2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean startsWith(byte[] src, byte[] subbytes) {
        if (src == null || subbytes == null) {
            return false;
        } else if (src.length < subbytes.length) {
            return false;
        }
        for (int i = subbytes.length - 1; i >= 0; i--) {
            if (subbytes[i] != src[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对数据添加定长填充
     *
     * @param data       待处理数据
     * @param packLenLen 定长说明位数
     * @return 定长说明+定长字符
     */
    public static byte[] includeLength(byte[] data, int packLenLen) {
        if (packLenLen == 0) {
            return data;
        }
        String length = Integer.toString(data.length, 10);
        if (length.length() > packLenLen) {
            throw new DataOutOfLengthException("data length [" + length + "] overflow..");
        }
        byte[] lenBytes = prevFilling(length, "iso8859_1", ZERO, packLenLen);
        byte[] result = new byte[packLenLen + data.length];
        System.arraycopy(lenBytes, 0, result, 0, packLenLen);
        System.arraycopy(data, 0, result, packLenLen, data.length);
        return result;
    }

    /**
     * 对数据添加定长填充
     *
     * @param data       待处理数据
     * @param encoding   encoding
     * @param packLenLen 定长说明位数
     * @return 定长说明+定长字符
     */
    public static byte[] includeLength(String data, String encoding, int packLenLen) {
        byte[] src = data.getBytes(Charset.forName(encoding));
        return includeLength(src, packLenLen);
    }

    /**
     * previous filling data
     *
     * @param data         source data
     * @param encoding     encoding
     * @param fillByte     filling byte
     * @param targetLength target length
     * @return target data
     */
    public static byte[] prevFilling(String data, String encoding, byte fillByte, int targetLength) {
        byte[] dataBytes = data.getBytes(Charset.forName(encoding));
        return prevFilling(dataBytes, fillByte, targetLength);
    }

    /**
     * previous filling data
     *
     * @param data         source data
     * @param fillByte     filling byte
     * @param targetLength target length
     * @return target data
     */
    public static byte[] prevFilling(byte[] data, byte fillByte, int targetLength) {
        if (targetLength < data.length) {
            throw new DataOutOfLengthException("input data digit too large.");
        } else if (targetLength == data.length) {
            return data;
        }
        byte[] result = new byte[targetLength];
        for (int i = 0; i < targetLength; i++) {
            result[i] = fillByte;
        }
        System.arraycopy(data, 0, result, targetLength - data.length, data.length);
        return result;
    }

    /**
     * 将多个byte数组拼接在一起
     *
     * @param byteArrs list of byte array
     * @return byte array
     */
    public static byte[] join(byte[]... byteArrs) {
        if (byteArrs == null) {
            return null;
        }
        if (byteArrs.length == 0) {
            return new byte[0];
        }
        int length = 0;
        for (byte[] byteArr : byteArrs) {
            if (byteArr == null || byteArr.length == 0) continue;
            length += byteArr.length;
        }
        byte[] result = new byte[length];
        int index = 0;
        for (byte[] byteArr : byteArrs) {
            if (byteArr == null || byteArr.length == 0) continue;
            System.arraycopy(byteArr, 0, result, index, byteArr.length);
            index += byteArr.length;
        }
        return result;
    }

    public static byte[] join(Field... fieldArrs) {
        if (fieldArrs == null) {
            return null;
        }
        if (fieldArrs.length == 0) {
            return new byte[0];
        }
        int length = 0;
        for (Field field : fieldArrs) {
            if (field == null) continue;
            length += field.length();
        }
        byte[] result = new byte[length];
        int index = 0;
        byte[] data;
        for (Field field : fieldArrs) {
            if (field == null) continue;
            data = field.toByteArray();
            System.arraycopy(data, 0, result, index, data.length);
            index += data.length;
        }
        return result;
    }

    /**
     * 数组截取
     *
     * @param srcArray  数据源
     * @param fromIndex 截取开始位置，最小为0
     * @param length    截取长度
     * @return 返回截取的数组
     */
    public static byte[] intercept(byte[] srcArray, int fromIndex, int length) {
        if (fromIndex + length > srcArray.length) {
            throw new ArrayIndexOutOfBoundsException(new StringBuffer("source array size=[")
                    .append(srcArray.length).append("] can not intercept from index=[")
                    .append(fromIndex).append("] with [")
                    .append(length).append("] characters!").toString());
        }
        byte[] result = new byte[length];
        System.arraycopy(srcArray, fromIndex, result, 0, length);
        return result;
    }

    /**
     * 数组截取
     *
     * @param src       数据源
     * @param encoding  数据源编码
     * @param fromIndex 截取开始位置，最小为0
     * @param length    截取长度
     * @return 返回截取的数组
     */
    public static byte[] intercept(String src, String encoding, int fromIndex, int length) {
        try {
            return intercept(src.getBytes(encoding), fromIndex, length);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Bytes[] split(byte[] bytes, byte vByte) {
        List<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == vByte) {
                indexList.add(i);
            }
        }
        indexList.add(bytes.length);
        Bytes[] result = new Bytes[indexList.size()];
        int start = 0;
        int i = 0;
        for (Integer index : indexList) {
            byte[] bt;
            if (index == start) {
                bt = new byte[]{};
            } else {
                bt = new byte[index - start];
                System.arraycopy(bytes, start, bt, 0, index - start);
            }
            result[i] = new Bytes(bt);
            start = index + 1;
            i++;
        }
        return result;
    }
}
