package cn.edu.xmu.wwf.opus.albumservice.model.vo;

import cn.edu.xmu.wwf.opus.albumservice.microservice.model.GetArtWorkRetVo;
import lombok.Data;

import java.util.List;

@Data
public class AlbumInfoRetVo {
    String name;
    int userId;
    String introduction;
    List<GetArtWorkRetVo> artworks;
}
