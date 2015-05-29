/**   
 * @title: StringUtils.java 
 * @package: liutf.test 
 * @description: TODO
 * @author liutf  
 * @date 2015年5月13日 上午11:02:35 
 * @version 1.0.0 
 */
package liutf.spring.test.springCore.StringUtilsTest;

/**
 * @description: 测试对象是spring-core-3.2.3.RELEASE包中的org.springframework.util.StringUtils类
 * @author: liutf
 * @date: 2015年5月13日 上午11:02:35
 * @version: V1.0.0
 */
public abstract class StringUtils {
	private static final String FOLDER_SEPARATOR = "/";// 文件夹分离器
	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";// windows系统下的文件分离器
	private static final String TOP_PATH = "..";
	private static final String CURRENT_PATH = ".";
	private static final char EXTENSION_SEPARATOR = 46;

	public static boolean isEmpty(Object str) {
		boolean b = false;
		if (str == null || "".equals(str)) {
			b = true;
		}
		return b;
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	public static boolean hasLength(String str) {
		return hasLength((CharSequence)str);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int charSeqLength = str.length();
		for (int i = 0; i < charSeqLength; i++) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasText(String str) {
		return hasText(str);
	}

	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; ++i) {
			System.out.println(i + "--" + str.charAt(i));
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && (Character.isWhitespace(sb.charAt(0)))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0
				&& Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int sbLen = sb.length();
		for (int i = 0; i < sbLen; i++) {
			while (Character.isWhitespace(sb.charAt(i))) {
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}
	
	
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if ((str == null) || (prefix == null)) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if ((str == null) || (suffix == null)) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length())
				.toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}
	
	
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}
}
