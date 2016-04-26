/**   
 * @title: AssertTest.java 
 * @package: liutf 
 * @description: TODO
 *  liutf  
 * @date 2015年6月2日 上午9:27:55 
 * @version 1.0.0 
 */
package liutf.assertTest;

import org.springframework.util.Assert;

/** 
 * @description: TODO
 * : liutf
 * @date: 2015年6月2日 上午9:27:55 
 * @version: V1.0.0
 */
public class AssertTest {

	/** 
	 * @description: TODO
	 * @param args
	 * : liutf
	 * @date: 2015年6月2日 上午9:27:55 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = null;
		Assert.notNull(str,"str不能为空");
		System.out.println(111);//断言后面的不会执行

	}

}
