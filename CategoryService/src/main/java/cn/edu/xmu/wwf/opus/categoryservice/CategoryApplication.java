package cn.edu.xmu.wwf.opus.categoryservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class, args);
    }

}
