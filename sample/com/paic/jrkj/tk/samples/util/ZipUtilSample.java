package com.paic.jrkj.tk.samples.util;

import com.paic.jrkj.tk.util.ZipUtil;
import com.paic.jrkj.tk.util.ByteUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 14:29:33 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 14:29:33
 */
public class ZipUtilSample {

    public static void main(String[] args){
          byte[] data = "BIS-Router��BIS-Client���ó����ӽ���ͨѶ��BIS-Router��BIS-Clientͨ��IP�Ͷ˿�ʶ��Է����ݣ�ͨ��ǩ����ǩ��֤�Է����ݵĺϷ��ԣ�".getBytes();
        System.out.println("data length:"+data.length);
        byte[] d1 = ZipUtil.zip(data);
        System.out.println("zip length:"+d1.length);
        byte[] data2 = ZipUtil.unzip(d1);
        System.out.println("unzip length:"+data2.length);
        System.out.println("unzip equals data:"+ ByteUtil.isEquals(data,data2));
    }
}