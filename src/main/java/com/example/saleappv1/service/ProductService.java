package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(@NotNull Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Sản phẩm ID " + product.getId() + " không tồn tại."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        if (product.getImage() != null) {
            existingProduct.setImage(product.getImage());
        }
        return productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Sản phẩm ID " + id + " không tồn tại.");
        }
        productRepository.deleteById(id);
    }

    // Các hàm tương thích từ tuần trước
    public Product getProductById(Integer id) {
        if (id == null) return null;
        return getProductById(id.longValue()).orElse(null);
    }

    public List<Product> getProducts(Long categoryId, String keyword, Double fromPrice, Double toPrice) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(p -> {
                    if (categoryId != null && (p.getCategory() == null || !categoryId.equals(p.getCategory().getId()))) {
                        return false;
                    }
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lowerKeyword = keyword.trim().toLowerCase();
                        if (p.getName() == null || !p.getName().toLowerCase().contains(lowerKeyword)) {
                            return false;
                        }
                    }
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

    public List<Product> getProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        return getProducts(categoryId != null ? categoryId.longValue() : null, keyword, fromPrice, toPrice);
    }
}
