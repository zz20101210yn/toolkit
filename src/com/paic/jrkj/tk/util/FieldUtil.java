package com.paic.jrkj.tk.util;

import com.paic.jrkj.tk.tools.msg.field.Field;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 17:03:56 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 17:03:56
 */
public class FieldUtil {

    private final static Log logger = LogFactory.getLog(FieldUtil.class);

    private FieldUtil(){}

    public static byte[] toByteArray(Field[] fieldArray){
        return ByteUtil.join(fieldArray);
    }

    public static byte[] toByteArray(List<Field> fieldList){
       Field[] fieldArray = new Field[fieldList.size()];
        fieldList.toArray(fieldArray);
        return toByteArray(fieldArray);
    }

    public static void encape(Field[] fieldArray,byte[] data){
        long t = System.currentTimeMillis();
        int fromIndex = 0;
        for (int i = 0; i < fieldArray.length; i++) {
            fromIndex = fieldArray[i].encape(fromIndex, data);
        }
        logger.info("FieldUtil#encape eclipse ["+(System.currentTimeMillis() -t)+"]");
    }

    public static String toString(Field[] fieldArray) {
        StringBuffer buffer = new StringBuffer();
        for (Field field : fieldArray) {
            buffer.append(field).append(";");
        }
        return buffer.toString();
    }
}
