/**   
 * @title: TestMain.java 
 * @package: liutf.test 
 * @description: TODO
 *  liutf  
 * @date 2015年5月13日 上午11:19:40 
 * @version 1.0.0 
 */
package liutf.spring.test.springCore.StringUtilsTest;

import org.springframework.beans.factory.support.ReplaceOverride;

/**
 * @description: TODO
 * : liutf
 * @date: 2015年5月13日 上午11:19:40
 * @version: V1.0.0
 */
public class TestMain {

	/**
	 * @description: TODO
	 * @param args
	 * : liutf
	 * @date: 2015年5月13日 上午11:19:40
	 */
	public static void main(String[] args) {
		String str = " a b c d e ";
		char c = 'c';
		CharSequence cs = "12345";// String实现了CharSequence接口

		// 1、测试hasLength方法
		// boolean b = StringUtils.hasLength(str);

		// 2、测试isWhitespace方法
		// boolean b = Character.isWhitespace(c);

		// 3、测试hasText方法
		// boolean b = StringUtils.hasText(str);//测试结果，死循环

		// 4、测试containsWhitespace方法
		// boolean b = StringUtils.containsWhitespace(cs);

		// 5、测试trimAllWhitespace方法
		String rs = org.springframework.util.StringUtils.trimAllWhitespace(str);

		// 6、测试startsWithIgnoreCase方法
		String prefix = " A";
		// boolean b = StringUtils.startsWithIgnoreCase(str, prefix);

		// System.out.println("rs-->"+rs);
		// System.out.println("b-->" + b);

		// 7.测试replace方法，debug查看原理
		String inString = "abcabcabcabcdabcabcabcd";
		String oldPattern = "ab";
		String newPattern = "xy";

		String outString = StringUtils
				.replace(inString, oldPattern, newPattern);
		System.out.println("outString-->" + outString);

	}

}
