/**   
 * @title: ProducerServiceImpl.java 
 * @package: liutf.jms.test 
 * @description: TODO
 * @author liutf  
 * @date 2015年6月8日 上午10:56:18 
 * @version 1.0.0 
 */
package liutf.jms.test;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @description: 生产者实现
 * @author: liutf
 * @date: 2015年6月8日 上午10:56:18
 * @version: V1.0.0
 */
public class ProducerServiceImpl implements ProducerService {

	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * @description: 发送消息
	 * @param msgId
	 * @param result
	 * @author: liutf
	 * @see liutf.jms.test.ProducerService#sendMessage(java.lang.String, int)
	 * @date: 2015年6月8日 上午11:02:58
	 */
	@Override
	public void sendMessage(Destination destination, final String message) {
		System.out.println("---------------生产者发送消息-----------------");
		System.out.println("---------------生产者发了一个消息：" + message);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});

	}

}
