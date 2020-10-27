package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.ShareTypeMapper;
import com.zkcm.problemtool.system.domain.ShareType;
import com.zkcm.problemtool.system.service.IShareTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author : tx
 * @date : 2020/9/16 下午1:33
 */
@Slf4j
@Service("shareTypeService")
public class ShareTypeServiceImpl  extends ServiceImpl<ShareTypeMapper, ShareType> implements IShareTypeService {
    @Override
    public IPage<ShareType> queryShareTypes(QueryRequest request, ShareType shareType) {
        QueryWrapper<ShareType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(shareType.getName())) {
            queryWrapper.lambda().like(ShareType::getName, shareType.getName());
        }
        if (StringUtils.isNotBlank(shareType.getCreateTimeFrom()) && StringUtils.isNotBlank(shareType.getCreateTimeTo())) {
            queryWrapper.lambda().between(ShareType::getCreateTime, shareType.getCreateTimeFrom(), shareType.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(shareType.getModifyTimeFrom()) && StringUtils.isNotBlank(shareType.getModifyTimeTo())) {
            queryWrapper.lambda().between(ShareType::getModifyTime, shareType.getModifyTimeFrom(), shareType.getModifyTimeTo());
        }
        Page<ShareType> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public ShareType queryShareTypeById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createShareType(ShareType shareType) {
        this.save(shareType);
    }

    @Override
    @Transactional
    public void updateShareType(ShareType shareType) {
        this.baseMapper.updateById(shareType);
    }

    @Override
    @Transactional
    public void deleteShareTypes(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<ShareType> queryList() {
        QueryWrapper<ShareType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShareType::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
