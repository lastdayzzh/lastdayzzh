package com.zkcm.problemtool.system.enumerate;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description： 类型枚举
 * @version:
 */
@Getter
public enum ProblemTypeEnum {

    EDIT("编辑加工问题"),
    COMPOSING("排版问题"),
    PROOF("校对问题"),
    PRINT_QUALITY("付印样质检问题"),
    DATA_PRODUCTION("数据制作问题"),
    PRINTED("印制问题"),
    BOOK_QUALITY("成书质检问题");

    private String name;

    private ProblemTypeEnum(String name){
        this.name = name;
    }

    public static String getByCodes(String codes) {
        if (StringUtils.isBlank(codes)) {
            return "";
        }
        Map<String, String> map = new HashMap();
        for (ProblemTypeEnum e : ProblemTypeEnum.values()) {
            map.put(e.toString(), e.getName());
        }
        String[] split = codes.split(StringPool.COMMA);
        List<String> list = new ArrayList<>();
        for (String code : split) {
            if (map.get(code) != null) {
                list.add(map.get(code));
            }
        }
        return list.stream().collect(Collectors.joining(StringPool.COMMA));
    }
}
