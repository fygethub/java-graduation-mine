package sy.controller.activity;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.pageModel.DataGrid;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.pageModel.activiti.PageLeaveBill;
import sy.service.WorkflowServiceI;
import sy.util.ConfigUtil;

@Controller
@RequestMapping("/histroyController")
public class HistroyProcessController {
	@Resource
	WorkflowServiceI workflowServiceI;
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid historyDatagrid(PageLeaveBill pLeaveBill,PageHelper ph,HttpSession session){
		SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		DataGrid dGrid=workflowServiceI.findHistoryDefineByUser(sessionInfo,ph);
		return dGrid;
	}
	
	@RequestMapping("/hisPage")
	public String hisPage(){
		return "/activity/history/history"	;
	}
}
