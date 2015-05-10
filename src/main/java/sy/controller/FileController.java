package sy.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.comparator.NameComparator;
import sy.comparator.SizeComparator;
import sy.comparator.TypeComparator;
import sy.util.ConfigUtil;

/**
 * 文件控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/fileController")
public class FileController extends BaseController {

	/**
	 * 浏览器服务器附件
	 * 
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/fileManage")
	@ResponseBody
	public Map<String, Object> fileManage(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();

		// 根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = session.getServletContext().getRealPath("/") + "attached/";

		// 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = request.getContextPath() + "/attached/";

		// 图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if (!Arrays.<String> asList(new String[] { "image", "flash", "media", "file" }).contains(dirName)) {
				// out.println("Invalid Directory name.");
				// return;
				try {
					response.getWriter().write("无效的目录名称！");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return m;
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}

		// 根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		// 排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			// out.println("Access is not allowed.");
			// return;
			try {
				response.getWriter().write("不允许访问！");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return m;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			// out.println("Parameter is not valid.");
			// return;
			try {
				response.getWriter().write("参数无效！");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return m;
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			// out.println("Directory does not exist.");
			// return;
			try {
				response.getWriter().write("目录不存在！");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return m;
		}

		// 遍历目录取的文件信息
		List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		m.put("moveup_dir_path", moveupDirPath);
		m.put("current_dir_path", currentDirPath);
		m.put("current_url", currentUrl);
		m.put("total_count", fileList.size());
		m.put("file_list", fileList);

		return m;
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String, Object> upload(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("error", 1);
		m.put("message", "上传失败！");
		// 文件保存目录路径
		String savePath = session.getServletContext().getRealPath("/") + "attached/";

		// 文件保存目录URL
		String saveUrl = request.getContextPath() + "/attached/";

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", ConfigUtil.get("image"));
		extMap.put("flash", ConfigUtil.get("flash"));
		extMap.put("media", ConfigUtil.get("media"));
		extMap.put("file", ConfigUtil.get("file"));

		long maxSize = Long.parseLong(ConfigUtil.get("maxFileSize")); // 允许上传最大文件大小(字节)

		if (!ServletFileUpload.isMultipartContent(request)) {
			m.put("error", 1);
			m.put("message", "请选择文件！");
			return m;
		}

		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			uploadDir.mkdirs();
		}

		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			m.put("error", 1);
			m.put("message", "上传目录没有写权限！");
			return m;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			m.put("error", 1);
			m.put("message", "目录名不正确！");
			return m;
		}

		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthDf = new SimpleDateFormat("MM");
		SimpleDateFormat dateDf = new SimpleDateFormat("dd");
		Date date = new Date();
		String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
		savePath += ymd;
		saveUrl += ymd;
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		if (ServletFileUpload.isMultipartContent(request)) {// 判断表单是否存在enctype="multipart/form-data"
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					String fileName = item.getName();
					if (!item.isFormField()) {
						// 检查文件大小
						if (item.getSize() > maxSize) {
							m.put("error", 1);
							m.put("message", "上传文件大小超过限制！(允许最大[" + maxSize + "]字节，您上传了[" + item.getSize() + "]字节)");
							return m;
						}
						// 检查扩展名
						String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
							m.put("error", 1);
							m.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式！");
							return m;
						}

						String newFileName = UUID.randomUUID().toString() + "." + fileExt;
						try {
							File uploadedFile = new File(savePath, newFileName);
							item.write(uploadedFile);
						} catch (Exception e) {
							m.put("error", 1);
							m.put("message", "上传文件失败！");
							return m;
						}

						m.put("error", 0);
						m.put("url", saveUrl + newFileName);
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}

		return m;
	}

}
