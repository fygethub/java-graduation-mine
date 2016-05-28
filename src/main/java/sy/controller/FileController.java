package sy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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

import javax.annotation.Resource;
import javax.print.DocFlavor.READER;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sy.comparator.NameComparator;
import sy.comparator.SizeComparator;
import sy.comparator.TypeComparator;
import sy.model.Tfile;
import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.service.FileUSerServiceI;
import sy.service.UserServiceI;
import sy.util.ClobUtil;
import sy.util.ConfigUtil;

/**
 * 文件控制器
 * 
 * 
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
	
	@Resource
	UserServiceI userServiceI;
	
	@Resource
	FileUSerServiceI fileUSerServiceI;
	
	@RequestMapping("/fileManage")
	@ResponseBody
	public Map<String, Object> fileManage(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		Map<String, Object> m = new HashMap<String, Object>();

		// 根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = session.getServletContext().getRealPath("/") + "attached/";

		// 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = request.getContextPath() + "/attached/";
		
		System.out.println("rootPath:"+rootPath+"\n"+"rootUrl:"+rootUrl);
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
			rootPath += dirName + "\\";
			rootUrl += dirName + "\\";
			File saveDirFile = new File(rootPath);
			System.out.println(saveDirFile);
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
	public String  upload(HttpServletResponse response, HttpServletRequest request, HttpSession session,@RequestParam(value="file",required=false)CommonsMultipartFile file,String remark) {

		fileUSerServiceI.save(request,file,remark,session);
		
		return "/fileupload/successFile";
	}
	
	@RequestMapping("/fileManagePage")
	public String fileManagePage(){
		return "/fileupload/fileupload";
	}
	
	@RequestMapping("/fileGrid")
	@ResponseBody
	public DataGrid fileGrid(HttpSession session,HttpServletRequest request,PageHelper ph){
		fileUSerServiceI.fileGrid(session,request,ph);
		
		return null;
	}
	
}
