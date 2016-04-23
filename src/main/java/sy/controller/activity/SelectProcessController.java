package sy.controller.activity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/processController")
public class SelectProcessController {
	
	
	
	/**跳转到流程start界面
	 * @return */
	@RequestMapping("/select")
	public String Sselect(){
		return "/activity/selectprocess/selectprocess";
	}
}
