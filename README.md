# BÀI THỰC HÀNH 2: PHÁT TRIỂN ỨNG DỤNG WEB VỚI JAVA SPRING BOOT

## Project: `saleappv1`

Ứng dụng web bán hàng trực tuyến đơn giản xây dựng bằng Java Spring Boot và Thymeleaf, quản lý dữ liệu sản phẩm qua file JSON theo mô hình kiến trúc Controller - Service - Model - View.

---

## 1. Công nghệ sử dụng
- **Ngôn ngữ**: Java 17+
- **Framework**: Spring Boot 3.2.x (Spring Web, Spring MVC)
- **Template Engine**: Thymeleaf
- **Xử lý JSON**: Jackson Databind (`ObjectMapper`)
- **Quản lý thư viện & Build**: Maven
- **Giao diện**: HTML5, CSS3 responsive

---

## 2. Cấu trúc thư mục project
```text
saleappv1
├── src
│   └── main
│       ├── java
│       │   └── com.example.saleappv1
│       │       ├── Saleappv1Application.java
│       │       ├── controller
│       │       │   ├── HomeController.java
│       │       │   └── ProductController.java
│       │       ├── model
│       │       │   ├── Product.java
│       │       │   └── Category.java
│       │       └── service
│       │           └── ProductService.java
│       └── resources
│           ├── data
│           │   ├── categories.json
│           │   └── products.json
│           ├── static
│           │   ├── css
│           │   │   └── style.css
│           │   ├── js
│           │   └── images
│           ├── templates
│           │   ├── index.html
│           │   ├── products.html
│           │   └── product-detail.html
│           └── application.properties
├── pom.xml
├── README.md
└── .gitignore
```

---

## 3. Các chức năng đã triển khai

### Yêu cầu 2: Trang chủ
- Controller: `HomeController` mapping `@GetMapping("/")` trả về template `index.html`.
- Hiển thị tiêu đề **“BÁN HÀNG TRỰC TUYẾN”** cùng các liên kết:
  - Tất cả (`/products`)
  - Điện thoại di động (`/products?categoryId=1`)
  - Máy tính bảng (`/products?categoryId=2`)

### Yêu cầu 3: Danh sách sản phẩm
- Model `Product` chứa: `id`, `name`, `description`, `price`, `image`, `categoryId`.
- File dữ liệu: `src/main/resources/data/products.json`.
- Sử dụng Jackson `ObjectMapper` đọc dữ liệu vào `ProductService`.
- Hiển thị danh sách sản phẩm gồm: Hình ảnh, Tên sản phẩm, Mô tả, Giá tại đường dẫn: `http://localhost:8080/products`.

### Yêu cầu 4: Xem chi tiết sản phẩm
- Đường dẫn: `http://localhost:8080/products/{productId}` (Ví dụ: `http://localhost:8080/products/1`).
- `ProductService.getProductById(id)` tìm kiếm theo ID.
- Hiển thị tối thiểu: Hình ảnh, Tên, Mô tả, Giá, Danh mục sản phẩm.

### Yêu cầu 5: Lọc và tìm kiếm sản phẩm
`ProductController` tiếp nhận các tham số `@RequestParam`:
- Lọc theo danh mục:
  - `http://localhost:8080/products?categoryId=1` (Điện thoại di động)
  - `http://localhost:8080/products?categoryId=2` (Máy tính bảng)
- Tìm theo tên sản phẩm:
  - `http://localhost:8080/products?keyword=iphone`
- Tìm theo khoảng giá:
  - `http://localhost:8080/products?fromPrice=5000000&toPrice=20000000`
- Có thể kết hợp cả 3 bộ lọc cùng lúc thông qua form tìm kiếm.

---

## 4. Hướng dẫn chạy ứng dụng

### Cách 1: Sử dụng IntelliJ IDEA / Eclipse / VS Code (Khuyên dùng)
1. Khởi động IDE (IntelliJ IDEA / Eclipse / Spring Tool Suite / VS Code).
2. Chọn **Open** (hoặc **Import**) $\rightarrow$ duyệt tới thư mục `saleappv1`.
3. Đợi IDE tải các thư viện Maven được khai báo trong `pom.xml`.
4. Mở file `Saleappv1Application.java` (đường dẫn `src/main/java/com/example/saleappv1/Saleappv1Application.java`).
5. Nhấn nút **Run** (biểu tượng Play màu xanh ▶).
6. Mở trình duyệt web và truy cập: `http://localhost:8080`.

### Cách 2: Chạy bằng Terminal / Command Prompt với Maven
Nếu máy đã cài sẵn Maven:
```bash
cd saleappv1
mvn spring-boot:run
```

---

