package com.paic.jrkj.tk.samples.tools.kv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.paic.jrkj.tk.tools.kv.Parameters;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2014-4-11 16:59:23 $
 * @serial 1.0
 * @since JDK1.6.0_27 2014-4-11 16:59:23
 */
public class ParametersTest {

    private final Log logger = LogFactory.getLog(ParametersTest.class);

    public ParametersTest() {

    }

    public static void main(String[] args){
        Parameters<String,String> param = new Parameters<String, String>();
        param.add("id","12123123123");
        param.add("orgId","paic");
        param.add("name","wang.chaos");
        param.add("value",new String[]{"150","120","130","140"});
        param.add("orgId","lpms");
        param.add("date","20130411");
        System.out.println(param.toString('&'));
        param.set("orgId","lpms2");
        System.out.println(param.toString('&'));
    }
}
