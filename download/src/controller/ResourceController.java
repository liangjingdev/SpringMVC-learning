package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Login;

/**
 * function:ResourceController类处理用户登录，并将一个secret.pdf文件发送给浏览器。
 * secret.pdf文件放在WEB-INF/data目录下，因此不可能直接访问，只有得到授权的用户，才能看到它。
 * 如果用户没有登录，应用程序就会跳转到登录页面。
 * 该类提供了一个控制器，负责发送secret.pdf文件。只有当用户的HttpSession中包含一个loggedIn属性，
 * 表示该用户已经成功登录，这才允许该用户访问。
 * 
 * @author liangjing
 *
 */
@Controller
public class ResourceController {

	private Log logger = LogFactory.getLog(ResourceController.class);

	//login()方法将用户带到登录表单
	@RequestMapping(value = "/login")
	public String login(@ModelAttribute Login login, HttpSession session, Model model) {
		model.addAttribute("login", login);
		if ("paul".equals(login.getUserName()) && "secret".equals(login.getPassword())) {
			session.setAttribute("loggedIn", Boolean.TRUE);
			//Main.jsp页面中包含了一个链接，用户可以单击它来下载文件
			return "Main";
		} else {
			return "LoginForm";
		}
	}

	
	@RequestMapping(value = "/resource_download")
	public String downloadResource(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		if (session == null || session.getAttribute("loggedIn") == null) {
			return "LoginForm";
		}

		// 需要提供给客户端下载的文件资源的存放路径
		String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/data");
		// 创建File对象
		File file = new File(dataDirectory, "secret.pdf");
		if (file.exists()) {
			// 设置文件类型
			response.setContentType("application/pdf");
			// 添加响应头
			response.addHeader("Content-Dispostion", "attachment;filename=secret.pdf");
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (IOException e) {
				// TODO: handle exception
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e2) {
						// TODO: handle exception
					}

					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e3) {
							// TODO: handle exception
						}
					}
				}
			}
		}
		return null;
	}
}
