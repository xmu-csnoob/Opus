package cn.edu.xmu.wwf.opus.imageservice.dao;

import cn.edu.xmu.wwf.opus.imageservice.mapper.ImageMapper;
import cn.edu.xmu.wwf.opus.imageservice.model.po.ImagePo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImagePostVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.imageservice.utils.RocketMQUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao {
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    RocketMQUtil<ImageRetVo> rocketMQUtil;
    public void asyncAddImageToDB(ImageRetVo imageRetVo){
        rocketMQUtil.sendMessage("insert-image-topic", JSON.toJSONString(imageRetVo));
    }
    public int addImageToDB(ImageRetVo imageRetVo){
        ImagePo imagePo=new ImagePo();
        imagePo.setName(imageRetVo.getName());
        imagePo.setUrl(imageRetVo.getUrl());
        return imageMapper.insertPo(imagePo);
    }
}
