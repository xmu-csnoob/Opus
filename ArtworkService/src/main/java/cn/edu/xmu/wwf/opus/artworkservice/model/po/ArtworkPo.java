package cn.edu.xmu.wwf.opus.artworkservice.model.po;

import lombok.Data;

@Data
public class ArtworkPo {
    int id;
    String name;
    int userId;
    int imageId;
    int categoryId;
}
