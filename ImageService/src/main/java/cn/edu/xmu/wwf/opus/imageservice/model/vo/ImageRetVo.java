package cn.edu.xmu.wwf.opus.imageservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ImageRetVo {
    int id;
    String name;
    String url;
    LocalDateTime uploadTime;
}
