package cn.edu.xmu.wwf.opus.albumservice.mapper;

import cn.edu.xmu.wwf.opus.albumservice.model.po.AlbumPo;
import cn.edu.xmu.wwf.opus.albumservice.model.po.AlbumContainPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlbumMapper {
    int insertAlbumPo(AlbumPo albumPo);
    int insertAlbumContainPo(int artworkId,int albumId);
    int deleteAlbumContainPo(int artworkId,int albumId);
    AlbumContainPo selectAlbumContainPoByPrimaryKey(int artworkId,int albumId);
    AlbumPo selectById(int id);
    List<AlbumContainPo> selectArtworkIdsByAlbumId(int id);
    int updateByPo(AlbumPo albumPo);
}
