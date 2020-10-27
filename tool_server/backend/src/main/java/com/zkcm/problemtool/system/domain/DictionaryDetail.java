package com.zkcm.problemtool.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zkcm.problemtool.common.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : tx
 * @date : 2020/9/28 下午7:22
 */
@Data
@TableName("t_dictionary_detail")
@ApiModel(value = "字典子表")
public class DictionaryDetail extends BaseModel {
    //id
    @TableId(value="id",type= IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "key")
    private String dictionaryKey;

    @ApiModelProperty(value = "value")
    private String dictionaryValue;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "code")
    private String code;

}
