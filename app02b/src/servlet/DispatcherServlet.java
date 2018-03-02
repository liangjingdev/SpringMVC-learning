package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.InputProductController;
import controller.SaveProductController;


@WebServlet(name="DispatcherServlet",urlPatterns={
		"/input-product","/save-product"})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
			}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);
		
		String dispatchUrl = null;
		if (action.equals("input-product")) {
			InputProductController controller = new InputProductController();
			dispatchUrl = controller.handleRequest(request, response);
		} else if (action.equals("save-product")) {
			SaveProductController controller = new SaveProductController();
			dispatchUrl = controller.handleRequest(request, response);
		}
		
		if (dispatchUrl!=null) {
			RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
			rd.forward(request, response);
		}
	}
}
