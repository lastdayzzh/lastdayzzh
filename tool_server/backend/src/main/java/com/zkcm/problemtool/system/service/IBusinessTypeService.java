package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.BusinessType;
import com.zkcm.problemtool.system.domain.vo.BusinessTypeVo;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IBusinessTypeService extends IService<BusinessType> {
    //根据条件分页查询信息
    IPage<BusinessType> queryBusinessTypes(QueryRequest request, BusinessTypeVo businessTypeVo);
    //根据id查询信息
    BusinessType queryBusinessTypeById(Integer id);
    //创建信息
    void createBusinessType(BusinessType businessType);
    //修改信息
    void updateBusinessType(BusinessType businessType);
    //删除信息
    void deleteBusinessTypes(String[] bookIds);

    List<BusinessType> queryList();

}
