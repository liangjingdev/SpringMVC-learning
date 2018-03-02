package controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import form.ProductForm;
import model.Product;

/**
 * function:SaveProductController类则会读取请求参数来构造一个ProductForm对象，
 * 之后用ProductForm对象来构造一个Product对象，并返回ProductDetail.jsp路径。
 * @author liangjing
 *
 */
public class SaveProductController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		ProductForm productForm = new ProductForm();
		productForm.setName(request.getParameter("name"));
		productForm.setDescription(request.getParameter("description"));
		productForm.setPrice(request.getParameter("price"));
		
		//create model
		Product product = new Product();
		product.setName(productForm.getName());
		product.setDescription(productForm.getDescription());
		try {
			product.setPrice(new BigDecimal(productForm.getPrice()));
		} catch (NumberFormatException e) {
		}
		
		request.setAttribute("product", product);
		return "/WEB-INF/jsp/ProductDetails.jsp";
	}
	
	
}
