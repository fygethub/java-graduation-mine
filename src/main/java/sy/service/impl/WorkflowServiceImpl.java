package sy.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.cmd.ActivateProcessInstanceCmd;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.LeaveBillDaoI;
import sy.dao.UserDaoI;
import sy.model.LeaveBill;
import sy.model.Trole;
import sy.model.Tuser;
import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.pageModel.activiti.HistoryInstance;
import sy.pageModel.activiti.PageLeaveBill;
import sy.pageModel.activiti.ProcessDefineModel;
import sy.pageModel.activiti.ProcessDeployModel;
import sy.pageModel.activiti.TaskPage;
import sy.pageModel.activiti.WorkflowModel;
import sy.service.WorkflowServiceI;
import sy.util.ClobUtil;
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
	
	@Resource
	UserDaoI userDaoI;
	
	/**部署流程定义*/
	@Override
	public boolean saveNewDeploye(File file, String filename) {
		// TODO Auto-generated method stub
			logger.info("部署流程定义");
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
			
				String id = workflowModel.getId();
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
			 	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
	   			(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
			 */
			String objId=key+"."+id;
			//4：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
			runtimeService.startProcessInstanceByKey(key,objId,variables);
	}

	/**使用当前用户名查询正在执行的任务表，获取当前任务的集合LIST<Task>*/
	@Override
	public List<Task> findTaskListByName(String name) {
		// TODO Auto-generated method stub
		List<Task> list=taskService.createTaskQuery()
							.taskAssignee(name)//查询个人任务办理人
							.orderByTaskCreateTime().asc()
							.list();
		if (list ==null && list.size()==0) {
			return null;
		}
		return list;
	}
	
	/**使用当前用户名查询正在执行的任务表，获取当前任务的集合LIST<Task> 封装成datagrid*/
	@Override
	public DataGrid findTaskListByName_listpage(String name, int page, int rows) {
		// TODO Auto-generated method stub
			DataGrid dataGrid=new DataGrid();
			List<Task> list=taskService.createTaskQuery()
					.taskAssignee(name)//查询个人任务办理人
					.orderByTaskCreateTime()
					.asc()
					.listPage((page-1)*rows, rows);
			if(list!=null && list.size() >0){
				Long long1=(long) findTaskListByName(name).size();
				
				dataGrid.setTotal(long1);
				
			}
			List<TaskPage> list2=new ArrayList<TaskPage>();
			for(Task task:list){
				TaskPage taskPage=new TaskPage();
				BeanUtils.copyProperties(task, taskPage);
				list2.add(taskPage);
			}
			dataGrid.setRows(list2);
			return dataGrid;
	}

	/**使用任务ID，获取当前任务节点中对应的Form key中的连接的值*/
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		// TODO Auto-generated method stub
		TaskFormData formData=formService.getTaskFormData(taskId);
		//获取form key  的值
		String url =formData.getFormKey();
		return url;
	}

	/**使用任务ID，查找请假单ID,从而获取请假信息*/
	@Override
	public PageLeaveBill findLeaveBillByTaskId(String taskId) {
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//使用流程实例ID查询
				.singleResult();
		//4：使用流程实例对象获取BUSINESS_KEY
				String buniness_key = pi.getBusinessKey();
		//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
				String id = "";
				if(StringUtils.isNotBlank(buniness_key)){
					//截取字符串，取buniness_key小数点的第2个值
					id = buniness_key.split("\\.")[1];
				}
			//查询请假单对象
			//使用hql语句：from LeaveBill o where o.id=1
			LeaveBill leaveBill = leaveBillDaoI.get(LeaveBill.class, id);
			
			PageLeaveBill pageLeaveBill=new PageLeaveBill();
			BeanUtils.copyProperties(leaveBill, pageLeaveBill,new String[]{"user","remark"});
			pageLeaveBill.setRemark(ClobUtil.getString(leaveBill.getRemark()));
			pageLeaveBill.setUser(leaveBill.getUser().getName());
			return pageLeaveBill;
				
	}

	/**已知任务ID，查询ProcessDefintionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		// TODO Auto-generated method stub
		List<String > list =new ArrayList<String>();
		
		//使用任务Id查询任务对象
		Task  task=taskService.createTaskQuery()
								.taskId(taskId)
								.singleResult();
		String processDefinitionId=task.getProcessDefinitionId();
		//流程定义对象 就是pbmn 文件
		ProcessDefinitionEntity pEntity=(ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象获取流程实例Id
		String processInstanceId=task.getProcessInstanceId();
		ProcessInstance pi=runtimeService.createProcessInstanceQuery()
											.processInstanceId(processInstanceId)
											.singleResult();
		//获取当前活动的id
		String activityId=pi.getActivityId();
		//获取当前活动
		ActivityImpl activityImpl=pEntity.findActivity(activityId);
		
		//获取当前活动之后的连线
		List<PvmTransition> pvmTransitions=activityImpl.getOutgoingTransitions();
		if (pvmTransitions !=null && pvmTransitions.size()>0) {
			for(PvmTransition pvmTransition:pvmTransitions){
				String name=(String) pvmTransition.getProperty("name");
				if (StringUtils.isNotBlank(name)) {
					list.add(name);
				}else {
					list.add("默认提交");
				}
			}
		}
		return list;
	}

	/**指定连线的名称完成任务*/
	@Override
	public void saveSubmitTask(WorkflowModel workflowModel,String id) {
		// TODO Auto-generated method stub
		String taskId=workflowModel.getTaskId();
		String outcome=workflowModel.getOutcome();
		Tuser tuser=userDaoI.get(Tuser.class, id);
		
		/**完成之前添加批注，向act_hi_commont表中添加数据
		 */
		Task task=taskService.createTaskQuery()
								.taskId(taskId)
								.singleResult();
		
		String processInstanceId =task.getProcessInstanceId();

		//		完成之前，添加一个批注信息用于记录当前申请人的一些审核信息
		/**添加批注的时候，由于activity底层代码是使用：
		 *   String userId = Authentication.getAuthenticatedUserId();
    		 CommentEntity comment = new CommentEntity();
    		comment.setUserId(userId);
    		所以需要添加user
		 */
		
		Authentication.setAuthenticatedUserId(tuser.getName());
		taskService.addComment(taskId, processInstanceId, workflowModel.getComment());
		
		Map<String, Object> variables =new HashMap<String, Object>();
		if(outcome != null && !outcome.equals("默认提交")){
			variables.put("outcome", outcome);
		}
		
		//通过当前人添加下一个办理人
		Tuser manager=tuser.getManager();
		
		if (manager != null) {
			logger.debug("上级领导存在是："+manager.getName());
			variables.put("inputUser",manager.getName());
		}else {
			logger.debug("上级领导不存在");
			variables.put("inputUser",tuser.getName());
		}
		
		taskService.complete(taskId,variables);
		
		/**5.完成之后判断流程是否结束
		 * 如果结束跟前请假表单的状态从1变成2（审核完成）
		 * */
		ProcessInstance processInstance=	runtimeService.createProcessInstanceQuery()
						.processInstanceId(processInstanceId)
						.singleResult();
		
		
		if(processInstance ==null){
			//更新状态
			LeaveBill leaveBill=leaveBillDaoI.get(LeaveBill.class, workflowModel.getId());
			leaveBill.setState(2);
		}
	}

	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/
	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		// TODO Auto-generated method stub
		List<Comment> comlist =new ArrayList<Comment>();
		//使用当前的任务id查询当前流程对应的历史任务
		Task task=taskService.createTaskQuery()
							.taskId(taskId)
							.singleResult();
		//获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例id 查询历史任务 获取历史任务对应的每个id
		List<HistoricTaskInstance> hislist=historyService.createHistoricTaskInstanceQuery()
						.processInstanceId(processInstanceId)
						.list();
		
		if(hislist != null && hislist.size()>0){
			for(HistoricTaskInstance h:hislist){
				String hisId=h.getId();
				//获取批注信息
				List<Comment> list=taskService.getTaskComments(hisId);
				comlist.addAll(list);
			}
		}
		
					
		return comlist;
	}

	/***/
	@Override
	public List<Comment> findCommentByLeaveBillId(String id) {
		// TODO Auto-generated method stub
		LeaveBill leaveBill=leaveBillDaoI.get(LeaveBill.class, id);
		//获取对象的名称
		String objectName = leaveBill.getClass().getSimpleName();
		//组织流程表中的字段中的值
		String objId = objectName+"."+id;
		/**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*/
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
						.singleResult();
		
		
		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
		/*HistoricVariableInstance hvi=historyService.createHistoricVariableInstanceQuery()
													.variableValueEquals("objId", objectName)
													.singleResult();*/
		//流程实例ID
		List<Comment> list=null;
		if(hpi != null ){
			//流程实例ID
			String processInstanceId = hpi.getId();
			//String processInstanceId=hvi.getProcessInstanceId();
			list=taskService.getProcessInstanceComments(processInstanceId);
		}
		return list;
	}

	/***/
	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		// TODO Auto-generated method stub
		Task task=taskService.createTaskQuery()
								.taskId(taskId)
								.singleResult();
		String processDefineId=task.getProcessDefinitionId();
		
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(processDefineId)
						.singleResult();
		
		
		return processDefinition;
	}

	/***/
	@Override
	public Map<String, Object> findCoordingByTask(String taskId) {
		// TODO Auto-generated method stub
		//使用任务id查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefineId = task.getProcessDefinitionId();

		
		ProcessDefinitionEntity processDefinitionEntity=	(ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefineId);
		
		//使用流程实例Id查询流程实例表 获取真正执行的执行对象，获取当前活动对于的流程实例对象
		String processInstanceId=task.getProcessInstanceId();
		ProcessInstance pInstance=runtimeService.createProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		//获取当前活动id
		String activityId=pInstance.getActivityId();
		
		//获取当前活动对象
		ActivityImpl activityImpl=processDefinitionEntity.findActivity(activityId);
		//获取坐标
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("height", activityImpl.getHeight());
		map.put("width", activityImpl.getWidth());
		return map;
	}

	/**
	 * 查询历史流程实例
	 * */
	@Override
	public DataGrid findHistoryDefineByUser(SessionInfo sessionInfo,
			PageHelper ph) {
		// TODO Auto-generated method stub
		String userid=sessionInfo.getId();
		String userName=sessionInfo.getName();
		List<HistoricActivityInstance> historicDetails=historyService.createHistoricActivityInstanceQuery()
																		.list();
		List<HistoricActivityInstance> hActivityInstances=historyService.createHistoricActivityInstanceQuery()
																		.orderByHistoricActivityInstanceStartTime()
																		.desc()
																		.listPage(ph.getRows()*(ph.getPage()-1), ph.getRows());
		Tuser tuser=userDaoI.get(Tuser.class, userid);	
		Set<Trole> troles=	tuser.getTroles();
		boolean isAdmin=false;
		for (Trole t:troles) {
			if(t.getName().equals("超管")){
				isAdmin =true;
			}
		}
		//如果是管理员则把所有的历史流程实例提交到前台，否则就提交当前人的历史流程实例
		
		//添加查询相同的流程实例信息
		List<HistoryInstance> hInstances=new ArrayList<HistoryInstance>();
		String seachString=ph.getSearch();
		if(seachString != null && !seachString.equals(""))
		{
			DataGrid dataGrid=new DataGrid();
			
			for(HistoricActivityInstance h:historicDetails){
				HistoryInstance historyInstance=new HistoryInstance();
				if (isVarible(h, seachString)) {
					BeanUtils.copyProperties(h, historyInstance);
					hInstances.add(historyInstance);
				}
			}
			dataGrid.setRows(hInstances);
			dataGrid.setTotal(Long.valueOf(hInstances.size()));
			return dataGrid;
		}
		else {
			
		
		
			for (HistoricActivityInstance h:hActivityInstances) {
				HistoryInstance historyInstance=new HistoryInstance();
				if (isAdmin) {
					BeanUtils.copyProperties(h, historyInstance);
					hInstances.add(historyInstance);
				}else if(historyInstance.getAssignee().equals(userName)) {
					BeanUtils.copyProperties(h, historyInstance);
					hInstances.add(historyInstance);
				}
			}
			DataGrid dataGrid=new DataGrid();
			dataGrid.setRows(hInstances);
			dataGrid.setTotal(Long.valueOf(historicDetails.size()));
			return dataGrid;
		}
	}

	

	public boolean isVarible(HistoricActivityInstance historyInstance,String varible){
		
		if (historyInstance.getActivityId() != null  && historyInstance.getActivityId().contains(varible)) {
			return true;
		}
		if (historyInstance.getActivityName() != null  &&  historyInstance.getActivityName().contains(varible)) {
			return true;
		}
		if (historyInstance.getActivityType() != null  && historyInstance.getActivityType().contains(varible)) {
			return true;
		}
		if (historyInstance.getAssignee() != null  && historyInstance.getAssignee().contains(varible)) {
			return true;
		}
		if (historyInstance.getTaskId() != null  && historyInstance.getTaskId().contains(varible)) {
			return true;
		}
		return false;
	}
		
	
	
	
}
