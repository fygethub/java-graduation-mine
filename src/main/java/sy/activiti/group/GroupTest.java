package sy.activiti.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class GroupTest {
	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void createProcessDefine() {
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				// 创建一个部署对象
				.name("任务")
				.addClasspathResource("diagrams/group.bpmn")// 从classpath的资源总加载
				.addClasspathResource("diagrams/group.png").deploy();

		System.out.println("部署" + deployment.getId());
		System.out.println("部署名称" + deployment.getName());
	}
	
	/**启动流程实例*/	
	@Test
	public void StartProcess(){
		String processkey="task";
		ProcessInstance p= processEngine.getRuntimeService()//与正在秩序的流程实例相关
						.startProcessInstanceByKey(processkey);//使用流程定义的key启动流程实例，key对应的是最新的流程
	
		System.out.println("流程实例ID:"+p.getId());
		System.out.println("流程定义ID:"+p.getProcessDefinitionId());
	}
	
	
	/**启动流程实例*/	
	@Test
	public void StartProcess_(){
		String processkey="task";
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("groups", "dd,zz,xx,cc");
		ProcessInstance p= processEngine.getRuntimeService()//与正在秩序的流程实例相关
						.startProcessInstanceByKey(processkey, variables);//使用流程定义的key启动流程实例，key对应的是最新的流程
	
		System.out.println("流程实例ID:"+p.getId());
		System.out.println("流程定义ID:"+p.getProcessDefinitionId());
	}
	
	/**查询当前人的个人任务*/
	@Test
	public void findMypersonalTask(){
		String assignee="张三";
		List<Task> list=processEngine.getTaskService()//与正在执行的任务管理的service
						.createTaskQuery()
						.taskAssignee(assignee)//指定查询人
						.list();
		if (list!=null && list.size()>0) {
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("task Name:"+task.getName());
				System.out.println("create Time:"+task.getCreateTime());
				System.out.println("Assignee:"+task.getAssignee());
				System.out.println("processinstanceId:"+task.getProcessInstanceId());
				System.out.println("excutionId:"+task.getExecutionId());
				System.out.println("processDefinID:"+task.getProcessDefinitionId());
				System.out.println("******************************************");
			}
		}
	}
	
	
	/**查询当前人的zu任务*/
	@Test
	public void findMypersonalTasks(){
		String candidateUser="张三";
		List<Task> list=processEngine.getTaskService()//与正在执行的任务管理的service
						.createTaskQuery()
//						.taskAssignee(assignee)//指定查询人
						.taskCandidateUser(candidateUser)
						.list();
		if (list!=null && list.size()>0) {
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("task Name:"+task.getName());
				System.out.println("create Time:"+task.getCreateTime());
				System.out.println("Assignee:"+task.getAssignee());
				System.out.println("processinstanceId:"+task.getProcessInstanceId());
				System.out.println("excutionId:"+task.getExecutionId());
				System.out.println("processDefinID:"+task.getProcessDefinitionId());
				System.out.println("******************************************");
			}
		}
	}
	
	
	/**查询正在执行的ban li人表*/
	@Test
	public void findRunPersonTask(){
		//id
		String taskId="5201";
		List<IdentityLink> 	list=processEngine.getTaskService()
									.getIdentityLinksForTask(taskId)
									;
		if (list!=null && list.size()>0) {
			
			for(IdentityLink identityLink:list){
				System.out.println(identityLink.getTaskId()+" "+identityLink.getProcessDefinitionId()+" "+identityLink.getUserId());
			}
		}
	}
	
	
	/**将组任务分配给个人任务*/
	@Test
	public void claim(){
		String taskId ="3504";
		
		//可以是组任务的成员，也可以不是组任务中的成员
		String userId="小F";
		processEngine.getTaskService()
			.claim(taskId, userId);
		
	}
	
	/**将个人任务回退到组任务，之前一定是一个组*/
	@Test
	public void setAssigee(){
		String taskId ="3504";
		processEngine.getTaskService()
						.setAssignee(taskId, null);
	}
	
	/**向组任务中添加成员*/
	@Test
	public void addGroupUser(){
		String taskId ="3504";
		String userId="dF";
		processEngine.getTaskService()
						.addCandidateUser(taskId, userId);
	}
	
	
	/**向组任务中删除成员*/
	@Test
	public void deletGroupUser(){
		String taskId ="3504";
		String userId="dF";
		processEngine.getTaskService()
						.deleteCandidateUser(taskId, userId);
	}
	
	
	/**完成我的任务*/
	@Test
	public void compliteMyPersonalTask(){
		String taskId ="3604";
		processEngine.getTaskService()
						.complete(taskId);
		System.out.println("Task Complete id is"+taskId);
	}
}
