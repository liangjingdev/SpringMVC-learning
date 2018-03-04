package controller;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.regexp.internal.recompile;

import form.ProductForm;
import model.Product;

/**
 * function:使用Controller注释类型的优点在于：其一，一个控制器类可以包含多个请求处理方法。
 * 其二，基于注解的控制器的请求映射不需要存储在配置文件中。使用RequestMapping注释类型，可以对一个方法进行请求处理。
 * @author liangjing
 *
 */

//@Controller注释类型用于指示Spring类的实例是一个控制器。
//Spring使用扫描机制来找到应用程序中所有基于注解的控制器类。为了保证Spring能找到你的控制器，需要完成两件事情。
//首先，需要在Spring MVC的配置文件中声明spring-context。然后，需要应用<component-scan/>元素，在该元素中指定控制器类的基本包。
@Controller
public class ProductController {

	private static final Log logger = LogFactory.getLog(ProductController.class);
	
	
	//需要在控制器类的内部为每一个动作开发相应的处理方法，要让Spring知道用哪一种方法来处理它的动作，需要使用RequestMapping
	//注释类型映射的URI与方法。一个采用该注释的方法将成为一个请求处理方法，并由调度程序在接收
	//到对应的URL请求时调用。除了value属性之外，还可以由method属性等。method属性指定处理哪些HTTP方法。
	@RequestMapping(value="/input-product")
	public String inputProduct() {
		logger.info("InputProductController called");
		//return new ModelAndView("/WEB-INF/jsp/ProductForm.jsp");
		return "ProductForm";
	}
	
	//该方法的第二个参数是org.springframework.ui.Model类型，无论是否会使用，Spring MVC都会在每一个
	//请求处理方法被调用时创建一个Model实例，用于增加需要显示在视图中的属性。
	//每个请求处理方法可以有多个不同类型的参数，以及一个多种类型的返回结果。Spring会将对象正确的传递给方法。
	@RequestMapping(value="/save-product")
	public String saveProduct(ProductForm productForm,Model model) {
        logger.info("SaveProductController called");
		
        // create model
        Product product = new Product();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        try {
            product.setPrice(new BigDecimal(productForm.getPrice()));
        } catch (NumberFormatException e) {
        }

        //通过调用该方法来添加Product实例，就可以像被添加到HttpServletRequest中那样访问了。
       model.addAttribute("product",product);
       return "ProductDetails";
	}
}
