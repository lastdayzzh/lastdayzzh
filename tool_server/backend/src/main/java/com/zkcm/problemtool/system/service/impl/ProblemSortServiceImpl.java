package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.ProblemSortMapper;
import com.zkcm.problemtool.system.domain.ProblemSort;
import com.zkcm.problemtool.system.service.IProblemSortService;
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
@Service("problemSortService")
public class ProblemSortServiceImpl  extends ServiceImpl<ProblemSortMapper, ProblemSort> implements IProblemSortService {
    @Override
    public IPage<ProblemSort> queryProblemSorts(QueryRequest request, ProblemSort problemSort) {
        QueryWrapper<ProblemSort> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(problemSort.getName())) {
            queryWrapper.lambda().like(ProblemSort::getName, problemSort.getName());
        }
        if (StringUtils.isNotBlank(problemSort.getCreateTimeFrom()) && StringUtils.isNotBlank(problemSort.getCreateTimeTo())) {
            queryWrapper.lambda().between(ProblemSort::getCreateTime, problemSort.getCreateTimeFrom(), problemSort.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(problemSort.getModifyTimeFrom()) && StringUtils.isNotBlank(problemSort.getModifyTimeTo())) {
            queryWrapper.lambda().between(ProblemSort::getModifyTime, problemSort.getModifyTimeFrom(), problemSort.getModifyTimeTo());
        }
        Page<ProblemSort> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public ProblemSort queryProblemSortById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createProblemSort(ProblemSort problemSort) {
        this.save(problemSort);
    }

    @Override
    @Transactional
    public void updateProblemSort(ProblemSort problemSort) {
        this.baseMapper.updateById(problemSort);
    }

    @Override
    @Transactional
    public void deleteProblemSorts(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<ProblemSort> queryList() {
        QueryWrapper<ProblemSort> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProblemSort::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
