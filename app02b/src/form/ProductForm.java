package form;

/**
 * 
 * @author liangjing
 * function:表单对象会传递ServletRequest给其他组件，类似Validator。而ServletRequest是一个Servlet层的对象，
 * 不应当暴露给应用的其他层。并且当数据校验失败时，表单对象将用于保存和展示用户在原始表单上的输入。
 * 注意：大部分情况下，一个表单类不需要实现Serializable接口，因为表单对象很少保存在HttpSession中。
 */
public class ProductForm {
	private String name;
    private String description;
    private String price;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
