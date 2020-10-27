package com.zkcm.problemtool.system.service.group.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.group.GroupDetailMapper;
import com.zkcm.problemtool.system.domain.GroupDetail;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;
import com.zkcm.problemtool.system.service.group.IGroupDetailService;
import com.zkcm.problemtool.system.service.picture.IPictureService;
import com.zkcm.problemtool.system.service.problem.IProblemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2020-09-28
 */
@Service
public class GroupDetailServiceImpl extends ServiceImpl<GroupDetailMapper, GroupDetail> implements IGroupDetailService {

    @Autowired
    private IProblemService problemService;
    @Autowired
    private IPictureService pictureService;

    @Override
    public IPage<ProblemInfo> getProblemRecordList(List<String> ids, QueryRequest request, ProblemInfo problemInfo) {
        if(StringUtils.isNotBlank(problemInfo.getCheckType())){
            problemInfo.setCheckTypeList(Arrays.asList(problemInfo.getCheckType().split(",")));
        }
        if(StringUtils.isNotBlank(problemInfo.getAscription())){
            problemInfo.setAscriptionList(Arrays.asList(problemInfo.getAscription().split(",")));
        }
        //通过groupId获取recordId
        List<GroupDetail> groupDetailList =this.baseMapper.selectList(new LambdaQueryWrapper<GroupDetail>().in(GroupDetail::getGroupId,ids));
         List<Long> problemIds = groupDetailList.stream().map(GroupDetail::getRecordId).collect(Collectors.toList());
        Page<ProblemInfo> page = new Page();
        if(CollUtil.isNotEmpty(problemIds)){
            SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
            List<ProblemInfo> problemInfoList = problemService.getProblemRecordList(page, problemInfo,problemIds);
            page.setRecords(problemInfoList);
            return problemService.dealPage(page);
        }else{
            List<ProblemInfo> problemInfoList = new ArrayList<>();
            page.setRecords(problemInfoList);
            return page;
        }
    }

    @Override
    public IPage<Picture> getPictureRecordList(List<String> ids, QueryRequest request, Picture picture) {
        //通过groupId获取recordId
        List<GroupDetail> groupDetailList =this.baseMapper.selectList(new LambdaQueryWrapper<GroupDetail>().in(GroupDetail::getGroupId,ids));
        List<Long> pictureIds = groupDetailList.stream().map(GroupDetail::getRecordId).collect(Collectors.toList());
        Page<Picture> page = new Page();
        if(CollUtil.isNotEmpty(pictureIds)){
            SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
            List<Picture> pictureList = pictureService.getPictureRecordList(page, picture,pictureIds);
            return page.setRecords(pictureList);
        }else{
            List<Picture> pictureList = new ArrayList<>();
            page.setRecords(pictureList);
            return page;
        }
    }

}
