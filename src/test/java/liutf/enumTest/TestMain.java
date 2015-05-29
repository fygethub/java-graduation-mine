/**   
 * @title: TestMain.java 
 * @package: liutf.enumTest 
 * @description: TODO
 * @author liutf  
 * @date 2015年5月29日 上午11:28:31 
 * @version 1.0.0 
 */
package liutf.enumTest;

/** 
 * @description: TODO
 * @author: liutf
 * @date: 2015年5月29日 上午11:28:31 
 * @version: V1.0.0
 */
public class TestMain {

	/** 
	 * @description: TODO
	 * @param args
	 * @author: liutf
	 * @date: 2015年5月29日 上午11:28:31 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = BeanNames.USER_BEAN.name();
		int i = BeanNames.USER_BEAN.hashCode();
		System.out.println(name);
		System.out.println(i);

	}

}
