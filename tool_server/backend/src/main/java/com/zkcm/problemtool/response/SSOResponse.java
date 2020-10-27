package com.zkcm.problemtool.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @description: 返回给SSO的结果
 * @author: Lidl
 * @create: 2020-06-17 17:17
 **/
@Slf4j
@Data
@Accessors(chain = true)
public class SSOResponse {


    /**
     * 状态 1-成功，0-失败
     */
    @JSONField(name = "_ZVING_STATUS")
    private String status = "1";
    /**
     * 消息（encode）
     */
    @JSONField(name = "_ZVING_MESSAGE")
    private String message;

    public void setMessage(String message) {
        try {
            this.message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }
}
