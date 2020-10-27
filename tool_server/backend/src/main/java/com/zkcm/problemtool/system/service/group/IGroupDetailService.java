package com.zkcm.problemtool.system.service.group;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.domain.Tree;
import com.zkcm.problemtool.system.domain.Group;
import com.zkcm.problemtool.system.domain.GroupDetail;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2020-09-28
 */
public interface IGroupDetailService extends IService<GroupDetail> {

    IPage<ProblemInfo> getProblemRecordList(List<String> ids, QueryRequest request, ProblemInfo problemInfo);

    IPage<Picture> getPictureRecordList(List<String> ids, QueryRequest request, Picture picture);
}
