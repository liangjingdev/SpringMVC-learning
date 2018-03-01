package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jrockit.jfr.Producer;

import action.SaveProductAction;
import form.ProductForm;
import model.Product;

@WebServlet(name="ControllerServlet",urlPatterns={
		"/input-product","/save-product"})
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ControllerServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}
	
	/**
	 * function:处理所有输入请求
	 * process方法执行以下步骤：
	 * （1）创建并根据请求参数构建一个表单对象。product_sava操作涉及3个属性：name、description和price。然后
	 *     创建一个领域对象，并通过表单对象设置相应属性。
	 * （2）执行针对领域对象的业务逻辑，包括将其持久化到数据库中。
	 * （3）转发请求到视图（JSP页面）
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void process(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
		
        //获取请求URI
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		//获取action名称
		String action = uri.substring(lastIndex + 1);
		
		String dispatchUrl = null;
		//对相对应的action进行对应的处理
		if (action.equals("input-product")) {
			dispatchUrl = "/WEB-INF/jsp/ProductForm.jsp";
		}else if (action.equals("save-product")) {
			ProductForm productForm = new ProductForm();
			productForm.setName(request.getParameter("name"));
			productForm.setDescription(request.getParameter("description"));
			productForm.setPrice(request.getParameter("price"));
			
			Product product = new Product();
			product.setName(productForm.getName());
			product.setDescription(productForm.getDescription());
			try {
				product.setPrice(new BigDecimal(productForm.getPrice()));
			} catch (NumberFormatException e) {
			}
			
			  // execute action method
            SaveProductAction saveProductAction =
            		new SaveProductAction();
            saveProductAction.save(product);

			request.setAttribute("product", product);
			
			dispatchUrl = "/WEB-INF/jsp/ProductDetails.jsp";
		}	
		
		//forward to a view
		if (dispatchUrl!=null) {
			RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
			rd.forward(request, response);
		}
	}

}
