package com.zkcm.problemtool.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.Dictionary;
import com.zkcm.problemtool.system.domain.vo.DictionaryVo;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:31
 */
public interface IDictionaryService extends IService<Dictionary> {
    //根据条件分页查询信息
    IPage<Dictionary> queryDictionarys(QueryRequest request, DictionaryVo dictionaryVo);
    //创建信息
    void createDictionary(Dictionary dictionary);
    //修改信息
    void updateDictionary(Dictionary dictionary);
    //删除信息
    void deleteDictionarys(String[] ids);

    Dictionary queryByCode(String code);
}
