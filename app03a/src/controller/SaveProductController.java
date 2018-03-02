package controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import form.ProductForm;
import model.Product;

public class SaveProductController implements Controller{
	
    private static final Log logger = LogFactory.getLog("SaveProductController.class");
    
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("SaveProductController called");
		
		ProductForm productForm = new ProductForm();
        // populate action properties
        productForm.setName(request.getParameter("name"));
        productForm.setDescription(request.getParameter("description"));
        productForm.setPrice(request.getParameter("price"));

        // create model
        Product product = new Product();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        try {
            product.setPrice(new BigDecimal(productForm.getPrice()));
        } catch (NumberFormatException e) {
        }

        // insert code to save Product
        //返回的ModelAndView模型包括了视图的路径、模型名称以及模型（product对象）。该模型将提供给目标视图，用于界面显示
        //return new ModelAndView("/WEB-INF/jsp/ProductDetails.jsp", "product",
        //        product);
        
        return new ModelAndView("ProductDetails", "product",
                product);
    }

}
