package com.zkcm.problemtool.system.dao.problem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;
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
public interface ProblemMapper extends BaseMapper<ProblemInfo> {

    List<ProblemInfo> pageProblem(Page<ProblemInfo> page, @Param("param") ProblemInfo file);

    List<ProblemInfo> getProblemRecordList(Page<ProblemInfo> page, @Param("param")ProblemInfo problemInfo, @Param("list")List<Long> problemIds);
}
