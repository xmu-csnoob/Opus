package cn.edu.xmu.wwf.opus.artworkservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArtworkPostVo {
    int userId;
    int imageId;
    String name;
    List<Integer> categoryIds;
    String introduction;
    String url;
}
