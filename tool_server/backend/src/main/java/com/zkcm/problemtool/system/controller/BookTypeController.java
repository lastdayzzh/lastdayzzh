package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.BookType;
import com.zkcm.problemtool.system.service.IBookTypeService;
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
@RequestMapping("bookType")
@Api(description = "图书类别controller")
public class BookTypeController extends BaseController {
    @Autowired
    private IBookTypeService bookTypeService;

    private String message;


    @GetMapping("/get")
    //@RequiresPermissions("bookType:view")
    public Map<String, Object> queryBookTypes(QueryRequest request, BookType bookType){
        IPage<BookType> IPage = bookTypeService.queryBookTypes(request,bookType);
        return this.getDataTable(IPage);
    }

    @GetMapping("/list")
    public List<BookType> queryList(){
        return this.bookTypeService.queryList();
    }

    @GetMapping("/getById")
    public BookType queryBookTypeById(Integer id){
        return this.bookTypeService.queryBookTypeById(id);
    }

    @Log("新增图书类别")
    @PostMapping("/save")
    //@RequiresPermissions("bookType:add")
    public ResultResponse createBookType(@RequestBody BookType bookType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            bookType.setCreateTime(LocalDateTime.now());
            bookTypeService.createBookType(bookType);
            rs.setMessage("新增图书类别成功!");
        }catch (Exception e){
            message = "新增图书类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改图书类别")
    @PutMapping("/update")
    //@RequiresPermissions("bookType:update")
    public ResultResponse updateBookType(@RequestBody BookType bookType) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //初始化
            bookType.setModifyTime(LocalDateTime.now());
            this.bookTypeService.updateBookType(bookType);
            rs.setMessage("修改图书类别成功!");
        }catch (Exception e){
            message = "修改图书类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除图书类别")
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("bookType:delete")
    public ResultResponse deleteBookTypes(@NotBlank(message = "{required}") @PathVariable String ids) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids1 = ids.split(StringPool.COMMA);
            this.bookTypeService.deleteBookTypes(ids1);
            rs.setMessage("删除图书类别成功!");
        }catch (Exception e){
            message = "删除图书类别失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

}
