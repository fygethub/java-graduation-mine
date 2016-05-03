package sy.controller.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sy.pageModel.activiti.ProcessDefineModel;
import sy.pageModel.activiti.ProcessDeployModel;
import sy.service.WorkflowServiceI;

@Controller
@RequestMapping("/processController")
public class DefineProcessController {

	private static final Logger logger = Logger.getLogger(DefineProcessController.class);
	
	@Autowired
	WorkflowServiceI workflowServiceI;
	
	/**跳转到流程部署界面
	 * @return */
	@RequestMapping("/deploy")
	public String deploy(){
		logger.info("跳转到流程部署界面");
		return "/activity/deployprocess/deployprocess";
	}
	
	/**上传流程文件*/
	@RequestMapping("/upload")
	public String upload(HttpServletResponse response, HttpServletRequest request, HttpSession session,@RequestParam(value="deployfile",required=false) MultipartFile deployfile,String processName){
		CommonsMultipartFile commonsMultipartFile=(CommonsMultipartFile)deployfile;
		System.out.println("mulf"+commonsMultipartFile.getName());
		System.out.println("mulType"+commonsMultipartFile.getOriginalFilename());
		DiskFileItem diskFileIte=(DiskFileItem)commonsMultipartFile.getFileItem();
		File file=diskFileIte.getStoreLocation();
		System.out.println(file.getName());
		System.out.println(deployfile.getName());
		if (workflowServiceI.saveNewDeploye(file, processName)) {
			return "success";
		}else {
			System.out.println("deploy failure,NOT A ZIPFILE");
			return "deploy failure,NOT A ZIPFILE";
		}
	}
	
	/**查询流程定义的信息，对应表（act_re_procdef）*/
	@RequestMapping(value="/processDefineList",produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> processDefineList(int page,int rows){
		System.out.println("查询流程定义的信息");
		try {
			System.out.println("无异常");
			System.out.println("page:"+page+"rows:"+rows);
			List<ProcessDefineModel> list =workflowServiceI.findProcessDefinitionList(page, rows);
			Map<String , Object> map=new HashMap<String, Object>();
			map.put("total",workflowServiceI.processDefineListSize());
			System.out.println(list.size()+".....................");
			map.put("rows", list);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("异常");
			return null;
		}
		
	}
	
	
	/**
	 * 查询部署对象信息，对应表（act_re_deployment）
	 * @return
	 */
	@RequestMapping(value="/processdeployList",produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> processdeployList(int page,int rows){
		//1:查询部署对象信息，对应表（act_re_deployment）
		System.out.println("查询部署对象信息");
		try {
			System.out.println("page:"+page+"rows:"+rows);
			List<ProcessDeployModel> depList = workflowServiceI.findDeploymentList(page,rows);
			Map<String , Object> map=new HashMap<String, Object>();
			map.put("total", workflowServiceI.findDeploymentListSize());
			map.put("rows", depList);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	
	}
	
	/**
	 * 删除部署信息
	 */
	@RequestMapping("/processdel")
	@ResponseBody
	public String delProcessDefine(String id){
		workflowServiceI.deleteProcessDefinitionByDeploymentId(id);
		return "success";
	}
	
	
	/**
	 * 查看流程图
	 * @throws IOException 
	 */
	@RequestMapping("/searchProcessPic")
	public String searchProcessPic(HttpServletRequest request,HttpServletResponse response,String id,String name){
	
		//获取资源文件
		System.out.println("id:"+id);
		System.out.println("name:"+name);
		InputStream inputStream=workflowServiceI.findImageInputStream(id.trim(), name.trim());
		OutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
			for(int b=-1;(b=inputStream.read())!=-1;){
				outputStream.write(b);
			}
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
