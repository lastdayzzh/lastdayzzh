package com.zkcm.problemtool.system.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.BookInfo;
import com.zkcm.problemtool.system.domain.vo.BookInfoVo;

import java.util.List;


public interface IBookInfoService extends IService<BookInfo> {
    //根据条件分页查询信息
    IPage<BookInfo> queryBookInfos(QueryRequest request, BookInfoVo bookInfoVo);
    //根据id查询图书信息
    BookInfo queryBookInfoById(Integer id);
    //删除图书信息
    void createBookInfo(BookInfo bookInfo);
    //修改图书信息
    void updateBookInfo(BookInfo bookInfo);
    //删除图书信息
    void deleteBookInfos(String[] bookIds);
    //逻辑删除图书信息
    void deleteBookInfos(List<String> bookIds);

    List<BookInfo> queryList();
}
