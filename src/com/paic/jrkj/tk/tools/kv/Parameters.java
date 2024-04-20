package com.paic.jrkj.tk.tools.kv;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-4-11 16:30:22 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-4-11 16:30:22
 */
public class Parameters<K extends Comparable, V> {

    private List<NameValuePair<K, V>> nvpList = new ArrayList<NameValuePair<K, V>>();

    public Parameters() {
    }

    public void add(K key, V value) {
        NameValuePair<K, V> nvp = null;
        for (NameValuePair<K, V> nv : nvpList) {
            if (key.equals(nv.getKey())) {
                nvp = nv;
                break;
            }
        }
        if (nvp == null) {
            nvpList.add(new NameValuePair<K, V>(key, value));
        } else {
            nvp.addValue(value);
        }
    }

    public void add(K key, V[] value) {
        NameValuePair<K, V> nvp = null;
        for (NameValuePair<K, V> nv : nvpList) {
            if (key.equals(nv.getKey())) {
                nvp = nv;
                break;
            }
        }
        if (nvp == null) {
            nvpList.add(new NameValuePair<K, V>(key, value));
        } else {
            nvp.addValue(value);
        }
    }

    public void set(K key, V value) {
        NameValuePair<K, V> nvp = null;
        for (NameValuePair<K, V> nv : nvpList) {
            if (key.equals(nv.getKey())) {
                nvp = nv;
                break;
            }
        }
        if (nvp == null) {
            nvpList.add(new NameValuePair<K, V>(key, value));
        } else {
            nvp.setValue(value);
        }
    }

    public void set(K key, V[] value) {
        NameValuePair<K, V> nvp = null;
        for (NameValuePair<K, V> nv : nvpList) {
            if (key.equals(nv.getKey())) {
                nvp = nv;
                break;
            }
        }
        if (nvp == null) {
            nvpList.add(new NameValuePair<K, V>(key, value));
        } else {
            nvp.setValue(value);
        }
    }

    public List<V> getParameterList(K key) {
        NameValuePair<K, V> nvp = null;
        for (NameValuePair<K, V> nv : nvpList) {
            if (key.equals(nv.getKey())) {
                nvp = nv;
                break;
            }
        }
        return nvp == null ? null : nvp.getValue();
    }

    public V getParameter(K key) {
        NameValuePair<K, V> nvp = null;
        for (NameValuePair<K, V> nv : nvpList) {
            if (key.equals(nv.getKey())) {
                nvp = nv;
                break;
            }
        }
        return (nvp == null || nvp.getValue() == null || nvp.getValue().size() == 0) ? null : nvp.getValue().get(0);
    }

    public String toString() {
        return toString('&');
    }

    public String toString(char splitChar) {
        StringBuffer buffer = new StringBuffer();
        if (this.nvpList != null && this.nvpList.size() > 0) {
            for (NameValuePair<K, V> nvp : this.nvpList) {
                buffer.append(splitChar).append(nvp.toParameter(splitChar));
            }
        }
        String str = buffer.toString();
        return str.length() > 1 ? str.substring(1) : str;
    }
}
