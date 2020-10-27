package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.DictionaryDetailMapper;
import com.zkcm.problemtool.system.domain.DictionaryDetail;
import com.zkcm.problemtool.system.domain.vo.DictionaryDetailVo;
import com.zkcm.problemtool.system.service.IDictionaryDetailService;
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
@Service("dictionaryDetailService")
public class DictionaryDetailServiceImpl extends ServiceImpl<DictionaryDetailMapper, DictionaryDetail> implements IDictionaryDetailService {
    @Override
    public IPage<DictionaryDetail> queryDictionaryDetails(QueryRequest request, DictionaryDetailVo dictionaryDetailVo) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dictionaryDetailVo.getName())) {
            queryWrapper.lambda().like(DictionaryDetail::getDictionaryValue, dictionaryDetailVo.getName());
        }
        if (StringUtils.isNotBlank(dictionaryDetailVo.getCreateTimeFrom()) && StringUtils.isNotBlank(dictionaryDetailVo.getCreateTimeTo())) {
            queryWrapper.lambda().between(DictionaryDetail::getCreateTime, dictionaryDetailVo.getCreateTimeFrom(), dictionaryDetailVo.getCreateTimeTo());
        }
        Page<DictionaryDetail> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }


    @Override
    @Transactional
    public void createDictionaryDetail(DictionaryDetail dictionaryDetail) {
        this.save(dictionaryDetail);
    }

    @Override
    @Transactional
    public void updateDictionaryDetail(DictionaryDetail dictionaryDetail) {
        this.baseMapper.updateById(dictionaryDetail);
    }

    @Override
    @Transactional
    public void deleteDictionaryDetails(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<DictionaryDetail> queryList(String code) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(code)) {
            queryWrapper.lambda().eq(DictionaryDetail::getCode, code);
        }
        return null;
    }

    @Override
    public List<DictionaryDetail> queryByCode(String code) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(code)) {
            queryWrapper.lambda().eq(DictionaryDetail::getCode, code);
        }
        return this.baseMapper.selectList(queryWrapper);
    }

}
