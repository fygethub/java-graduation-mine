/**   
 * @title: MainTest.java 
 * @package: liutf.quartz.test 
 * @description: TODO
 * @author liutf  
 * @date 2015年6月1日 下午2:59:22 
 * @version 1.0.0 
 */
package liutf.quartz.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * @description: TODO
 * @author: liutf
 * @date: 2015年6月1日 下午2:59:22
 * @version: V1.0.0
 */
public class MainTest {

	/**
	 * @description: TODO
	 * @param args
	 * @author: liutf
	 * @date: 2015年6月1日 下午2:59:22
	 */
	public static void main(String[] args) {
		
		String xmlPath = "liutf/quartz/test/quartzTest.xml";
		Resource resource = new ClassPathResource(xmlPath);
		BeanFactory beanFactory = new XmlBeanFactory(resource);
		
		QuartzTest qt = (QuartzTest) beanFactory.getBean("quartzTest");
		
		//qt.execute(context);
		
		
	}

}
