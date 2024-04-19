package com.paic.jrkj.tk.net;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-8 13:31:18 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-8 13:31:18
 */
public interface ConnectionListener<E extends Connection> {

    /**
     * �����ӳɹ��󣬴˷���������
     * @param connection  connection
     */
    void callAfterConnected(E connection);

    /**
     * �������Ͽ�����ǰ���˷���������
     * @param connection  connection
     */
    void callBeforeDisconnected(E connection);
}
