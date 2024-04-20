package com.paic.jrkj.tk.samples.tools.sequence;

import com.paic.jrkj.tk.tools.sequence.Sequence;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-5 14:42:45 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-5 14:42:45
 */
public class SequenceSample {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new SeqThread(i + 1)).start();
        }
    }

    private static class SeqThread implements Runnable {
        private int index;

        private SeqThread(int index) {
            this.index = index;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("thread[" + index + "]:" + Sequence.nextValue("test", 8));
            }
        }
    }
}
