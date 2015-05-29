/**   
 * @title: HttpClentUtils.java 
 * @package: liutf.httpClient.test 
 * @description: TODO
 * @author liutf  
 * @date 2015年5月21日 上午11:22:55 
 * @version 1.0.0 
 */
package liutf.httpClient.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;

/**
 * @description: TODO
 * @author: liutf
 * @date: 2015年5月21日 上午11:22:55
 * @version: V1.0.0
 */
public class HttpClientUtils2 {

	public static String sendGet(String url) throws ClientProtocolException,
			IOException {
		return sendGet(url, "UTF-8");
	}

	public static String sendGet(String url, String charSet)
			throws ClientProtocolException, IOException {
		return Request.Get(url).execute().returnContent()
				.asString(Charset.forName(charSet));
	}

	public static HttpResponse sendGet4Request(String url)
			throws ClientProtocolException, IOException {
		return Request.Get(url).execute().returnResponse();
	}

	public static String sendPost(String url, Map<String, String> paramMap)
			throws ClientProtocolException, IOException {
		return sendPost(url, paramMap, "UTF-8");
	}

	public static String sendPost(String url, Map<String, String> paramMap,
			String charSet) throws ClientProtocolException, IOException {

		return Request.Post(url).bodyForm(map2form(paramMap)).execute()
				.returnContent().asString(Charset.forName(charSet));
	}

	public static HttpResponse sendPost4Response(String url,
			Map<String, String> paramMap) throws ClientProtocolException,
			IOException {
		return Request.Post(url).bodyForm(map2form(paramMap)).execute()
				.returnResponse();
	}

	public static String uploadSingle(String url, File file)
			throws ClientProtocolException, IOException {
		return uploadSingle(url, file, "UTF-8");
	}

	public static String uploadSingle(String url, File file, String charSet)
			throws ClientProtocolException, IOException {
		return Request.Post(url)
				.bodyFile(file, ContentType.MULTIPART_FORM_DATA).execute()
				.returnContent().asString(Charset.forName(charSet));
	}

	public static HttpResponse uploadSingle4Response(String url, File file)
			throws ClientProtocolException, IOException {
		return Request.Post(url)
				.bodyFile(file, ContentType.MULTIPART_FORM_DATA).execute()
				.returnResponse();
	}

	public static String uploadMultiple(String url, Map<String, File> fileMap)
			throws ClientProtocolException, IOException {
		return uploadMultiple(url, fileMap, "UTF-8");
	}

	public static String uploadMultiple(String url, Map<String, File> fileMap,
			String charSet) throws ClientProtocolException, IOException {
		return Request.Post(url).body(map2entity(fileMap)).execute()
				.returnContent().asString(Charset.forName(charSet));
	}

	public static HttpResponse uploadMultiple4Response(String url,
			Map<String, File> fileMap) throws ClientProtocolException,
			IOException {
		return Request.Post(url).body(map2entity(fileMap)).execute()
				.returnResponse();
	}

	protected static HttpEntity map2entity(Map<String, File> map) {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (String key : map.keySet()) {
			builder.addPart(key, new FileBody(map.get(key)));
		}
		return builder.build();
	}

	protected static List<NameValuePair> map2form(Map<String, String> map) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		for (String key : map.keySet()) {
			param.add(new BasicNameValuePair(key, map.get(key)));
		}
		return param;
	}

	public static void main(String[] args) {
		String url = "http://www.baidu.com";
		String result = null;
		try {
			result = HttpClientUtils2.sendGet(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("result-->" + result);
	}
}
