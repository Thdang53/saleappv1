package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service // Khai báo đây là lớp Service được Spring quản lý
@RequiredArgsConstructor // Tự động tạo constructor cho các thuộc tính "final" (Dependency Injection)
@Transactional // Đảm bảo tính toàn vẹn của dữ liệu khi thao tác với database
public class CategoryService {
    private final CategoryRepository categoryRepository; // Kết nối với Database

    // 1. Lấy toàn bộ danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 2. Tìm danh mục theo ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // 3. Thêm mới danh mục
    public void addCategory(Category category) {
        categoryRepository.save(category); // Hàm save() tự động sinh câu lệnh INSERT trong SQL
    }

    // 4. Cập nhật danh mục
    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Danh mục ID " + category.getId() + " không tồn tại."));
        existingCategory.setName(category.getName()); // Thay đổi tên mới
        categoryRepository.save(existingCategory); // Cập nhật lại vào DB
    }

    // 5. Xóa danh mục theo ID
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Danh mục ID " + id + " không tồn tại."); // Tránh lỗi xóa ID ảo
        }
        categoryRepository.deleteById(id); // Thực hiện xóa
    }
}
