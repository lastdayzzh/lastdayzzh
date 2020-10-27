package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.DictionaryDetail;
import com.zkcm.problemtool.system.domain.vo.DictionaryDetailVo;

import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IDictionaryDetailService extends IService<DictionaryDetail> {
    //根据条件分页查询信息
    IPage<DictionaryDetail> queryDictionaryDetails(QueryRequest request, DictionaryDetailVo dictionaryDetailVo);
    //创建信息
    void createDictionaryDetail(DictionaryDetail dictionaryDetail);
    //修改信息
    void updateDictionaryDetail(DictionaryDetail dictionaryDetail);
    //删除信息
    void deleteDictionaryDetails(String[] ids);

    List<DictionaryDetail> queryList(String code);

    List<DictionaryDetail> queryByCode(String code);
}
