package com.zkcm.problemtool.system.service.problem;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.system.domain.ProblemInfo;
import com.zkcm.problemtool.system.domain.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2020-09-15
 */
public interface IProblemService extends IService<ProblemInfo> {

    IPage<ProblemInfo> page(QueryRequest request, ProblemInfo file, User user);

    IPage<ProblemInfo> dealPage(Page<ProblemInfo> page);

    List<ProblemInfo> getProblemRecordList(Page<ProblemInfo> page, ProblemInfo problemInfo, List<Long> problemIds);
}
