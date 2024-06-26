package com.paic.jrkj.tk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;

import com.paic.jrkj.tk.util.StreamUtil;
import com.paic.jrkj.tk.tools.lock.LockPool;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 11:43:00 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 11:43:00
 */
public class XmlReader {

    private final Log logger = LogFactory.getLog(XmlReader.class);

    public XmlReader() {
    }

    public <T> T toXml(String s, Class<T> typeClass, Mapping mapping)
            throws IOException {
        Reader reader = null;
        LockPool lock = LockPool.getLockPool("castor");
        try {
            long t = System.currentTimeMillis();
            lock.fetchLock();
            reader = new StringReader(s);
            Unmarshaller unmarshaller = new Unmarshaller(typeClass);
            if (mapping != null) {
                unmarshaller.setMapping(mapping);
            }
            @SuppressWarnings("unchecked")
            T node = (T) unmarshaller.unmarshal(reader);
            logger.debug("string to xml user:[" + (System.currentTimeMillis() - t) + "] ms!");
            return node;
        } catch (MappingException e) {
            throw new IOException("CHANGE TO XML FAIL", e);
        } catch (MarshalException e) {
            throw new IOException("CHANGE TO XML FAIL", e);
        } catch (ValidationException e) {
            throw new IOException("CHANGE TO XML FAIL", e);
        } catch(Exception e){
            throw new IOException("CHANGE TO XML FAIL", e);
        }finally {
            StreamUtil.close(reader);
             lock.releaseLock();
        }
    }

    public <T> T toXml(InputStream is, Class<T> typeClass, Charset charset, Mapping mapping)
            throws IOException {
        if (is == null) {
            throw new IOException("InputStream is Closed!");
        }
        Reader reader = null;
        LockPool lock = LockPool.getLockPool("castor");
        try {
            long t = System.currentTimeMillis();
            lock.fetchLock();
            reader = new InputStreamReader(is, charset);
            Unmarshaller unmarshaller = new Unmarshaller(typeClass);
            if (mapping != null) {
                unmarshaller.setMapping(mapping);
            }
            @SuppressWarnings("unchecked")
            T xmlObject = (T) unmarshaller.unmarshal(reader);
            logger.debug("transfer string to xml user:[" + (System.currentTimeMillis() - t) + "] ms!");
            return xmlObject;
        } catch (MappingException e) {
            throw new IOException("CHANGE TO XML FAIL", e);
        } catch (MarshalException e) {
            throw new IOException("CHANGE TO XML FAIL", e);
        } catch (ValidationException e) {
            throw new IOException("CHANGE TO XML FAIL", e);
        } catch(Exception e){
            throw new IOException("CHANGE TO XML FAIL", e);
        } finally {
            StreamUtil.close(reader);
            StreamUtil.close(is);
             lock.releaseLock();
        }
    }
}
