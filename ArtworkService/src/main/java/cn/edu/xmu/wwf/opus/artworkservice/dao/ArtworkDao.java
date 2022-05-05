package cn.edu.xmu.wwf.opus.artworkservice.dao;

import cn.edu.xmu.wwf.opus.artworkservice.mapper.ArtworkMapper;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.CategoryService;
import cn.edu.xmu.wwf.opus.artworkservice.model.po.ArtworkPo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import cn.edu.xmu.wwf.opus.artworkservice.utils.PageConfigUtil;
import cn.edu.xmu.wwf.opus.artworkservice.utils.RedisUtil;
import cn.edu.xmu.wwf.opus.artworkservice.utils.RocketMQUtil;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtworkDao {
    @Autowired
    ArtworkMapper artworkMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    RocketMQUtil rocketMQUtil;
    @Autowired
    RedisUtil<String,ArtworkPo> redisUtil;
    static final String ARTWORK_KEY_PREFIX="artwork";
    public boolean isKeyExistInCache(int id){
        return redisUtil.hasKey(ARTWORK_KEY_PREFIX+id);
    }
    public void addArtworkIntoCache(ArtworkPo artworkPo){
        redisUtil.add(ARTWORK_KEY_PREFIX+artworkPo.getId(),artworkPo);
    }
    public ArtworkPo getArtworkFromCache(int id){
        return redisUtil.get(ARTWORK_KEY_PREFIX+id);
    }
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
        BeanUtils.copyProperties(artworkPostVo,artworkPo);
        artworkMapper.insertPo(artworkPo);
        List<Integer> categoryIds=artworkPostVo.getCategoryIds();
        for(int id:categoryIds){
            categoryService.addArtworkIntoCategory(id,artworkPo.getId());
        }
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
