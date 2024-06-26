package com.paic.jrkj.tk.util;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:06:03 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:06:03
 */
public class Base64 {

    private Base64() {
    }

    public static byte[] decode(char[] data)
            throws SecurityException {
        return decode(data, true);
    }

    public static byte[] decode(char[] data, boolean checkLength)
            throws SecurityException {
        int tempLen = data.length;
        for (char ch : data) {
            int value = codes[ch & 0xff];
            if (value < 0 && ch != '=')
                tempLen--;
        }

        int len = ((tempLen + 3) / 4) * 3;
        if (tempLen > 0 && data[tempLen - 1] == '=')
            len--;
        if (tempLen > 1 && data[tempLen - 2] == '=')
            len--;
        byte out[] = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        for (char ch : data) {
            int value = codes[ch & 0xff];
            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte) (accum >> shift & 0xff);
                }
            }
        }
        if (checkLength && index != out.length)
            throw new SecurityException("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
        else
            return out;
    }

    public static char[] encode(byte[] data) {
        char out[] = new char[((data.length + 2) / 3) * 4];
        int i = 0;
        for (int index = 0; i < data.length; index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = 0xff & data[i];
            val <<= 8;
            if (i + 1 < data.length) {
                val |= 0xff & data[i + 1];
                trip = true;
            }
            val <<= 8;
            if (i + 2 < data.length) {
                val |= 0xff & data[i + 2];
                quad = true;
            }
            out[index + 3] = alphabet[quad ? val & 0x3f : 64];
            val >>= 6;
            out[index + 2] = alphabet[trip ? val & 0x3f : 64];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3f];
            val >>= 6;
            out[index] = alphabet[val & 0x3f];
            i += 3;
        }
        return out;
    }

    private static char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte codes[];

    static {
        codes = new byte[256];
        for (int i = 0; i < 256; i++)
            codes[i] = -1;

        for (int i = 65; i <= 90; i++)
            codes[i] = (byte) (i - 65);

        for (int i = 97; i <= 122; i++)
            codes[i] = (byte) ((26 + i) - 97);

        for (int i = 48; i <= 57; i++)
            codes[i] = (byte) ((52 + i) - 48);

        codes[43] = 62;
        codes[47] = 63;
    }
}
