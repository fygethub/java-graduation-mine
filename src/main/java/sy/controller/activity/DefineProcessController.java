package sy.controller.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/processController")
public class DefineProcessController {

	
	/**跳转到流程部署界面
	 * @return */
	@RequestMapping("/deploy")
	public String deploy(){
		return "/activity/uploadprocess/uploadprocess";
	}
	
	/**上传流程文件*/
	@RequestMapping("/upload")
	public void upload(HttpServletResponse response, HttpServletRequest request, HttpSession session){
		System.out.println(session.getServletContext().getRealPath("/"));
	}
}
