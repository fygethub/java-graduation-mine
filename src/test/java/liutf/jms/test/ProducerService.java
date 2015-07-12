/**   
 * @title: ProducerService.java 
 * @package: liutf.jms.test 
 * @description: TODO
 * @author liutf  
 * @date 2015年6月8日 上午11:01:24 
 * @version 1.0.0 
 */
package liutf.jms.test;

import javax.jms.Destination;

/**
 * @description: 消息生产者接口
 * @author: liutf
 * @date: 2015年6月8日 上午11:01:24
 * @version: V1.0.0
 */
public interface ProducerService {

	/**
	 * @description: 发送消息的接口方法
	 * @param msgId
	 * @param result
	 * @author: liutf
	 * @date: 2015年6月8日 上午11:02:33
	 */
	void sendMessage(Destination destination, final String message);
}
