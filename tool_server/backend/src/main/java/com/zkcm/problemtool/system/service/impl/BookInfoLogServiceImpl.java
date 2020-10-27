package com.zkcm.problemtool.system.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.system.dao.BookInfoLogMapper;
import com.zkcm.problemtool.system.domain.BookInfoLog;
import com.zkcm.problemtool.system.service.IBookInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("bookInfoLogService")
public class BookInfoLogServiceImpl extends ServiceImpl<BookInfoLogMapper, BookInfoLog> implements IBookInfoLogService {
    @Override
    public void createBookInfoLog(BookInfoLog bookInfoLog) {
        this.save(bookInfoLog);
    }

    @Override
    public List<BookInfoLog> queryBookInfoLogs(Integer id) {
        QueryWrapper<BookInfoLog> queryWrapper = new QueryWrapper<>();
        if(id>0){
            queryWrapper.lambda().eq(BookInfoLog::getBookId,id);
        }
        queryWrapper.orderByAsc("id");
       return this.baseMapper.selectList(queryWrapper);
    }
}
