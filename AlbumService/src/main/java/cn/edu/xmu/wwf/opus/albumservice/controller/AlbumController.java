package cn.edu.xmu.wwf.opus.albumservice.controller;

import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumContainPostVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumPostVo;
import cn.edu.xmu.wwf.opus.albumservice.model.vo.AlbumPutVo;
import cn.edu.xmu.wwf.opus.albumservice.service.AlbumService;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("专辑模块")
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @ApiOperation("新建专辑")
    @PostMapping("")
    public ReturnObject addAlbum(@Validated @RequestBody AlbumPostVo albumPostVo, BindingResult result){
        if(result.hasErrors()){
            return new ReturnObject<>(ReturnNo.DATA_FORMAT_INVALID,"数据格式错误");
        }
        return albumService.addAlbumIntoAlbum(albumPostVo);
    }
    @ApiOperation("根据专辑id获取一个Album")
    @GetMapping("/{id}")
    public ReturnObject getAlbum(@PathVariable int id){
        return albumService.getAlbumInfo(id);
    }
    @ApiOperation("修改专辑相关信息")
    @PutMapping("/{id}")
    public ReturnObject alterAlbumInfo(@PathVariable int id,@Validated @RequestBody AlbumPutVo albumPutVo,BindingResult result){
        if(result.hasErrors()){
            return new ReturnObject<>(ReturnNo.DATA_FORMAT_INVALID,"数据格式错误");
        }
        albumPutVo.setId(id);
        return albumService.updateAlbumInfo(albumPutVo);
    }
    @ApiOperation("逻辑删除专辑")
    @DeleteMapping("/{id}")
    public ReturnObject delAlbum(@PathVariable int id){
        AlbumPutVo albumPutVo=new AlbumPutVo();
        albumPutVo.setId(id);
        albumPutVo.setDeleted(true);
        return albumService.updateAlbumInfo(albumPutVo);
    }
    @ApiOperation("向专辑中增加作品")
    @PostMapping("/{id}/artwork/{artworkId}")
    public ReturnObject putArtworkIntoAlbum(@PathVariable int id,@PathVariable int artworkId){
        AlbumContainPostVo albumContainPostVo=new AlbumContainPostVo();
        albumContainPostVo.setAlbumId(id);
        albumContainPostVo.setArtworkId(artworkId);
        return albumService.addArtworkIntoAlbum(albumContainPostVo);
    }
    @ApiOperation("从专辑中删除作品")
    @DeleteMapping("/{id}/artwork/{artworkId}")
    public ReturnObject delArtworkFromAlbum(@PathVariable int id,@PathVariable int artworkId){
        return albumService.removeArtworkFromAlbum(id,artworkId);
    }

}
