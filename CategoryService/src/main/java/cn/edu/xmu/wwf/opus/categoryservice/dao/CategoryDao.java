package cn.edu.xmu.wwf.opus.categoryservice.dao;

import cn.edu.xmu.wwf.opus.categoryservice.mapper.CategoryMapper;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryPo;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryContainPo;
import cn.edu.xmu.wwf.opus.categoryservice.util.PageConfigUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao {
    @Autowired
    CategoryMapper categoryMapper;
    public CategoryPo addCategoryIntoDB(CategoryPo categoryPo){
        categoryMapper.insertCategoryPo(categoryPo);
        return categoryPo;
    }
    public boolean delCategoryFromDB(int id){
        return categoryMapper.delCategoryPo(id)==1;
    }
    public CategoryContainPo insertCategoryContainIntoDB(CategoryContainPo categoryContainPo){
        categoryMapper.insertCategoryContainPo(categoryContainPo);
        return categoryContainPo;
    }
    public boolean delCategotyContainFromDB(int categoryId,int artworkId){
        return categoryMapper.delCategoryContainPo(categoryId,artworkId)==1;
    }
    public CategoryPo selectCategoryFromDB(int id){
        return categoryMapper.selectCategoryPo(id);
    }
    public CategoryContainPo selectCategoryContainFromDB(int categoryId,int artworkId){
        return categoryMapper.selectCategoryContainPo(categoryId,artworkId);
    }
    public List<CategoryContainPo> selectCategoryContainsByArtworkIdFromDB(int artworkId){
        return categoryMapper.selectCategoryContainsByArtworkId(artworkId);
    }
    public PageInfo<CategoryContainPo> selectCategoryContainsByCategoryIdFromDB(int categoryId, PageConfigUtil pageConfigUtil){
        PageHelper.startPage(pageConfigUtil.getPage(),pageConfigUtil.getPageSize());
        List<CategoryContainPo> categoryPos=categoryMapper.selectCategoryContainsByCategoryId(categoryId);
        return new PageInfo<>(categoryPos);
    }
    public List<CategoryPo> selectAllCategoryFromDB(){
        return categoryMapper.selectAllCategoryPo();
    }
}
