package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * function:将业务逻辑代码迁移到controller类的好处很明显：Controller Servlet变得更加专注。现在
 * 作用更像一个dispatcher，而非一个controller，因此，我们将其改名为DispatcherServlet。DispatcherServlet
 * 类检查每个URI，创建相应的controller，并调用其handleRequest。
 * @author liangjing
 *
 */
public interface Controller {
	
	String handleRequest(HttpServletRequest request,
			HttpServletResponse response);

}
