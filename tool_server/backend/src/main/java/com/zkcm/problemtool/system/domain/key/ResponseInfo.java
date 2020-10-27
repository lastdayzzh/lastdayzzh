package com.zkcm.problemtool.system.domain.key;

import lombok.Data;

/**
 * @author : tx
 * @date : 2020/10/16 上午10:57
 */
@Data
public class ResponseInfo {

    private String returnCode;

    private String returnMsg;

    private String returnStatus;
}
