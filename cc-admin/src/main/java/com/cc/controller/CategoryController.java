package com.cc.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.cc.enums.AppHttpCodeEnum;
import com.cc.pojo.dto.CategoryDto;
import com.cc.pojo.entity.Category;
import com.cc.pojo.vo.CategoryVo;
import com.cc.pojo.vo.ExcelCategoryVo;
import com.cc.result.ResponseResult;
import com.cc.service.CategoryService;
import com.cc.utils.BeanCopyUtils;
import com.cc.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: CCBlog
 * @ClassName CategoryController
 * @author: c9noo
 * @create: 2023-10-24 15:23
 * @Version 1.0
 **/
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,Integer status){

        return ResponseResult.okResult(categoryService.listCategory(pageNum,pageSize,name,status));
    }

    @PostMapping
    public ResponseResult add(@RequestBody CategoryDto categoryDto){
        categoryService.save(BeanCopyUtils.copyBean(categoryDto, Category.class));
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable Long id){
        return ResponseResult.okResult(BeanCopyUtils.copyBean(categoryService.getById(id), CategoryVo.class));
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        categoryService.saveOrUpdate(BeanCopyUtils.copyBean(categoryDto, Category.class));
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }


}
