package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * function:在Spring2.5版本前，开发一个控制器的唯一方法是实现org.springframework.web.servlet.mvc.Controller
 * 接口。这个接口公开了一个handleRequest方法。其实现类可以访问对应请求的HttpServletRequest和HttpServletResponse，
 * 还必须返回一个包含视图路径或视图路径和模的ModelAndView对象。
 * Controller接口的实现类只能处理一个单一动作，而一个基于注解的控制器可以同时支持多个请求处理动作，并且无需实现任何接口。
 * @author liangjing
 *
 */
public class InputProductController implements Controller{

	private static final Log logger = LogFactory.getLog(InputProductController.class);
	
	
	/**
	 * function:该方法只是返回一个ModelAndView，包含一个视图，且没有模型。因此，该请求将被
	 * 转发到/WEB-INF/jsp/ProductForm.jsp页面。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("InputProductController called");
		//return new ModelAndView("/WEB-INF/jsp/ProductForm.jsp");
		return new ModelAndView("ProductForm");
	}

}
