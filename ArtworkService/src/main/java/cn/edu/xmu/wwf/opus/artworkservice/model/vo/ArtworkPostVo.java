package cn.edu.xmu.wwf.opus.artworkservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtworkPostVo {
    int userId;
    int imageId;
    String name;
    String category;
    String introduction;
    String url;
}
