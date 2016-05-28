package sy.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sy.dao.FileDaoI;
import sy.dao.UserDaoI;
import sy.model.Tfile;
import sy.model.Trole;
import sy.model.Tuser;
import sy.pageModel.DataGrid;
import sy.pageModel.Filegrid;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.service.FileUSerServiceI;
import sy.util.ClobUtil;
import sy.util.ConfigUtil;
import sy.util.DateUtil;

@Service
public class FileUserServiceImpl implements FileUSerServiceI{
	
	@Resource
	FileDaoI fileDaoI;

	@Resource
	UserDaoI userDaoI;
	
	//上传文件到指定文件夹，并且按照人员存入数据库
	@Override
	public void save(HttpServletRequest request, CommonsMultipartFile file,
		String remark,HttpSession session) {
		SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		String userid=sessionInfo.getId();
		String username=sessionInfo.getName();
		String url="";
		if (!file.isEmpty()) {
			//拿到输入流
			String filename=new Date().getTime()+file.getOriginalFilename();
			String size=size2string(file.getSize());
			String path=request.getSession().getServletContext().getRealPath("");
			String proName=request.getContextPath();
			
			path=path.substring(0,path.length()-proName.length())+"/upload";
			
			url=path+filename;
			File filefolder=new File(path);
			System.out.println(filefolder.exists());
			
			File tartgetFile=new File(path, filename);
			if (!tartgetFile.exists()) {
				tartgetFile.mkdirs();
			}
			
			try {
				file.transferTo(tartgetFile);
				Tfile tfile=new Tfile();
				tfile.setFilename(filename);
				tfile.setId(UUID.randomUUID().toString());
				tfile.setUploadtime(new Date());
				tfile.setUrl(url);
				tfile.setUserid(userid);
				tfile.setUsername(username);
				tfile.setSize(size);
				tfile.setRemark(ClobUtil.getClob(remark));
				tfile.setType(filename.split("\\.")[1]);
				fileDaoI.save(tfile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			//按上传者存进数据库中。
			
		}
		
	}
	
	private String size2string(long size){  
		  DecimalFormat df = new DecimalFormat("0.00");  
		  String mysize = "";  
		  if( size > 1024*1024){  
		    mysize = df.format( size / 1024f / 1024f ) +"M";  
		  }else if( size > 1024 ){  
		    mysize = df.format( size / 1024f ) +"K";  
		  }else{  
		    mysize = size + "B";  
		  }  
		  return mysize;  
		}

	
	@Override
	public DataGrid fileGrid(HttpSession session, HttpServletRequest request,PageHelper pHelper) {
		// TODO Auto-generated method stub
		SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		String userid=sessionInfo.getId();
		Tuser tuser=userDaoI.get(Tuser.class, userid);
		Set<Trole> rList= tuser.getTroles();
		String hql="";
		boolean isadmin=false;
		for(Trole trole:rList){
			if (trole.getName().equals("admin")) {
					isadmin=true;
				}
		}
		List<Tfile> list=new ArrayList<Tfile>();
		if(!isadmin){
			hql="from Tfile ";
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("userid", userid); 
			list=fileDaoI.find(hql, params,pHelper.getPage(),pHelper.getRows());
		}else {
			hql="from Tfile";
			list=fileDaoI.find(hql,pHelper.getPage(),pHelper.getRows());
		}
		List<Filegrid> filegrids=new ArrayList<Filegrid>();
		for (Tfile t:list) {
			Filegrid filegrid=new Filegrid();
			BeanUtils.copyProperties(t, filegrid,new String[] {"remark","uploadtime"});
			filegrid.setRemark(ClobUtil.getString(t.getRemark()));
			filegrid.setUploadtime(DateUtil.format(t.getUploadtime()));
			filegrids.add(filegrid);
		}
		
		DataGrid dataGrid=new DataGrid();
		dataGrid.setRows(filegrids);
		dataGrid.setTotal(Long.valueOf(list.size()));
		return dataGrid;
	} 
}
