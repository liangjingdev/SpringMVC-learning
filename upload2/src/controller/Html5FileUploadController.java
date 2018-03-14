package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import domain.UploadedFile;

/**
 * function:HTML5在其DOM中添加了一个File API，它允许访问本地文件。html5.jsp页面采用了JavaScript和HTML5
 * File API来提供报告上传进度的进度条。简言之，我们关注的是HTML5 input元素的change事件，当input元素的值发生
 * 改变时，它就会被触发。还关注HTML5在XMLHttpRequest对象中添加的progress事件。XMLHttpRequest自然是
 * AJAX的骨架，当异步使用XMLHttpRequest对象上传文件时，就会持续的触发progress事件，直到上传进度完成或取消，
 * 或者直到上传进度因为出错而中断。通过监听progress事件，可以轻松地监测文件上传操作的进度。
 * Html5FileUploadController类能够将已经上传的文件保存到应用程序目录的file目录下。
 * 
 * @author liangjing
 *
 */
@Controller
public class Html5FileUploadController {

	private Log logger = LogFactory.getLog(Html5FileUploadController.class);

	@RequestMapping(value = "/html5")
	public String inputProduct() {
		return "Html5";
	}

	/**
	 * function:saveFile()方法将已经上传的文件保存到应用程序目录中的file目录下。
	 * @param servletRequest
	 * @param uploadedFile
	 * @param bindingResult
	 * @param model
	 */
	@RequestMapping(value = "/file_upload")
	public void saveFile(HttpServletRequest servletRequest, @ModelAttribute UploadedFile uploadedFile,
			BindingResult bindingResult, Model model) {
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		String fileName = multipartFile.getOriginalFilename();
		try {
			File file = new File(servletRequest.getServletContext().getRealPath("/file"), fileName);
			multipartFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
