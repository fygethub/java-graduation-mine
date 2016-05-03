package sy.controller.activity;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.pageModel.activiti.WorkflowModel;
import sy.service.WorkflowServiceI;

@Controller
@RequestMapping("/startprocessController")
public class StartProcessController {
	
	
	@Resource
	WorkflowServiceI workflowServiceI;
	
	/**流程start
	 * @return */
	@RequestMapping("/startprocess")
	public String startprocess(HttpServletRequest request,HttpSession session,WorkflowModel workflowModel){
		workflowServiceI.saveStartProcess(workflowModel,session);
		
		return "/activity/apdprocess/startprocess";
	}
}
