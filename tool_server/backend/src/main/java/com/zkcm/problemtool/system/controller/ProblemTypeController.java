package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.ProblemType;
import com.zkcm.problemtool.system.service.IProblemTypeService;
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
@RequestMapping("problemType")
@Api(description = "问题类型controller")
public class ProblemTypeController extends BaseController {
    @Autowired
    private IProblemTypeService problemTypeService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("problemType:view")
    public Map<String, Object> queryProblemTypes(QueryRequest request, ProblemType problemType){
        IPage<ProblemType> IPage = problemTypeService.queryProblemTypes(request,problemType);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<ProblemType> queryList(){
        return this.problemTypeService.queryList();
    }

    @GetMapping("/getById")
    public ProblemType queryProblemTypeById(Integer id){
        return this.problemTypeService.queryProblemTypeById(id);
    }

    @Log("新增问题类型")
    @PostMapping("/save")
    //@RequiresPermissions("problemType:add")
    public ResultResponse createProblemType(@RequestBody ProblemType problemType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            problemType.setCreateTime(LocalDateTime.now());
            problemTypeService.createProblemType(problemType);
            rs.setMessage("新增问题类型成功!");
        }catch (Exception e){
            message = "新增问题类型失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改问题类型")
    @PutMapping("/update")
    //@RequiresPermissions("problemType:update")
    public ResultResponse updateProblemType(@RequestBody ProblemType problemType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            problemType.setModifyTime(LocalDateTime.now());
            this.problemTypeService.updateProblemType(problemType);
            rs.setMessage("修改问题类型成功!");
        }catch (Exception e){
            message = "修改问题类型失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除问题类型")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("problemType:delete")
    public ResultResponse deleteProblemTypes(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.problemTypeService.deleteProblemTypes(ids1);
            rs.setMessage("删除问题类型成功!");
        }catch (Exception e){
            message = "删除问题类型失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
