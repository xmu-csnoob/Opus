package cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostImageRetVo {
    String name;
    String url;
    LocalDateTime uploadTime;
}
