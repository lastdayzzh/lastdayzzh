package com.zkcm.problemtool.system.service.problem.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.problem.ProblemMapper;
import com.zkcm.problemtool.system.domain.*;
import com.zkcm.problemtool.system.service.*;
import com.zkcm.problemtool.system.service.problem.IProblemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2020-09-08
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, ProblemInfo> implements IProblemService {

    @Autowired
    private IDataMakeTypeService dataMakeTypeService;
    @Autowired
    private IBookTypeService bookTypeService;
    @Autowired
    private IBookLanguageService bookLanguageService;
    @Autowired
    private IProblemSortService problemSortService;
    @Autowired
    private IProofreadTypeService proofreadTypeService;
    @Autowired
    private IProblemBelongService problemBelongService;



    @Override
    public IPage<ProblemInfo> page(QueryRequest request, ProblemInfo problemInfo, User user) {
        if(StringUtils.isNotBlank(problemInfo.getCheckType())){
            problemInfo.setCheckTypeList(Arrays.asList(problemInfo.getCheckType().split(",")));
        }
        if(StringUtils.isNotBlank(problemInfo.getAscription())){
            problemInfo.setAscriptionList(Arrays.asList(problemInfo.getAscription().split(",")));
        }
//        problemInfo.getSqlMap().put("dsf", FilterUtil.dataScopeFilter(user, "dta", "ua"));
        Page<ProblemInfo> page = new Page();
        SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
        List<ProblemInfo> problemInfoList = this.baseMapper.pageProblem(page, problemInfo);
        page.setRecords(problemInfoList);
        return dealPage(page);
    }

    public IPage<ProblemInfo> dealPage(Page<ProblemInfo> page){
        List <ProblemInfo> problemInfoList = page.getRecords();
        List<DataMakeType> dataMakeTypeList = dataMakeTypeService.queryList();
        List<BookType> bookTypeList = bookTypeService.queryList();
        List<BookLanguage> bookLanguageList = bookLanguageService.queryList();
        List<ProblemSort> problemSortList = problemSortService.queryList();
        List<ProofreadType> proofreadTypeList =  proofreadTypeService.queryList();
        List<ProblemBelong> problemBelongList = problemBelongService.queryList();

        problemInfoList.stream().forEach(p -> {
            DataMakeType dataMakeType =dataMakeTypeList.stream().filter(temp -> p.getDataType().equals(temp.getId().toString())).findAny().orElse(null);
            p.setDataTypeName(ObjectUtils.isNull(dataMakeType)? "":dataMakeType.getName());

            BookType bookType=bookTypeList.stream().filter(temp -> p.getBookType().equals(temp.getId().toString())).findAny().orElse(null);
            p.setBookTypeName(ObjectUtils.isNull(bookType)? "":bookType.getName());

            BookLanguage bookLanguage=bookLanguageList.stream().filter(temp -> p.getLanguage().equals(temp.getId().toString())).findAny().orElse(null);
            p.setLanguageName(ObjectUtils.isNull(bookLanguage)? "":bookLanguage.getName());

            if(StringUtils.isNotBlank(p.getProblemClassification())){
                List<String> stringList = new ArrayList<>();
                String arr [] = p.getProblemClassification().split(StringPool.COMMA);
                for(String m : arr){
                    ProblemSort problemSort = problemSortList.stream().filter(temp -> m.equals(temp.getId().toString())).findAny().orElse(null);
                    stringList.add(ObjectUtils.isNull(problemSort)? "":problemSort.getName());
                }
                p.setProblemClassificationName(StringUtils.join(stringList.toArray(), ","));
            }
            if(StringUtils.isNotBlank(p.getCheckType())){
                List<String> stringList = new ArrayList<>();
                String arr [] = p.getCheckType().split(StringPool.COMMA);
                for(String m : arr){
                    ProofreadType proofreadType = proofreadTypeList.stream().filter(temp -> m.equals(temp.getId().toString())).findAny().orElse(null);
                    stringList.add(ObjectUtils.isNull(proofreadType)? "":proofreadType.getName());
                }
                p.setCheckTypeName(StringUtils.join(stringList.toArray(), ","));
            }
            if(StringUtils.isNotBlank(p.getAscription())){
                List<String> stringList = new ArrayList<>();
                String arr [] = p.getAscription().split(StringPool.COMMA);
                for(String m : arr){
                    ProblemBelong problemBelong = problemBelongList.stream().filter(temp -> p.getAscription().equals(temp.getId().toString())).findAny().orElse(null);
                    stringList.add(ObjectUtils.isNull(problemBelong)? "":problemBelong.getName());
                }
                p.setAscriptionName(StringUtils.join(stringList.toArray(), ","));
            }

        });
        page.setRecords(problemInfoList);
        return page;
    }

    @Override
    public List<ProblemInfo> getProblemRecordList(Page<ProblemInfo> page, ProblemInfo problemInfo, List<Long> problemIds) {
        return this.baseMapper.getProblemRecordList(page,problemInfo,problemIds);
    }
}
