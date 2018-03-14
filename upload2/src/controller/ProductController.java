package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import domain.Product;

/**
 * function:有了Servlet3，就不需要Commons FileUpload和Commons IO元件了。在Servlet3及其以上版本的容器中
 * 进行服务器端文件上传的编程，是围绕着标注类型MultipartConfig和javax.servlet.http.Part接口进行的。处理已上传
 * 文件的Servlets必须以@MultipartConfig进行标注。 下列是可能在MultipartConfig标注类型中出现的属性，它们都是可选的：
 * 1、maxFileSize：上传文件的最大容量，默认值为-1，表示没有限制。大于指定值的文件将会遭到拒绝。
 * 2、maxRequestSize：表示多部分HTTP请求允许的最大容量，默认值为-1，表示没有限制。
 * 3、location：表示在Part调用write方法时，要将已上传的文件保存到磁盘中的位置。
 * 4、fileSizeThreshold：上传文件超出这个容量界限时，会被写入磁盘。 Spring
 * MVC的DispatcherServlet处理大部分或者所有请求。令人遗憾的是，如果不修改源代码，将无法对Servlet进行
 * 标注。但是，Servlet3中有一种比较容易的方法，能使一个Servlet变成一个MultipartConfig
 * Servlet，即给部署描述符(web.xml) 中的Servlet声明赋值。
 * 
 * @author liangjing
 *
 */
@Controller
public class ProductController {
	
	private static final Log logger = LogFactory.getLog(ProductController.class);

	@RequestMapping(value = "/product_input")
	public String inputProduct(Model model) {
		model.addAttribute("product", new Product());
		return "ProductForm";
	}

	@RequestMapping(value = "/product_save")
	public String saveProduct(HttpServletRequest servletRequest, @ModelAttribute Product product,
			BindingResult bindingResult, Model model) {

		List<MultipartFile> files = product.getImages();

		List<String> fileNames = new ArrayList<String>();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);

				File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
				try {
					multipartFile.transferTo(imageFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// save product here
		model.addAttribute("product", product);
		return "ProductDetails";
	}
}
