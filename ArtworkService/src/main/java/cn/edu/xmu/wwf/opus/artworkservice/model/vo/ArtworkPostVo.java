package cn.edu.xmu.wwf.opus.artworkservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ArtworkPostVo {
    int userId;
    MultipartFile file;
    String name;
    String category;
    String url;
}
