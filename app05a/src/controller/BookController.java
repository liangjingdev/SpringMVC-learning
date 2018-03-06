package controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.recompile;

import domain.Book;
import domain.Category;
import service.BookService;

/**
 * function:允许用户创建新书目、更新书的详细信息，并在系统中列出所有书目。
 * @author liangjing
 *
 */

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	//获取日志
	private static final Log logger = LogFactory.getLog(BookController.class);
	
	@RequestMapping(value="/book_input")
	public String inputBook(Model model){
		//获取书目的所有分类
		List<Category> categories = bookService.getAllCategories();
		model.addAttribute("categories",categories);
		//利用表单标签将jsp页面上的数据绑定到该Book对象上
		model.addAttribute("book",new Book());
		return "BookAddForm";
	}
	
	
	@RequestMapping(value="/book_edit/{id}")
	public String editBook(Model model,@PathVariable long id){
		List<Category> categories = bookService.getAllCategories();
		model.addAttribute("categories",categories);
		Book book = bookService.get(id);
		model.addAttribute("book", book);
		return "BookEditForm";
	}
	
	
	//该方法参数上的Book对象就是经过BookAddForm.jsp页面后的Book对象
	@RequestMapping(value="/book_save")
	public String saveBook(@ModelAttribute Book book){
		Category category = bookService.getCategory(book.getCategory().getId());
		book.setCategory(category);
		bookService.save(book);
		return "redirect:/book_list";
	}
	
	
	@RequestMapping(value="/book_list")
	public String listBooks(Model model){
		logger.info("book_list");
		List<Book> books = bookService.getAllBooks();
		model.addAttribute("books",books);
		return "BookList";
	}
	
	 @RequestMapping(value = "/book_update")
	    public String updateBook(@ModelAttribute Book book) {
	        Category category = bookService.getCategory(book.getCategory().getId());
	        book.setCategory(category);
	        bookService.update(book);
	        return "redirect:/book_list";
	    }
}
