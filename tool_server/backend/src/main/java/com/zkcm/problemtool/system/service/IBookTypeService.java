package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.BookType;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IBookTypeService extends IService<BookType> {
    //根据条件分页查询信息
    IPage<BookType> queryBookTypes(QueryRequest request, BookType bookType);
    //根据id查询信息
    BookType queryBookTypeById(Integer id);
    //创建信息
    void createBookType(BookType bookType);
    //修改信息
    void updateBookType(BookType bookType);
    //删除信息
    void deleteBookTypes(String[] bookIds);

    List<BookType> queryList();

}
