package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.ProofreadType;
import com.zkcm.problemtool.system.service.IProofreadTypeService;
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
@RequestMapping("proofreadType")
@Api(description = "校对类别controller")
public class ProofreadTypeController extends BaseController {
    @Autowired
    private IProofreadTypeService proofreadTypeService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("proofreadType:view")
    public Map<String, Object> queryBookLanguages(QueryRequest request, ProofreadType proofreadType){
        IPage<ProofreadType> IPage = proofreadTypeService.queryProofreadTypes(request,proofreadType);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<ProofreadType> queryList(){
        return this.proofreadTypeService.queryList();
    }

    @GetMapping("/getById")
    public ProofreadType queryBookLanguageById(Integer id){
        return this.proofreadTypeService.queryProofreadTypeById(id);
    }

    @Log("新增校对类别")
    @PostMapping("/save")
    //@RequiresPermissions("proofreadType:add")
    public ResultResponse createBookInfo(@RequestBody ProofreadType proofreadType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            proofreadType.setCreateTime(LocalDateTime.now());
            proofreadTypeService.createProofreadType(proofreadType);
            rs.setMessage("新增校对类别成功!");
        }catch (Exception e){
            message = "新增校对类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改校对类别信息")
    @PutMapping("/update")
    //@RequiresPermissions("proofreadType:update")
    public ResultResponse updateBookInfo(@RequestBody ProofreadType proofreadType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            proofreadType.setModifyTime(LocalDateTime.now());
            this.proofreadTypeService.updateProofreadType(proofreadType);
            rs.setMessage("修改校对类别成功!");
        }catch (Exception e){
            message = "修改校对类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除校对类别")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("proofreadType:delete")
    public ResultResponse deleteBookInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.proofreadTypeService.deleteProofreadTypes(ids1);
            rs.setMessage("删除校对类别成功!");
        }catch (Exception e){
            message = "删除校对类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
