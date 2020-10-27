package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.Dictionary;
import com.zkcm.problemtool.system.domain.vo.DictionaryVo;
import com.zkcm.problemtool.system.service.IDictionaryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:44
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dictionary")
@Api(description = "数据字典controller")
public class DictionaryController extends BaseController {
    @Autowired
    private IDictionaryService dictionaryService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("bookType:view")
    public Map<String, Object> queryDictionarys(QueryRequest request, DictionaryVo dictionaryVo){
        IPage<Dictionary> IPage = dictionaryService.queryDictionarys(request,dictionaryVo);
        return this.getDataTable(IPage);
    }

    @GetMapping("/getCode")
    public Dictionary queryByCode(String code){
        return this.dictionaryService.queryByCode(code);
    }

    @Log("新增数据字典")
    @PostMapping("/save")
    //@RequiresPermissions("bookType:add")
    public ResultResponse createDictionary(@RequestBody Dictionary dictionary) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            dictionary.setCreateTime(LocalDateTime.now());
            dictionaryService.createDictionary(dictionary);
            rs.setMessage("新增数据字典成功!");
        }catch (Exception e){
            message = "新增数据字典成功!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改数据字典")
    @PutMapping("/update")
    //@RequiresPermissions("bookType:update")
    public ResultResponse updateDictionary(@RequestBody Dictionary dictionary) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            dictionary.setModifyTime(LocalDateTime.now());
            this.dictionaryService.updateDictionary(dictionary);
            rs.setMessage("修改数据字典成功!");
        }catch (Exception e){
            message = "修改数据字典成功!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除数据字典")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("bookType:delete")
    public ResultResponse deleteDictionarys(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.dictionaryService.deleteDictionarys(ids1);
            rs.setMessage("删除数据字典成功!");
        }catch (Exception e){
            message = "删除数据字典成功!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
