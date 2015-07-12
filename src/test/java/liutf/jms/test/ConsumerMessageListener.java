/**   
 * @title: ConsumerMessageListener.java 
 * @package: liutf.jms.test 
 * @description: TODO
 * @author liutf  
 * @date 2015年6月8日 上午11:36:27 
 * @version 1.0.0 
 */
package liutf.jms.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @description: 消息监听器
 * @author: liutf
 * @date: 2015年6月8日 上午11:36:27
 * @version: V1.0.0
 */
public class ConsumerMessageListener {

	public void onMessage(Message message) {
		// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换
		TextMessage textMsg = (TextMessage) message;
		System.out.println("接收到一个纯文本消息。");
		try {
			System.out.println("消息内容是：" + textMsg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
