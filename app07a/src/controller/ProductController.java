package controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Product;
import validator.ProductValidator;

@Controller
public class ProductController {

	   private static final Log logger = LogFactory
	            .getLog(ProductController.class);

	    @RequestMapping(value = "/product_input")
	    public String inputProduct(Model model) {
	        model.addAttribute("product", new Product());
	        return "ProductForm";
	    }

	    @RequestMapping(value = "/product_save")
	    public String saveProduct(@ModelAttribute Product product,
	            BindingResult bindingResult, Model model) {
	    	
	    	//使用Spring验证器的另一种方法是：在Controller中编写initBinder方法，并将验证器传到
	    	//WebDataBinder，并调用其validate方法。将验证器传到WebDataBinder会使该验证器应用于
	    	//Controller类中所有处理请求的方法，或者利用@javax.validation.Vaild对要验证的对象参数进行标注。
	        ProductValidator productValidator = new ProductValidator();
	        productValidator.validate(product, bindingResult);

	        if (bindingResult.hasErrors()) {
	            FieldError fieldError = bindingResult.getFieldError();
	            logger.debug("Code:" + fieldError.getCode() + ", field:"
	                    + fieldError.getField());
	            return "ProductForm";
	        }

	        // save product here
	        
	        model.addAttribute("product", product);
	        return "ProductDetails";
	    }
}
