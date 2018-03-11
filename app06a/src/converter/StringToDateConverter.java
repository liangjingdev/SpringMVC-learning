package converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * function:Spring的数据绑定并非没有任何限制。有案例表明，Spring在如何正确绑定数据方面是杂乱无章的。
 * 例如，Spring总是试图用默认的语言区域将日期输入绑定到java.util.Date。假如想让Spring使用不同的日期样式，就需要用一个
 * Converter（转换器）或者Formatter（格式化）来协助Spring完成。这两者均可用于将一种对象类型转换成另一种对象类型。
 * Converter是通用元件，可以在应用程序的任意层中使用，而Formatter则是专门为Web层设计的。如如果希望Spring在将输入的日期
 * 字符串绑定到Date时，使用不同的日期样式，则需要编写一个Converter，才能将字符串转换成日期。为了创建Converter，必须编写
 * 一个实现该接口的类。
 * @author liangjing
 *
 */
public class StringToDateConverter implements Converter<String, Date>{

	//日期样式
	private String datePattern;
	
	public StringToDateConverter(String datePattern) {
		this.datePattern = datePattern;
		System.out.println("instantiating ... converter with pattern:*" + datePattern);
	}
	
	/**
	 * function:通过该方法来实现你需要显示的日期样式。
	 */
	@Override
	public Date convert(String s) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
			dateFormat.setLenient(false);
			return dateFormat.parse(s);
		} catch (ParseException e) {
			//the error message will be displayed when using
			//<form:errors>
			throw new IllegalArgumentException("invalid date format. Please use this pattern\""
					+ datePattern + "\"");
		}
		
	}

}
