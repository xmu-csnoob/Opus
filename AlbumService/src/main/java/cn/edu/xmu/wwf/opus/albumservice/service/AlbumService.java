package cn.edu.xmu.wwf.opus.albumservice.service;

import cn.edu.xmu.wwf.opus.albumservice.dao.AlbumDao;
import cn.edu.xmu.wwf.opus.albumservice.microservice.model.GetArtWorkRetVo;
import cn.edu.xmu.wwf.opus.albumservice.microservice.ArtworkService;
import cn.edu.xmu.wwf.opus.albumservice.model.po.AlbumContainPo;
import cn.edu.xmu.wwf.opus.albumservice.model.po.AlbumPo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumContainPostVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumInfoRetVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumPostVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumPutVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Autowired
    ArtworkService artworkService;

    public ReturnObject addAlbumIntoAlbum(AlbumPostVo albumPostVo){
        AlbumPo albumPo=albumDao.addAlbumIntoDB(albumPostVo);
        return new ReturnObject(albumPo);
    }
    public ReturnObject getAlbumInfo(int id){
        AlbumPo albumPo=albumDao.getAlbumById(id);
        if(albumPo==null){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"专辑id不存在");
        }
        List<AlbumContainPo> containPoList=albumDao.getAlbumContainByAlbumId(id);
        AlbumInfoRetVo albumInfoRetVo=new AlbumInfoRetVo();
        BeanUtils.copyProperties(albumPo,albumInfoRetVo);
        try{
            List<GetArtWorkRetVo> getArtWorkRetVos=new ArrayList<>();
            for(AlbumContainPo containPo:containPoList){
                getArtWorkRetVos.add(artworkService.getArtwork(containPo.getArtworkId()).data);
            }
            albumInfoRetVo.setArtworks(getArtWorkRetVos);
            return new ReturnObject(albumInfoRetVo);
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    public ReturnObject updateAlbumInfo(AlbumPutVo albumPutVo){
        AlbumPo albumPo=albumDao.getAlbumById(albumPutVo.getId());
        if(albumPo==null){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"专辑id不存在");
        }
        return new ReturnObject(albumDao.updateAlbumInDB(albumPutVo));
    }
    public ReturnObject addArtworkIntoAlbum(AlbumContainPostVo albumContainPostVo){
        AlbumPo albumPo=albumDao.getAlbumById(albumContainPostVo.getAlbumId());
        ReturnObject returnObject=artworkService.getArtwork(albumContainPostVo.getArtworkId());
        System.out.println(returnObject.returnNo.getValue());
        if(returnObject.returnNo==ReturnNo.RESOURCE_NOT_FOUND){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"作品id不存在");
        }
        if(albumPo==null){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"专辑id不存在");
        }
        return new ReturnObject(albumDao.addAlbumContainIntoDB(albumContainPostVo));
    }
    public ReturnObject removeArtworkFromAlbum(int albumId,int artworkId){
        AlbumContainPo albumContainPo=albumDao.getAlbumContainFromDB(albumId,artworkId);
        if(albumContainPo==null){
            System.out.println("here");
            return new ReturnObject(ReturnNo.STATE_NOT_ALLOWED,"该专辑并不包含该作品");
        }
        return new ReturnObject(albumDao.removeAlbumContainFromDB(albumId,artworkId));
    }
}
