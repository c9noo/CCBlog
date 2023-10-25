package com.cc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.entity.Link;
import com.cc.pojo.vo.PageVo;
import com.cc.result.ResponseResult;

/**
* @author 49751
* @description 针对表【cc_link(友链)】的数据库操作Service
* @createDate 2023-10-17 09:21:08
*/
public interface LinkService extends IService<Link> {

    /**
     * 查询所有友链
     * @return
     */
    ResponseResult getAllLink();

    PageVo pageList(Integer pageNum, Integer pageSize, String name, Integer status);
}
