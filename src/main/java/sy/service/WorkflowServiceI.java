package sy.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.pageModel.activiti.PageLeaveBill;
import sy.pageModel.activiti.ProcessDefineModel;
import sy.pageModel.activiti.ProcessDeployModel;
import sy.pageModel.activiti.WorkflowModel;

public interface WorkflowServiceI {
	boolean saveNewDeploye(File file, String filename);//部署流程定义
	List<ProcessDeployModel> findDeploymentList(int page,int rows);
	int findDeploymentListSize();
	
	List<ProcessDefineModel> findProcessDefinitionList(int page, int rows);
	int processDefineListSize();
	
	
	InputStream findImageInputStream(String deploymentId, String imageName);

	void deleteProcessDefinitionByDeploymentId(String deploymentId);

	void saveStartProcess(WorkflowModel workflowModel,HttpSession session);

	List<Task> findTaskListByName(String name);
	DataGrid findTaskListByName_listpage(String name, int page,int rows);

	String findTaskFormKeyByTaskId(String taskId);

	PageLeaveBill findLeaveBillByTaskId(String taskId);

	List<String> findOutComeListByTaskId(String taskId);

	void saveSubmitTask(WorkflowModel workflowModel, String id);

	List<Comment> findCommentByTaskId(String taskId);

	List<Comment> findCommentByLeaveBillId(String id);

	ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	Map<String, Object> findCoordingByTask(String taskId);
	DataGrid findHistoryDefineByUser(SessionInfo sessionInfo, PageHelper ph);
	
	
	
	
	
	

}
