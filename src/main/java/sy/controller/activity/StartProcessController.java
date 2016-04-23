package sy.controller.activity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/processController")
public class StartProcessController {
	
	/**跳转到流程start界面
	 * @return */
	@RequestMapping("/start")
	public String start(){
		return "/activity/apdprocess/startprocess";
	}
}
