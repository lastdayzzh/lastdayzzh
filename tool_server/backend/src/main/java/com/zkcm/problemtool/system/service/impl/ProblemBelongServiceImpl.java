package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.ProblemBelongMapper;
import com.zkcm.problemtool.system.domain.ProblemBelong;
import com.zkcm.problemtool.system.domain.vo.ProblemBelongVo;
import com.zkcm.problemtool.system.service.IProblemBelongService;
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
@Service("problemBelongService")
public class ProblemBelongServiceImpl  extends ServiceImpl<ProblemBelongMapper, ProblemBelong> implements IProblemBelongService {
    @Override
    public IPage<ProblemBelong> queryProblemBelongs(QueryRequest request, ProblemBelongVo problemBelongVo) {
        QueryWrapper<ProblemBelong> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(problemBelongVo.getName())) {
            queryWrapper.lambda().like(ProblemBelong::getName, problemBelongVo.getName());
        }
        if (StringUtils.isNotBlank(problemBelongVo.getCreateTimeFrom()) && StringUtils.isNotBlank(problemBelongVo.getCreateTimeTo())) {
            queryWrapper.lambda().between(ProblemBelong::getCreateTime, problemBelongVo.getCreateTimeFrom(), problemBelongVo.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(problemBelongVo.getModifyTimeFrom()) && StringUtils.isNotBlank(problemBelongVo.getModifyTimeTo())) {
            queryWrapper.lambda().between(ProblemBelong::getModifyTime, problemBelongVo.getModifyTimeFrom(), problemBelongVo.getModifyTimeTo());
        }
        Page<ProblemBelong> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public ProblemBelong queryProblemBelongById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createProblemBelong(ProblemBelong problemBelong) {
        this.save(problemBelong);
    }

    @Override
    @Transactional
    public void updateProblemBelong(ProblemBelong problemBelong) {
        this.baseMapper.updateById(problemBelong);
    }

    @Override
    @Transactional
    public void deleteProblemBelongs(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<ProblemBelong> queryList() {
        QueryWrapper<ProblemBelong> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProblemBelong::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
