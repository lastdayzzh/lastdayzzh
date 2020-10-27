package com.zkcm.problemtool.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : tx
 * @date : 2020/9/15 上午10:54
 */
@Data
public class BaseModel implements Serializable {
    //创建人
    @ApiModelProperty(value = "创建人")
    private String createUser;
    //编辑人
//    @ApiModelProperty(value = "编辑人")
//    private String modifyUser;
    //创建时间
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    //编辑时间
    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime modifyTime;
}
