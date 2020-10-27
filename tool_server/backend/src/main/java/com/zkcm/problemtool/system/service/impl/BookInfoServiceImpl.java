package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.BookInfoMapper;
import com.zkcm.problemtool.system.domain.BookInfo;
import com.zkcm.problemtool.system.domain.vo.BookInfoVo;
import com.zkcm.problemtool.system.service.IBookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service("bookInfoService")
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements IBookInfoService{
    @Override
    public IPage<BookInfo> queryBookInfos(QueryRequest request, BookInfoVo bookInfoVo) {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(bookInfoVo.getBookInfo())){
           queryWrapper.lambda().like(BookInfo::getBookName,bookInfoVo.getBookInfo());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getStatus())){
            queryWrapper.lambda().eq(BookInfo::getStatus,bookInfoVo.getStatus());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getBookType())){
            queryWrapper.lambda().eq(BookInfo::getBookType,bookInfoVo.getBookType());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getLanguage())){
            queryWrapper.lambda().eq(BookInfo::getLanguage,bookInfoVo.getLanguage());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getEditor())){
            queryWrapper.lambda().eq(BookInfo::getEditor,bookInfoVo.getEditor());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getBranch())){
            queryWrapper.lambda().like(BookInfo::getBranch,bookInfoVo.getBranch());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getEdition())){
            queryWrapper.lambda().like(BookInfo::getEdition,bookInfoVo.getEdition());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getCreateUser())){
            queryWrapper.lambda().like(BookInfo::getCreateUser,bookInfoVo.getCreateUser());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getCreateTimeFrom()) && StringUtils.isNotBlank(bookInfoVo.getCreateTimeTo())){
            queryWrapper.lambda().between(BookInfo::getCreateTime,bookInfoVo.getCreateTimeFrom(),bookInfoVo.getCreateTimeTo());
        }
        if(StringUtils.isNotBlank(bookInfoVo.getModifyTimeFrom()) && StringUtils.isNotBlank(bookInfoVo.getModifyTimeTo())){
            queryWrapper.lambda().between(BookInfo::getModifyTime,bookInfoVo.getModifyTimeFrom(),bookInfoVo.getModifyTimeTo());
        }
        //未逻辑删除
        queryWrapper.lambda().eq(BookInfo::getIsDelete,0);
        Page<BookInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);
    }

    @Override
    public BookInfo queryBookInfoById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createBookInfo(BookInfo bookInfo) {
        this.save(bookInfo);
    }

    @Override
    @Transactional
    public void updateBookInfo(BookInfo bookInfo) {
        this.baseMapper.updateById(bookInfo);
    }

    @Override
    @Transactional
    public void deleteBookInfos(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    @Transactional
    public void deleteBookInfos(List<String> bookIds) {
        this.baseMapper.deleteBookInfos(bookIds);
    }

    @Override
    public List<BookInfo> queryList() {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BookInfo::getStatus,"Yes");
        return this.baseMapper.selectList(queryWrapper);
    }
}
