/**   
 * @title: RegexTest.java 
 * @package: liutf.regexTest 
 * @description: TODO
 *  liutf  
 * @date 2015年7月6日 上午9:10:30 
 * @version 1.0.0 
 */
package liutf.regexTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 关于正则表达式的使用测试
 * : liutf
 * @date: 2015年7月6日 上午9:10:30
 * @version: V1.0.0
 */
public class RegexTest {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*[0-9]*");
		Matcher matcher = pattern.matcher("12234");
		if (matcher.matches())
			System.out.println("true");
		else
			System.out.println("false");
	}
}
