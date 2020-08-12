package gamestore.model.binding;

import gamestore.model.entity.CategoryName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductAddBindingModel {
    private String name;
    private String manufacturer;
    private String description;
    private int quantity;
    private BigDecimal price;
    private CategoryName category;

    public ProductAddBindingModel() {
    }

    @Length(min = 2, message = "Name length must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 3, message = "Manufacturer name must be more than 3 characters")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Length(min = 2, message = "Description length must be more than two characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Min(value = 1, message = "Enter valid quantity!")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @DecimalMin(value = "0", message = "Enter valid price!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Enter valid category name!")
    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }

}
