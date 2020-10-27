package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zkcm.problemtool.common.domain.BaseSearch;
import lombok.Data;

@Data
@TableName("t_book_info")
public class BookInfo extends BaseSearch {

    //图书id
    @TableId(value="book_id",type= IdType.AUTO)
    private Integer bookId;
    //书名
    private String bookName;
    //isbn
    private String isbn;
    //选题号
    private String questionNum;
    //图书信息
    private String bookInfo;
    //图书种类id
    private Integer bookTypeId;
    //图书种类
    @TableField(exist = false)
    private String bookType;
    //语种id
    private Integer languageId;
    //语种
    @TableField(exist = false)
    private String language;
    //版次
    private String edition;
    //印次
    private String printNum;
    //分社
    private String branch;
    //责任编辑
    private String editor;
    //状态  YES NO
    private String status;
    //是否删除
    private String isDelete;
    //质检结果
    private String checkResult;
}
