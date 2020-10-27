package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.ShareType;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IShareTypeService extends IService<ShareType> {
    //根据条件分页查询信息
    IPage<ShareType> queryShareTypes(QueryRequest request, ShareType shareType);
    //根据id查询信息
    ShareType queryShareTypeById(Integer id);
    //创建信息
    void createShareType(ShareType shareType);
    //修改信息
    void updateShareType(ShareType shareType);
    //删除信息
    void deleteShareTypes(String[] bookIds);

    List<ShareType> queryList();
}
