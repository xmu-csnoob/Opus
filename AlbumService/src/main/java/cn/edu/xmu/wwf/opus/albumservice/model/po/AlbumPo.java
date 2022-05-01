package cn.edu.xmu.wwf.opus.albumservice.model.po;

import lombok.Data;

@Data
public class AlbumPo {
    int id;
    String name;
    int userId;
    String introduction;
    boolean isDeleted;
}
