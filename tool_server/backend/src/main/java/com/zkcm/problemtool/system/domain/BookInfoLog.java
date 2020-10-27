package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zkcm.problemtool.common.domain.BaseModel;
import lombok.Data;

@Data
@TableName("t_book_info_log")
public class BookInfoLog extends BaseModel {
    //id
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    //图书id
    private Integer bookId;
    //书名
    private String bookName;
    //isbn
    private String isbn;
    //选题号
    private String questionNum;
    //图书种类
    private String bookType;
    //语种
    private String language;
    //版次
    private String edition;
    //印次
    private String printNum;
    //分社
    private String branch;
    //责任编辑
    private String editor;
    //部门
    private String dept;
    //操作状态
    private String operation;
}
