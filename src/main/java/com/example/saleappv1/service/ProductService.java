package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        loadCategories();
        loadProducts();
    }

    private void loadCategories() {
        try {
            ClassPathResource resource = new ClassPathResource("data/categories.json");
            try (InputStream inputStream = resource.getInputStream()) {
                categories = objectMapper.readValue(inputStream, new TypeReference<List<Category>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
            categories = new ArrayList<>();
        }
    }

    private void loadProducts() {
        try {
            ClassPathResource resource = new ClassPathResource("data/products.json");
            try (InputStream inputStream = resource.getInputStream()) {
                products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
            products = new ArrayList<>();
        }
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public Category getCategoryById(Integer categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categories.stream()
                .filter(cat -> categoryId.equals(cat.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(Integer id) {
        if (id == null) {
            return null;
        }
        return products.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        return products.stream()
                .filter(p -> {
                    // Lọc theo categoryId
                    if (categoryId != null && !categoryId.equals(p.getCategoryId())) {
                        return false;
                    }
                    // Tìm kiếm theo keyword trong tên sản phẩm (không phân biệt hoa thường)
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lowerKeyword = keyword.trim().toLowerCase();
                        if (p.getName() == null || !p.getName().toLowerCase().contains(lowerKeyword)) {
                            return false;
                        }
                    }
                    // Tìm kiếm theo khoảng giá
                    if (fromPrice != null && p.getPrice() != null && p.getPrice() < fromPrice) {
                        return false;
                    }
                    if (toPrice != null && p.getPrice() != null && p.getPrice() > toPrice) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
