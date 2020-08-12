package gamestore.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class ProductUpdateBindingModel {
    private String name;
    private int quantity;

    public ProductUpdateBindingModel() {
    }

    @Length(min = 2, message = "Wrong name!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Min(value = 0, message = "Enter valid quantity!")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
