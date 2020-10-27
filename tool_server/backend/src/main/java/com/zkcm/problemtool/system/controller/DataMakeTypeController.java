package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.DataMakeType;
import com.zkcm.problemtool.system.service.IDataMakeTypeService;
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
@RequestMapping("dataMakeType")
@Api(description = "数据制作类别controller")
public class DataMakeTypeController extends BaseController {
    @Autowired
    private IDataMakeTypeService dataMakeTypeService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("dataMakeType:view")
    public Map<String, Object> queryDataMakeTypes(QueryRequest request, DataMakeType dataMakeType){
        IPage<DataMakeType> IPage = dataMakeTypeService.queryDataMakeTypes(request,dataMakeType);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<DataMakeType> queryList(){
        return this.dataMakeTypeService.queryList();
    }

    @GetMapping("/getById")
    public DataMakeType queryDataMakeTypeById(Integer id){
        return this.dataMakeTypeService.queryDataMakeTypeById(id);
    }

    @Log("新增数据制作类别信息")
    @PostMapping("/save")
    //@RequiresPermissions("dataMakeType:add")
    public ResultResponse createDataMakeType(@RequestBody DataMakeType dataMakeType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            dataMakeType.setCreateTime(LocalDateTime.now());
            dataMakeTypeService.createDataMakeType(dataMakeType);
            rs.setMessage("新增数据制作类别成功!");
        }catch (Exception e){
            message = "新增数据制作类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改数据制作类别信息")
    @PutMapping("/update")
    //@RequiresPermissions("dataMakeType:update")
    public ResultResponse updateDataMakeType(@RequestBody DataMakeType dataMakeType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            dataMakeType.setModifyTime(LocalDateTime.now());
           dataMakeTypeService.updateDataMakeType(dataMakeType);
            rs.setMessage("修改数据制作类别成功!");
        }catch (Exception e){
            message = "修改数据制作类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除数据制作类别信息")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("dataMakeType:delete")
    public ResultResponse deleteDataMakeTypes(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.dataMakeTypeService.deleteDataMakeTypes(ids1);
            rs.setMessage("删除数据制作类别信息成功!");
        }catch (Exception e){
            message = "删除数据制作类别信息失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
