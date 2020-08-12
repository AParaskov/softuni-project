package gamestore.model.view;

import gamestore.model.service.ReviewServiceModel;

import java.math.BigDecimal;
import java.util.List;

public class ProductViewModel {
    private String id;
    private String name;
    private String manufacturer;
    private String description;
    private int quantity;
    private BigDecimal price;
    private List<ReviewServiceModel> reviews;
    private String imgUrl;

    public ProductViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ReviewServiceModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewServiceModel> reviews) {
        this.reviews = reviews;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
