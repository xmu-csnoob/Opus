package cn.edu.xmu.wwf.opus.artworkservice.service;

import cn.edu.xmu.wwf.opus.artworkservice.dao.ArtworkDao;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.ImgService;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image.GetImageUrlRetVo;
import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.GetArtworkRetVo;
import cn.edu.xmu.wwf.opus.artworkservice.utils.PageConfigUtil;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtworkService {
    @Autowired
    ArtworkDao artworkDao;
    @Autowired
    ImgService imgService;
    public ReturnObject addArtwork(ArtworkPostVo artworkPostVo){
        return artworkDao.asyncAddArtworkIntoDB(artworkPostVo);
    }
    public ReturnObject onShelfArtwork(int id){
        ArtworkPo artworkPo=artworkDao.getArtworkByIdFromDB(id);
        if(artworkPo!=null){
            //artworkPo.getState()!=4
            if(artworkPo.getState()==1){
                return new ReturnObject<>(ReturnNo.STATE_NOT_ALLOWED,"当前作品已上架");
            }else if(artworkPo.getState()==4){
                return new ReturnObject(ReturnNo.STATE_NOT_ALLOWED,"当前作品违规，无法上架");
            }
            artworkDao.alterArtworkStateInDB(id,1);
            return new ReturnObject(ReturnNo.OK,"success");
        }
        return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"该作品id不存在");
    }
    public ReturnObject offShelfArtwork(int id){
        ArtworkPo artworkPo=artworkDao.getArtworkByIdFromDB(id);
        if(artworkPo!=null){
            if(artworkPo.getState()==2){
                return new ReturnObject<>(ReturnNo.STATE_NOT_ALLOWED,"当前作品已下架");
            }else if(artworkPo.getState()==4){
                return new ReturnObject(ReturnNo.STATE_NOT_ALLOWED,"当前作品违规，无法上架");
            }
            artworkDao.alterArtworkStateInDB(id,2);
            return new ReturnObject(ReturnNo.OK,"success");
        }
        return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"该作品id不存在");
    }
    public ReturnObject getGetArtworkRetVo(int id) {
        if (!artworkDao.isKeyExistInCache(id)) {
            ArtworkPo artworkPo = artworkDao.getArtworkByIdFromDB(id);
            if (artworkPo == null) {
                return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND, "作品id不存在");
            }
            GetArtworkRetVo getArtworkRetVo = new GetArtworkRetVo();
            BeanUtils.copyProperties(artworkPo, getArtworkRetVo);
            artworkDao.addArtworkIntoCache(artworkPo);
            return new ReturnObject(getArtworkRetVo);
        } else {
            ArtworkPo artworkPo = artworkDao.getArtworkFromCache(id);
            GetArtworkRetVo getArtworkRetVo = new GetArtworkRetVo();
            BeanUtils.copyProperties(artworkPo, getArtworkRetVo);
            return new ReturnObject(getArtworkRetVo);
        }

    }
    public ReturnObject getPagedUserArtworkList(int userId,PageConfigUtil pageConfigUtil){
        return new ReturnObject(artworkDao.getUserArtworkListWithPage(userId,pageConfigUtil));
    }
    public ReturnObject getPagedObscureSearchResults(String keyword,PageConfigUtil pageConfigUtil){
        return new ReturnObject(artworkDao.obscureSearchInDB(keyword,pageConfigUtil));
    }
}
