package cn.edu.xmu.wwf.opus.albumservice.dao;

import cn.edu.xmu.wwf.opus.albumservice.mapper.AlbumMapper;
import cn.edu.xmu.wwf.opus.albumservice.model.po.AlbumContainPo;
import cn.edu.xmu.wwf.opus.albumservice.model.po.AlbumPo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumContainPostVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumPostVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumPutVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumDao {
    @Autowired
    AlbumMapper albumMapper;
    public AlbumPo addAlbumIntoDB(AlbumPostVo albumPostVo){
        AlbumPo albumPo=new AlbumPo();
        BeanUtils.copyProperties(albumPostVo,albumPo);
        albumMapper.insertAlbumPo(albumPo);
        return albumPo;
    }
    public int addAlbumContainIntoDB(AlbumContainPostVo albumContainPostVo){
        return albumMapper.insertAlbumContainPo(albumContainPostVo.getArtworkId(),albumContainPostVo.getAlbumId());
    }
    public int removeAlbumContainFromDB(int albumId,int artworkId){
        return albumMapper.deleteAlbumContainPo(artworkId,albumId);
    }
    public AlbumContainPo getAlbumContainFromDB(int albumId,int artworkId){
        return albumMapper.selectAlbumContainPoByPrimaryKey(artworkId,albumId);
    }
    public AlbumPo getAlbumById(int id){
        return albumMapper.selectById(id);
    }
    public List<AlbumContainPo> getAlbumContainByAlbumId(int id){
        return albumMapper.selectArtworkIdsByAlbumId(id);
    }
    public AlbumPo updateAlbumInDB(AlbumPutVo albumPutVo){
        AlbumPo albumPo=new AlbumPo();
        BeanUtils.copyProperties(albumPutVo,albumPo);
        int res=albumMapper.updateByPo(albumPo);
        return res==1?albumPo:null;
    }
}
