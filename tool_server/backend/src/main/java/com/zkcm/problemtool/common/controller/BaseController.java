package com.zkcm.problemtool.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkcm.problemtool.common.authentication.JWTUtil;
import com.zkcm.problemtool.common.utils.FebsUtil;
import com.zkcm.problemtool.system.domain.User;
import com.zkcm.problemtool.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    @Autowired
    private UserService userService;

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getRecords());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
    }

    /**
     * 从token中解析出用户名
     *
     * @param
     * @return
     */
    public String getApiUserName() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authentication");
        return JWTUtil.getUsername(FebsUtil.decryptToken(token));
    }

    /**
     * 从token中解析出用户
     *
     * @param
     * @return
     */
    public User getCurrentUser() {
        String username = getApiUserName();
        User user = userService.findByName(username);
        return  user;
    }

}
