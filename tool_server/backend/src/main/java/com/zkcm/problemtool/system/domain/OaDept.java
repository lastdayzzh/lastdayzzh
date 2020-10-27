package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_oa_dept")
public class OaDept implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String deptName;

    private String deptId;

    private String deptFlagCode;

    private String chargeEmployee;

    private String deptPhone;

    private String deptFax;

    private String deptStatus;

    private String orderCode;

    private String ifExtBranch;

    private String deptManagerNum;

    private String chargePost;

    private String chargePostNum;

    private String deptLeaderName;

    private String deptLeaderNum;

    private String deptLeaderPost;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;

    private String deptNum;

    private String parentDeptNum;

    private String parentDept;

    private String deptType;

    private String deptTypeCode;

}