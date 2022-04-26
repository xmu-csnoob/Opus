package cn.edu.xmu.wwf.opus.artworkservice.mapper;

import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArtworkMapper {
    List<ArtworkPo> selectAll();
    int insertPo(ArtworkPo artworkPo);
}
