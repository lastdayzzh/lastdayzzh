package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.ProblemTypeMapper;
import com.zkcm.problemtool.system.domain.ProblemType;
import com.zkcm.problemtool.system.service.IProblemTypeService;
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
@Service("ProblemTypeService")
public class ProblemTypeServiceImpl extends ServiceImpl<ProblemTypeMapper, ProblemType> implements IProblemTypeService {
    @Override
    public IPage<ProblemType> queryProblemTypes(QueryRequest request, ProblemType problemType) {
        QueryWrapper<ProblemType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(problemType.getName())) {
            queryWrapper.lambda().like(ProblemType::getName, problemType.getName());
        }
        if (StringUtils.isNotBlank(problemType.getCreateTimeFrom()) && StringUtils.isNotBlank(problemType.getCreateTimeTo())) {
            queryWrapper.lambda().between(ProblemType::getCreateTime, problemType.getCreateTimeFrom(), problemType.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(problemType.getModifyTimeFrom()) && StringUtils.isNotBlank(problemType.getModifyTimeTo())) {
            queryWrapper.lambda().between(ProblemType::getModifyTime, problemType.getModifyTimeFrom(), problemType.getModifyTimeTo());
        }
        Page<ProblemType> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public ProblemType queryProblemTypeById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createProblemType(ProblemType problemType) {
        this.save(problemType);
    }

    @Override
    @Transactional
    public void updateProblemType(ProblemType problemType) {
        this.baseMapper.updateById(problemType);
    }

    @Override
    @Transactional
    public void deleteProblemTypes(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<ProblemType> queryList() {
        QueryWrapper<ProblemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProblemType::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
