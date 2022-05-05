package cn.edu.xmu.wwf.opus.categoryservice.controller;

import cn.edu.xmu.wwf.opus.categoryservice.model.vo.PostCategoryContainVo;
import cn.edu.xmu.wwf.opus.categoryservice.model.vo.PostCategoryVo;
import cn.edu.xmu.wwf.opus.categoryservice.service.CategoryService;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("类别模块")
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @ApiOperation("新增类别")
    @PostMapping("")
    public ReturnObject addCategory(@Validated @RequestBody PostCategoryVo postCategoryVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ReturnObject(ReturnNo.DATA_FORMAT_INVALID,"数据格式错误");
        }
        return categoryService.addCategory(postCategoryVo);
    }
    @ApiOperation("删除类别")
    @DeleteMapping("/{id}")
    public ReturnObject delCategory(@PathVariable int id){
        return categoryService.removeCategory(id);
    }
    @ApiOperation("作品划分到类别")
    @PostMapping("/{id}/artworks/{artworkId}")
    public ReturnObject addArtworkIntoCategory(@PathVariable int id,@PathVariable int artworkId){
       return categoryService.addArtworkIntoCategory(new PostCategoryContainVo(artworkId,id));
    }
    @ApiOperation("从类别中删除作品")
    @DeleteMapping("/{id}/artworks/{artworkId}")
    public ReturnObject removeArtworkFromCategory(@PathVariable int id,@PathVariable int artworkId){
        return categoryService.removeArtworkFromCategory(id,artworkId);
    }
    @ApiOperation("根据ArtworkId查询一个Category")
    @GetMapping("/artwork/{artworkId}")
    public ReturnObject getCategoryByArtworkId(@PathVariable int artworkId){
        return categoryService.getCategoryInfoAboutArtwork(artworkId);
    }
    @ApiOperation("获取所有Category")
    @GetMapping("")
    public ReturnObject getAllCategory(){
        return categoryService.getAllCategory();
    }
}
