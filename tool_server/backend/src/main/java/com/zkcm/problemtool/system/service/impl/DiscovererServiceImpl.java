package com.zkcm.problemtool.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.DiscovererMapper;
import com.zkcm.problemtool.system.domain.Discoverer;
import com.zkcm.problemtool.system.service.IDiscovererService;
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
@Service("discovererService")
public class DiscovererServiceImpl  extends ServiceImpl<DiscovererMapper, Discoverer> implements IDiscovererService {
    @Override
    public IPage<Discoverer> queryDiscoverers(QueryRequest request, Discoverer discoverer) {
        QueryWrapper<Discoverer> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(discoverer.getName())) {
            queryWrapper.lambda().like(Discoverer::getName, discoverer.getName());
        }
        if (StringUtils.isNotBlank(discoverer.getCreateTimeFrom()) && StringUtils.isNotBlank(discoverer.getCreateTimeTo())) {
            queryWrapper.lambda().between(Discoverer::getCreateTime, discoverer.getCreateTimeFrom(), discoverer.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(discoverer.getModifyTimeFrom()) && StringUtils.isNotBlank(discoverer.getModifyTimeTo())) {
            queryWrapper.lambda().between(Discoverer::getModifyTime, discoverer.getModifyTimeFrom(), discoverer.getModifyTimeTo());
        }
        Page<Discoverer> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public Discoverer queryDiscovererById(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createDiscoverer(Discoverer discoverer) {
        this.save(discoverer);
    }

    @Override
    @Transactional
    public void updateDiscoverer(Discoverer discoverer) {
        this.baseMapper.updateById(discoverer);
    }

    @Override
    @Transactional
    public void deleteDiscoverers(String[] bookIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(bookIds));
    }

    @Override
    public List<Discoverer> queryList() {
        QueryWrapper<Discoverer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Discoverer::getStatus,"YES");
        return this.baseMapper.selectList(queryWrapper);
    }
}
