package sy.activiti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class HelloWorld {
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**部署流程定义*/
	@Test
	public void deploymentProcessDefinition(){
	
	Deployment deployment=	processEngine.getRepositoryService()
						.createDeployment()//创建一个部署对象
						.name("FyProcess")
						.addClasspathResource("diagrams/FyProcess.bpmn")//从classpath的资源总加载 
						.addClasspathResource("diagrams/FyProcess.png")
						.deploy();
		
		System.out.println("部署:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	}
	
	/**zip部署流程定义*/
	@Test
	public void deploymentProcessDefinition_zip(){
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("diagrams/FyProcess.zip");
		ZipInputStream zipInputStream=new ZipInputStream(in);
	Deployment deployment=	processEngine.getRepositoryService()
						.createDeployment()//创建一个部署对象
						.name("FyProcess")
						.addZipInputStream(zipInputStream)
						.deploy();
		
		System.out.println("部署:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	}
	
	/**启动流程实例*/	
	@Test
	public void StartProcess(){
		String processkey="helloword";
		ProcessInstance p= processEngine.getRuntimeService()//与正在秩序的流程实例相关
						.startProcessInstanceByKey(processkey);//使用流程定义的key启动流程实例，key对应的是最新的流程
	
		System.out.println("流程实例ID:"+p.getId());
		System.out.println("流程定义ID:"+p.getProcessDefinitionId());
	}
	
	/**查询当前人的个人任务*/
	@Test
	public void findMypersonalTask(){
		String assignee="李四";
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
	
	/**查询流程定义 */ 
	@Test
	public void findProcessDefine(){
	  List<ProcessDefinition> list=	processEngine.getRepositoryService()
			.createProcessDefinitionQuery()//创建一个流程定义的查询
//			.deploymentId(deploymentId)//使用部署id查询
//			.processDefinitionKey(processDefinitionKey)//使用流程定义的key
//			.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
				
			.orderByProcessDefinitionName().asc()//按照版本的升序排列
			.list();
				
	  	if (list !=null && list.size() > 0) {
	  		for (ProcessDefinition p:list) {
				System.out.println("deploymentId:"+p.getDeploymentId());
				System.out.println("key:"+p.getKey());
				System.out.println("resource name:"+p.getResourceName());
				System.out.println("FyProcess.bpmb 's Id:"+p.getKey());//对应bpmn下的id
				System.out.println("Fyprocess.png's name :"+p.getDiagramResourceName());
				System.out.println("Version:"+p.getVersion());//版本 当key相同的情况下版本增加
				System.out.println("###########################################");
	  		}
	  	}
	}

	/**完成我的任务*/
	@Test
	public void compliteMyPersonalTask(){
		String taskId ="2704";
		processEngine.getTaskService()
						.complete(taskId);
		System.out.println("Task Complete id is"+taskId);
	}
	
	/**look for progrocess file picture*/
	@Test
	public void ViewPic(){
		String deploymentId="905";
		String resourceName="";
		List<String> list=	processEngine.getRepositoryService()
						.getDeploymentResourceNames(deploymentId);
		if (list!=null && list.size()>0) {
			for(String s:list){
				if (s.indexOf(".png")>=0) {
					resourceName =s;
				}
			}
		}
		//get picture inputstream
		InputStream inputStream=processEngine.getRepositoryService()//将生成的图片放到文件夹下
			.getResourceAsStream(deploymentId, resourceName);
		//将生成的图片拷贝到D盘下
		File file=new File("D:/"+resourceName);
		//将输入流的图片写到D盘下
		try {
			FileUtils.copyInputStreamToFile(inputStream, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("查看 success");
	}
	
	
	
	
	/**删除流程定义*/
	@Test
	public void deleteProcessDefinition(){
			//使用部署Id，完成删除
		String deploymentId="1202";
		processEngine.getRepositoryService()
		//	.deleteDeployment(deploymentId);//delete deployment which don't use
			.deleteDeployment(deploymentId,true);//delete use deployment
		System.out.println("删除成功");
		
	}
	
	
	
	/**删除流程定义 与流程定义相关的都是repositoryService*/
	@Test
	public void deleteProcessDefinition_(){
		
	}
	
	
	/**查询最新版本流程定义*/
	@Test
	public void selectListVision(){
		//map 当map key相同的情况下 后一次覆盖前一次
		List<ProcessDefinition> list= processEngine.getRepositoryService()
		 				.createProcessDefinitionQuery()
		 				.processDefinitionKey("helloword")
		 				.orderByProcessDefinitionVersion().asc()
		 				.list();
		 Map<String , ProcessDefinition> map=new LinkedHashMap<String, ProcessDefinition>();
		 if (list!=null && list.size()>0) {
			 for(ProcessDefinition pd:list){
				 map.put(pd.getKey(), pd);
			 }
		}
		 List<ProcessDefinition> pList=new ArrayList<ProcessDefinition>(map.values());
		 
		 if (pList !=null && pList.size() > 0) {
	  		for (ProcessDefinition p:pList) {
				System.out.println("deploymentId:"+p.getDeploymentId());
				System.out.println("key:"+p.getKey());
				System.out.println("resource name:"+p.getResourceName());
				System.out.println("FyProcess.bpmb 's Id:"+p.getKey());//对应bpmn下的id
				System.out.println("Fyprocess.png's name :"+p.getDiagramResourceName());
				System.out.println("Version:"+p.getVersion());//版本 当key相同的情况下版本增加
				System.out.println("###########################################");
	  		}
	  	}
	}
	
	/**附加功能：删除流程定义（删除不同版本的流程定义*/
	@Test
	public void deleteProcessdefinitionByKey(){
		//key definition 
		String processDefinitionKey="helloword";
		//select all processdefinition
		List<ProcessDefinition> list=processEngine.getRepositoryService()
										.createProcessDefinitionQuery()
										.processDefinitionKey("helloword")
										.list();
		//遍历获取每个流程定义的部署ID
		if(list != null && list.size()>0){
			for(ProcessDefinition p:list){
				String deploymentId=p.getDeploymentId();
				processEngine.getRepositoryService()
								.deleteDeployment(deploymentId,true);
				System.out.println(deploymentId+"删除成功");
			}
		}
	}
	
	
	/**process is end */                
	@Test
	public void  isProcessEnd(){
		String processInstanceId="2701";
		ProcessInstance pInstance=	processEngine.getRuntimeService()
						.createProcessInstanceQuery()
						.processInstanceId(processInstanceId)
						.singleResult();
	
		if(pInstance == null){
			System.out.println("is end ");
		}else {
			System.out.println("is run");
			System.out.println(pInstance.toString());
		}
	}
}
