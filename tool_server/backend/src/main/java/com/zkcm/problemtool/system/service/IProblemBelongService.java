package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.ProblemBelong;
import com.zkcm.problemtool.system.domain.vo.ProblemBelongVo;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IProblemBelongService extends IService<ProblemBelong> {
    //根据条件分页查询信息
    IPage<ProblemBelong> queryProblemBelongs(QueryRequest request, ProblemBelongVo problemBelongVo);
    //根据id查询信息
    ProblemBelong queryProblemBelongById(Integer id);
    //创建信息
    void createProblemBelong(ProblemBelong problemBelong);
    //修改信息
    void updateProblemBelong(ProblemBelong problemBelong);
    //删除信息
    void deleteProblemBelongs(String[] bookIds);

    List<ProblemBelong> queryList();

}
