package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.Discoverer;
import com.zkcm.problemtool.system.service.IDiscovererService;
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
@RequestMapping("discoverer")
@Api(description = "发现人controller")
public class DiscovererController extends BaseController {
    @Autowired
    private IDiscovererService discovererService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("discoverer:view")
    public Map<String, Object> queryDiscoverers(QueryRequest request, Discoverer discoverer){
        IPage<Discoverer> IPage = discovererService.queryDiscoverers(request,discoverer);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<Discoverer> queryList(){
        return this.discovererService.queryList();
    }

    @GetMapping("/getById")
    public Discoverer queryDiscovererById(Integer id){
        return this.discovererService.queryDiscovererById(id);
    }

    @Log("新增发现人信息")
    @PostMapping("/save")
    //@RequiresPermissions("discoverer:add")
    public ResultResponse createDiscoverer(@RequestBody Discoverer discoverer) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            discoverer.setCreateTime(LocalDateTime.now());
            discovererService.createDiscoverer(discoverer);
            rs.setMessage("新增发现人成功!");
        }catch (Exception e){
            message = "新增发现人失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改发现人信息")
    @PutMapping("/update")
    //@RequiresPermissions("discoverer:update")
    public ResultResponse updateDiscoverer(@RequestBody Discoverer discoverer) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            discoverer.setModifyTime(LocalDateTime.now());
            this.discovererService.updateDiscoverer(discoverer);
            rs.setMessage("修改发现人成功!");
        }catch (Exception e){
            message = "修改发现人失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除发现人")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("discoverer:delete")
    public ResultResponse deleteDiscoverers(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.discovererService.deleteDiscoverers(ids1);
            rs.setMessage("删除发现人成功!");
        }catch (Exception e){
            message = "删除发现人失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
