package com.paic.jrkj.tk.util;

public class SystemUtil {
	
	private static final boolean OS_IS_PROD_MODE = Boolean.parseBoolean(System.getProperty("product.mode", "false"));
	
	private SystemUtil(){}
	
	public static boolean isInProductMode(){
		return OS_IS_PROD_MODE;
	}
}
