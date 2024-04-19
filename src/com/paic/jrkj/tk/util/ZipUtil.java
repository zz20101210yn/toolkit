package com.paic.jrkj.tk.util;

import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.DataFormatException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 14:04:02 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 14:04:02
 */
public class ZipUtil {

    private ZipUtil() {
    }

    public static byte[] zip(byte[] src){
        Deflater compresser = new Deflater();
        compresser.setInput(src);
        compresser.finish();
        byte[] ziped = new byte[src.length * 2];
        int compressedDataLength = compresser.deflate(ziped);
        byte[] output = new byte[compressedDataLength];
        System.arraycopy(ziped, 0, output, 0, compressedDataLength);
        return output;
    }

    public static byte[] unzip(byte[] src) {
        int resultLength = 0;
        byte[] unzip = null;
        boolean remaining = true;
        int unzipLength = src.length * 20;
        Inflater decompresser;
        while (remaining) {
            decompresser = new Inflater();
            try {
                unzip = new byte[unzipLength];
                decompresser.setInput(src, 0, src.length);
                resultLength = decompresser.inflate(unzip);
                if (decompresser.getRemaining() <= 0) {
                    remaining = false;
                } else {
                    unzipLength = unzipLength * 2;
                }
            } catch (DataFormatException e) {
                throw new SecurityException("UNZIP DATA ERROR", e);
            } catch (Exception e) {
                throw new SecurityException( "UNZIP DATA ERROR", e);
            } finally {
                decompresser.end();
            }
        }
        byte[] output = new byte[resultLength];
        System.arraycopy(unzip, 0, output, 0, resultLength);
        return output;
    }
}
