package com.paic.jrkj.tk.util;


import java.util.Map;
import java.util.HashMap;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-11 16:14:56 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-11 16:14:56
 */
public class Properties {

    private final Map<String, String[]> properties = new HashMap<String, String[]>();

    public Properties() {
    }

    public void add(String name, String vlaue) {
        String[] current = properties.get(name);
        if (current == null) {
            properties.put(name, new String[]{vlaue});
        } else {
            int size = current.length + 1;
            String[] result = new String[size];
            System.arraycopy(current, 0, result, 0, current.length);
            result[size - 1] = vlaue;
            properties.put(name, result);
        }
    }

    public String[] getStringArray(String name) {
        return properties.get(name);
    }

    public String getString(String name) {
        String[] values = getStringArray(name);
        return values == null || values.length == 0 ? null : values[0];
    }

    public byte getByte(String name) {
        String s = getString(name);
        if (s == null)
            return 0;
        else
            return (byte) Integer.parseInt(s, 10);
    }

    public byte[] getByteArray(String name) {
        String s = getString(name);
        return s == null ? null : s.getBytes(ByteUtil.ISO8859_1);
    }
}
