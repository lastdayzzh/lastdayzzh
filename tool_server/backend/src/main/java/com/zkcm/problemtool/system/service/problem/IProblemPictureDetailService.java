package com.zkcm.problemtool.system.service.problem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.system.domain.ProblemPictureDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2020-09-15
 */
public interface IProblemPictureDetailService extends IService<ProblemPictureDetail> {

    List<ProblemPictureDetail> getDetailList(long id);
}
