package com.paic.jrkj.tk.samples.tools.image;



import com.paic.jrkj.tk.tools.image.GifVerifyImageImpl;
import com.paic.jrkj.tk.tools.image.ImageUtil;
import com.paic.jrkj.tk.tools.image.JPEGVerifyImageImpl;
import com.paic.jrkj.tk.tools.image.VerifyImage;
import com.paic.jrkj.tk.util.Base64;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:wang.chaos@ustc.edu">wang.chaos</a>
 * @version $Revision: 1.0 $ $Date: 2014-5-20 10:09:40 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-5-20 10:09:40
 */
public class ImageTest {

    public static void main(String[] args)
            throws IOException {
//        String s ="zDnVTbRTJKQJp4YGcLlGtCnCN4JPtf9LyqGssKh218Tv1zhncLLM";
//        System.out.println(new String(Base64.decode(s.toCharArray()),"GBK"));
        for(int i=0;i<20;i++){
            VerifyImage image = new JPEGVerifyImageImpl(ImageUtil.getCode(4));
            image.display(new FileOutputStream("d:\\temp\\verify_"+i+".jpg"));
        }

//        for(int i=0;i<10;i++){
//            VerifyImage image = new GifVerifyImageImpl(ImageUtil.getCode(4));
//            image.display(new FileOutputStream("d:\\Temp\\verify_"+i+".gif"));
//        }
    }
}
