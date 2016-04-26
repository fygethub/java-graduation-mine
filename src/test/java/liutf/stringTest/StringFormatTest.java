/**   
 * @title: StringFormatTest.java 
 * @package: liutf.stringTest 
 * @description: TODO
 *  liutf  
 * @date 2015年6月4日 下午3:26:56 
 * @version 1.0.0 
 */
package liutf.stringTest;

/** 
 * @description: TODO
 * : liutf
 * @date: 2015年6月4日 下午3:26:56 
 * @version: V1.0.0
 */
public class StringFormatTest {

	/** 
	 * @description: 对String的format进行测试
	 * @param args
	 * : liutf
	 * @date: 2015年6月4日 下午3:26:56 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String format = "my name is %s , my age is %s";
		String result = String.format(format, "liutf","27");
		System.out.println("result-->"+result);
	}

}
