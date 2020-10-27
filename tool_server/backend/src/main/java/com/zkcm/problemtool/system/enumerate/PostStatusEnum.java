package com.zkcm.problemtool.system.enumerate;

import lombok.Getter;

@Getter
public enum PostStatusEnum {
    DRAFT("草稿"),ALREADY_RELEASED("已发布");

    private String name;
    private PostStatusEnum(String name) {

        this.name = name;
    }
}
