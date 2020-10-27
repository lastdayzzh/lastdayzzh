package com.zkcm.problemtool.system.service.picture.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.FilterUtil;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.system.dao.picture.PictureMapper;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.User;
import com.zkcm.problemtool.system.service.picture.IPictureService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2020-09-08
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {

    @Override
    public IPage<Picture> page(QueryRequest request, Picture file, User user) {
//        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
//        if (file != null) {
//            if (StringUtils.isNotBlank(file.getOriginal())) {
//                queryWrapper.like(Picture::getOriginal, file.getOriginal());
//            }
//            if (StringUtils.isNotBlank(file.getCreateUser())) {
//                queryWrapper.eq(Picture::getCreateUser, file.getCreateUser());
//            }
//            if (ObjectUtils.isNotNull(file.getCreateTimeStart())) {
//                queryWrapper.apply("to_date({0},'yyyy-mm-dd hh24:mi:ss') <= create_time",file.getCreateTimeStart());
//            }
//            if (ObjectUtils.isNotNull(file.getCreateTimeEnd())) {
//                queryWrapper.apply("to_date({0},'yyyy-mm-dd hh24:mi:ss') >= create_time",file.getCreateTimeEnd());
//            }
//        }
        file.getSqlMap().put("dsf", FilterUtil.dataScopeFilter(user, "o", "u"));
        Page<Picture> page = new Page();
        if ("1".equals(file.getSortBy())){
            SortUtil.handlePageSort(request, page, "a.original", FebsConstant.ORDER_DESC, false);
        }else{
            SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
        }
         return page.setRecords(this.baseMapper.pagePicture(page, file));
    }

    @Override
    public List<Picture> pictureList(Page<Picture> page,Picture file) {
        return this.baseMapper.pictureList(page,file);
    }
    @Override
    public List<Picture> getPictureRecordList(Page<Picture> page, Picture picture, List<Long> pictureIds) {
        return this.baseMapper.getPictureRecordList(page,picture,pictureIds);
    }

}
