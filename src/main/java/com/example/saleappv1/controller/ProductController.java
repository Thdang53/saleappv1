package com.example.saleappv1.controller;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.CategoryService;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService; // Dùng để nạp danh sách danh mục khi thêm/sửa sản phẩm

    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products/products-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // Đổ dữ liệu danh mục ra dropdown select
        return "/products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result,
                             @RequestParam(value = "imageFile", required = false) org.springframework.web.multipart.MultipartFile imageFile,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories()); // Nạp lại danh mục nếu form lỗi
            return "/products/add-product";
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String originalFilename = org.springframework.util.StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileName = java.util.UUID.randomUUID().toString().substring(0, 8) + "_" + originalFilename;
                java.nio.file.Path uploadDir = java.nio.file.Paths.get("src/main/resources/static/images");
                if (!java.nio.file.Files.exists(uploadDir)) {
                    java.nio.file.Files.createDirectories(uploadDir);
                }
                java.nio.file.Files.copy(imageFile.getInputStream(), uploadDir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                java.nio.file.Path targetDir = java.nio.file.Paths.get("target/classes/static/images");
                if (java.nio.file.Files.exists(targetDir)) {
                    java.nio.file.Files.copy(imageFile.getInputStream(), targetDir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImage("/images/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID sản phẩm không hợp lệ: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); // Nạp danh mục để người dùng thay đổi
        return "/products/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result,
                                @RequestParam(value = "imageFile", required = false) org.springframework.web.multipart.MultipartFile imageFile,
                                Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/update-product";
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String originalFilename = org.springframework.util.StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileName = java.util.UUID.randomUUID().toString().substring(0, 8) + "_" + originalFilename;
                java.nio.file.Path uploadDir = java.nio.file.Paths.get("src/main/resources/static/images");
                if (!java.nio.file.Files.exists(uploadDir)) {
                    java.nio.file.Files.createDirectories(uploadDir);
                }
                java.nio.file.Files.copy(imageFile.getInputStream(), uploadDir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                java.nio.file.Path targetDir = java.nio.file.Paths.get("target/classes/static/images");
                if (java.nio.file.Files.exists(targetDir)) {
                    java.nio.file.Files.copy(imageFile.getInputStream(), targetDir.resolve(fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImage("/images/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            productService.getProductById(id).ifPresent(old -> {
                if (product.getImage() == null || product.getImage().isEmpty()) {
                    product.setImage(old.getImage());
                }
            });
        }
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
