package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.system.domain.BookInfoLog;

import java.util.List;

public interface IBookInfoLogService extends IService<BookInfoLog> {
    void createBookInfoLog(BookInfoLog bookInfoLog);
    List<BookInfoLog> queryBookInfoLogs(Integer id);
}
