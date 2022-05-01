package cn.edu.xmu.wwf.opus.artworkservice.model.vo;

import lombok.Data;

@Data
public class GetArtworkRetVo {
    String name;
    String introduction;
    int userId;
    int imageId;
    int categoryId;
    String url;
}
