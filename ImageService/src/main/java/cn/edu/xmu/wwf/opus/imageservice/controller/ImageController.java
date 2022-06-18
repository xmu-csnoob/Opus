package cn.edu.xmu.wwf.opus.imageservice.controller;

import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImagePostVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageUrlRetVo;
import cn.edu.xmu.wwf.opus.imageservice.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Api(tags = "图片")
@RestController
@CrossOrigin
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    @ApiOperation("上传图像")
    @PostMapping("")
    public ReturnObject<ImageRetVo> uploadImage(@RequestPart("file") MultipartFile file,@RequestParam String region)throws IOException {
        if(file.isEmpty()){
            return new ReturnObject<>(ReturnNo.FILE_NOT_VALID,"上传的文件是空文件");
        }
        String filename=region+"/"+file.getOriginalFilename();
        assert filename != null;
        String postfix=filename.split("\\.")[1];
        if(!postfix.equals("png")&&!postfix.equals("jpg")&&!postfix.equals("jpeg")){
            return new ReturnObject<>(ReturnNo.FILE_NOT_VALID,"上传的文件格式有误");
        }
        return imageService.addImageToCos(new ImagePostVo(file));
    }
    @ApiOperation("根据id查询name和url")
    @GetMapping("/{id}")
    public ReturnObject<ImageUrlRetVo> getImageInfo(@PathVariable int id) {
        return imageService.getUrlRetVo(id);
    }
}
