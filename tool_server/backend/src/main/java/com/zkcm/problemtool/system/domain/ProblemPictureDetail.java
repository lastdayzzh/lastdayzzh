package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 *
 * @author MrBird
 */
@Data
@Accessors(chain = true)
@TableName("t_problem_picture_detail")
public class ProblemPictureDetail extends Model<ProblemPictureDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 问题id
     */
    private String problemId;

    /**
     * 图片id
     */
    private String pictureId;

    /**
     * 问题分类
     */
    private String problemClassification;

}
