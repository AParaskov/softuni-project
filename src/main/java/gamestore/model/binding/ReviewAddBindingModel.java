package gamestore.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public class ReviewAddBindingModel {
    private String userName;
    private String text;
    private LocalDateTime addedOn;
    private String productId;

    public ReviewAddBindingModel() {
    }


    @Length(min = 2, message = "Name must be more than two characters!")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Length(min = 2, message = "Review must be more than two characters!")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @PastOrPresent(message = "The date cannot be in the future!")
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    @Length(min = 2, message = "Product name must be more than two characters!")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
