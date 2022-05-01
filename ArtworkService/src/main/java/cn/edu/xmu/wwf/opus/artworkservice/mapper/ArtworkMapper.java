package cn.edu.xmu.wwf.opus.artworkservice.mapper;

import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArtworkMapper {
    List<ArtworkPo> selectAll();
    List<ArtworkPo> selectByUserId(int userId);
    int insertPo(ArtworkPo artworkPo);
    ArtworkPo selectById(int id);
    int alterState(int id,int state);
    List<ArtworkPo> selectByKeyword(String keyword);
}
