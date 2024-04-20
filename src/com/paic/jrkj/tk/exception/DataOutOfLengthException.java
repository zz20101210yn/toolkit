package com.paic.jrkj.tk.exception;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 9:22:01 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 9:22:01
 */
public class DataOutOfLengthException extends RuntimeException{

    public DataOutOfLengthException() {
    }

    public DataOutOfLengthException(String message) {
        super(message);
    }

    public DataOutOfLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataOutOfLengthException(Throwable cause) {
        super(cause);
    }
}
