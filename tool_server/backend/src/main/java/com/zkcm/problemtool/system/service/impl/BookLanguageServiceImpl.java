package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.BookLanguageMapper;
import com.zkcm.problemtool.system.domain.BookLanguage;
import com.zkcm.problemtool.system.service.IBookLanguageService;
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
@Service("bookLanguageService")
public class BookLanguageServiceImpl  extends ServiceImpl<BookLanguageMapper, BookLanguage> implements IBookLanguageService {
    @Override
    public IPage<BookLanguage> queryBookLanguages(QueryRequest request, BookLanguage bookLanguage) {
        QueryWrapper<BookLanguage> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(bookLanguage.getName())) {
            queryWrapper.lambda().like(BookLanguage::getName, bookLanguage.getName());
        }
        if (StringUtils.isNotBlank(bookLanguage.getCreateTimeFrom()) && StringUtils.isNotBlank(bookLanguage.getCreateTimeTo())) {
            queryWrapper.lambda().between(BookLanguage::getCreateTime, bookLanguage.getCreateTimeFrom(), bookLanguage.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(bookLanguage.getModifyTimeFrom()) && StringUtils.isNotBlank(bookLanguage.getModifyTimeTo())) {
            queryWrapper.lambda().between(BookLanguage::getModifyTime, bookLanguage.getModifyTimeFrom(), bookLanguage.getModifyTimeTo());
        }
        Page<BookLanguage> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public BookLanguage queryBookLanguageById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createBookLanguage(BookLanguage bookLanguage) {
        this.save(bookLanguage);
    }

    @Override
    @Transactional
    public void updateBookLanguage(BookLanguage bookLanguage) {
        this.baseMapper.updateById(bookLanguage);
    }

    @Override
    @Transactional
    public void deleteBookLanguages(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<BookLanguage> queryList() {
        QueryWrapper<BookLanguage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BookLanguage::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
