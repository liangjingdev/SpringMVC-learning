package controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.bcel.internal.generic.NEW;

import domain.Employee;

@Controller
public class EmployeeController {

	private static final Log logger = LogFactory.getLog(EmployeeController.class);
	
	@RequestMapping(value="employee_input")
	public String inputEmployee(Model model){
		model.addAttribute(new Employee());
		return "EmployeeForm";
	}
	
	/**
	 * function:saveEmployee方法取出一个在提交Employee表单时创建的Employee对象。有了StringToDateConverter 
	 * converter，就不需要劳驾controller类将字符串转换成日期了。saveEmployee方法的BindingResult参数中放置了
	 * Spring的所有绑定错误。该方法利用BindingResult记录所有绑定错误。绑定错误也可以利用errors标签显示在一个表单中。
	 * @param employee
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value="employee_save")
	public String saveEmployee(@ModelAttribute Employee employee,BindingResult bindingResult,
			Model model){
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			logger.info("Code:" + fieldError.getCode() + ", field:" + fieldError.getField());
			return "EmployeeForm";
		}
		
		model.addAttribute("employee",employee);
		return "EmployeeDetails";
	}
}
