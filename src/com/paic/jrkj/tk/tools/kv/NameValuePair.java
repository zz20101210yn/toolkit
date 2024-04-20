package com.paic.jrkj.tk.tools.kv;

import java.util.List;
import java.util.ArrayList;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-4-11 16:09:59 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-4-11 16:09:59
 */
public class NameValuePair<K extends Comparable, V> {

    private final K key;
    private List<V> valueList;

    public NameValuePair(K key, List<V> valueList) {
        this.key = key;
        this.valueList = valueList;
    }

    public NameValuePair(K key, V[] valueArray) {
        this.key = key;
        addValue(valueArray);
    }

    public NameValuePair(K key, V value) {
        this.key = key;
        this.valueList = new ArrayList<V>();
        this.valueList.add(value);
    }

    public void addValue(V value) {
        if (valueList == null) {
            valueList = new ArrayList<V>();
        }
        valueList.add(value);
    }

    public void addValue(V[] valueArray) {
        if (valueArray == null || valueArray.length == 0) {
            return;
        }
        if (valueList == null) {
            valueList = new ArrayList<V>();
        }
        for (V value : valueArray) {
            this.valueList.add(value);
        }
    }

    public void setValue(V value) {
        this.valueList = new ArrayList<V>();
        this.valueList.add(value);
    }

    public void setValue(V[] valueArray) {
        this.valueList = new ArrayList<V>();
        for (V value : valueArray) {
            this.valueList.add(value);
        }
    }

    public K getKey() {
        return key;
    }

    public List<V> getValue() {
        return valueList;
    }

    public String toParameter() {
        return toParameter('&');
    }

    public String toParameter(char splitChar) {
        StringBuffer buffer = new StringBuffer();
        if (this.valueList != null && this.valueList.size() > 0) {
            for (V value : this.valueList) {
                buffer.append(splitChar).append(this.key).append("=").append(value);
            }
        }
        String str = buffer.toString();
        return str.length() > 1 ? str.substring(1) : str;
    }

    public String toString() {
        return toString(',');
    }

    public String toString(char splitChar) {
        StringBuffer buffer = new StringBuffer();
        if (this.valueList != null && this.valueList.size() > 0) {
            for (V value : this.valueList) {
                buffer.append(splitChar).append(value);
            }
        }
        String str = buffer.toString();
        return this.key + "=[" + (str.length() == 0 ? "" : str.substring(1)) + "]";
    }
}
