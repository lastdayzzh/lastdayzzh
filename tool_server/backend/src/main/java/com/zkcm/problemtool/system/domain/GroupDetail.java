package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.zkcm.problemtool.common.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_group_detail")
public class GroupDetail extends BaseModel implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分组id")
    private Long groupId;

    @ApiModelProperty(value = "记录id")
    private Long recordId;

    //0：问题，1：图片
    @ApiModelProperty(value = "分组类型")
    private String groupType;

    //编辑人
    @ApiModelProperty(value = "编辑人")
    private String modifyUser;
}