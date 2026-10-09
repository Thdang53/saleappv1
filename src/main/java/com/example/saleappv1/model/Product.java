package com.example.saleappv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên là bắt buộc")
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Giá là bắt buộc")
    @Column(nullable = false)
    private Double price;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product(Long id, String name, String description, Double price, String image, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Product(Integer id, String name, String description, Double price, String image, Integer categoryId) {
        this.id = id != null ? id.longValue() : null;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        if (categoryId != null) {
            this.category = new Category(categoryId.longValue(), null);
        }
    }

    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public void setCategoryId(Long categoryId) {
        if (categoryId != null) {
            this.category = new Category(categoryId, null);
        }
    }
}
