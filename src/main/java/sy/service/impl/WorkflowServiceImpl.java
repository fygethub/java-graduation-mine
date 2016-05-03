package sy.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.LeaveBillDaoI;
import sy.model.LeaveBill;
import sy.pageModel.SessionInfo;
import sy.pageModel.activiti.ProcessDefineModel;
import sy.pageModel.activiti.ProcessDeployModel;
import sy.pageModel.activiti.WorkflowModel;
import sy.service.WorkflowServiceI;
import sy.util.ConfigUtil;

@Service
public class WorkflowServiceImpl implements WorkflowServiceI{
	private static final Logger logger=Logger.getLogger(WorkflowServiceImpl.class);
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private FormService formService;
	
	@Resource
	private HistoryService historyService;

	@Resource
	LeaveBillDaoI leaveBillDaoI;
	
	/**部署流程定义*/
	@Override
	public boolean saveNewDeploye(File file, String filename) {
		// TODO Auto-generated method stub
		logger.info("部署流程定义");
		String fileTypeName=file.getName().substring(file.getName().lastIndexOf(".")+1);
		if (fileTypeName.equals("zip")) {
			return false;
		}else {
			ZipInputStream zipInputStream;
			try {
			zipInputStream = new ZipInputStream(new FileInputStream(file));
			Deployment deployment=	repositoryService.createDeployment()
				.name(filename)
				.addZipInputStream(zipInputStream)
				.deploy();//完成部署
				
			System.out.println("完成部署");	
			System.out.println("ID:"+deployment.getId());
			System.out.println("name:"+deployment.getName());
				return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		
	}

	/**查询部署对象信息 act_re_deployment*/
	@Override
	public List<ProcessDeployModel> findDeploymentList(int page,int rows) {
		// TODO Auto-generated method stub
		logger.info("查询部署对象信息");
		List<Deployment> list=repositoryService.createDeploymentQuery()
										.orderByDeploymenTime()
										.asc()
										.listPage((page-1)*rows,rows);	
		List<ProcessDeployModel> list2=new ArrayList<ProcessDeployModel>();
		for(Deployment d:list){
			ProcessDeployModel processDeployModel=new ProcessDeployModel();
			/*processDeployModel.setId(d.getId());
			processDeployModel.setDeploymentTime(d.getDeploymentTime());
			processDeployModel.setName(d.getName());*/
			BeanUtils.copyProperties(d, processDeployModel);
			
			list2.add(processDeployModel);
		}
		return list2;
	}

	/**查询部署对象信息 act_re_deployment 大小*/
	@Override
	public int findDeploymentListSize() {
		// TODO Auto-generated method stub
		int size=repositoryService.createDeploymentQuery()
										.list()
										.size();	
		System.out.println("size:"+size);
		return size;
	}
	
	
	/**查询流程定义信息 act_re_procdef*/
	@Override
	public List<ProcessDefineModel> findProcessDefinitionList(int page,int rows) {
		// TODO Auto-generated method stub
		logger.info("");
		List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery()
														.orderByProcessDefinitionVersion()
														.asc()
														.listPage((page-1)*rows, rows);
		List<ProcessDefineModel> list2=new ArrayList<ProcessDefineModel>();
		for(ProcessDefinition p:list){
			ProcessDefineModel pModel=new ProcessDefineModel();
			/*pModel.setName(p.getName());
			pModel.setId(p.getId());
			pModel.setVersion(p.getVersion());
			pModel.setKey(p.getKey());
			pModel.setDeploymentId(p.getDeploymentId());
			pModel.setResourceName(p.getResourceName());
			pModel.setDiagramResourceName(p.getDiagramResourceName());
			pModel.setDescription(p.getDescription());*/
			BeanUtils.copyProperties(p, pModel);
			list2.add(pModel);
		}
		
		return list2;
	}
	
	@Override
	public int processDefineListSize() {
		// TODO Auto-generated method stub
		int size=repositoryService.createProcessDefinitionQuery()
														.list().size();
		System.out.println("size:"+size);
		return size;
	}
	

	/**使用部署对象ID和资源图片名称，获取图片的输入流*/
	@Override
	public InputStream findImageInputStream(String deploymentId,
			String imageName) {
		// TODO Auto-generated method stub
		
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	/**使用部署对象ID，删除流程定义*/
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		// TODO Auto-generated method stub
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**更新请假状态，启动流程实例，让启动的流程实例关联业务*/
	@Override
	public void saveStartProcess(WorkflowModel workflowModel,HttpSession session) {
		// TODO Auto-generated method stub
		//1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
		//更新请假单的请假状态从0变成1（初始录入-->审核中）
			
				Long id = workflowModel.getId();
				LeaveBill leaveBill = leaveBillDaoI.get(LeaveBill.class, id);
				leaveBill.setState(1);
				
			String key=	leaveBill.getClass().getSimpleName();
			/**
			 * 2：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
				    * inputUser是流程变量的名称，
				    * 获取的办理人是流程变量的值
			 */
			SessionInfo sessionInfo= (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			logger.info("更新请假状态，启动流程实例"+sessionInfo.getName());
			Map<String, Object> variables = new HashMap<String,Object>();
			variables.put("inputUser",sessionInfo.getName());//表示惟一用户
			/**
			 * 3：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
	   				(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
			 */
			String objId=key+"."+id;
			variables.put("objId", objId);
			//4：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
			runtimeService.startProcessInstanceByKey(key,objId,variables);
	}

	/**使用当前用户名查询正在执行的任务表，获取当前任务的集合LIST<Task>*/
	@Override
	public List<Task> findTaskListByName(String name) {
		// TODO Auto-generated method stub
		List<Task> list=taskService.createTaskQuery()
							.taskAssignee(name)//指定个人任务办理人
							.orderByTaskCreateTime().asc()
							.list();
		if (list ==null && list.size()==0) {
			return null;
		}
		return list;
	}

	/***/
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**使用任务ID，查找请假单ID,从而获取请假信息*/
	@Override
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**已知任务ID，查询ProcessDefintionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		// TODO Auto-generated method stub
		
		//
		return null;
	}

	/**指定连线的名称完成任务*/
	@Override
	public void saveSubmitTask(WorkflowModel workflowModel) {
		// TODO Auto-generated method stub
		
	}

	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/
	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	/***/
	@Override
	public List<Comment> findCommentByLeaveBillId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/***/
	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	/***/
	@Override
	public Map<String, Object> findCoordingByTask(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
}
