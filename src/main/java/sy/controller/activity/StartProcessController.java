package sy.controller.activity;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.pageModel.Json;
import sy.pageModel.activiti.WorkflowModel;
import sy.service.WorkflowServiceI;

@Controller
@RequestMapping("/startprocessController")
public class StartProcessController {
	
	
	@Resource
	WorkflowServiceI workflowServiceI;
	
	/**个人流程start
	 * @return */
	@RequestMapping("/startprocess")
	@ResponseBody
	public Json startprocess(HttpServletRequest request,HttpSession session,WorkflowModel workflowModel){
		
		Json j = new Json();
		try {
			workflowServiceI.saveStartProcess(workflowModel,session);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
}
