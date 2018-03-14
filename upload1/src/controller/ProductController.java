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
 * function:在Spring MVC中处理文件上传有两种方法：
 * 1、购买Apache Commons FileUpload元件
 * 2、利用Servlet3.0及其更高版本的内置支持。如果要将应用程序部署到支持Servlet3.0及其更高版本
 * 的容器中，只能使用这种方法。
 * 无论选择哪一种方法，都要利用相同的API来处理已经上传的文件。
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

	/**
	 * function:保存已上传文件，并显示product详情。
	 * 在Spring MVC中处理已经上传的文件十分容易。上传到Spring MVC应用程序中的文件会被包在一个MultipartFile对象
	 * 中。唯一的任务就是用类型为MultipartFile的属性编写一个domain类。
	 * 
	 * org.springframework.web.multipart.MultipartFile接口具有以下方法：
	 * 1、byte[] getBytes() 它以字节数组的形式返回文件的内容
	 * 2、String getContentType() 它返回文件的内容类型
	 * 3、InputStream getInputStream() 它返回一个InputStream，从中读取文件的内容
	 * 4、String getName() 它以多部分的形式返回参数的名称
	 * 5、String getOriginalFileName() 它返回客户端本地驱动器中的初始文件名。
	 * 6、long getSize() 它以字节为单位，返回文件的大小
	 * 7、boolean isEmpty() 它表示被上传的文件是否为空
	 * 8、void transferTo(File destination) 它将上传的文件保存到目标目录下
	 * @param servletRequest
	 * @param product 该Product对象是ProductForm页面传递过来的Product对象。
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/product_save")
	public String saveProduct(HttpServletRequest servletRequest, @ModelAttribute Product product,
			BindingResult bindingResult, Model model) {
		
		//获取已上传图片文件集合
		List<MultipartFile> files = product.getImages();
		List<String> fileNames = new ArrayList<String>();
		
		//遍历图片集合
		if (files!=null&&files.size()>0) {
			for (MultipartFile multipartFile : files) {
				//获取每个图片的文件名
				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);
				
				//首先创建File对象，该File对象保存于应用程序目录的image目录下，名称为对应的文件名
				File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"),fileName);
				
				try {
					//然后利用transferTo()方法将该上传的文件保存到目标目录下
					multipartFile.transferTo(imageFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//save product here
		model.addAttribute("product",product);
		return "ProductDetails";
	}
}
