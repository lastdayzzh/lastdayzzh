package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.Discoverer;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IDiscovererService extends IService<Discoverer> {
    //根据条件分页查询信息
    IPage<Discoverer> queryDiscoverers(QueryRequest request, Discoverer discoverer);
    //根据id查询信息
    Discoverer queryDiscovererById(Integer id);
    //创建信息
    void createDiscoverer(Discoverer discoverer);
    //修改信息
    void updateDiscoverer(Discoverer discoverer);
    //删除信息
    void deleteDiscoverers(String[] bookIds);

    List<Discoverer> queryList();

}
