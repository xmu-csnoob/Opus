package cn.edu.xmu.wwf.opus.categoryservice.service;

import cn.edu.xmu.wwf.opus.categoryservice.dao.CategoryDao;
import cn.edu.xmu.wwf.opus.categoryservice.microservice.ArtworkService;
import cn.edu.xmu.wwf.opus.categoryservice.microservice.model.GetArtWorkRetVo;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryContainPo;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryPo;
import cn.edu.xmu.wwf.opus.categoryservice.model.vo.PostCategoryContainVo;
import cn.edu.xmu.wwf.opus.categoryservice.model.vo.PostCategoryVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ArtworkService artworkService;

    public ReturnObject addCategory(PostCategoryVo postCategoryVo) {
        CategoryPo categoryPo = new CategoryPo();
        BeanUtils.copyProperties(postCategoryVo, categoryPo);
        return new ReturnObject(categoryDao.addCategoryIntoDB(categoryPo));
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
}
