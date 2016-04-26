/**   
 * @title: SimpleAliasRegistry.java 
 * @package: liutf.test.springBeans 
 * @description: TODO
 *  liutf  
 * @date 2015年5月14日 上午10:46:52 
 * @version 1.0.0 
 */
package liutf.spring.test.springBeans.BeanFactoryTest;

import org.springframework.core.SimpleAliasRegistry;
import org.springframework.util.StringUtils;

/**
 * @description: TODO
 * : liutf
 * @date: 2015年5月14日 上午10:46:52
 * @version: V1.0.0
 */
public class SimpleAliasRegistryTest {

	/**
	 * @description: TODO
	 * @param args
	 * : liutf
	 * @date: 2015年5月14日 上午10:46:52
	 */
	public static void main(String[] args) {

		try {
			// 1、测试SimpleAliasRegistry的注册流程
			SimpleAliasRegistry sar = new SimpleAliasRegistry();
			sar.registerAlias("1", "01");
			sar.registerAlias("01", "001");
			sar.registerAlias("001", "0001");

			sar.registerAlias("1", "001");
			sar.registerAlias("1", "0001");

			System.out.println(StringUtils.arrayToCommaDelimitedString(sar
					.getAliases("1")));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
