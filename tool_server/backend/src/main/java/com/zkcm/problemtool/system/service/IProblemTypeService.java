package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.ProblemType;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IProblemTypeService extends IService<ProblemType> {
    //根据条件分页查询信息
    IPage<ProblemType> queryProblemTypes(QueryRequest request, ProblemType problemType);
    //根据id查询信息
    ProblemType queryProblemTypeById(Integer id);
    //创建信息
    void createProblemType(ProblemType problemType);
    //修改信息
    void updateProblemType(ProblemType problemType);
    //删除信息
    void deleteProblemTypes(String[] bookIds);

    List<ProblemType> queryList();
}
