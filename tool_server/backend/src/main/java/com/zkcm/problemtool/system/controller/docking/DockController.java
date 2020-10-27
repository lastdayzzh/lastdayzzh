package com.zkcm.problemtool.system.controller.docking;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.system.domain.EsbResponseInfo;
import com.zkcm.problemtool.system.domain.OaResponse;
import com.zkcm.problemtool.system.domain.OaReturnJson;
import com.zkcm.problemtool.system.domain.key.EsbInfoVo;
import com.zkcm.problemtool.system.domain.key.OaReturn;
import com.zkcm.problemtool.system.domain.key.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/docking")
public class DockController extends BaseController {

    @GetMapping("/user")
    public OaReturnJson getUser(String json) {
        log.info("========================用户============================");
        log.info(json);
        OaResponse oaResponse = JSONUtil.toBean(new JSONObject(json), OaResponse.class);
        OaReturnJson oaReturnJson = new OaReturnJson();
        oaReturnJson.setEsbInfo(oaResponse.getEsbInfo());
        EsbResponseInfo esbResponseInfo = new EsbResponseInfo();
        esbResponseInfo.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        esbResponseInfo.setReturnCode("A0001");
        esbResponseInfo.setReturnMsg("成功");
        esbResponseInfo.setReturnStatus("S");
        oaReturnJson.setResponseInfo(esbResponseInfo);
        return oaReturnJson;
    }

    @GetMapping("/dept")
    public OaReturnJson getDept( String json) {
        log.info("========================组织============================");
        log.info(json);
        OaResponse oaResponse = JSONUtil.toBean(new JSONObject(json), OaResponse.class);
        OaReturnJson oaReturnJson = new OaReturnJson();
        oaReturnJson.setEsbInfo(oaResponse.getEsbInfo());
        EsbResponseInfo esbResponseInfo = new EsbResponseInfo();
        esbResponseInfo.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        esbResponseInfo.setReturnCode("A0001");
        esbResponseInfo.setReturnMsg("成功");
        esbResponseInfo.setReturnStatus("S");
        oaReturnJson.setResponseInfo(esbResponseInfo);
        return oaReturnJson;
    }

    @GetMapping("/post")
    public OaReturnJson getPost( String json) {
        log.info("========================岗位============================");
        log.info(json);
        OaResponse oaResponse = JSONUtil.toBean(new JSONObject(json), OaResponse.class);
        OaReturnJson oaReturnJson = new OaReturnJson();
        oaReturnJson.setEsbInfo(oaResponse.getEsbInfo());
        EsbResponseInfo esbResponseInfo = new EsbResponseInfo();
        esbResponseInfo.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        esbResponseInfo.setReturnCode("A0001");
        esbResponseInfo.setReturnMsg("成功");
        esbResponseInfo.setReturnStatus("S");
        oaReturnJson.setResponseInfo(esbResponseInfo);
        return oaReturnJson;
    }


    @PostMapping("/key")
    public OaReturn getKey(@RequestBody JSONObject jsonObject) {
        log.info("========================秘钥============================");
        log.info(jsonObject.toString());
        OaReturn oaReturn = new OaReturn();
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode("A0001");
        responseInfo.setReturnMsg("成功");
        responseInfo.setReturnStatus("S");
        Object o = jsonObject.get("esbInfo");
        JSONObject json = (JSONObject) o;
        EsbInfoVo esbInfoVo = new EsbInfoVo();
        //返回信息
        esbInfoVo.setResponseTime(json.get("requestTime").toString());
        esbInfoVo.setInstId(json.get("instId").toString());
        esbInfoVo.setRequestTime(json.get("requestTime").toString());
        oaReturn.setResponseInfo(responseInfo);
        oaReturn.setEsbInfo(esbInfoVo);
        return oaReturn;
    }

    @PostMapping("/account")
    public OaReturn getAccount(@RequestBody JSONObject jsonObject) {
        log.info("========================账户============================");
        log.info(jsonObject.toString());
        OaReturn oaReturn = new OaReturn();
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode("A0001");
        responseInfo.setReturnMsg("成功");
        responseInfo.setReturnStatus("S");
        Object o = jsonObject.get("esbInfo");
        JSONObject json = (JSONObject) o;
        EsbInfoVo esbInfoVo = new EsbInfoVo();
        //返回信息
        esbInfoVo.setResponseTime(json.get("requestTime").toString());
        esbInfoVo.setInstId(json.get("instId").toString());
        esbInfoVo.setRequestTime(json.get("requestTime").toString());
        oaReturn.setResponseInfo(responseInfo);
        oaReturn.setEsbInfo(esbInfoVo);
        return oaReturn;
    }

}
