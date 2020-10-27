package com.zkcm.problemtool.web.controller;

import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.FebsResponse;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("article")
public class ArticleController {

    @GetMapping
    @RequiresPermissions("article:view")
    public FebsResponse queryArticle(String date) throws ProblemToolException {
        String param;
        String data;
        try {
            if (StringUtils.isNotBlank(date)) {
                param = "dev=1&date=" + date;
                data = HttpUtil.sendSSLPost(FebsConstant.MRYW_DAY_URL, param);
            } else {
                param = "dev=1";
                data = HttpUtil.sendSSLPost(FebsConstant.MRYW_TODAY_URL, param);
            }
            return new FebsResponse().data(data);
        } catch (Exception e) {
            String message = "获取文章失败";
            log.error(message, e);
            throw new ProblemToolException(message);
        }
    }
}
