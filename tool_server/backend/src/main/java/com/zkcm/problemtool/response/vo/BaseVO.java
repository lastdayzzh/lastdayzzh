package com.zkcm.problemtool.response.vo;

public interface BaseVO {

    /**
     * 转换PO 到VO ，以便于只传输需要的数据到界面端
     * @param model
     *result:
     *creater: lwl
     *time: 2019/9/29 15:23
     */
    public void convertPOToVO(Object model);
}
