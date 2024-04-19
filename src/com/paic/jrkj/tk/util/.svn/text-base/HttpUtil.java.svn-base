package com.paic.jrkj.tk.util;

import com.paic.jrkj.tk.tools.kv.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

public class HttpUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String replaceFromUrl(String str) {
        str = str.replaceAll("%2B", "\\+");
        str = str.replaceAll("%2F", "\\/");
        return str.replaceAll("%3D", "\\=");
    }

    public static Parameters<String, String> encapeRequest(ServletRequest request) {
        Parameters<String, String> parameters = new Parameters<String, String>();
        String name;
        for (java.util.Enumeration enume = request.getParameterNames(); enume.hasMoreElements();) {
            name = (String) enume.nextElement();
            String[] values = request.getParameterValues(name);
            parameters.add(name, values);
        }
        return parameters;
    }
}
