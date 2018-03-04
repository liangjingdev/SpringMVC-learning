package controller;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.org.apache.regexp.internal.recompile;

import form.ProductForm;
import model.Product;
import service.ProductService;

/**
 * function:使用Controller注释类型的优点在于：其一，一个控制器类可以包含多个请求处理方法。
 * 其二，基于注解的控制器的请求映射不需要存储在配置文件中。使用RequestMapping注释类型，可以对一个方法进行请求处理。
 * 注意：使用Spring框架的一个好处是容易进行依赖注入，将依赖注入到Spring MVC控制器的最简单方法是通过注解@Autowired
 * 到字段或方法。此外，为了能被作为依赖注入，类必须要注明为@Service。Service注释类型指示类是一个服务。此外，在配置文件中，
 * 还需要添加一个<component-scan/>元素来扫描依赖基本包。-><context:component-scan base-package="dependencyPackage"/>
 * 在app04a应用程序中，该类已经不同于app04a。
 * @author liangjing
 *
 */

//@Controller注释类型用于指示Spring类的实例是一个控制器。
//Spring使用扫描机制来找到应用程序中所有基于注解的控制器类。为了保证Spring能找到你的控制器，需要完成两件事情。
//首先，需要在Spring MVC的配置文件中声明spring-context。然后，需要应用<component-scan/>元素，在该元素中指定控制器类的基本包。
@Controller
public class ProductController {

	private static final Log logger = LogFactory.getLog(ProductController.class);
	
	//ProductService是一个提供各种处理产品方法的接口。为productService字段添加@Autowired注解会使ProductService
	//的一个实例被注入到ProductService。
	@Autowired
	private ProductService productService;
	
	
	//需要在控制器类的内部为每一个动作开发相应的处理方法，要让Spring知道用哪一种方法来处理它的动作，需要使用RequestMapping
	//注释类型映射的URI与方法。一个采用该注释的方法将成为一个请求处理方法，并由调度程序在接收
	//到对应的URL请求时调用。除了value属性之外，还可以由method属性等。method属性指定处理哪些HTTP方法。
	@RequestMapping(value="/input-product")
	public String inputProduct() {
		logger.info("InputProductController called");
		//return new ModelAndView("/WEB-INF/jsp/ProductForm.jsp");
		return "ProductForm";
	}
	
	
	//每个请求处理方法可以有多个不同类型的参数，以及一个多种类型的返回结果。Spring会将对象正确的传递给方法。
	/**
	 * 转发与重定向的区别：
	 * 1、转发比重定向快，因为重定向经过客户端，而转发没有。
	 * 2、使用重定向的场景是避免在用户重新加载页面时再次调用同样的动作。
	 * 3、使用重定向的一个不便的地方是：无法轻松的传值给目标页面。而采用转发，则可以简单地将属性添加到Model，使得目标视图
	 * 可以轻松访问。由于重定向经过客户端，所以所以Model中的一切都在重定向时丢失。幸运的是，Spring3.1版本以及更高版本通过
	 * Flash属性提供了一种供重定向传值的方法。（要使用Flash属性，必须在Spring MVC配置文件中有一个<annotation-dirven/>
	 * 元素，然后，还必须在方法上添加一个新的参数类型-RedirectAttributes）
	 * @param productForm
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/save-product")
	public String saveProduct(ProductForm productForm,RedirectAttributes redirectAttributes) {
        logger.info("SaveProductController called");
		
        // create model
        Product product = new Product();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        try {
            product.setPrice(new BigDecimal(productForm.getPrice()));
        } catch (NumberFormatException e) {
        }

        //add product
        Product savedProduct = productService.add(product);
        
        //重定向传值
        redirectAttributes.addFlashAttribute("message","The product was successfully added.");
        //此处利用了重定向而不是转发来防止当用户重新加载页面时，saveProduct被二次调用。
        //productId被称作路径变量，用来发送一个值到服务器。
        return "redirect:/product_view/" + savedProduct.getId();
	}
	
	/**
	 * function:Spring MVC提供了一个更简单的方法来获取请求参数值：通过使用RequestParam注释类型来注释方法参数。
	 * @RequestParam注解的参数类型不一定是字符串。路径变量类似请求参数，但没有key部分，只是一个值。
	 * 为了使用路径变量，首先需要在RequestMapping注解的值属性中添加一个变量，该变量必须放在花括号之间。然后，
	 * 在方法签名中添加一个同名变量，并加上@PathVariable注解。
	 * @author liangjing
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/product_view/{id}")
	public String viewProduct(@PathVariable Long id,Model model){
		Product product = productService.get(id);
		//通过调用该方法来添加Product实例，就可以像被添加到HttpServletRequest中那样访问了。
	    model.addAttribute("product",product);
	    return "ProductView";
	}
}


/**
 * 前面谈到Spring MVC在每次调用请求处理方法时，都会创建Model类型的一个实例。若打算使用该实例，则可以在方法中添加一个Model
 * 类型的参数。事实上，还可以使用在方法中添加ModelAttribute注释类型来访问Model实例。可以用@ModelAttribute来注释方法参数或方法。
 * 带@ModelAttribute注解的方法会将其输入的或创建的参数对象添加到Model对象中（若方法中没有显式添加）。
 * @ModelAttribute的第二个用途是标注一个非请求的处理方法。被@ModelAttribute注释的方法会在每次调用该控制器类的请求处理方法
 * 时被调用。Spring MVC会在调用请求处理方法之前调用带@modelAttribute注解的方法，带该注解的方法可以返回一个对象或一个void类型。如果
 * 返回一个对象，则该对象会自动添加到Model中。
 */
