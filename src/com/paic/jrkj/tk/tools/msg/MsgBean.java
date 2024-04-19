package com.paic.jrkj.tk.tools.msg;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 9:16:18 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 9:16:18
 */
public interface MsgBean<E extends MsgBean> {

    byte[] toByteArray();

    byte[] getBizData();
}
