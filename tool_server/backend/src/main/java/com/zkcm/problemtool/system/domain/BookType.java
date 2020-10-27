package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zkcm.problemtool.common.domain.BaseSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : tx
 * @date : 2020/9/15 下午1:46
 * 图书类别
 */
@Data
@TableName("t_book_type")
@ApiModel(value = "图书类别")
public class BookType extends BaseSearch {

    //id
    @TableId(value="id",type= IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    //名称
    @ApiModelProperty(value = "名称")
    private String name;

    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

    //状态
    @ApiModelProperty(value = "状态")
    private String status;
}
