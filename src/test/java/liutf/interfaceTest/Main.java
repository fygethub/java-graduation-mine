/**   
 * @title: Main.java 
 * @package: liutf.interfaceTest 
 * @description: TODO
 * @author liutf  
 * @date 2015年5月20日 下午4:40:29 
 * @version 1.0.0 
 */
package liutf.interfaceTest;

/**
 * @description: TODO
 * @author: liutf
 * @date: 2015年5月20日 下午4:40:29
 * @version: V1.0.0
 */
public class Main {

	/**
	 * @description: TODO
	 * @param args
	 * @author: liutf
	 * @date: 2015年5月20日 下午4:40:29
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test() {
			@Override
			public void doSomeThing(String arg) {
				System.out.println("arg-->" + arg);
			}
		};
		test.doSomeThing("hello world!");

	}

}
