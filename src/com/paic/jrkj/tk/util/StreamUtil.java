package com.paic.jrkj.tk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.HttpURLConnection;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-8 18:28:57 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-8 18:28:57
 */
public class StreamUtil {

    private final static Log logger = LogFactory.getLog(StreamUtil.class);

    private StreamUtil() {
    }

    public static void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (IOException e) {
            logger.fatal("close inputstream fail", e);
        }
    }

    public static void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        } catch (IOException e) {
            logger.fatal("close Reader fail", e);
        }
    }

    public static void close(OutputStream os) {
        try {
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (IOException e) {
            logger.fatal("close outputstream fail", e);
        }
    }

    public static void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            logger.fatal("close socket fail", e);
        }
    }

    public static void close(ServerSocket server) {
        try {
            if (server != null) {
                server.close();
                server = null;
            }
        } catch (IOException e) {
            logger.fatal("close ServerSocket fail", e);
        }
    }

    public static void close(HttpURLConnection con) {
        if (con != null) {
            con.disconnect();
            con = null;
        }
    }

    public static byte[] toByteArray(InputStream is)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = new byte[1024 * 4];
        int len;
        while ((len = is.read(data)) != -1) {
            out.write(data, 0, len);
            out.flush();
        }
        return out.toByteArray();
    }
}
