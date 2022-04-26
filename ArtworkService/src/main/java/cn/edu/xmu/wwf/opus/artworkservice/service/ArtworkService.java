package cn.edu.xmu.wwf.opus.artworkservice.service;

import cn.edu.xmu.wwf.opus.artworkservice.dao.ArtworkDao;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.ImgService;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image.PostImageRetVo;
import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import cn.edu.xmu.wwf.opus.artworkservice.utils.RocketMQUtil;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtworkService {
    @Autowired
    ImgService imgService;
    @Autowired
    ArtworkDao artworkDao;
    @Autowired
    RocketMQUtil<ArtworkPo> rocketMQUtil;
    public void addArtwork(ArtworkPostVo artworkPostVo){
        ReturnObject<PostImageRetVo> returnObject=imgService.PostImageToCos(artworkPostVo.getFile(),artworkPostVo.getCategory());
        artworkPostVo.setUrl(returnObject.data.getUrl());
        artworkDao.addArtworkIntoDB(artworkPostVo);
    }
}
