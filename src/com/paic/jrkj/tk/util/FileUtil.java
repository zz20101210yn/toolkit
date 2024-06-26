package com.paic.jrkj.tk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-6 16:38:45 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-6 16:38:45
 */
public class FileUtil {

    private final static Log logger = LogFactory.getLog(FileUtil.class);

    private FileUtil(){}

    public static byte[] readFile(File file)
            throws IOException {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024*5];
            int ch;
            while ((ch = is.read(buffer)) != -1) {
                os.write(buffer, 0, ch);
                os.flush();
            }
            return os.toByteArray();
        } catch (FileNotFoundException e) {
            logger.fatal("file not found!", e);
        }
        catch (IOException e) {
            logger.fatal("read file error!", e);
        }
        finally {
            try{
                if(is!=null){
                    is.close();
                }
            }catch(IOException e){
                logger.fatal("read file error!", e);
            }
            try{
                if(os!=null){
                    os.close();
                }
            }catch(IOException e){
                logger.fatal("read file error!", e);
            }
        }
        return null;
    }
    
    public static void write(byte[] data, String file)
            throws IOException {
        if (file == null) {
            throw new NullPointerException(" param File is null in write method!");
        }
        if (data == null) {
            throw new NullPointerException(" param content is null in write method!");
        }
        File f = new File(file);
        String parent = f.getParent();
        if (parent != null)
            makeDirs(parent);
        if (f.exists()) {
            f.delete();
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data);
            os.flush();
        }
        catch (IOException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IOException("write file fail", e);
        }
        finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean makeDirs(File f) {
        return f.exists() || f.mkdirs();
    }

    public static boolean makeDirs(String f) {
        File file = new File(f);
        return makeDirs(file);
    }
    
    public static boolean write(File f, InputStream is) {
        if (f == null) {
            throw new NullPointerException("param File is null in write method!");
        }
        if (is == null) {
            throw new NullPointerException("param InputStream is null in write method!");
        }
        OutputStream os = null;
        try {
            String parent = f.getParent();
            if (parent != null)
                makeDirs(parent);
            if (f.exists()) {
                f.delete();
            }
            os = new FileOutputStream(f);
            byte[] data = new byte[1024*8];
            int ch;
            while ((ch = is.read(data)) > 0) {
                os.write(data, 0, ch);
                os.flush();
            }
            return true;
        }
        catch (IOException e) {
            logger.fatal("write file fail", e);
        }
        catch (Exception e) {
            logger.fatal("write file fail", e);
        }
        finally {
            StreamUtil.close(os);
        }
        return false;
    }

}
