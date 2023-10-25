package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.constants.SystemConstants;
import com.cc.pojo.entity.Link;
import com.cc.pojo.vo.LinkVo;
import com.cc.pojo.vo.PageVo;
import com.cc.result.ResponseResult;
import com.cc.service.LinkService;
import com.cc.mapper.LinkMapper;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author 49751
* @description 针对表【cc_link(友链)】的数据库操作Service实现
* @createDate 2023-10-17 09:21:08
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Autowired
    private LinkMapper linkMapper;

    /**
     * 查询所有友链
     * @return
     */
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_REVIEW_PASS);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(list(lambdaQueryWrapper), LinkVo.class));
    }

    @Override
    public PageVo pageList(Integer pageNum, Integer pageSize, String name, Integer status) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(status),Link::getStatus,status)
                .eq(Objects.nonNull(name)&& !name.isEmpty(),Link::getName,name);
        Page<Link> page = new Page<>(pageNum,pageSize);
        linkMapper.selectPage(page,wrapper);
        return new PageVo(page.getRecords(),page.getTotal());
    }


}




