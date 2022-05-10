package cn.edu.xmu.wwf.opus.artworkservice.controller;

import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.image.PostImageRetVo;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import cn.edu.xmu.wwf.opus.artworkservice.service.ArtworkService;
import cn.edu.xmu.wwf.opus.artworkservice.microservice.ImgService;
import cn.edu.xmu.wwf.opus.artworkservice.utils.PageConfigUtil;
import cn.edu.xmu.wwf.opus.common.utils.jwt.TokenDecodeUtil;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(tags = "作品模块")
@RestController
@CrossOrigin
@RequestMapping("/artwork")
public class ArtworkController {
    @Autowired
    ArtworkService artworkService;
    @Autowired
    ImgService imgService;
    @ApiOperation("提交作品")
    @PostMapping("")
    public ReturnObject addArtwork(@RequestHeader("token") String token,@RequestPart("File") MultipartFile file, @RequestPart("Name") String name, @RequestPart("Category") List<Integer> categoryIds, @RequestPart("Introduction")String introduction) throws Throwable {
        int id= Integer.parseInt(TokenDecodeUtil.get(token));
        if(file.isEmpty()){
            return new ReturnObject(ReturnNo.FILE_NOT_VALID,"上传文件为空");
        }
        String filename=file.getOriginalFilename();
        String postfix=filename.split("\\.")[1];
        if(!postfix.equals("png")&&!postfix.equals("jpg")&&!postfix.equals("jpeg")){
            return new ReturnObject<>(ReturnNo.FILE_NOT_VALID,"上传的文件格式有误");
        }
        ReturnObject<PostImageRetVo> returnObject;
        returnObject=imgService.uploadImage(file,"artwork");
        return artworkService.addArtwork(new ArtworkPostVo(id,returnObject.data.getId(),name,categoryIds,introduction,returnObject.data.getUrl()));
    }
    @ApiOperation("作品上架")
    @PutMapping("/{id}/on")
    public ReturnObject onShelfArtwork(@PathVariable int id){
        return artworkService.onShelfArtwork(id);
    }
    @ApiOperation("作品下架")
    @PutMapping("/{id}/off")
    public ReturnObject offShelfArtwork(@PathVariable int id){
        return artworkService.offShelfArtwork(id);
    }
    @ApiOperation("根据关键词查询一个相关的Artwork List")
    @GetMapping("")
    public ReturnObject obscureSearch(@RequestParam String word,@RequestParam int page,@RequestParam int pageSize){
        return artworkService.getPagedObscureSearchResults(word,new PageConfigUtil(page,pageSize));
    }
    @ApiOperation("根据用户id查询一个Artwork List")
    @GetMapping("/users/{id}")
    public ReturnObject getUserArtworks(@PathVariable int id,@RequestParam int page,@RequestParam int pageSize){
        return artworkService.getPagedUserArtworkList(id,new PageConfigUtil(page,pageSize));
    }
    @ApiOperation("根据ArtworkId查询一个Artwork")
    @GetMapping(value="/{id}",produces = "application/json;charset=UTF-8")
    public ReturnObject getArtwork(@PathVariable int id){
        return artworkService.getGetArtworkRetVo(id);
    }
}
