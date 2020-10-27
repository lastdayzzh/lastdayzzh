package com.zkcm.problemtool.system.dao.problem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkcm.problemtool.system.domain.ProblemPictureDetail;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2020-09-08
 */
public interface ProblemPictureDetailMapper extends BaseMapper<ProblemPictureDetail> {

    List<ProblemPictureDetail> getDetailList(long id);
}
