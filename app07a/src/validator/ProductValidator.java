package validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import domain.Product;


/**
 * function:在Spring MVC中，有两种方式可以验证输入，即利用Spring自带的验证框架，或者利用JSR 303实现。
 * 在此处是利用了Spring自带的验证器框架。为了创建Spring验证器，要实现该接口。
 * @author liangjing
 *
 */
public class ProductValidator implements Validator{

	//如果验证器可以处理指定的Class，supports方法将返回true。
	@Override
	public boolean supports(Class<?> klass) {
		
		return Product.class.isAssignableFrom(klass);
	}

	
	//validate方法会验证目标对象，并将验证错误填入Errors对象。Errors对象中包含了一系列FieldError和ObjectError对象。
	//FieldError表示与被验证对象中的某个属性相关的一个错误。编写验证器时，不需要直接创建Error对象，因为实例化ObjectError
	//或FieldError花费了大量的编程精力。给Errors对象添加错误最容易的方法是：在Errors对象上调用一个reject或者rejectValue
	//方法。调用reject，往FieldError中添加一个ObjectError和rejectValue。大多数时候，只给reject或者rejectValue
	//方法传入一个错误码，Spring就会在属性文件中查找错误码，获得相应的错误消息。还可以传入一个默认消息，当没有找到指定的错误码
	//时，就会使用默认消息。Errors对象中的错误消息，可以利用表单标签库的Errors标签显示在HTML页面中。
	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		//ValidationUtils类是一个工具，有助于编写Spring验证器。
		ValidationUtils.rejectIfEmpty(errors, "name", "productName.required");
		ValidationUtils.rejectIfEmpty(errors, "price", "price.required");
		ValidationUtils.rejectIfEmpty(errors, "productionDate", "productionDate.required");
		BigDecimal price = product.getPrice();
		
		//检查price是否为负数
		if (price!=null && price.compareTo(BigDecimal.ZERO)<0) {
			errors.rejectValue("price", "price.negative");
		}
		
		//检查生产日期是否在当前日期之后
		LocalDate productionDate = product.getProductionDate();
        if (productionDate != null) {
            if (productionDate.isAfter(LocalDate.now())) {
                errors.rejectValue("productionDate", "productionDate.invalid");
            }
        }
	}

}
