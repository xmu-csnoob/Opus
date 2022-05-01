package cn.edu.xmu.wwf.opus.artworkservice.model.po;

import lombok.Data;

@Data
public class ArtworkPo {
    int id;
    String name;
    String introduction;
    int userId;
    int imageId;
    int categoryId;
    int state;
}
