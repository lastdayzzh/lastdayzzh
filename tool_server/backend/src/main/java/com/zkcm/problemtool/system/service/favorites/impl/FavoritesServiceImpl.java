package com.zkcm.problemtool.system.service.favorites.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.favorites.FavoritesMapper;
import com.zkcm.problemtool.system.domain.Favorites;
import com.zkcm.problemtool.system.domain.GroupDetail;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;
import com.zkcm.problemtool.system.service.favorites.IFavoritesService;
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
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements IFavoritesService {
    @Autowired
    private IProblemService problemService;
    @Autowired
    private IPictureService pictureService;

    @Override
    public IPage<ProblemInfo> getProblemRecordList(List<Long> ids, QueryRequest request, ProblemInfo problemInfo) {
        if(StringUtils.isNotBlank(problemInfo.getCheckType())){
            problemInfo.setCheckTypeList(Arrays.asList(problemInfo.getCheckType().split(",")));
        }
        if(StringUtils.isNotBlank(problemInfo.getAscription())){
            problemInfo.setAscriptionList(Arrays.asList(problemInfo.getAscription().split(",")));
        }
        Page<ProblemInfo> page = new Page();
        SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
        List<ProblemInfo> problemInfoList = new ArrayList<>();
        if(CollUtil.isNotEmpty(ids)){
            problemInfoList = problemService.getProblemRecordList(page, problemInfo,ids);
            page.setRecords(problemInfoList);
            return problemService.dealPage(page);
        }else{
            page.setRecords(problemInfoList);
            return page;
        }
    }

    @Override
    public IPage<Picture> getPictureRecordList(List<Long> ids, QueryRequest request, Picture picture) {
        Page<Picture> page = new Page();
        SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
        List<Picture> pictureList = new ArrayList<>();
        if(CollUtil.isNotEmpty(ids)){
            pictureList = pictureService.getPictureRecordList(page, picture,ids);
        }
        return page.setRecords(pictureList);
    }
}
