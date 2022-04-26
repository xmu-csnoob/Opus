package cn.edu.xmu.wwf.opus.artworkservice.dao;

import cn.edu.xmu.wwf.opus.artworkservice.mapper.ArtworkMapper;
import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArtworkDao {
    @Autowired
    ArtworkMapper artworkMapper;
    public int addArtworkIntoDB(ArtworkPostVo artworkPostVo){
        ArtworkPo artworkPo=new ArtworkPo();
        artworkPo.setUserId(artworkPostVo.getUserId());
        artworkPo.setName(artworkPostVo.getName());
        artworkPo.setImageId(0);
        artworkPo.setCategoryId(20);
        return artworkMapper.insertPo(artworkPo);
    }
}
