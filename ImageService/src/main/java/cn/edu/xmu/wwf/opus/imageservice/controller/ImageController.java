package cn.edu.xmu.wwf.opus.imageservice.controller;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImagePostVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.imageservice.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "图片")
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    @ApiOperation("上传图像")
    @PostMapping("/{category}")
    public ReturnObject<ImageRetVo> uploadImage(@RequestPart("file")MultipartFile file, @PathVariable String category)throws IOException {
        return imageService.addImageToCos(new ImagePostVo(file,category));
    }
    /*@GetMapping("/test")
    public Object test(){
    }*/
}
