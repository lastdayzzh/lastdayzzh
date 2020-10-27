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
 * @date : 2020/9/15 下午1:24
 * 问题类型11
 */
@Data
@TableName("t_problem_type")
@ApiModel(value = "问题类型")
public class ProblemType extends BaseSearch {

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

    /**
     * 默认组设置
     */

    //部门
    @ApiModelProperty(value = "默认组部门")
    private String groupDept;

    //岗位
    @ApiModelProperty(value = "默认组岗位")
    private String groupPosition;

    //人员名称
    @ApiModelProperty(value = "默认组人员名称")
    private String groupName;

    /**
     * 创建权限设置
     */

    //部门
    @ApiModelProperty(value = "创建人部门")
    private String createDept;

    //岗位
    @ApiModelProperty(value = "创建人岗位")
    private String createPosition;

    //人员名称
    @ApiModelProperty(value = "创建人人员名称")
    private String createName;

    /**
     * 可见性设置
     */
    //部门
    @ApiModelProperty(value = "可见性部门")
    private String visibleDept;

    //岗位
    @ApiModelProperty(value = "可见性岗位")
    private String visiblePosition;

    //人员名称
    @ApiModelProperty(value = "可见性人员名称")
    private String visibleName;


}
