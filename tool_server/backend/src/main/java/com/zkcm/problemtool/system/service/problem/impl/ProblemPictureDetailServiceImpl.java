package com.zkcm.problemtool.system.service.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.system.dao.problem.ProblemPictureDetailMapper;
import com.zkcm.problemtool.system.domain.ProblemPictureDetail;
import com.zkcm.problemtool.system.service.problem.IProblemPictureDetailService;
import org.springframework.stereotype.Service;

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
public class ProblemPictureDetailServiceImpl extends ServiceImpl<ProblemPictureDetailMapper, ProblemPictureDetail> implements IProblemPictureDetailService {

    @Override
    public List<ProblemPictureDetail> getDetailList(long id) {
        return this.baseMapper.getDetailList(id);
    }
}
