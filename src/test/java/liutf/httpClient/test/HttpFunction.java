/**   
 * @title: HttpFunction.java 
 * @package: liutf.httpClient.test 
 * @description: TODO
 *  liutf  
 * @date 2015年5月21日 下午2:48:21 
 * @version 1.0.0 
 */
package liutf.httpClient.test;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/**
 * @description: 处理提交工具中的http请求部分
 * : liutf
 * @date: 2015年5月21日 下午2:48:21
 * @version: V1.0.0
 */
public class HttpFunction {

	/**
	 * @description: 上传xml文件到电子商务系统
	 * @param url
	 * @param xmlFile
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * : liutf
	 * @date: 2015年5月21日 下午2:50:15
	 */
	public static String uploadXml(String url, File xmlFile)
			throws ClientProtocolException, IOException {
		return HttpClientUtils.uploadSingle(url, xmlFile);
	}

	/**
	 * @description: 通知电子商务系统修改资源状态（此处是通知修资源为可用状态）
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * : liutf
	 * @date: 2015年5月21日 下午2:53:22
	 */
	public static String notify(String url) throws ClientProtocolException,
			IOException {
		return HttpClientUtils.sendGet(url);
	}

}
