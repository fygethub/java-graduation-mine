package sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.UserServiceI;

import com.alibaba.fastjson.JSON;

/**
 * 图表控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/chartController")
public class ChartController extends BaseController {

	@Autowired
	private UserServiceI userService;

	/**
	 * 用户创建时间图表
	 * 
	 * @return
	 */
	@RequestMapping("/userCreateDatetimeChart")
	public String userCreateDatetimeChart(HttpServletRequest request) {
		request.setAttribute("userCreateDatetimeChart", JSON.toJSONString(userService.userCreateDatetimeChart()));
		return "/charts/userCreateDatetimeChart";
	}

}
