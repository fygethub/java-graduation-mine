package sy.activiti.processdefine;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class ProcessDefine {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void createProcessDefine() {
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				// 创建一个部署对象
				.name("FyProcess")
				.addClasspathResource("diagrams/FyProcess.bpmn")// 从classpath的资源总加载
				.addClasspathResource("diagrams/FyProcess.png").deploy();

		System.out.println("部署" + deployment.getId());
		System.out.println("部署名称" + deployment.getName());
	}
}
