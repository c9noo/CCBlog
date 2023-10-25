package com.cc.service;

import com.cc.pojo.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.vo.CategoryVo;
import com.cc.pojo.vo.PageVo;
import com.cc.result.ResponseResult;

import java.util.List;

/**
* @author 49751
* @description 针对表【cc_category(分类表)】的数据库操作Service
* @createDate 2023-10-16 16:12:30
*/
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类列表
     * @return
     */
    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    PageVo listCategory(Integer pageNum, Integer pageSize, String name, Integer status);
}
