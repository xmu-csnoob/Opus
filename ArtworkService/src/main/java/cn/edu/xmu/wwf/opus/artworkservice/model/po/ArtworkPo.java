package cn.edu.xmu.wwf.opus.artworkservice.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArtworkPo implements Serializable {
    int id;
    String name;
    String introduction;
    int userId;
    int imageId;
    int categoryId;
    int state;
}
