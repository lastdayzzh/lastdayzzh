package com.zkcm.problemtool.system.enumerate;


import lombok.Getter;

@Getter
public enum ContentTypeEnum {

    // 上传视频
    VIDEO_CONTENT_TYPE("video/mp4"),
    // 上传图片
    IMAGE_CONTENT_TYPE("image/jpeg"),
    // 上传文件
    FILE_CONTENT_TYPE("application/octet-stream"),
    // 上传字符串大文本内容
    HTML_CONTENT_TYPE("text/html"),
    ;

    private String name;

    private ContentTypeEnum(String name){
        this.name = name;
    }


}
