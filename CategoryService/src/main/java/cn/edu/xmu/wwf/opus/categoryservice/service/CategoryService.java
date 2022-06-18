package cn.edu.xmu.wwf.opus.categoryservice.service;

import cn.edu.xmu.wwf.opus.categoryservice.dao.CategoryDao;
import cn.edu.xmu.wwf.opus.categoryservice.microservice.ImageService;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryPo;
import cn.edu.xmu.wwf.opus.categoryservice.model.vo.GetCategoryVo;
import cn.edu.xmu.wwf.opus.categoryservice.model.vo.PostCategoryVo;
import cn.edu.xmu.wwf.opus.categoryservice.microservice.ArtworkService;
import cn.edu.xmu.wwf.opus.categoryservice.microservice.model.GetArtWorkRetVo;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryContainPo;
import cn.edu.xmu.wwf.opus.categoryservice.model.vo.PostCategoryContainVo;
import cn.edu.xmu.wwf.opus.categoryservice.util.PageConfigUtil;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ArtworkService artworkService;
    @Autowired
    ImageService imageService;
    public ReturnObject addCategory(PostCategoryVo postCategoryVo) {
        CategoryPo categoryPo = new CategoryPo();
        BeanUtils.copyProperties(postCategoryVo, categoryPo);
        return new ReturnObject(categoryDao.addCategoryIntoDB(categoryPo));
    }

    public ReturnObject getAllCategory(){
        List<CategoryPo> categoryPos=categoryDao.selectAllCategoryFromDB();
        List<GetCategoryVo> getCategoryVos=new ArrayList<>();
        for(CategoryPo categoryPo:categoryPos){
            GetCategoryVo getCategoryVo=new GetCategoryVo();
            BeanUtils.copyProperties(categoryPo,getCategoryVo);
            getCategoryVos.add(getCategoryVo);
        }
        return new ReturnObject(getCategoryVos);
    }
    public ReturnObject removeCategory(int id) {
        CategoryPo categoryPo = categoryDao.selectCategoryFromDB(id);
        if (categoryPo == null) {
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND, "类别id不存在");
        }
        return new ReturnObject(categoryDao.delCategoryFromDB(id));
    }

    public ReturnObject addArtworkIntoCategory(PostCategoryContainVo postCategoryContainVo) {
        ReturnObject<GetArtWorkRetVo> returnObject = artworkService.getArtwork(postCategoryContainVo.getArtworkId());
        System.out.println(returnObject);
        if (returnObject.returnNo == ReturnNo.RESOURCE_NOT_FOUND) {
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND, "作品id不存在");
        }
        CategoryPo categoryPo = categoryDao.selectCategoryFromDB(postCategoryContainVo.getCategoryId());
        if (categoryPo == null) {
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND, "类别id不存在");
        }
        CategoryContainPo categoryContainPo = new CategoryContainPo();
        BeanUtils.copyProperties(postCategoryContainVo, categoryContainPo);
        return new ReturnObject(categoryDao.insertCategoryContainIntoDB(categoryContainPo));
    }

    public ReturnObject removeArtworkFromCategory(int categoryId, int artworkId) {
        ReturnObject<GetArtWorkRetVo> returnObject = artworkService.getArtwork(artworkId);
        if (returnObject.returnNo == ReturnNo.RESOURCE_NOT_FOUND) {
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND, "作品id不存在");
        }
        CategoryPo categoryPo = categoryDao.selectCategoryFromDB(categoryId);
        if (categoryPo == null) {
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND, "类别id不存在");
        }
        CategoryContainPo categoryContainPo=categoryDao.selectCategoryContainFromDB(categoryId,artworkId);
        if(categoryContainPo==null){
            return new ReturnObject(ReturnNo.STATE_NOT_ALLOWED,"该作品不在该类别中");
        }
        return new ReturnObject(categoryDao.delCategotyContainFromDB(categoryId, artworkId));
    }
    public ReturnObject getCategoryInfoAboutArtwork(int artworkId){
        List<CategoryContainPo> categoryContainPoList=categoryDao.selectCategoryContainsByArtworkIdFromDB(artworkId);
        if(categoryContainPoList.isEmpty()){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"该作品不属于任何一个类别");
        }
        List<GetCategoryVo> getCategoryVos=new ArrayList<>();
        for(CategoryContainPo categoryContainPo:categoryContainPoList){
            CategoryPo categoryPo=categoryDao.selectCategoryFromDB(categoryContainPo.getCategoryId());
            GetCategoryVo getCategoryVo=new GetCategoryVo();
            BeanUtils.copyProperties(categoryPo,getCategoryVo);
            getCategoryVos.add(getCategoryVo);
        }
        return new ReturnObject(getCategoryVos);
    }
    public ReturnObject getCategoryInfo(int id){
        CategoryPo categoryPo=categoryDao.selectCategoryFromDB(id);
        GetCategoryVo getCategoryVo=new GetCategoryVo();
        BeanUtils.copyProperties(categoryPo,getCategoryVo);
        getCategoryVo.setImageUrl(imageService.getImageInfo(categoryPo.getImageId()).data.getUrl());
        return new ReturnObject(getCategoryVo);
    }
    public ReturnObject getArtworksByCategoryId(int categoryId, PageConfigUtil pageConfigUtil){
        List<CategoryContainPo> categoryContainPoList=categoryDao.selectCategoryContainsByCategoryIdFromDB(categoryId,pageConfigUtil).getList();
        List<GetArtWorkRetVo> getArtWorkRetVos=new ArrayList<>();
        for(CategoryContainPo categoryContainPo:categoryContainPoList){
            ReturnObject<GetArtWorkRetVo> returnObject=artworkService.getArtwork(categoryContainPo.getArtworkId());
            if(returnObject.returnNo==ReturnNo.OK){
                System.out.println(returnObject.data);
                getArtWorkRetVos.add(returnObject.data);
            }
        }
        return new ReturnObject(getArtWorkRetVos);
    }
}
