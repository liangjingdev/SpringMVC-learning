package formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * function:Formatter就像Converter一样，也是将一种类型转换成另一种类型。但是，Formatter的源类型必须是一个
 * String，而Converter则适用于任意的源类型。为了转换Spring MVC应用程序表单中的用户输入，始终应该选择Formatter，而不是
 * Converter。
 * @author liangjing
 *
 */
public class LocalDateFormatter implements Formatter<LocalDate>{

	 private DateTimeFormatter formatter;
	 private String datePattern;

	 public LocalDateFormatter(String datePattern) {
	     this.datePattern = datePattern;
	     formatter = DateTimeFormatter.ofPattern(datePattern);
	 }
	    
	//返回目标对象的字符串表示法
	@Override
	public String print(LocalDate date, Locale locale) {
		System.out.println(date.format(formatter));
        return date.format(formatter);
	}

	//该方法利用指定的Locale将一个String解析成目标类型。
	@Override
	public LocalDate parse(String s, Locale locale) throws ParseException {
		 System.out.println("formatter.parse. s:" + s + ", pattern:" + datePattern);
	        try {
	            return LocalDate.parse(s, DateTimeFormatter.ofPattern(datePattern));
	        } catch (DateTimeParseException e) {
	            // the error message will be displayed in <form:errors>
	            throw new IllegalArgumentException(
	                    "invalid date format. Please use this pattern\""
	                            + datePattern + "\"");
	        }
	}

}
