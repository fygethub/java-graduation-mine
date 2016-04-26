/**   
 * @title: FilenameFiterTest.java 
 * @package: liutf.stream.test 
 * @description: TODO
 *  liutf  
 * @date 2015年6月16日 上午9:16:24 
 * @version 1.0.0 
 */
package liutf.stream.test;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @description: TODO
 * : liutf
 * @date: 2015年6月16日 上午9:16:24
 * @version: V1.0.0
 */
public class FilenameFiterTest implements FilenameFilter {

	/**
	 * @description: TODO
	 * @param dir
	 * @param name
	 * @return
	 * : liutf
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 * @date: 2015年6月16日 上午9:18:04
	 */
	@Override
	public boolean accept(File dir, String name) {
		return (name.endsWith(".xml") || new File(dir.getAbsolutePath() + File.separator + name).isDirectory());
	}

	public static void printFileName(File file) {
		FilenameFiterTest filter = new FilenameFiterTest();
		File[] files = file.listFiles(filter);
		for (File f : files) {
			if (f.isFile())
				System.out.println(f.getName());
			else
				printFileName(f);
		}
	}

	/**
	 * @description: TODO
	 * @param args
	 * : liutf
	 * @date: 2015年6月16日 上午9:16:24
	 */
	public static void main(String[] args) {
		File file = new File("E:\\上传工具测试数据\\测试数据 - 副本");
		printFileName(file);
	}
}
