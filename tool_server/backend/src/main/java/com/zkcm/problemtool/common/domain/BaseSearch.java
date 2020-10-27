package com.zkcm.problemtool.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class BaseSearch extends BaseModel {

    @ApiModelProperty(value = "创建时间从")
    private transient String createTimeFrom;
    @ApiModelProperty(value = "创建时间到")
    private transient String createTimeTo;

    @ApiModelProperty(value = "编辑时间从")
    private transient String modifyTimeFrom;
    @ApiModelProperty(value = "编辑时间到")
    private transient String modifyTimeTo;
}
