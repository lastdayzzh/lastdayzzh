package com.zkcm.problemtool.common.utils;

import com.zkcm.problemtool.system.domain.Dept;
import com.zkcm.problemtool.system.domain.User;
import com.zkcm.problemtool.system.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 工具类
 */
@Slf4j
public class FilterUtil {

    private static DeptService deptService = SpringContextHolder.getBean(DeptService.class);
    /**
     * 数据范围过滤
     * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
     * @param officeAlias 机构表别名，多个用“,”逗号隔开。
     * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter(User user, String officeAlias, String userAlias) {
        return dataScopeFilterDeal(user, officeAlias, userAlias);
    }

    /**
     * 数据范围过滤生成
     * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
     * @param officeAlias 机构表别名，多个用“,”逗号隔开。
     * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return 标准连接条件对象
     */
    public static String dataScopeFilterDeal(User user, String officeAlias, String userAlias){
        StringBuilder sqlString = new StringBuilder();
        //是否管理员权限（留个入口）
        if("".equals(user.getUsername())){
            return "";
        }else{
            if (StringUtils.isNotBlank(userAlias)){
                for (String ua : StringUtils.split(userAlias, ",")) {
                    sqlString.append(" OR (" + ua + ".username = '" + user.getUsername() + "'");
                    if (StringUtils.isNotBlank(officeAlias)) {
                        sqlString.append(" AND ( 1=0 ");
                    }
                    for (String oa : StringUtils.split(officeAlias, ",")) {
                        sqlString.append(" OR " + ua + ".dept_id =" + oa + ".dept_id");
                        if (StringUtils.isNotBlank(user.getExtraDeptIds())) {
                            List<String> officeList = Arrays.asList(user.getExtraDeptIds().split(","));
                            for (String office : officeList) {
                                sqlString.append(" OR '" + office + "' =" + oa + ".dept_id");
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(officeAlias)) {
                        sqlString.append("))");
                    } else {
                        sqlString.append(")");
                    }
                }
            }

                // 主管权限为部门及以下 todo
            if(StringUtils.isNotBlank(user.getAuthDeptId())){
                sqlString.append(" OR " + officeAlias + ".dept_id = '" + user.getAuthDeptId() + "'");
                //获取主管部门parendIds
                Dept dept = deptService.getById(user.getAuthDeptId());
                sqlString.append(" OR " + officeAlias + ".parent_ids LIKE '" + dept.getParentIds() + user.getAuthDeptId() + ",%'");
//                if(StringUtils.isNotBlank(user.getExtraDeptIds())) {
//                    List<String> officeList = Arrays.asList(user.getExtraDeptIds().split(","));
//                    for(String office : officeList ) {
//                        sqlString.append(" OR " + officeAlias + ".id = '" + office + "'");
//                        sqlString.append(" OR " + officeAlias + ".parent_ids LIKE '" + OfficeUtils.getOffice(office) + office + ",%'");
//                    }
//                }
            }
            //权限为所在部门
                sqlString.append(" OR " + officeAlias + ".dept_id = '" + user.getDeptId() + "'");
                if(StringUtils.isNotBlank(user.getExtraDeptIds())){
                    List<String> officeList = Arrays.asList(user.getExtraDeptIds().split(","));
                    for(String office : officeList ) {
                        sqlString.append(" OR " + officeAlias + ".dept_id = '" + office + "'");
                    }
                }
        }

        return " AND (" + sqlString.substring(4) + ")";
    }



}
