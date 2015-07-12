/**   
 * @title: Test.java 
 * @package: liutf.ruiliTest 
 * @description: TODO
 * @author liutf  
 * @date 2015年6月25日 下午4:21:14 
 * @version 1.0.0 
 */
package liutf.ruiliTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: liutf
 * @date: 2015年6月25日 下午4:21:14
 * @version: V1.0.0
 */
public class Test {

	public static void main(String[] args) {
		// List<List<String>> strList = new ArrayList<List<String>>();
		// List<String> sl1 = new ArrayList<String>();
		// sl1.add("1-1");
		// sl1.add("1-2");
		//
		// strList.add(sl1);
		//
		// List<String> s = strList.get(0);

		List<String> strList = new ArrayList<String>();
		strList.add("111");
		strList.add("222");

		for (int i = 0; i < strList.size(); i++)
			System.out.println(strList.get(i));

	}

}
