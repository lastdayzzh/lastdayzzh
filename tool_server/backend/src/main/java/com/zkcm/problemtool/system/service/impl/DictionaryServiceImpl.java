package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.DictionaryMapper;
import com.zkcm.problemtool.system.domain.Dictionary;
import com.zkcm.problemtool.system.domain.vo.DictionaryVo;
import com.zkcm.problemtool.system.service.IDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:33
 */
@Slf4j
@Service("dictionaryService")
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {
    @Override
    public IPage<Dictionary> queryDictionarys(QueryRequest request, DictionaryVo dictionaryVo) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dictionaryVo.getName())) {
            queryWrapper.lambda().like(Dictionary::getName, dictionaryVo.getName());
        }
        if (StringUtils.isNotBlank(dictionaryVo.getCreateTimeFrom()) && StringUtils.isNotBlank(dictionaryVo.getCreateTimeTo())) {
            queryWrapper.lambda().between(Dictionary::getCreateTime, dictionaryVo.getCreateTimeFrom(), dictionaryVo.getCreateTimeTo());
        }
        Page<Dictionary> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }


    @Override
    @Transactional
    public void createDictionary(Dictionary dictionary) {
        this.save(dictionary);
    }

    @Override
    @Transactional
    public void updateDictionary(Dictionary dictionary) {
        this.baseMapper.updateById(dictionary);
    }

    @Override
    @Transactional
    public void deleteDictionarys(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public Dictionary queryByCode(String code) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(code)) {
            queryWrapper.lambda().eq(Dictionary::getCode, code);
        }
        return this.baseMapper.selectOne(queryWrapper);
    }

}
