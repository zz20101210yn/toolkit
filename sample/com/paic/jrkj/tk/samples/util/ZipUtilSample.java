package com.paic.jrkj.tk.samples.util;

import com.paic.jrkj.tk.util.ZipUtil;
import com.paic.jrkj.tk.util.ByteUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 14:29:33 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 14:29:33
 */
public class ZipUtilSample {

    public static void main(String[] args){
          byte[] data = "BIS-Router与BIS-Client采用长连接进行通讯，BIS-Router与BIS-Client通过IP和端口识别对方身份，通过签名验签验证对方身份的合法性；".getBytes();
        System.out.println("data length:"+data.length);
        byte[] d1 = ZipUtil.zip(data);
        System.out.println("zip length:"+d1.length);
        byte[] data2 = ZipUtil.unzip(d1);
        System.out.println("unzip length:"+data2.length);
        System.out.println("unzip equals data:"+ ByteUtil.isEquals(data,data2));
    }
}
