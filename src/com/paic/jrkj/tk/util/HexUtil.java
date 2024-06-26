package com.paic.jrkj.tk.util;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 14:06:12 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 14:06:12
 */
public class HexUtil {

    private HexUtil(){}

    public static String encode(byte[] src) {
        StringBuffer hs = new StringBuffer();
        String stmp;
        for (int i = 0; i < src.length; i++) {
            stmp = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    public static byte[] decode(String hex)
            throws SecurityException {
        if (hex.length() % 2 != 0) {
            throw new SecurityException("invalid hex string");
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }
    public static void main(String[] args) {
		System.out.println("ssss:"+new String(decode("333030303031")));
	}
}
