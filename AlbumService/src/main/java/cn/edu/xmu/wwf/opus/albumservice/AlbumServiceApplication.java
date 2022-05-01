package cn.edu.xmu.wwf.opus.albumservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("cn.edu.xmu.wwf.opus.albumservice.mapper")
public class AlbumServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlbumServiceApplication.class, args);
    }

}