## 5. Yêu cầu 6: Hướng dẫn quản lý mã nguồn bằng GitHub
1. Khởi tạo Git trong thư mục `saleappv1`:
   ```bash
   cd saleappv1
   git init
   ```
2. Thêm file và commit lần đầu (file `.gitignore` đã loại trừ thư mục `target/`, `.idea/`, v.v.):
   ```bash
   git add .
   git commit -m "Bài thực hành 2: Phát triển ứng dụng Web với Java Spring Boot"
   ```
3. Tạo repository mới trên GitHub (ví dụ tên `saleappv1`).
4. Liên kết và đẩy code lên:
   ```bash
   git remote add origin https://github.com/<tai-khoan-github-cua-ban>/saleappv1.git
   git branch -M main
   git push -u origin main
   ```

---

## 6. BÀI THỰC HÀNH 4: XÂY DỰNG CHỨC NĂNG HIỂN THỊ / THÊM / XÓA / SỬA (CRUD)

### Giai đoạn 1: Quản lý Danh mục (Category)
- **Lớp nghiệp vụ Service**: `CategoryService.java`
  - Thực hiện các thao tác CRUD với `CategoryRepository`:
    - `getAllCategories()`: Lấy toàn bộ danh mục.
    - `getCategoryById(id)`: Tìm danh mục theo ID.
    - `addCategory(category)`: Thêm mới danh mục vào Database.
    - `updateCategory(category)`: Cập nhật tên danh mục.
    - `deleteCategoryById(id)`: Xóa danh mục theo ID.
- **Lớp điều khiển Controller**: `CategoryController.java` (`@RequestMapping("/categories")`)
  - `@GetMapping`: Hiển thị danh sách danh mục (`/categories/categories-list`).
  - `@GetMapping("/add")` & `@PostMapping("/add")`: Hiển thị form và lưu thêm mới danh mục với kiểm tra hợp lệ (`@Valid`, `BindingResult`).
  - `@GetMapping("/edit/{id}")` & `@PostMapping("/update/{id}")`: Hiển thị form cập nhật và lưu thay đổi danh mục.
  - `@GetMapping("/delete/{id}")`: Xóa danh mục và chuyển hướng về danh sách.
- **Giao diện Thymeleaf Templates**:
  - `templates/categories/categories-list.html`: Danh sách danh mục dạng bảng kèm nút Thêm mới, Sửa, Xóa.
  - `templates/categories/add-category.html`: Form nhập tên danh mục mới.
  - `templates/categories/update-category.html`: Form cập nhật thông tin danh mục.

### Giai đoạn 2: Quản lý Sản phẩm (Product)
- **Lớp nghiệp vụ Service**: `ProductService.java`
  - Thực hiện các thao tác CRUD với `ProductRepository`:
    - `getAllProducts()`: Lấy danh sách sản phẩm.
    - `getProductById(id)`: Lấy chi tiết sản phẩm.
    - `addProduct(product)`: Lưu sản phẩm mới vào Database.
    - `updateProduct(product)`: Cập nhật thông tin sản phẩm (tên, giá, mô tả, danh mục).
    - `deleteProductById(id)`: Xóa sản phẩm theo ID.
- **Lớp điều khiển Controller**: `ProductController.java` (`@RequestMapping("/products")`)
  - `@GetMapping`: Hiển thị danh sách sản phẩm (`/products/products-list`).
  - `@GetMapping("/add")` & `@PostMapping("/add")`: Nạp danh sách danh mục từ `CategoryService` vào dropdown `<select>` để chọn khi thêm mới sản phẩm.
  - `@GetMapping("/edit/{id}")` & `@PostMapping("/update/{id}")`: Nạp dữ liệu cũ và danh sách danh mục để cập nhật sản phẩm.
  - `@GetMapping("/delete/{id}")`: Xóa sản phẩm theo ID.
- **Giao diện Thymeleaf Templates**:
  - `templates/products/products-list.html`: Danh sách sản phẩm dạng bảng (ID, Tên, Giá, Mô tả, Danh mục, Hành động).
  - `templates/products/add-product.html`: Form thêm mới sản phẩm với dropdown chọn danh mục.
  - `templates/products/update-product.html`: Form cập nhật sản phẩm.

### Khung giao diện dùng chung (Decorator Layout)
- `templates/layout.html`: Tích hợp Thymeleaf Layout Dialect (`layout:decorate="~{layout}"`) và Bootstrap 5.3, thanh điều hướng chuẩn theo bài học.
- **Cổng dịch vụ**: `http://localhost:9999`
  - Quản lý Danh mục: `http://localhost:9999/categories`
  - Quản lý Sản phẩm: `http://localhost:9999/products`
