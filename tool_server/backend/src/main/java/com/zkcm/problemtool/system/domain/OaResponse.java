package com.zkcm.problemtool.system.domain;

import com.zkcm.problemtool.common.domain.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OaResponse implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    private EsbInfo esbInfo;

    private List<OaUser> requestInfo;
}