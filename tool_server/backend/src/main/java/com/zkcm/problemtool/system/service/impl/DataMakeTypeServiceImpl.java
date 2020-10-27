package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.DataMakeTypeMapper;
import com.zkcm.problemtool.system.domain.DataMakeType;
import com.zkcm.problemtool.system.service.IDataMakeTypeService;
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
@Service("dataMakeTypeService")
public class DataMakeTypeServiceImpl extends ServiceImpl<DataMakeTypeMapper, DataMakeType> implements IDataMakeTypeService {
    @Override
    public IPage<DataMakeType> queryDataMakeTypes(QueryRequest request, DataMakeType dataMakeType) {
        QueryWrapper<DataMakeType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dataMakeType.getName())) {
            queryWrapper.lambda().like(DataMakeType::getName, dataMakeType.getName());
        }
        if (StringUtils.isNotBlank(dataMakeType.getCreateTimeFrom()) && StringUtils.isNotBlank(dataMakeType.getCreateTimeTo())) {
            queryWrapper.lambda().between(DataMakeType::getCreateTime, dataMakeType.getCreateTimeFrom(), dataMakeType.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(dataMakeType.getModifyTimeFrom()) && StringUtils.isNotBlank(dataMakeType.getModifyTimeTo())) {
            queryWrapper.lambda().between(DataMakeType::getModifyTime, dataMakeType.getModifyTimeFrom(), dataMakeType.getModifyTimeTo());
        }
        Page<DataMakeType> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public DataMakeType queryDataMakeTypeById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createDataMakeType(DataMakeType dataMakeType) {
        this.save(dataMakeType);
    }

    @Override
    @Transactional
    public void updateDataMakeType(DataMakeType dataMakeType) {
        this.baseMapper.updateById(dataMakeType);
    }

    @Override
    @Transactional
    public void deleteDataMakeTypes(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<DataMakeType> queryList() {
        QueryWrapper<DataMakeType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataMakeType::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
