package com.zkcm.problemtool.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkcm.problemtool.system.domain.BookInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookInfoMapper extends BaseMapper<BookInfo> {
    void deleteBookInfos(@Param("bookIds") List<String> bookIds);
}
