package service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.NEW;

import model.Product;

/**
 * function:为了使该类能被Spring扫描到并作为依赖注入，必须为其标注@Service。
 * @author liangjing
 *
 */

@Service
public class ProductServiceImpl implements ProductService{
	
	private Map<Long, Product> products = new HashMap<Long, Product>();
	private AtomicLong generator =  new AtomicLong();
	
	public ProductServiceImpl() {
		Product product = new Product();
		product.setName("JX1 Power Drill");
		product.setDescription("Powerful hand drill");
		product.setPrice(new BigDecimal(129.99F));
		add(product);
	}

	@Override
	public Product add(Product product) {
		//依次增长
		long newId = generator.incrementAndGet();
		product.setId(newId);
		products.put(newId, product);
		return product;
	}

	@Override
	public Product get(long id) {
		return products.get(id);
	}

}
