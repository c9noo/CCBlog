package com.cc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.mapper.MenuMapper;
import com.cc.pojo.entity.Menu;
import com.cc.pojo.vo.MenuListVo;
import com.cc.pojo.vo.MenuVo;
import com.cc.pojo.vo.RoutersVo;
import com.cc.result.ResponseResult;
import com.cc.service.MenuService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @program: CCBlog
 * @ClassName MenuController
 * @author: c9noo
 * @create: 2023-10-25 09:58
 * @Version 1.0
 **/
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return ResponseResult.okResult(menuService.treeSelect());
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable Long id){
        return ResponseResult.okResult(menuService.roleMenuTreeselect(id));
    }

    @GetMapping("/list")
    public ResponseResult list(Integer status,String menuName){
        return ResponseResult.okResult(
                BeanCopyUtils.copyBeanList(menuService.list(new LambdaQueryWrapper<Menu>()
                        .eq(Objects.nonNull(status), Menu::getStatus, status)
                        .eq(Objects.nonNull(menuName) && !menuName.isEmpty(), Menu::getMenuName, menuName)
                ), MenuListVo.class)
        );
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu){
        menuService.save(menu);
        return null;
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable Long id){
        Menu menu = menuService.getById(id);
        return ResponseResult.okResult(menu);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Long id){
        List<Menu> list = menuService.list(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getParentId, id)
        );

        if (Objects.nonNull(list)&&list.size()>0){
            throw new RuntimeException("存在子菜单，无法删除");
        }
        menuService.removeById(id);
        return ResponseResult.okResult();
    }
}
