package com.paic.jrkj.tk.samples.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.paic.jrkj.tk.util.XmlUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-2-17 20:00:50 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-2-17 20:00:50
 */
public class XmlUtilSample {

    private final Log logger = LogFactory.getLog(XmlUtilSample.class);

    public XmlUtilSample() {

    }

    public static void main(String[] args){
        String s = "<root><a><b>1313123</b></a><c>dacsdasdcsdasd</c></root>";
        System.out.println(XmlUtil.getNodeValue(s,"a","b"));
        System.out.println(XmlUtil.getNodeValue(s,"c"));

        s = "<root><a><gb>1313123</gb><gb>sdf</gb><gb><c>adsdad</c></gb><gb>1</gb><gb>1313123</gb></a><c>dacsdasdcsdasd</c></root>";
        System.out.println(XmlUtil.getNodeValueList(s,"a","gb"));
    }
}
