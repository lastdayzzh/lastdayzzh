package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.BookLanguage;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IBookLanguageService extends IService<BookLanguage> {
    //根据条件分页查询信息
    IPage<BookLanguage> queryBookLanguages(QueryRequest request, BookLanguage bookLanguage);
    //根据id查询信息
    BookLanguage queryBookLanguageById(Integer id);
    //创建信息
    void createBookLanguage(BookLanguage bookLanguage);
    //修改信息
    void updateBookLanguage(BookLanguage bookLanguage);
    //删除信息
    void deleteBookLanguages(String[] bookIds);

    List<BookLanguage> queryList();

}
