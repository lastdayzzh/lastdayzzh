package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.ProofreadType;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IProofreadTypeService extends IService<ProofreadType> {
    //根据条件分页查询信息
    IPage<ProofreadType> queryProofreadTypes(QueryRequest request, ProofreadType proofreadType);
    //根据id查询信息
    ProofreadType queryProofreadTypeById(Integer id);
    //创建信息
    void createProofreadType(ProofreadType proofreadType);
    //修改信息
    void updateProofreadType(ProofreadType proofreadType);
    //删除信息
    void deleteProofreadTypes(String[] bookIds);

    List<ProofreadType> queryList();
}
