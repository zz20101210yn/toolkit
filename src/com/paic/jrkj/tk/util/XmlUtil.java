package com.paic.jrkj.tk.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;

import com.paic.jrkj.tk.XmlReader;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-10-31 11:30:40 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-10-31 11:30:40
 */
public class XmlUtil {

    private static final Log logger = LogFactory.getLog(XmlUtil.class);

    private XmlUtil() {
    }

    /**
     * get node value by name from xml
     *
     * @param xmlString xml
     * @param name      node name
     * @return node value
     */
    public static String getNodeValue(String xmlString, String name) {
        if (xmlString == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("xml is null!");
            }
            return null;
        }
        int startPos = xmlString.indexOf("<" + name + ">");
        if (startPos == -1) {
            if (logger.isDebugEnabled()) {
                logger.debug("empty node[" + name + "]");
            }
            return null;
        }
        int endPos = xmlString.lastIndexOf("</" + name + ">");
        if (endPos == -1) {
            if (logger.isDebugEnabled()) {
                logger.debug("error xml node [" + name + "]");
            }
            return null;
        }
        return xmlString.substring(startPos + ("</" + name + ">").length() - 1, endPos);
    }

    /**
     * get node value by name from xml
     *
     * @param xmlString xml
     * @param name      node name
     * @return node value list
     */
    public static java.util.List<String> getNodeValueList(String xmlString, String name) {
        if (xmlString == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("xml is null!");
            }
            return null;
        }
        java.util.List<String> valueList = new java.util.ArrayList<String>();
        String substr = xmlString;
        int startPos;
        int tagNameLen = name.length();
        String nodeValue;
        while ((startPos = substr.indexOf("<" + name + ">")) != -1) {
            int endPos = substr.indexOf("</" + name + ">");
            if (endPos == -1) {
                throw new IllegalArgumentException("error xml tag[</" + name + ">] in " + xmlString);
            }
            nodeValue = substr.substring(startPos + tagNameLen + 2, endPos);
            if (nodeValue.indexOf("</" + name + ">") != -1) {
                throw new IllegalArgumentException("error xml tag[<" + name + ">] in " + xmlString);
            }
            valueList.add(nodeValue);
            substr = substr.substring(endPos + tagNameLen + 3);
        }
        return valueList;
    }

    /**
     * get node value by name from xml
     *
     * @param xmlString xml
     * @param names     node name
     * @return node value list
     */
    public static java.util.List<String> getNodeValueList(String xmlString, String... names) {
        if (xmlString == null || names.length == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("xml is null!");
            }
            return null;
        }
        if (names.length == 1) {
            return getNodeValueList(xmlString, names[0]);
        }
        String[] names2 = new String[names.length - 1];
        System.arraycopy(names, 0, names2, 0, names.length - 1);
        return getNodeValueList(getNodeValue(xmlString, names2), names[names.length - 1]);
    }

    /**
     * get node value by names from xml<br/>
     * example: <br/>
     * String s ="<xml><a><b><c><a>ddddddddddddddd</a></c></b></a></xml>";<br/>
     * use #getNodeValue(s,"a","b","c","a") will return "ddddddddddddddd"<br/>
     *
     * @param xmlString xml
     * @param names     name
     * @return node value
     */
    public static String getNodeValue(String xmlString, String... names) {
        if (names == null || names.length == 0) {
            return null;
        }
        String nodeValue = xmlString;
        for (String name : names) {
            nodeValue = getNodeValue(nodeValue, name);
            if (nodeValue == null) {
                return null;
            }
        }
        return nodeValue;
    }

    public static <T> T xml2Object(Class<T> vClassType, String xmlClassPath, String mappingClassPath)
            throws IOException {
        try {
            XmlReader reader = new XmlReader();
            Charset utf8 = Charset.forName("UTF-8");
            return reader.toXml(XmlUtil.class.getResourceAsStream(xmlClassPath), vClassType, utf8, getMapping(mappingClassPath));
        } catch (MappingException e) {
            throw new IOException("read xml fail:", e);
        }
    }

    private static Mapping getMapping(String mappingClassPath)
            throws IOException, MappingException {
        Mapping mapping = mappingMap.get(mappingClassPath);
        if (mapping == null) {
            logger.info("load mapping[" + mappingClassPath + "]");
            synchronized (XmlUtil.class) {
                mapping = mappingMap.get(mappingClassPath);
                if (mapping == null) {
                    mapping = new Mapping();
                    mapping.loadMapping(XmlUtil.class.getResource(mappingClassPath));
                    mappingMap.put(mappingClassPath, mapping);
                }
            }
        }
        return mapping;
    }

    private static Map<String, Mapping> mappingMap = new HashMap<String, Mapping>();

}
