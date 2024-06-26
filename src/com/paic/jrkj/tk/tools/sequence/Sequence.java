package com.paic.jrkj.tk.tools.sequence;

import com.paic.jrkj.tk.util.ByteUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-5 14:24:39 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-5 14:24:39
 */
public class Sequence {

    private static final Map<String, Sequence> seqMap = new HashMap<String, Sequence>();
    private final static Log logger = LogFactory.getLog(Sequence.class);
    private long currentValue = 0;
    private long maxValue;

    private Sequence(long maxValue) {
        if (maxValue <= 0) {
            throw new IllegalArgumentException("sequence max value must above zero!");
        }
        this.maxValue = maxValue;
    }

    /**
     * @param name   Sequence Name
     * @param length Sequence Length
     * @return Sequence Next value
     */
    public static String nextValue(final String name,final int length) {
        Sequence seq = seqMap.get(name);
        if (seq == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("init sequence[" + name + ']');
            }
            synchronized (seqMap) {
                seq = seqMap.get(name);
                if (seq == null) {
                    char[] charr = new char[length];
                    for (int i=0;i<length;i++) {
                        charr[i] = '9';
                    }
                    long maxValue = Long.parseLong(new String(charr),10);
                    seq = new Sequence(maxValue);
                    seqMap.put(name, seq);
                }
            }
        }
        byte[] valueBytes = Long.toString(seq.nextValue(),36).getBytes(ByteUtil.ISO8859_1);
        return new String(ByteUtil.prevFilling(valueBytes,ByteUtil.ZERO, length), ByteUtil.ISO8859_1);
    }

    private synchronized long nextValue() {
        if (currentValue < maxValue) {
            currentValue++;
        } else {
            currentValue = 1;
        }
        return currentValue;
    }
}
