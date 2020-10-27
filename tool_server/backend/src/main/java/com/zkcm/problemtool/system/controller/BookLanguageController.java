package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.BookLanguage;
import com.zkcm.problemtool.system.service.IBookLanguageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("bookLanguage")
@Api(description = "图书语种controller")
public class BookLanguageController extends BaseController {
    @Autowired
    private IBookLanguageService bookLanguageService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("bookLanguage:view")
    public Map<String, Object> queryBookLanguages(QueryRequest request, BookLanguage bookLanguage){
        IPage<BookLanguage> IPage = bookLanguageService.queryBookLanguages(request,bookLanguage);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<BookLanguage> queryList(){
        return this.bookLanguageService.queryList();
    }

    @GetMapping("/getById")
    @ApiOperation("通过id查找图书语种")
    @ApiImplicitParam(name = "id",value = "通过id查找图书语种",dataType = "int ")
    public BookLanguage queryBookLanguageById(Integer id){
        return this.bookLanguageService.queryBookLanguageById(id);
    }

    @Log("新增图书语种信息")
    @PostMapping("/save")
    //@RequiresPermissions("bookLanguage:add")
    public ResultResponse createBookInfo(@RequestBody BookLanguage bookLanguage) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            bookLanguage.setCreateTime(LocalDateTime.now());
            bookLanguageService.createBookLanguage(bookLanguage);
            rs.setMessage("新建图书语种成功!");
        }catch (Exception e){
            message = "新增图书语种失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改图书语种信息")
    @PutMapping("/update")
    //@RequiresPermissions("bookLanguage:update")
    public ResultResponse updateBookInfo(@RequestBody BookLanguage bookLanguage) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            bookLanguage.setModifyTime(LocalDateTime.now());
            this.bookLanguageService.updateBookLanguage(bookLanguage);
            rs.setMessage("修改图书语种成功!");
        }catch (Exception e){
            message = "修改图书信息失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除图书信息")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("bookLanguage:delete")
    public ResultResponse deleteBookInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.bookLanguageService.deleteBookLanguages(ids1);
            rs.setMessage("删除图书语种信息成功!");
        }catch (Exception e){
            message = "删除图书语种信息失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
