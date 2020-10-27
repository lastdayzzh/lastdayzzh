package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.BookTypeMapper;
import com.zkcm.problemtool.system.domain.BookType;
import com.zkcm.problemtool.system.service.IBookTypeService;
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
@Service("bookTypeService")
public class BookTypeServiceImpl  extends ServiceImpl<BookTypeMapper, BookType> implements IBookTypeService {
    @Override
    public IPage<BookType> queryBookTypes(QueryRequest request, BookType bookType) {
        QueryWrapper<BookType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(bookType.getName())) {
            queryWrapper.lambda().like(BookType::getName, bookType.getName());
        }
        if (StringUtils.isNotBlank(bookType.getCreateTimeFrom()) && StringUtils.isNotBlank(bookType.getCreateTimeTo())) {
            queryWrapper.lambda().between(BookType::getCreateTime, bookType.getCreateTimeFrom(), bookType.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(bookType.getModifyTimeFrom()) && StringUtils.isNotBlank(bookType.getModifyTimeTo())) {
            queryWrapper.lambda().between(BookType::getModifyTime, bookType.getModifyTimeFrom(), bookType.getModifyTimeTo());
        }
        Page<BookType> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public BookType queryBookTypeById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createBookType(BookType bookType) {
        this.save(bookType);
    }

    @Override
    @Transactional
    public void updateBookType(BookType bookType) {
        this.baseMapper.updateById(bookType);
    }

    @Override
    @Transactional
    public void deleteBookTypes(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<BookType> queryList() {
        QueryWrapper<BookType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BookType::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
