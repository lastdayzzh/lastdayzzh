package com.zkcm.problemtool.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsbResponseInfo  implements Serializable {
    private  String  uuid;

    private String returnStatus;

    private String returnCode;

    private String returnMsg;
}
