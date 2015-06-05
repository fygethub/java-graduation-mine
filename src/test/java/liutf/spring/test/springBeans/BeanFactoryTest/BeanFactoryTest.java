/**   
 * @title: BeanFactoryTest.java 
 * @package: liutf.test.springBeans 
 * @description: TODO
 * @author liutf  
 * @date 2015年5月15日 上午9:23:14 
 * @version 1.0.0 
 */
package liutf.spring.test.springBeans.BeanFactoryTest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/** 
 * @description: TODO
 * @author: liutf
 * @date: 2015年5月15日 上午9:23:14 
 * @version: V1.0.0
 */
public class BeanFactoryTest {

	/** 
	 * @description: TODO
	 * @param args
	 * @author: liutf
	 * @date: 2015年5月15日 上午9:23:14 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String path = "liutf/spring/test/springBeans/BeanFactoryTest/BeanFactoryTest.xml";
		Resource resource = new ClassPathResource(path);
		BeanFactory bf = new XmlBeanFactory(resource);
		//BeanFactory bf = Beans.
		
		MyTestBean myTestBean = (MyTestBean) bf.getBean("myTestBean");
		System.out.println(myTestBean.getTestStr());

	}

}
