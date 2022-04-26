package cn.edu.xmu.wwf.opus.artworkservice.controller;

import cn.edu.xmu.wwf.opus.artworkservice.microservice.ImgService;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import cn.edu.xmu.wwf.opus.artworkservice.service.ArtworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "作品模块")
@RestController
@RequestMapping("/artwork")
public class ArtworkController {
    @Autowired
    ArtworkService artworkService;
    @ApiOperation("提交作品")
    //, @RequestBody ArtworkPostVo artworkPostVo
    @RequestMapping("")
    public void addArtwork(@RequestPart("File") MultipartFile file,@RequestPart("Name") String name,@RequestPart("Category")String category) throws Throwable {
        int id=0;
        artworkService.addArtwork(new ArtworkPostVo(id,file,name,category,""));
    }

}
