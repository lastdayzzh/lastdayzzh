package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zkcm.problemtool.common.domain.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("t_oa_user")
public class OaUser implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String employeeName;

    private String employeeSex;

    private String employeeAge;

    private String birthday;

    private String nation;

    private String nativePlace;

    private String hrLoginAccount;

    private String birthPlace;

    private String identityCode;

    private String registrationName;

    private String healthStatus;

    private String maritalStatus;

    private String education;

    private String degree;

    private String workDate;

    private String joinDate;

    private String orgDate;

    private String editDate;

    private String relatedWorkDate;

    private String orgCode;

    private String orgName;

    private String postCode;

    private String postName;

    private String employeeId;

    private String staffLibrary;

    private String jobLevel;

    private String title;

    private String titleName;

    private String employeeType;

    private String employeeCategory;

    private String orderNum;

    private String parttimeJob;

    private String email;

    private String mobile;

    private String tel;

    private String telExt;

    private String employeeStatus;

    private String employeeNum;

    private String reimburseLevel;

    private String reimburseLevelCode;

    private String bankCardName;

    private String bankCardNum;

    private String bankAddr;

    private String bankName;

    private String bankAddrCode;

    private String sexCode;

    private String nationCode;

    private String nativePlaceCode;

    private String birthPlaceCode;

    private String registrationCode;

    private String healthStatusCode;

    private String maritalStatusCode;

    private String ifTalentsGroup;

    private String educationCode;

    private String degreeCode;

    private String jobLevelCode;

    private String titleCode;

    private String employeeTypeCode;

    private String employeeCategoryCode;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;

    private String employeeCode;

    private String uuid;

}