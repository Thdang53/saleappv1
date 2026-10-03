-- Script khởi tạo Cơ sở dữ liệu WebBanHang
-- Phục vụ Bài thực hành 3: Tạo cơ sở dữ liệu cho website bán hàng

CREATE DATABASE IF NOT EXISTS `WebBanHang` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `WebBanHang`;

-- 1. Bảng categories (Danh mục sản phẩm)
CREATE TABLE IF NOT EXISTS `categories` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Bảng products (Sản phẩm)
CREATE TABLE IF NOT EXISTS `products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `price` DOUBLE NOT NULL,
    `image` VARCHAR(500),
    `category_id` BIGINT,
    CONSTRAINT `fk_products_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Bảng orders (Đơn hàng)
CREATE TABLE IF NOT EXISTS `orders` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `customer_name` VARCHAR(255),
    `customer_email` VARCHAR(255),
    `customer_phone` VARCHAR(255),
    `shipping_address` VARCHAR(255),
    `order_date` DATETIME,
    `total_amount` DOUBLE,
    `status` VARCHAR(50) DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Bảng order_details (Chi tiết đơn hàng)
CREATE TABLE IF NOT EXISTS `order_details` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `price` DOUBLE NOT NULL,
    CONSTRAINT `fk_order_details_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_order_details_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dữ liệu mẫu ban đầu cho categories
INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Điện thoại di động'),
(2, 'Máy tính bảng')
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

-- Dữ liệu mẫu ban đầu cho products
INSERT INTO `products` (`id`, `name`, `description`, `price`, `image`, `category_id`) VALUES
(1, 'iPhone 15 Pro Max 256GB', 'Titan tự nhiên, chip A17 Pro siêu mạnh mẽ, màn hình Super Retina XDR 120Hz ProMotion, hệ thống camera 48MP zoom quang 5x.', 29990000.0, '/images/iphone-15-pro-max.jpg', 1),
(2, 'iPhone 14 128GB', 'Thiết kế bền bỉ, màn hình OLED Super Retina XDR sắc nét, chip A15 Bionic mượt mà, thời lượng pin cả ngày.', 16990000.0, '/images/iphone-14.jpg', 1),
(3, 'Samsung Galaxy S24 Ultra', 'Quyền năng Galaxy AI, camera đỉnh cao 200MP, khung viền titan và bút S-Pen đẳng cấp tích hợp sẵn.', 27990000.0, '/images/samsung-s24-ultra.jpg', 1),
(4, 'Samsung Galaxy A54 5G', 'Thiết kế hiện đại, màn hình Super AMOLED 120Hz, pin 5000mAh, hiệu năng mượt mà trong tầm giá.', 7490000.0, '/images/samsung-a54.jpg', 1),
(5, 'iPad Pro M4 11 inch', 'Màn hình Ultra Retina XDR siêu sáng, vi xử lý Apple M4 bứt phá mọi giới hạn, thiết kế mỏng nhẹ hàng đầu thế giới.', 26990000.0, '/images/ipad-pro-m4.jpg', 2),
(6, 'iPad Air 6 M2 11 inch', 'Hiệu năng kinh ngạc cùng chip Apple M2, hỗ trợ Apple Pencil Pro và Magic Keyboard mới nhất.', 16490000.0, '/images/ipad-air-6.jpg', 2),
(7, 'Samsung Galaxy Tab S9', 'Màn hình Dynamic AMOLED 2X sắc nét sống động, chuẩn kháng nước bụi IP68, kèm sẵn bút S-Pen thế hệ mới.', 17990000.0, '/images/samsung-tab-s9.jpg', 2),
(8, 'Samsung Galaxy Tab A9+', 'Máy tính bảng màn hình 11 inch 90Hz, âm thanh vòm Dolby Atmos, giá phổ thông cho học tập và giải trí.', 4990000.0, '/images/samsung-tab-a9.jpg', 2)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`), `price` = VALUES(`price`), `image` = VALUES(`image`);
