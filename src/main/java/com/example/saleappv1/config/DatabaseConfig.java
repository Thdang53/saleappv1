package com.example.saleappv1.config;

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
}
