package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.BusinessTypeMapper;
import com.zkcm.problemtool.system.domain.BusinessType;
import com.zkcm.problemtool.system.domain.vo.BusinessTypeVo;
import com.zkcm.problemtool.system.service.IBusinessTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:33
 */
@Slf4j
@Service("businessTypeService")
public class BusinessTypeServiceImpl extends ServiceImpl<BusinessTypeMapper, BusinessType> implements IBusinessTypeService {
    @Override
    public IPage<BusinessType> queryBusinessTypes(QueryRequest request, BusinessTypeVo businessTypeVo) {
        QueryWrapper<BusinessType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(businessTypeVo.getName())) {
            queryWrapper.lambda().like(BusinessType::getName, businessTypeVo.getName());
        }
        if (StringUtils.isNotBlank(businessTypeVo.getCreateTimeFrom()) && StringUtils.isNotBlank(businessTypeVo.getCreateTimeTo())) {
            queryWrapper.lambda().between(BusinessType::getCreateTime, businessTypeVo.getCreateTimeFrom(), businessTypeVo.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(businessTypeVo.getModifyTimeFrom()) && StringUtils.isNotBlank(businessTypeVo.getModifyTimeTo())) {
            queryWrapper.lambda().between(BusinessType::getModifyTime, businessTypeVo.getModifyTimeFrom(), businessTypeVo.getModifyTimeTo());
        }
        Page<BusinessType> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public BusinessType queryBusinessTypeById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createBusinessType(BusinessType businessType) {
        this.save(businessType);
    }

    @Override
    @Transactional
    public void updateBusinessType(BusinessType businessType) {
        this.baseMapper.updateById(businessType);
    }

    @Override
    @Transactional
    public void deleteBusinessTypes(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<BusinessType> queryList() {
        QueryWrapper<BusinessType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusinessType::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
