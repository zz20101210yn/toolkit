package com.paic.jrkj.tk.samples.tools.msg.field;

import com.paic.jrkj.tk.util.ByteUtil;
import com.paic.jrkj.tk.util.FieldUtil;
import com.paic.jrkj.tk.tools.msg.field.Field;
import com.paic.jrkj.tk.tools.msg.field.FixField;
import com.paic.jrkj.tk.tools.msg.field.VarField;
import com.paic.jrkj.tk.tools.msg.field.LVarField;
import com.paic.jrkj.tk.tools.work.WorkManager;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 16:49:25 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 16:49:25
 */
public class FieldSample implements Runnable {


    public static void main(String[] args) {
        WorkManager wm = WorkManager.getWorkManager("WorkManager");
        for(int i=0;i<100;i++){
            wm.startWork(new FieldSample());
        }
    }

    public void run(){
        Field[] fieldArray = new Field[]{
                new FixField("a11", "jiyangguang".getBytes()),
                new FixField("a12", "jk".getBytes()),
                new VarField("a13", 3, "lvguoyun".getBytes()),
                new LVarField("a14", "专家表示，年终奖并不是法律概念,没有任何一条法律法规规定年终奖如何处理，它也不是薪酬的必备项目。但只要有证据证明，年终奖是全民待遇，即使离开了，也可以要求获得按一定比例来发放所做满月份的年终奖。".getBytes()),
                new FixField("a21", "jiyangguang".getBytes()),
                new FixField("a22", "jk".getBytes()),
                new VarField("a23", 3, "lvguoyun".getBytes()),
                new LVarField("a24", "专家表示，年终奖并不是法律概念,没有任何一条法律法规规定年终奖如何处理，它也不是薪酬的必备项目。但只要有证据证明，年终奖是全民待遇，即使离开了，也可以要求获得按一定比例来发放所做满月份的年终奖。".getBytes()),
                new FixField("a31", "jiyangguang".getBytes()),
                new FixField("a32", "jk".getBytes()),
                new VarField("a33", 3, "lvguoyun".getBytes()),
                new LVarField("a34", "专家表示，年终奖并不是法律概念,没有任何一条法律法规规定年终奖如何处理，它也不是薪酬的必备项目。但只要有证据证明，年终奖是全民待遇，即使离开了，也可以要求获得按一定比例来发放所做满月份的年终奖。".getBytes()),
                new FixField("a41", "jiyangguang".getBytes()),
                new FixField("a42", "jk".getBytes()),
                new VarField("a43", 3, "lvguoyun".getBytes()),
                new LVarField("a44", "专家表示，年终奖并不是法律概念,没有任何一条法律法规规定年终奖如何处理，它也不是薪酬的必备项目。但只要有证据证明，年终奖是全民待遇，即使离开了，也可以要求获得按一定比例来发放所做满月份的年终奖。".getBytes()),
                new FixField("a51", "jiyangguang".getBytes()),
                new FixField("a52", "jk".getBytes()),
                new VarField("a53", 3, "lvguoyun".getBytes()),
                new LVarField("a54", "专家表示，年终奖并不是法律概念,没有任何一条法律法规规定年终奖如何处理，它也不是薪酬的必备项目。但只要有证据证明，年终奖是全民待遇，即使离开了，也可以要求获得按一定比例来发放所做满月份的年终奖。".getBytes()),
                new FixField("a61", "jiyangguang".getBytes()),
                new FixField("a62", "jk".getBytes()),
                new VarField("a63", 3, "lvguoyun".getBytes()),
                new LVarField("a64", "专家表示，年终奖并不是法律概念,没有任何一条法律法规规定年终奖如何处理，它也不是薪酬的必备项目。但只要有证据证明，年终奖是全民待遇，即使离开了，也可以要求获得按一定比例来发放所做满月份的年终奖。".getBytes()),
        };
        FieldUtil.toByteArray(fieldArray);
        byte[] data = FieldUtil.toByteArray(fieldArray);
        Field[] fieldArray2 = new Field[]{
                new FixField("a11", 11),
                new FixField("a12", 2),
                new VarField("a13", 3),
                new LVarField("a14"),
                new FixField("a21", 11),
                new FixField("a22", 2),
                new VarField("a23", 3),
                new LVarField("a24"),
                new FixField("a31", 11),
                new FixField("a32", 2),
                new VarField("a33", 3),
                new LVarField("a34"),
                new FixField("a41", 11),
                new FixField("a42", 2),
                new VarField("a43", 3),
                new LVarField("a44"),
                new FixField("a51", 11),
                new FixField("a52", 2),
                new VarField("a53", 3),
                new LVarField("a54"),
                new FixField("a61", 11),
                new FixField("a62", 2),
                new VarField("a63", 3),
                new LVarField("a64"),
        };
        FieldUtil.encape(fieldArray2, data);
//        System.out.println(FieldUtil.toString(fieldArray2));
        byte[] data2 = FieldUtil.toByteArray(fieldArray2);
        System.out.println(ByteUtil.isEquals(data, data2));
    }
}
