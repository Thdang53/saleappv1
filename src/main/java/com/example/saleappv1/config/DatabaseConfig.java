package com.example.saleappv1.config;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.repository.CategoryRepository;
import com.example.saleappv1.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.Socket;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        boolean mysqlAvailable = false;
        try (Socket socket = new Socket("localhost", 3306)) {
            mysqlAvailable = true;
        } catch (Exception ignored) {
        }

        if (mysqlAvailable) {
            System.out.println(">>> [DatabaseConfig] Phat hien MySQL localhost:3306 -> Su dung MySQL WebBanHang");
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/WebBanHang?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true")
                    .username("root")
                    .password("")
                    .build();
        } else {
            System.out.println(">>> [DatabaseConfig] Khong tim thay MySQL port 3306 -> Tu dong chuyen sang H2 In-Memory Database (MODE=MySQL) de chay web ngay!");
            return DataSourceBuilder.create()
                    .driverClassName("org.h2.Driver")
                    .url("jdbc:h2:mem:WebBanHang;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                    .username("sa")
                    .password("")
                    .build();
        }
    }

    @Bean
    public CommandLineRunner initData(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                Category cat1 = new Category();
                cat1.setName("Điện Thoại");
                categoryRepository.save(cat1);

                Category cat2 = new Category();
                cat2.setName("Máy Tính Bảng");
                categoryRepository.save(cat2);

                Product p1 = new Product();
                p1.setName("iPhone 14");
                p1.setPrice(20000000.0);
                p1.setDescription("Điện thoại Apple iPhone 14 chính hãng");
                p1.setImage("/images/iphone-14.jpg");
                p1.setCategory(cat1);
                productRepository.save(p1);

                Product p2 = new Product();
                p2.setName("Samsung Galaxy S24 Ultra");
                p2.setPrice(28000000.0);
                p2.setDescription("Điện thoại Samsung cao cấp với bút S-Pen");
                p2.setImage("/images/samsung-s24-ultra.jpg");
                p2.setCategory(cat1);
                productRepository.save(p2);

                Product p3 = new Product();
                p3.setName("iPad Pro M4");
                p3.setPrice(26000000.0);
                p3.setDescription("Máy tính bảng Apple iPad Pro chip M4 siêu mạnh");
                p3.setImage("/images/ipad-pro-m4.jpg");
                p3.setCategory(cat2);
                productRepository.save(p3);
            }
        };
    }
}
