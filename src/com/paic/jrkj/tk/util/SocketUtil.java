package com.paic.jrkj.tk.util;

import java.net.SocketAddress;

public class SocketUtil {

	public static String getIp(SocketAddress address){
		String ip = null;
		if(address != null){
			ip = address.toString().trim();
			int index = ip.lastIndexOf(':');
			if(index < 1){
				index = ip.length();
			}
			ip = ip.substring(1, index);
			if(ip.length() > 15){
				ip = ip.substring(Math.max(ip.indexOf("/") + 1, ip.length() - 15));
			}
		}
		return ip;
	}
}
