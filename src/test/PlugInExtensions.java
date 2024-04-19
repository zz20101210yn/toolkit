package com.yonghui.mid.dtmc.plugin.hbase.bussiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlugInExtensions {
	public static Logger logger = LoggerFactory.getLogger(PlugInExtensions.class);

	private static String FACTOR = "sdt";

	private static Integer OFFSET = 5;

	private static String FORMAT = "yyyyMMdd";

	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern(FORMAT);

	private static List<String> PREFIXS = new ArrayList<String>();
	static {
		String offset = System.getProperty("splitor.offset");
		if (!Objects.isNull(offset) && !"".equals(offset)) {
			OFFSET = Integer.parseInt(offset);
		}
		PREFIXS.add("benefit_growth_d_");
		PREFIXS.add("benefit_growth_m_");
		PREFIXS.add("execl_member_label_");
	}

	//返回true过期;false不过期
	public static boolean isExpire(String k, String v) {
		if (Objects.isNull(v) || "".equals(v)) {
			return false;
		}
		for (String prefix : PREFIXS) {
			if (k.startsWith(prefix)) {
				try {
					int startIdx = v.indexOf(FACTOR) + OFFSET;
					int endIdx = startIdx + 8;
					String sdt = v.substring(startIdx, endIdx);
					if(isExpireByTime(sdt)) {
						logger.info("access hbase :" + k + ":" + sdt);
						return true;
					}
					return false;
				} catch (Exception e) {
					logger.warn("verify split failed:" + k);
					return false;
				}
			}
		}
		return false;
	}
	private static boolean isExpireByTime(String sdt) {
		LocalDateTime time = LocalDateTime.now();
		time = time.minusDays(1);
		String nowDt = DTF.format(time);
		if (Integer.parseInt(sdt) < Integer.parseInt(nowDt)) {
			return true;
		}
		return false;
	}
}
