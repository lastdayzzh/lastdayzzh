package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.ProblemSort;
import com.zkcm.problemtool.system.service.IProblemSortService;
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
@RequestMapping("problemSort")
@Api(description = "问题分类controller")
public class ProblemSortController extends BaseController {
    @Autowired
    private IProblemSortService problemSortService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("problemSort:view")
    public Map<String, Object> queryProblemSorts(QueryRequest request, ProblemSort problemSort){
        IPage<ProblemSort> IPage = problemSortService.queryProblemSorts(request,problemSort);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<ProblemSort> queryList(){
        return this.problemSortService.queryList();
    }

    @GetMapping("/getById")
    public ProblemSort queryProblemSortById(Integer id){
        return this.problemSortService.queryProblemSortById(id);
    }

    @Log("新增问题分类")
    @PostMapping("/save")
    //@RequiresPermissions("problemSort:add")
    public ResultResponse createProblemSort(@RequestBody ProblemSort problemSort) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            problemSort.setCreateTime(LocalDateTime.now());
            problemSortService.createProblemSort(problemSort);
            rs.setMessage("新增问题分类成功!");
        }catch (Exception e){
            message = "新增问题分类失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改问题分类")
    @PutMapping("/update")
    //@RequiresPermissions("problemSort:update")
    public ResultResponse updateProblemSort(@RequestBody ProblemSort problemSort) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            problemSort.setModifyTime(LocalDateTime.now());
            this.problemSortService.updateProblemSort(problemSort);
            rs.setMessage("修改问题分类成功!");
        }catch (Exception e){
            message = "修改问题分类失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除问题分类")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("problemSort:delete")
    public ResultResponse deleteProblemSorts(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.problemSortService.deleteProblemSorts(ids1);
            rs.setMessage("删除问题分类成功!");
        }catch (Exception e){
            message = "删除问题分类失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
