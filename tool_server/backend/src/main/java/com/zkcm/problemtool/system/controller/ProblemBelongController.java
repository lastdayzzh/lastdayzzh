package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.ProblemBelong;
import com.zkcm.problemtool.system.domain.vo.ProblemBelongVo;
import com.zkcm.problemtool.system.service.IProblemBelongService;
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
@RequestMapping("problemBelong")
@Api(description = "问题归属controller")
public class ProblemBelongController extends BaseController {
    @Autowired
    private IProblemBelongService problemBelongService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("problemBelong:view")
    public Map<String, Object> queryBookLanguages(QueryRequest request, ProblemBelongVo problemBelongVo){
        IPage<ProblemBelong> IPage = problemBelongService.queryProblemBelongs(request,problemBelongVo);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<ProblemBelong> queryList(){
        return this.problemBelongService.queryList();
    }

    @GetMapping("/getById")
    public ProblemBelong queryBookLanguageById(Integer id){
        return this.problemBelongService.queryProblemBelongById(id);
    }

    @Log("新增问题归属")
    @PostMapping("/save")
    //@RequiresPermissions("problemBelong:add")
    public ResultResponse createBookInfo(@RequestBody ProblemBelong problemBelong) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            problemBelong.setCreateTime(LocalDateTime.now());
            problemBelongService.createProblemBelong(problemBelong);
            rs.setMessage("新增问题归属成功!");
        }catch (Exception e){
            message = "新增问题归属失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改问题归属")
    @PutMapping("/update")
    //@RequiresPermissions("problemBelong:update")
    public ResultResponse updateBookInfo(@RequestBody ProblemBelong problemBelong) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            problemBelong.setModifyTime(LocalDateTime.now());
            this.problemBelongService.updateProblemBelong(problemBelong);
            rs.setMessage("修改问题归属成功!");
        }catch (Exception e){
            message = "修改问题归属失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除问题归属")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("problemBelong:delete")
    public ResultResponse deleteBookInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.problemBelongService.deleteProblemBelongs(ids1);
            rs.setMessage("删除问题归属成功!");
        }catch (Exception e){
            message = "删除问题归属失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
