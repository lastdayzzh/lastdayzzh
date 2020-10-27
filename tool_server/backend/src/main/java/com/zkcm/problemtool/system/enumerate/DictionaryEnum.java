package com.zkcm.problemtool.system.enumerate;

import lombok.Getter;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:48
 */
@Getter
public enum DictionaryEnum {
    YES("有效"),NO("无效");

    private String name;
    private DictionaryEnum(String name) {

        this.name = name;
    }
}
