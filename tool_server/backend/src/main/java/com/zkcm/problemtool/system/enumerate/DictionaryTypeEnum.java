package com.zkcm.problemtool.system.enumerate;

/**
 * @author : tx
 * @date : 2020/9/28 下午1:27
 */
public enum DictionaryTypeEnum {
    BOOK_TYPE("图书种类"),
    BOOK_LANGUAGE("图书语种"),
    SHARE_TYPE("分享类型"),
    PROBLEM_BELONG("问题归属"),
    BUSINESS_TYPE("业务类型");

    private String name;
    private DictionaryTypeEnum(String name) {

        this.name = name;
    }
}
