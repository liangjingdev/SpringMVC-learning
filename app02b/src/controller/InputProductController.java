package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * function:InputProductController类直接返回了ProductFrom.jsp的路径。
 * @author liangjing
 *
 */
public class InputProductController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		return "/WEB-INF/jsp/ProductForm.jsp";
	}

	
}
