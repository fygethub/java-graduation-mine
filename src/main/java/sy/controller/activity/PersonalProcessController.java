package sy.controller.activity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.task.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.pageModel.DataGrid;
import sy.pageModel.Json;
import sy.pageModel.SessionInfo;
import sy.pageModel.activiti.PageComment;
import sy.pageModel.activiti.PageLeaveBill;
import sy.pageModel.activiti.WorkflowModel;
import sy.service.LeaveBillServiceI;
import sy.service.WorkflowServiceI;
import sy.util.ConfigUtil;
import sy.util.DateUtil;

@Controller
@RequestMapping("/personalProcessController")
public class PersonalProcessController {
	
	@Resource
	WorkflowServiceI workflowServiceI;
	
	@Resource
	LeaveBillServiceI leaveBillServiceI;
	
	@RequestMapping("/task")
	@ResponseBody
	public DataGrid listTask(HttpSession session,int page,int rows){
		//1：从Session中获取当前用户名
		SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		
		//2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
		DataGrid dataGrid=workflowServiceI.findTaskListByName_listpage(sessionInfo.getName(),page,rows);
		return dataGrid;
		
	}
	
	@RequestMapping("/taskPage")
	public String taskPage(){
		return "/activity/personal/selectPersonalProcess";
	}

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
	
	/**动态从流程中读取URL路径*/
	@RequestMapping("/taskEditPage")
	public String taskEditPage(HttpServletRequest request,String id){
			String url=workflowServiceI.findTaskFormKeyByTaskId(id);
			url += "?taskId="+id;
			System.out.println(url);
		
			return "/"+url;
	}
	
	/**准备表单数据,返回批注页面*/
	@RequestMapping("/taskHandleReady")
	public String audit(HttpServletRequest request,HttpSession session,String taskId){
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		System.out.println(taskId.split("\\.")[0]);
		taskId=taskId.split("\\.")[0];
		PageLeaveBill pageLeaveBill=workflowServiceI.findLeaveBillByTaskId(taskId);
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		List<String > outStrings=workflowServiceI.findOutComeListByTaskId(taskId);
		outStrings.add("测试一下");
		session.setAttribute("outStrings", outStrings);
		session.setAttribute("pageLeaveBill", pageLeaveBill);
		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
		session.setAttribute("taskid", taskId);
		List<Comment> commentList = workflowServiceI.findCommentByTaskId(taskId);
		List<PageComment> pList=new ArrayList<PageComment>();
		for(Comment c:commentList){
			PageComment pcomment=new PageComment();
			BeanUtils.copyProperties(c, pcomment,new String[]{"time"});
			pcomment.setTime(DateUtil.format(c.getTime()));
			pList.add(pcomment);
		}
		
		session.setAttribute("commentList", pList);
		return "/activity/personal/handleTaskPage";
		
	}
	
	
	/**提交任务*/
	@RequestMapping(value="/submitTtask",produces="application/json;charset=utf-8")
	@ResponseBody
	public Json submitTask(WorkflowModel workflowModel,HttpSession session){
		SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		
		Json json=new Json();
		try {
			workflowServiceI.saveSubmitTask(workflowModel,sessionInfo.getId());
			json.setSuccess(true);
			json.setMsg("批注成功");
		} catch (Exception e) {
			// TODO: handle exception
			json.setMsg("批注失败");
		}
		return json;
	}
	
	@RequestMapping("/handleViewPage")
	public String  handleViewPage(String id,HttpSession session){
		 PageLeaveBill pageLeaveBill=leaveBillServiceI.findpageLeaveBillById(id);
		 session.setAttribute("pageLeaveBill", pageLeaveBill);
		 //使用请假单ID查询历史批注信息
		 List<Comment> commentList	= workflowServiceI.findCommentByLeaveBillId(id);
		 session.setAttribute("commentList", commentList);
		 return "/activity/LeaveBillHandleView";
	}
	
}
