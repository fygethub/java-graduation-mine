/**   
 * @title: HttpClientTest.java 
 * @package: liutf.httpClient.test 
 * @description: TODO
 *  liutf  
 * @date 2015年5月21日 上午10:54:40 
 * @version 1.0.0 
 */
package liutf.httpClient.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @description: TODO
 * : liutf
 * @date: 2015年5月21日 上午10:54:40
 * @version: V1.0.0
 */
public class HttpClientTest {
	public void longerMethod() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://targethost/homepage");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}

		HttpPost httpPost = new HttpPost("http://targethost/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);

		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}
	}

	//@Test
	public void simplerMethod() throws ClientProtocolException, IOException {
		// The fluent API relieves the user from having to deal with manual
		// deallocation of system
		// resources at the cost of having to buffer response content in memory
		// in some cases.

		Content r1 = Request.Get("http://www.baidu.com").execute()
				.returnContent();
		// System.out.println(r1.asString());
		Content r2 = Request
				.Post("http://www.baidu.com/login")
				.bodyForm(
						Form.form().add("username", "vip")
								.add("password", "secret").build()).execute()
				.returnContent();
		System.out.println(r2.asString());
	}
}
