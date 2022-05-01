package cn.edu.xmu.wwf.opus.categoryservice.mapper;

import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryContainPo;
import cn.edu.xmu.wwf.opus.categoryservice.model.po.CategoryPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    int insertCategoryPo(CategoryPo categoryPo);
    int delCategoryPo(int id);
    int insertCategoryContainPo(CategoryContainPo categoryContainPo);
    int delCategoryContainPo(int categoryId,int artworkId);
    CategoryPo selectCategoryPo(int id);
    CategoryContainPo selectCategoryContainPo(int categoryId,int artworkId);
}
