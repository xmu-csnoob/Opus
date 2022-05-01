package cn.edu.xmu.wwf.opus.imageservice.mapper;

import cn.edu.xmu.wwf.opus.imageservice.model.po.ImagePo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper{
    int insertPo(ImagePo imagePo);
    ImagePo selectById(int id);
}
