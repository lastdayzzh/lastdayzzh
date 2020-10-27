package com.zkcm.problemtool.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.*;
import com.zkcm.problemtool.system.domain.vo.BookInfoVo;
import com.zkcm.problemtool.system.service.IBookInfoLogService;
import com.zkcm.problemtool.system.service.IBookInfoService;
import com.zkcm.problemtool.system.service.IBookLanguageService;
import com.zkcm.problemtool.system.service.IBookTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Validated
@RestController
@RequestMapping("bookInfo")
public class BookInfoController extends BaseController {
    @Autowired
    private IBookInfoService bookInfoService;
    @Autowired
    private IBookInfoLogService bookInfoLogService;
    @Autowired
    private IBookTypeService bookTypeService;
    @Autowired
    private IBookLanguageService bookLanguageService;

    private String message;

    @GetMapping("/get")
    //@RequiresPermissions("bookInfo:view")
    public Map<String, Object> queryBookInfos(QueryRequest request, BookInfoVo bookInfoVo){
        IPage<BookInfo> bookInfoIPage = bookInfoService.queryBookInfos(request,bookInfoVo);
        bookInfoIPage.setRecords(BookInfoUtil(bookInfoIPage.getRecords()));
        return this.getDataTable(bookInfoIPage);
    }
    @GetMapping("/list")
    List<BookInfo> queryList(){
        return BookInfoUtil(this.bookInfoService.queryList());
    }

    @GetMapping("/getById")
    public ResultResponse queryBookInfoById(Integer id) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            BookInfo bookInfo = BookInfoUtil(this.bookInfoService.queryBookInfoById(id));
            if(bookInfo == null){
                rs.setErrorMessage("图书不存在");
            }
            rs.setData(bookInfo);
        } catch (Exception e) {
            message = "图书查询失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("新增图书信息")
    @PostMapping("/save")
    //@RequiresPermissions("bookInfo:add")
    public ResultResponse createBookInfo(@RequestBody BookInfo bookInfo) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //处理图书名  格式：名称-isbn-选题号
            StringBuilder sb = new StringBuilder();
            sb.append(bookInfo.getBookName()).append("-")
                    .append(bookInfo.getIsbn()).append("-")
                    .append(bookInfo.getQuestionNum());
            bookInfo.setBookInfo(sb.toString());
            //初始化
            bookInfo.setCreateTime(LocalDateTime.now());
            bookInfo.setIsDelete("0");
            this.bookInfoService.createBookInfo(bookInfo);
            BookInfoLog bookInfoLog = new BookInfoLog();
            BeanUtils.copyProperties(BookInfoUtil(bookInfo),bookInfoLog);
            bookInfoLog.setOperation("新建");
            bookInfoLog.setCreateTime(LocalDateTime.now());
            this.bookInfoLogService.createBookInfoLog(bookInfoLog);
            rs.setMessage("新建图书成功!");
        }catch (Exception e){
            message = "新增图书信息失败!";
           log.error(message,e);
           throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("修改图书信息")
    @PutMapping("/update")
    //@RequiresPermissions("bookInfo:update")
    public ResultResponse updateBookInfo(@RequestBody BookInfo bookInfo) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            //处理图书名  格式：名称-isbn-选题号
            StringBuilder sb = new StringBuilder();
            sb.append(bookInfo.getBookName()).append("-")
                    .append(bookInfo.getIsbn()).append("-")
                    .append(bookInfo.getQuestionNum());
            bookInfo.setBookName(sb.toString());
            //初始化
            bookInfo.setModifyTime(LocalDateTime.now());
            this.bookInfoService.updateBookInfo(bookInfo);
            BookInfoLog bookInfoLog = new BookInfoLog();
            //记录日志
            BeanUtils.copyProperties(BookInfoUtil(bookInfo),bookInfoLog);
            bookInfoLog.setOperation("更新");
            bookInfoLog.setCreateTime(LocalDateTime.now());
            this.bookInfoLogService.createBookInfoLog(bookInfoLog);
            rs.setMessage("修改图书成功!");
        }catch (Exception e){
            message = "修改图书信息失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @Log("删除图书信息")
    @DeleteMapping("/{bookIds}")
    //@RequiresPermissions("bookInfo:delete")
    public ResultResponse deleteBookInfos(@NotBlank(message = "{required}") @PathVariable String bookIds) throws ProblemToolException {
        ResultResponse rs = new ResultResponse();
        try {
            String[] ids = bookIds.split(StringPool.COMMA);
            //物理删除
            //this.bookInfoService.deleteBookInfos(ids);
            //逻辑删除
            this.bookInfoService.deleteBookInfos(Arrays.asList(ids));
            rs.setMessage("删除图书成功!");
        }catch (Exception e){
            message = "删除图书信息失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
        return rs;
    }

    @GetMapping("/getLog")
    public Map<Integer,List<FieldInfo>> getBookInfoLog(Integer id) throws ProblemToolException {
        try {
         Map<Integer,List<FieldInfo>> map = new HashMap<>();
         List<BookInfoLog> bookInfoLogs = this.bookInfoLogService.queryBookInfoLogs(id);
            for (int i = 0; i < bookInfoLogs.size()-1; i++) {
                Equator equator = new GetterBaseEquator();
                // 获取不同的属性
                List<FieldInfo> diff = equator.getDiffFields(bookInfoLogs.get(bookInfoLogs.size()-i-1),bookInfoLogs.get(bookInfoLogs.size()-i-2));
                List<FieldInfo> newDiff = new ArrayList<>();
                //排除不需要的属性
                for (FieldInfo fieldInfo:diff) {
                    if(fieldInfo.getFieldName().equals("id") ||fieldInfo.getFieldName().equals("operation")||fieldInfo.getFieldName().equals("modifyTime")){
                    }else {
                        newDiff.add(fieldInfo);
                    }
                }
                map.put(i,newDiff);
            }
            return map;
        }catch (Exception e){
            message = "获取图书操作日志失败!";
            log.error(message,e);
            throw new ProblemToolException(message);
        }
    }

    public  BookInfo BookInfoUtil(BookInfo bookInfo){
            BookType bookType = null;
            BookLanguage bookLanguage =  null;
        if(bookInfo!=null){
                bookType =  bookTypeService.queryBookTypeById(bookInfo.getBookTypeId());
                bookLanguage = bookLanguageService.queryBookLanguageById(bookInfo.getLanguageId());
            }
            if(bookType!=null){
                bookInfo.setBookType(bookType.getName());
            }
            if(bookLanguage!=null){
                bookInfo.setLanguage(bookLanguage.getName());
            }
        return bookInfo;
    }

    public List<BookInfo> BookInfoUtil( List<BookInfo> bookInfos){
        for (BookInfo bookInfo:bookInfos) {
            BookInfoUtil(bookInfo);
        }
        return bookInfos;
    }
}
