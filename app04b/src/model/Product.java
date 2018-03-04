package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {
	
	private static final long serialVersionUID = 748392348L;
    private String name;
    private long id;
    private String description;
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal f) {
        this.price = f;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
