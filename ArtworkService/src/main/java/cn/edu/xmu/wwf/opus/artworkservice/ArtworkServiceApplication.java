package cn.edu.xmu.wwf.opus.artworkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ArtworkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtworkServiceApplication.class, args);
    }

}
