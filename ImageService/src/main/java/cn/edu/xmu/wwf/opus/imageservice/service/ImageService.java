package cn.edu.xmu.wwf.opus.imageservice.service;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.imageservice.dao.ImageDao;
import cn.edu.xmu.wwf.opus.imageservice.model.po.ImagePo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImagePostVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageUrlRetVo;
import cn.edu.xmu.wwf.opus.imageservice.utils.CosUtils;
import cn.edu.xmu.wwf.opus.imageservice.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
public class ImageService {
    @Autowired
    CosUtils cosUtils;
    @Autowired
    ImageDao imageDao;

    public ReturnObject<ImageRetVo> addImageToCos(ImagePostVo imagePostVo) throws IOException {
        MultipartFile file = imagePostVo.getMultipartFile();
        InputStream in = file.getInputStream();
        String filename = file.getOriginalFilename();
        String key =TextUtils.generateFileName(filename);
        try {
            String imageUrl = cosUtils.uploadImage(in, key);
            ImageRetVo imageRetVo = new ImageRetVo(0, filename, imageUrl, LocalDateTime.now());
            int imageId = imageDao.addImageToDB(imageRetVo).getId();
            imageRetVo.setId(imageId);
            return new ReturnObject<>(imageRetVo);
        } catch (Exception e) {
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERROR, "上传图片时发生未知错误");
        }
    }
    public ReturnObject<ImageUrlRetVo> getUrlRetVo(int id){
        ImagePo imagePo= imageDao.getImagePoFromDB(id);
        if(imagePo==null){
            return new ReturnObject<>(ReturnNo.RESOURCE_NOT_FOUND,"该图像id不存在");
        }
        ImageUrlRetVo imageUrlRetVo=new ImageUrlRetVo();
        imageUrlRetVo.setUrl(imagePo.getUrl());
        return new ReturnObject<>(imageUrlRetVo);
    }
}
