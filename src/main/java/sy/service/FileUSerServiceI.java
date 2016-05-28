package sy.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;

public interface FileUSerServiceI {

	void save(HttpServletRequest request, CommonsMultipartFile file,
			String remark, HttpSession session);

	DataGrid  fileGrid(HttpSession session, HttpServletRequest request, PageHelper ph);

}
