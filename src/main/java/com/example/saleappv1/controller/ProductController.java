package com.example.saleappv1.controller;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Yêu cầu 3 & Yêu cầu 5: Hiển thị danh sách sản phẩm, hỗ trợ lọc và tìm kiếm
     */
    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice,
            Model model) {

        List<Product> productList = productService.getProducts(categoryId, keyword, fromPrice, toPrice);
        List<Category> categoryList = productService.getAllCategories();

        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryList);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("fromPrice", fromPrice);
        model.addAttribute("toPrice", toPrice);

        // Tìm tên danh mục đang chọn (nếu có)
        if (categoryId != null) {
            Category selectedCategory = productService.getCategoryById(categoryId);
            model.addAttribute("selectedCategoryName", selectedCategory != null ? selectedCategory.getName() : null);
        }

        return "products";
    }

    /**
     * Yêu cầu 4: Xem chi tiết sản phẩm theo id
     */
    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "redirect:/products";
        }

        Category category = productService.getCategoryById(product.getCategoryId());

        model.addAttribute("product", product);
        model.addAttribute("category", category);

        return "product-detail";
    }
}
