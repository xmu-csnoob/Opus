package cn.edu.xmu.wwf.opus.imageservice.service;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.imageservice.dao.ImageDao;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImagePostVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.imageservice.utils.CosUtils;
import cn.edu.xmu.wwf.opus.imageservice.utils.RocketMQUtil;
import cn.edu.xmu.wwf.opus.imageservice.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
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
        MultipartFile file=imagePostVo.getMultipartFile();
        String category=imagePostVo.getCategory();
        InputStream in = file.getInputStream();
        String filename = file.getOriginalFilename();
        String key = category + "/" + TextUtils.generateFileName(filename);
        String imageUrl = cosUtils.uploadImage(in, key);
        if (imageUrl != null) {
            ImageRetVo imageRetVo=new ImageRetVo(filename,imageUrl, LocalDateTime.now());
            imageDao.asyncAddImageToDB(imageRetVo);
            return new ReturnObject<>(imageRetVo);
        }
        return new ReturnObject<ImageRetVo>(ReturnNo.INTERNAL_SERVER_ERROR,"服务器内部错误");
    }
}
