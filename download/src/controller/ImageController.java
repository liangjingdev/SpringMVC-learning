package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * function:有些人有可能通过交叉引用“窃取”你的网站资产。例如，将你的资料公然放在他的网站上，好像那些东西原本
 * 就属于他的一样。如果通过编程控制，使得只有当refer标题中包含你的域名时才发出资源，就可以放在那种情况发送。
 * 比如，在该类中，使得仅当refer标题不为null时，才将图片发送给浏览器。这样就可以防止仅在浏览器中输入网址就能 下载图片的情况发生。
 * 
 * @author liangjing
 *
 */
@Controller
public class ImageController {

	private Log logger = LogFactory.getLog(ResourceController.class);


	@RequestMapping(value = "/image_get/{id}", method = RequestMethod.GET)
	public void getImage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response,
			@RequestHeader String referer) {

		//referer是用于获取请求是从哪里来的。例如：如果是直接输入的地址，或者不是从本网站访问的重定向到本网站的首页
		//代码：if (referer == null || !referer.startsWith("http://localhost")) 
		if (referer != null) {
			String imageDirectory = request.getServletContext().getRealPath("/WEB-INF/image");
			File file = new File(imageDirectory, id + ".jpg");
			if (file.exists()) {
				// 设置文件类型
				response.setContentType("image/jpg");
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
		}
	}
}
