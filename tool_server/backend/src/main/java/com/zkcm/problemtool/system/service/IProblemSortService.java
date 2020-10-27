package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.ProblemSort;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IProblemSortService extends IService<ProblemSort> {
    //根据条件分页查询信息
    IPage<ProblemSort> queryProblemSorts(QueryRequest request, ProblemSort problemSort);
    //根据id查询信息
    ProblemSort queryProblemSortById(Integer id);
    //创建信息
    void createProblemSort(ProblemSort problemSort);
    //修改信息
    void updateProblemSort(ProblemSort problemSort);
    //删除信息
    void deleteProblemSorts(String[] bookIds);

    List<ProblemSort> queryList();
}
