package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.BusinessType;
import com.zkcm.problemtool.system.domain.vo.BusinessTypeVo;
import com.zkcm.problemtool.system.service.IBusinessTypeService;
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
@RequestMapping("businessBelong")
@Api(description = "业务类型controller")
public class BusinessTypeController extends BaseController {
    @Autowired
    private IBusinessTypeService businessTypeService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("problemBelong:view")
    public Map<String, Object> queryBusinessTypes(QueryRequest request, BusinessTypeVo businessTypeVo){
        IPage<BusinessType> IPage = businessTypeService.queryBusinessTypes(request,businessTypeVo);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<BusinessType> queryList(){
        return this.businessTypeService.queryList();
    }

    @GetMapping("/getById")
    public BusinessType queryBusinessTypeById(Integer id){
        return this.businessTypeService.queryBusinessTypeById(id);
    }

    @Log("新增业务类型")
    @PostMapping("/save")
    //@RequiresPermissions("problemBelong:add")
    public ResultResponse createBusinessType(@RequestBody BusinessType businessType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            businessType.setCreateTime(LocalDateTime.now());
            businessTypeService.createBusinessType(businessType);
            rs.setMessage("新增业务类型成功!");
        }catch (Exception e){
            message = "新增业务类型失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改业务类型")
    @PutMapping("/update")
    //@RequiresPermissions("problemBelong:update")
    public ResultResponse updateBusinessType(@RequestBody BusinessType businessType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            businessType.setModifyTime(LocalDateTime.now());
            this.businessTypeService.updateBusinessType(businessType);
            rs.setMessage("修改业务类型成功!");
        }catch (Exception e){
            message = "修改业务类型失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除业务类型")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("problemBelong:delete")
    public ResultResponse deleteBusinessTypes(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.businessTypeService.deleteBusinessTypes(ids1);
            rs.setMessage("删除业务类型成功!");
        }catch (Exception e){
            message = "删除业务类型失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
