package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sun.org.apache.regexp.internal.recompile;

import domain.Book;
import domain.Category;

@Service
public class BookServiceImpl implements BookService{
	
	private List<Category> categories;
	private List<Book> books;

	public BookServiceImpl() {
		categories = new ArrayList<Category>();
		Category category1 = new Category(1,"Computing");
		Category category2 = new Category(2,"Travel");
		Category category3 = new Category(3,"Health");
		categories.add(category1);
		categories.add(category2);
		categories.add(category3);
		
		books = new ArrayList<Book>();
		 books.add(new Book(1L, "9781771970273",
	                "Servlet & JSP: A Tutorial (2nd Edition)", 
	                category1, "Budi Kurniawan"));
	        books.add(new Book(2L, "9781771970297",
	                "C#: A Beginner's Tutorial (2nd Edition)",
	                category1, "Jayden Ky"));
	}
	
	@Override
	public List<Category> getAllCategories() {
		
		return categories;
	}

	@Override
	public Category getCategory(int id) {
		for (Category category : categories) {
			if (id == category.getId()) {
				return category;
			}
		}
		return null;
	}

	//获取所有书目
	@Override
	public List<Book> getAllBooks() {
		
		return books;
	}

	//添加书目
	@Override
	public Book save(Book book) {
		book.setId(getNextId());
		books.add(book);
		return book;
	}

	//更新书目
	@Override
	public Book update(Book book) {
		int bookCount = books.size();
		for(int i = 0;i < bookCount; i++){
			Book saveBook = books.get(i);
			if (saveBook.getId() == book.getId()) {
				books.set(i, book);
				return book;
			}
		}
		return null;
	}

	//获取单个书目
	@Override
	public Book get(long id) {
		for (Book book : books) {
			if (id == book.getId()) {
				return book;
			}
		}
		return null;
	}

	@Override
	public long getNextId() {
		//needs to be locked
		long id = 0L;
		for (Book book : books) {
			long bookId = book.getId();
			if(bookId > id){
				id = bookId;
			}
		}
		return id + 1;
	}

}
