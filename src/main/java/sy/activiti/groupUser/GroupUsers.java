package sy.activiti.groupUser;

import java.io.InputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;


public class GroupUsers {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deploymentProcessDefinition(){
		InputStream inputStreambpmn=this.getClass().getResourceAsStream("group.bpmn");
		InputStream inputStreampng=this.getClass().getResourceAsStream("group.png");
		System.out.println(processEngine);
		Deployment deployment =processEngine.getRepositoryService()
												.createDeployment()//创建部署对象
												.name("任务")
												.addInputStream("task", inputStreambpmn)
												.addInputStream("task", inputStreampng)
												.deploy();
		System.out.println("部署:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
		
		/**添加角色组*/
		IdentityService identityService=processEngine.getIdentityService();
		//创建角色
		identityService.saveGroup(new GroupEntity("总经理"));
		identityService.saveGroup(new GroupEntity("部门经理"));
		//创建用户
		identityService.saveUser(new UserEntity("ffy"));
		identityService.saveUser(new UserEntity("李四"));
		identityService.saveUser(new UserEntity("王五"));
		 //建立角色和用户关系
		identityService.createMembership("", "部门经理");
		identityService.createMembership("李四", "部门经理");
		identityService.createMembership("王五", "总经理");
		System.out.println("添加组织机构成功");
	}
	

	/**启动流程实例*/	
	@Test
	public void StartProcess_(){
		String processkey="task";
		ProcessInstance p= processEngine.getRuntimeService()//与正在秩序的流程实例相关
						.startProcessInstanceByKey(processkey);//使用流程定义的key启动流程实例，key对应的是最新的流程
	
		System.out.println("流程实例ID:"+p.getId());
		System.out.println("流程定义ID:"+p.getProcessDefinitionId());
	}
	
	
}
