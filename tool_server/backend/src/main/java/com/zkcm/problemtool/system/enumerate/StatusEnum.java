package com.zkcm.problemtool.system.enumerate;

import lombok.Getter;

@Getter
public enum StatusEnum {
    YES("启用"),NO("停用");

    private String name;
    private StatusEnum(String name) {

        this.name = name;
    }
}
