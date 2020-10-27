package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.ShareType;
import com.zkcm.problemtool.system.service.IShareTypeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:44
 */
@Slf4j
@Validated
@RestController
@RequestMapping("shareType")
@Api(description = "分享类别controller")
public class ShareTypeController extends BaseController {
    @Autowired
    private IShareTypeService shareTypeService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("shareType:view")
    public Map<String, Object> queryBookLanguages(QueryRequest request, ShareType shareType){
        IPage<ShareType> IPage = shareTypeService.queryShareTypes(request,shareType);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<ShareType> queryList(){
        return this.shareTypeService.queryList();
    }

    @GetMapping("/getById")
    public ShareType queryBookLanguageById(Integer id){
        return this.shareTypeService.queryShareTypeById(id);
    }

    @Log("新增分享类别信息")
    @PostMapping("/save")
    //@RequiresPermissions("shareType:add")
    public ResultResponse createBookInfo(@RequestBody ShareType shareType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            shareType.setCreateTime(LocalDateTime.now());
            shareTypeService.createShareType(shareType);
            rs.setMessage("新增分享类别成功!");
        }catch (Exception e){
            message = "新增分享类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改分享类别信息")
    @PutMapping("/update")
    //@RequiresPermissions("shareType:update")
    public ResultResponse updateBookInfo(@RequestBody ShareType shareType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            shareType.setModifyTime(LocalDateTime.now());
            this.shareTypeService.updateShareType(shareType);
            rs.setMessage("修改分享类别成功!");
        }catch (Exception e){
            message = "修改分享类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除分享类别")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("shareType:delete")
    public ResultResponse deleteBookInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.shareTypeService.deleteShareTypes(ids1);
            rs.setMessage("删除分享类别成功!");
        }catch (Exception e){
            message = "删除分享类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
