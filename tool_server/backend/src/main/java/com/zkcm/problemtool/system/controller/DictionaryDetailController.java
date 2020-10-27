package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.DictionaryDetail;
import com.zkcm.problemtool.system.domain.vo.DictionaryDetailVo;
import com.zkcm.problemtool.system.service.IDictionaryDetailService;
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
@RequestMapping("dictionaryDetail")
@Api(description = "数据字典子表controller")
public class DictionaryDetailController extends BaseController {
    @Autowired
    private IDictionaryDetailService dictionaryDetailService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("bookType:view")
    public Map<String, Object> queryDictionaryDetails(QueryRequest request, DictionaryDetailVo dictionaryDetailVo){
        IPage<DictionaryDetail> IPage = dictionaryDetailService.queryDictionaryDetails(request,dictionaryDetailVo);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<DictionaryDetail> queryList(String code){
        return this.dictionaryDetailService.queryList(code);
    }

    @GetMapping("/getCode")
    public List<DictionaryDetail> queryByCode(String code){
        return this.dictionaryDetailService.queryByCode(code);
    }

    @Log("新增数据字典子表")
    @PostMapping("/save")
    //@RequiresPermissions("bookType:add")
    public ResultResponse createDictionaryDetail(@RequestBody DictionaryDetail dictionaryDetail) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            dictionaryDetail.setCreateTime(LocalDateTime.now());
            dictionaryDetailService.createDictionaryDetail(dictionaryDetail);
            rs.setMessage("新增数据字典成功!");
        }catch (Exception e){
            message = "新增数据字典失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改数据字典子表")
    @PutMapping("/update")
    //@RequiresPermissions("bookType:update")
    public ResultResponse updateDictionaryDetail(@RequestBody DictionaryDetail dictionaryDetail) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            dictionaryDetail.setModifyTime(LocalDateTime.now());
            this.dictionaryDetailService.updateDictionaryDetail(dictionaryDetail);
            rs.setMessage("修改数据字典成功!");
        }catch (Exception e){
            message = "修改数据字典成功!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除数据字典子表")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("bookType:delete")
    public ResultResponse deleteDictionaryDetails(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.dictionaryDetailService.deleteDictionaryDetails(ids1);
            rs.setMessage("删除数据字典成功!");
        }catch (Exception e){
            message = "删除数据字典成功!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
