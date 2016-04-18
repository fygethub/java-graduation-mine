package sy.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.pageModel.DataGrid;
import sy.pageModel.Online;
import sy.service.OnlineServiceI;

@RequestMapping("/onlineController")
@Controller
public class OnlineController {

	@Resource
	private OnlineServiceI onlineService;

	
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(Online online) {
		return onlineService.datagrid(online);
	}

}
