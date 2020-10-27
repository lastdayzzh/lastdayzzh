package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.DataMakeType;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IDataMakeTypeService extends IService<DataMakeType> {
    //根据条件分页查询信息
    IPage<DataMakeType> queryDataMakeTypes(QueryRequest request, DataMakeType dataMakeType);
    //根据id查询信息
    DataMakeType queryDataMakeTypeById(Integer id);
    //创建信息
    void createDataMakeType(DataMakeType dataMakeType);
    //修改信息
    void updateDataMakeType(DataMakeType dataMakeType);
    //删除信息
    void deleteDataMakeTypes(String[] bookIds);

    List<DataMakeType> queryList();
}
