package com.zkcm.problemtool.response;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description： 封装结果，给前端显示使用
 * @author     ：lwl
 * @date       ：2019/10/11 17:31
 * @version:
 */
@Data
@Accessors(chain = true)
public class ResultResponse {

    //异常码
    private int code = 200;
    //消息
    private String message;
    //数据集
    private  Object data;
    //是否成功
    private boolean success = true;

    public ResultResponse(){

    }

    public ResultResponse(Object data){
        this.data = data;
    }

    public ResultResponse(boolean success,int code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResultResponse(IPage<?> page){
        PageVO vo = new PageVO(page);
        this.data = vo;
    }

    public ResultResponse(PageVO<?> page){
        this.data = page;
    }

    public static String getJsonString(boolean success,int code,String message){
        ResultResponse response = new ResultResponse(success,code,message);
        return JSON.toJSONString(response);
    }

    public ResultResponse setErrorMessage(String message){
        this.setMessage(message);
        this.setSuccess(false);
        return this;
    }

    public static ResultResponse ok() {
        ResultResponse r = new ResultResponse();
        r.setSuccess(true);
        return r;
    }

    public static ResultResponse page(IPage<?> page) {
        ResultResponse r = new ResultResponse(page);
        r.setSuccess(true);
        return r;
    }

    public static ResultResponse ok(Object data) {
        ResultResponse r = new ResultResponse();
        r.setData(data);
        r.setSuccess(true);
        return r;
    }

    public static ResultResponse error(String message) {
        ResultResponse r = new ResultResponse();
        r.setCode(500);
        r.setErrorMessage(message);
        return r;
    }
}
