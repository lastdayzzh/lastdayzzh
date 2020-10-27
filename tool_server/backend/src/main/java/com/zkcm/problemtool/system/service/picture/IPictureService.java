package com.zkcm.problemtool.system.service.picture;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2020-09-08
 */
public interface IPictureService extends IService<Picture> {

    IPage<Picture> page(QueryRequest request, Picture file, User user);

    List<Picture> pictureList(Page<Picture> page,Picture file);

    List<Picture> getPictureRecordList(Page<Picture> page, Picture picture, List<Long> pictureIds);
}
