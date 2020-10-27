package com.zkcm.problemtool.system.service.favorites;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.Favorites;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2020-09-29
 */
public interface IFavoritesService extends IService<Favorites> {

    IPage<ProblemInfo> getProblemRecordList(List<Long> ids, QueryRequest request, ProblemInfo problemInfo);

    IPage<Picture> getPictureRecordList(List<Long> ids, QueryRequest request, Picture picture);
}
