package com.paic.jrkj.tk.samples.net;

import com.paic.jrkj.tk.ProcessAction;

import java.io.IOException;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-22 10:55:32 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-22 10:55:32
 */
public class SampleProcessAction implements ProcessAction<SampleProcessAction>{

    public void setAttribute(String name, Object value) {
    }

    public byte[] processAction(byte[] bytes)
            throws IOException {
        return bytes;
    }

    public SampleProcessAction newInstance() {
        return new SampleProcessAction();
    }
}
