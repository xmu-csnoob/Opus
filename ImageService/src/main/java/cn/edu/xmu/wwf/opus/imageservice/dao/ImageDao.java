package cn.edu.xmu.wwf.opus.imageservice.dao;

import cn.edu.xmu.wwf.opus.imageservice.mapper.ImageMapper;
import cn.edu.xmu.wwf.opus.imageservice.model.po.ImagePo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.imageservice.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao {
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    RedisUtils<String,ImagePo> redisUtils;
    static final String IMAGE_KEY_PREFIX="image-";
    public ImagePo addImageToDB(ImageRetVo imageRetVo){
        ImagePo imagePo=new ImagePo();
        BeanUtils.copyProperties(imageRetVo,imagePo);
        imageMapper.insertPo(imagePo);
        return imagePo;
    }
    public ImagePo getImagePoFromDB(int id){
        return imageMapper.selectById(id);
    }
}
