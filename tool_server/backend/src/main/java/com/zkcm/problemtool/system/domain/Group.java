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
@TableName("t_group")
public class Group extends BaseModel implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父id")
    private Long parentId;

    @ApiModelProperty(value = "分组名称")
    private String name;

    //0：问题，1：图片
    @ApiModelProperty(value = "分组类型")
    private String type;

    //编辑人
    @ApiModelProperty(value = "编辑人")
    private String modifyUser;
}