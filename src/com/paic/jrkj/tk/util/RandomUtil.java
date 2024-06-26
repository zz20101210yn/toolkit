package com.paic.jrkj.tk.util;

import java.util.Random;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-7 17:04:18 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-7 17:04:18
 */
public class RandomUtil {

    private RandomUtil(){}

    public static int getRandom(int size){
		return new Random().nextInt(size);
	}

    public static byte[] getRandomASCII(int size){
       Random random = new Random();
       byte[] bytes = new byte[size];
        for(int i=0;i<size;i++){
            bytes[i] = (byte) (random.nextInt(0x7f-0x21)+0x21);
        }
        return bytes;
    }
}
