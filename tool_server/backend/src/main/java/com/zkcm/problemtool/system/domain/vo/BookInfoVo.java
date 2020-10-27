package com.zkcm.problemtool.system.domain.vo;

import com.zkcm.problemtool.common.domain.BaseSearch;
import lombok.Data;

/**
 * @author : tx
 * @date : 2020/9/29 上午9:44
 */
@Data
public class BookInfoVo extends BaseSearch {

    //图书信息
    private String bookInfo;

    //状态
    private String status;

    //图书种类
    private String bookType;

    //语种
    private String language;

    //责任编辑
    private String editor;

    //分社
    private String branch;

    //版次
    private String edition;

    //印次
    private String printNum;

}
