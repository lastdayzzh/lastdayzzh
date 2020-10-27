package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.zkcm.problemtool.system.enumerate.PostStatusEnum;
import com.zkcm.problemtool.system.enumerate.ProblemTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author MrBird
 */
@Data
@Accessors(chain = true)
@TableName("t_problem")
public class ProblemInfo extends Model<ProblemInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 图书id
     */
    private String bookId;
    @TableField(exist = false)
    private String bookName;

    /**
     * 发现人
     */
    private String discoverer;

    /**
     * 类型
     */
    private ProblemTypeEnum type;

    public String getProblemTypeName() {
        return type!=null?type.getName():"";
    }

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private PostStatusEnum status;
    public String getStatusName() {
        return status!=null?status.getName():"";
    }

    /**
     * 校对类别
     */
    private String checkType;
    @TableField(exist = false)
    private String checkTypeName;
    @TableField(exist = false)
    private List<String> checkTypeList;
    /**
     * 数据制作类别
     */
    private String dataType;
    @TableField(exist = false)
    private String dataTypeName;

    /**
     * 页码
     */
    private String pageNumber;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 问题属性(0:一般，1:精华)
     */
    private String attributes;

    /**
     * 问题归属
     */
    private String ascription;
    private String ascriptionName;
    private List<String> ascriptionList;

    /**
     * 处理意见
     */
    private String handle;

    /**
     * 发现问题阶段
     */
    private String stage;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除 0-否 1-是
     */
    private String delFlag;

    @TableField(exist = false)
    private List<ProblemPictureDetail> problemPictureDetailList;

//    @TableField(exist = false)
//    private List<ProblemPictureRelate> problemPictureRelateList;

    /**
     * 视图，分享转发我的，我创建的
     */
    @TableField(exist = false)
    private String viewBy;

    //图书种类
    @TableField(exist = false)
    private String bookType;
    @TableField(exist = false)
    private String bookTypeName;

    //语种
    @TableField(exist = false)
    private String language;
    @TableField(exist = false)
    private String languageName;

    //分社
    @TableField(exist = false)
    private String branch;
    /**
     * 图书信息
     */
    @TableField(exist = false)
    private String bookInfo;
    /**
     * 问题分类
     */
    @TableField(exist = false)
    private String problemClassification;
    @TableField(exist = false)
    private String problemClassificationName;
    /**
     * 创建人部门
     */
    @TableField(exist = false)
    private String createrDept;
    /**
     * 发现人部门
     */
    @TableField(exist = false)
    private String discovererDept;

    /**
     * 质检结果
     */
    @TableField(exist = false)
    private String checkResult;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private transient Date updateTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private transient Date updateTimeEnd;

    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    @JsonIgnore
    @XmlTransient
    @TableField(exist = false)
    protected Map<String, String> sqlMap;
    public Map<String, String> getSqlMap() {
        if (sqlMap == null){
            sqlMap = Maps.newHashMap();
        }
        return sqlMap;
    }

    /**
     * 所属机构
     */
    private Long organization;

}
