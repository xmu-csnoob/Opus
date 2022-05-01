package cn.edu.xmu.wwf.opus.albumservice.microservice.model;

import lombok.Data;

@Data
public class GetArtWorkRetVo {
    String name;
    String introduction;
    int userId;
    int imageId;
    int categoryId;
    String url;
}
