package com.paic.jrkj.tk.runner;

import com.fadada.sdk.extra.client.FddExtraClient;
import com.fadada.sdk.extra.model.req.DownloadTemplateParams;

public class FddRunner {
	public static void main(String[] args) {
		String APPID = "406082";
		String APPKEY = "pE1TFa0itKPdqvdSupW6YGG3";
		String V = "2.0";
		String HOST = "http://test.api.fabigbig.com:8888/api/";
		String CONTRACTID = "FXJSSSF20250114001";

		String customerid = "1D58C54844A62281F58FC10EA21574B5";
		// String APPID = "501672";
		// String APPKEY = "n7YtpiMX27XPBCmEN3U3kNDl";
		// String V = "2.0";
		// String HOST = "https://textapi.fadada.com/api2/";

		// FddBaseClient client = new FddBaseClient(APPID, APPKEY, V, HOST);
		// DownloadPdfParams params = new DownloadPdfParams();
		// params.setContractId(CONTRACTID); // 合同编号
		//
		// 如下，传setPath参数可以直接保存文件到本地，不传则返回url
		String path = "E:\\data\\" + CONTRACTID + ".pdf";
		// params.setPath(path); // 指定路径，如：D:\\pdf\\uuidNew.pdf
		// String result = client.invokeDownloadPdf(params);
		// System.out.println(result);

		FddExtraClient client = new FddExtraClient(APPID, APPKEY, V, HOST);
		DownloadTemplateParams params = new DownloadTemplateParams();
		params.setTemplateId(CONTRACTID);
		params.setPath(path); // 指定保存地址
		String result = client.invokeDownloadTemplate(params);
		System.out.println("导出成功.....");
	}
}
