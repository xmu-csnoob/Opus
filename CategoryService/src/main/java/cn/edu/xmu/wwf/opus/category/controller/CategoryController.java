package cn.edu.xmu.wwf.opus.category.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api("类别模块")
@RequestMapping("/category")
public class CategoryController {
    @ApiOperation("新增类别")
    @PostMapping("")
    public void addCategory(){

    }
    @ApiOperation("删除类别")
    @DeleteMapping("")
    public void delCategory(){

    }
}
