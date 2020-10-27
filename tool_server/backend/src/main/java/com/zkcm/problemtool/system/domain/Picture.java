package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 *
 *
 * @author MrBird
 */
@Data
@Accessors(chain = true)
@TableName("t_picture")
public class Picture extends Model<Picture> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 原始文件名
     */
    private String original;

    /**
     * 文件地址
     */
    private String url;

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

    /**
     * 随机Id
     */
    private String pictureId;

    /**
     * 排序方式，0-时间    1-名称
     */
    @TableField(exist = false)
    private String sortBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private transient Date createTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private transient Date createTimeEnd;

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
