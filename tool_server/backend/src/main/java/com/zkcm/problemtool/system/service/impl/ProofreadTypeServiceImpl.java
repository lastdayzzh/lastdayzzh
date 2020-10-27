package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.ProofreadTypeMapper;
import com.zkcm.problemtool.system.domain.ProofreadType;
import com.zkcm.problemtool.system.service.IProofreadTypeService;
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
@Service("proofreadTypeServiceI")
public class ProofreadTypeServiceImpl  extends ServiceImpl<ProofreadTypeMapper, ProofreadType> implements IProofreadTypeService {
    @Override
    public IPage<ProofreadType> queryProofreadTypes(QueryRequest request, ProofreadType proofreadType) {
        QueryWrapper<ProofreadType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(proofreadType.getName())) {
            queryWrapper.lambda().like(ProofreadType::getName, proofreadType.getName());
        }
        if (StringUtils.isNotBlank(proofreadType.getCreateTimeFrom()) && StringUtils.isNotBlank(proofreadType.getCreateTimeTo())) {
            queryWrapper.lambda().between(ProofreadType::getCreateTime, proofreadType.getCreateTimeFrom(), proofreadType.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(proofreadType.getModifyTimeFrom()) && StringUtils.isNotBlank(proofreadType.getModifyTimeTo())) {
            queryWrapper.lambda().between(ProofreadType::getModifyTime, proofreadType.getModifyTimeFrom(), proofreadType.getModifyTimeTo());
        }
        Page<ProofreadType> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public ProofreadType queryProofreadTypeById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createProofreadType(ProofreadType proofreadType) {
        this.save(proofreadType);
    }

    @Override
    @Transactional
    public void updateProofreadType(ProofreadType proofreadType) {
        this.baseMapper.updateById(proofreadType);
    }

    @Override
    @Transactional
    public void deleteProofreadTypes(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<ProofreadType> queryList() {
        QueryWrapper<ProofreadType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProofreadType::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
