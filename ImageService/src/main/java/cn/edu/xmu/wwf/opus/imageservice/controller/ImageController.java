package cn.edu.xmu.wwf.opus.imageservice.controller;

import cn.edu.xmu.wwf.opus.imageservice.utils.CosUtils;
import cn.edu.xmu.wwf.opus.imageservice.utils.TextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(tags = "图片")
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    CosUtils cosUtils;
    @ApiOperation("上传图像")
    @PostMapping("/{category}")
    public String uploadImage(@RequestParam("file")MultipartFile file,@PathVariable String category)throws IOException {
        if(!file.isEmpty())
        {
            InputStream in = file.getInputStream();
            String filename = file.getOriginalFilename();
            try
            {
                String key = category+"/"+TextUtils.generateFileName(filename);
                String imageUrl = cosUtils.uploadImage(in,key);
                if(imageUrl!=null)
                {
                    return "上传成功: "+imageUrl;
                }
            }
            catch(IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }
        return "上传失败";
    }
    @ApiOperation("删除图像")
    @DeleteMapping("")
    public void deleteImage(){}
}
