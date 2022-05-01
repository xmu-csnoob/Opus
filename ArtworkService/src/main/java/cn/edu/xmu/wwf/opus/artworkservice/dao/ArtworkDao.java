package cn.edu.xmu.wwf.opus.artworkservice.dao;

import cn.edu.xmu.wwf.opus.artworkservice.mapper.ArtworkMapper;
import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import cn.edu.xmu.wwf.opus.artworkservice.utils.PageConfigUtil;
import cn.edu.xmu.wwf.opus.artworkservice.utils.RocketMQUtil;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtworkDao {
    @Autowired
    ArtworkMapper artworkMapper;
    @Autowired
    RocketMQUtil rocketMQUtil;
    public ReturnObject asyncAddArtworkIntoDB(ArtworkPostVo artworkPostVo){
        try{
            rocketMQUtil.sendMessage("insert-artwork-topic", JSON.toJSONString(artworkPostVo));
            return new ReturnObject(ReturnNo.OK,"success");
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    public ArtworkPo addArtworkIntoDB(ArtworkPostVo artworkPostVo){
        ArtworkPo artworkPo=new ArtworkPo();
        artworkPo.setImageId(artworkPostVo.getImageId());
        artworkPo.setUserId(artworkPostVo.getUserId());
        artworkPo.setName(artworkPostVo.getName());
        artworkPo.setIntroduction(artworkPostVo.getIntroduction());
        artworkPo.setCategoryId(20);
        artworkMapper.insertPo(artworkPo);
        return artworkPo;
    }
    public ArtworkPo getArtworkByIdFromDB(int id){
        return artworkMapper.selectById(id);
    }
    public int alterArtworkStateInDB(int id,int state){
        return artworkMapper.alterState(id,state);
    }
    public PageInfo<ArtworkPo> getUserArtworkListWithPage(int userId, PageConfigUtil pageConfig){
        PageHelper.startPage(pageConfig.getPage(),pageConfig.getPageSize());
        List<ArtworkPo> artworkPos=artworkMapper.selectByUserId(userId);
        return new PageInfo<>(artworkPos);
    }
    public PageInfo<ArtworkPo> obscureSearchInDB(String keyword,PageConfigUtil pageConfig){
        PageHelper.startPage(pageConfig.getPage(),pageConfig.getPageSize());
        List<ArtworkPo> artworkPos=artworkMapper.selectByKeyword("%"+keyword+"%");
        return new PageInfo<>(artworkPos);
    }
}
