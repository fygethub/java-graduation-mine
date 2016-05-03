package sy.controller.activity;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.model.LeaveBill;
import sy.pageModel.DataGrid;
import sy.pageModel.Json;
import sy.pageModel.PageHelper;
import sy.pageModel.SessionInfo;
import sy.pageModel.User;
import sy.pageModel.activiti.PageLeaveBill;
import sy.service.LeaveBillServiceI;
import sy.service.UserServiceI;
import sy.util.ClobUtil;
import sy.util.ConfigUtil;

@Controller
@RequestMapping("/leaveBillController")
public class LeaveBillController {
	
	
	
	@Resource
	LeaveBillServiceI leaveBillServiceI;
	
	@Resource
	UserServiceI userServiceI;
	
	
	@RequestMapping("/manager")
	public String manager(){
		return "/activity/leaveBill";
	}
	
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid manager(PageLeaveBill pLeaveBill,PageHelper ph){
		return leaveBillServiceI.dataGrid(pLeaveBill,ph);
	}
	
	@RequestMapping("/view")
	public String view(String id,HttpServletRequest request){
		PageLeaveBill pageLeaveBill=leaveBillServiceI.findpageLeaveBillById(id);
		request.setAttribute("leaveBill", pageLeaveBill);
		return "/activity/leaveBillView";
	}
	
	
	@RequestMapping("/leaveBillAddpage")
	public String leaveBillAddpage(HttpServletRequest request){
		PageLeaveBill pageLeaveBill=new PageLeaveBill();
		pageLeaveBill.setId(UUID.randomUUID().toString());
		request.setAttribute("pageLeaveBill", pageLeaveBill);
		return "/activity/leaveBillAdd";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json leaveBillAdd(HttpSession session,String name,int days,String content,String remark ,String  id){
		
		SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		User user=userServiceI.get(sessionInfo.getId());
		LeaveBill leaveBill=new LeaveBill();
		leaveBill.setContent(content);
		leaveBill.setRemark(ClobUtil.getClob(remark));
		leaveBill.setId(id);
		leaveBill.setDays(days);;
		leaveBillServiceI.add(leaveBill,user);
		Json json=new Json();
		json.setSuccess(true);
		json.setMsg("success");
		return json;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request,String id){
		
		PageLeaveBill pageLeaveBill=leaveBillServiceI.findpageLeaveBillById(id);
		request.setAttribute("leaveBill", pageLeaveBill);
		return "/activity/leaveBillEdit";
	}
	
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json eidt(HttpServletRequest request,PageLeaveBill pageLeaveBill){
		
		Json j = new Json();
		try {
			leaveBillServiceI.edit(pageLeaveBill);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request,String id){
		
		Json j = new Json();
		try {
			leaveBillServiceI.deleteLeaveBillById(id);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
}
