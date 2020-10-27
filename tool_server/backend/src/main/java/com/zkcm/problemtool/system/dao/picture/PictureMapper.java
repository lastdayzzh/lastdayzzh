package com.zkcm.problemtool.system.dao.picture;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkcm.problemtool.system.domain.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2020-09-08
 */
public interface PictureMapper extends BaseMapper<Picture> {

    List<Picture> pagePicture(Page<Picture> page,@Param("param") Picture file);

    List<Picture> pictureList(Page<Picture> page,@Param("param") Picture file);

    List<Picture> getPictureRecordList(Page<Picture> page, @Param("param")Picture picture, @Param("list")List<Long> pictureIds);
}
