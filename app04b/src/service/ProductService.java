package service;

import model.Product;

public interface ProductService {
	
	Product add(Product product);
	Product get(long id);
	
}
