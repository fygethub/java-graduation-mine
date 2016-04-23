package sy.activiti.processVariables;

import java.io.InputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablesTest {
	@Resource
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine(); 
	
	@Test
	public void deploymentProcessDefinition(){
		InputStream inputStreambpmn=this.getClass().getClassLoader().getResourceAsStream("diagrams/Fyprocess.bpmn");
		InputStream inputStreampng=this.getClass().getResourceAsStream("/diagrams/Fyprocess.png");
		Deployment deployment =processEngine.getRepositoryService()
												.createDeployment()//创建部署对象
												.name("FyProcess")
												.addInputStream("helloword", inputStreambpmn)
												.addInputStream("helloword", inputStreampng)
												.deploy();
		System.out.println("部署"+deployment.getId());
		System.out.println("部署名称"+deployment.getName());
	}
	
	
	
	 /**启动流程实例 （runtimeService)*/
	@Test
	public void StartProcess(){
		String processDefinitionKey="helloword";
		ProcessInstance p=processEngine.getRuntimeService()
									.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程ID"+p.getId());
		System.out.println("流程实例id"+p.getProcessInstanceId());
	}
	
	/**设置流程变量*/
	@Test
	public void setVariables(){
		
	}
	
	/**获取流程变量*/
	@Test
	public void getVariables(){
		
	}
	
	/**模拟设置和获取流程变量的场景*/
	public void setAndGetVariables(){
		/**与流程实例，执行对象(正在执行中)*/
		RuntimeService runtimeService=processEngine.getRuntimeService();
		
		/**与任务（正在执行)*/
		TaskService taskService=processEngine.getTaskService();
		
		/**设置流程变量*/
//		runtimeService.setVariable(executionId, variableName, value);//使用执行对象的ID,和流程变量的名称，设置流程变量的值（一次设置一个值）
//		runtimeService.setVariables(executionId, variables);//使用执行对象ID,和map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是变量的值
//		runtimeService.setVariable(executionId, variableName, value);//使用执行对象的ID,和流程变量的名称，设置流程变量的值（一次设置一个值）
//		runtimeService.setVariables(executionId, variables);//使用执行对象ID,和map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是变量的值
		
	}
}
