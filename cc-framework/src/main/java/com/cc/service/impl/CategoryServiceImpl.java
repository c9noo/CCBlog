package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.constants.SystemConstants;
import com.cc.mapper.ArticleMapper;
import com.cc.pojo.entity.Article;
import com.cc.pojo.entity.Category;
import com.cc.pojo.vo.CategoryVo;
import com.cc.pojo.vo.PageVo;
import com.cc.result.ResponseResult;
import com.cc.service.CategoryService;
import com.cc.mapper.CategoryMapper;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 49751
* @description 针对表【cc_category(分类表)】的数据库操作Service实现
* @createDate 2023-10-16 16:12:30
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询分类列表（如果分类中没有文章是不显示的）
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {;

        //1. 查询出已经发布的文章
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleMapper.selectList(lambdaQueryWrapper);

        //2. 将已经发布的文章进行去重，提取出分类id,使用分类id，去查询分类表中对应的数据
        List<Category> categoryList = listByIds(
                articleList.stream()
                .map(Article::getCategoryId)
                .distinct()
                .collect(Collectors.toList())
        );

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class));
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public PageVo listCategory(Integer pageNum, Integer pageSize, String name, Integer status) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(status),Category::getStatus,status);
        wrapper.eq(Objects.nonNull(name) && !name.isEmpty(),Category::getName,name);

        Page<Category> page = new Page<>(pageNum,pageSize);
        categoryMapper.selectPage(page,wrapper);
        List<Category> records = page.getRecords();

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(records, CategoryVo.class);
        return new PageVo(categoryVos, page.getTotal());
    }


}




