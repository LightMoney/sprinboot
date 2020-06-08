package cn.fan.springboot_easyexcle.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuchen
 *
 */
public class MessageUtils {
	
	/**
	 * @param photo 电话号码 
	 * @param msg 信息内容
	 */
	public static void send(String photo, String msg) {
		// 创建默认的HttpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 短信接口平台地址
		String url = "http://yl.mobsms.net/send/gsend.aspx";
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", "k3-cdht"));
		params.add(new BasicNameValuePair("pwd", "cdht123"));
		params.add(new BasicNameValuePair("dst", photo));
		params.add(new BasicNameValuePair("msg", msg));

		UrlEncodedFormEntity entity = null;
		try {
			// 将参数封装到请求实体中，这里必须设置字符编码为"gb2312"
			entity = new UrlEncodedFormEntity(params, "gb2312");
			httpPost.setEntity(entity);
			System.out.println("executing request:" + httpPost.getURI());
			System.out.println(EntityUtils.toString(entity));
			System.out.println(httpPost.toString());
			// 获取服务器响应
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				System.out.println("华丽的分割线-----------------------------");
				System.out.println("Response content:" + EntityUtils.toString(httpEntity, "utf-8"));
				System.out.println("华丽的分割线-----------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
