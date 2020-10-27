package com.zkcm.problemtool.system.controller.problem.facade;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.*;
import com.zkcm.problemtool.system.enumerate.PostStatusEnum;
import com.zkcm.problemtool.system.service.IBookInfoService;
import com.zkcm.problemtool.system.service.picture.IPictureService;
import com.zkcm.problemtool.system.service.problem.IProblemPictureDetailService;
import com.zkcm.problemtool.system.service.problem.IProblemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zzh
 * @description：问题记录
 * @date ：2020/09/14
 */
@Slf4j
@Component
public class ProblemFacade {

    @Resource
    private IProblemService problemService;
    @Resource
    private IPictureService pictureService;
    @Resource
    private IProblemPictureDetailService problemPictureDetailService;
    @Autowired
    private IBookInfoService bookInfoService;

    /**
     * @desc 删除
     * @author zzh
     * @date 2020/09/09
     */
    @Transactional
    public ResultResponse del(String ids){
        ResultResponse rs = new ResultResponse().setMessage("删除成功");
        if(StringUtils.isBlank(ids)){
            return rs.setErrorMessage("请选择要删除的数据");
        }
        List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
        if(idList.size()>0){
            ProblemInfo problemInfo = new ProblemInfo();
            problemInfo.setDelFlag("1");
            problemService.update(problemInfo,new LambdaQueryWrapper<ProblemInfo>().in(ProblemInfo::getId,idList));
        }
//        problemService.removeByIds(idList);

        //删除明细表
//        problemPictureDetailService.remove(new LambdaQueryWrapper<ProblemPictureDetail>().in(ProblemPictureDetail::getProblemId, idList));

        //删除图片
        List<ProblemPictureDetail> problemPictureDetails = problemPictureDetailService.list(new LambdaQueryWrapper<ProblemPictureDetail>().in(ProblemPictureDetail::getProblemId, idList));
        if(problemPictureDetails.size()>0){
            Picture picture = new Picture();
            picture.setDelFlag("1");
            pictureService.update(picture,new LambdaQueryWrapper<Picture>().in(Picture::getId,problemPictureDetails.stream().map(ProblemPictureDetail::getPictureId).collect(Collectors.toList())));
        }
//        List<Picture> pictures = (List<Picture>) pictureService.listByIds(problemPictureDetails.stream().map(ProblemPictureDetail::getPictureId).collect(Collectors.toList()));

//        pictures.stream().forEach(t->{
//            try {
//                MinIOUtil.removeObject(minioClient,"test-lidl-2020-08-14",t.getFileName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        return rs;
    }

    @Transactional
    public ResultResponse save(List<ProblemInfo> problemInfos, User user) {
        ResultResponse rs = new ResultResponse().setMessage("添加成功");
        problemInfos.stream().forEach(problemInfo->{
            problemInfo.setCreateUser(user.getUsername());
            problemInfo.setOrganization(user.getDeptId());
            problemService.save(problemInfo);
            //更新图书质检结果
            updateBook(problemInfo);
            //插入明细表
            saveProblemPictureDetail(problemInfo.getProblemPictureDetailList(),problemInfo.getId());
            //插入校对类别明细表2
//            saveProblemProofreadTypeRelate(problemInfo);
        });
        return rs;
    }

    @Transactional
    public ResultResponse update(ProblemInfo problemInfo) {
        ResultResponse rs = new ResultResponse().setMessage("保存成功");
//        List<ProblemPictureDetail> problemPictureDetailList = new ArrayList<>();
        boolean flag = problemService.updateById(problemInfo);
        //更新图书质检结果
        updateBook(problemInfo);
        if(flag){
            //明细表全删全插
            problemPictureDetailService.remove(new LambdaQueryWrapper<ProblemPictureDetail>().eq(ProblemPictureDetail::getProblemId, problemInfo.getId()));
            //插入明细表
            saveProblemPictureDetail(problemInfo.getProblemPictureDetailList(),problemInfo.getId());
            //明细表全删全插
//            proofreadTypeRelateService.remove(new LambdaQueryWrapper<ProblemProofreadTypeRelate>().eq(ProblemProofreadTypeRelate::getProblemId, problemInfo.getId()));
            //插入校对类别明细表2
//            saveProblemProofreadTypeRelate(problemInfo);
        }
        return  rs;
    }

    public ResultResponse get(long id) {
        ResultResponse hs = new ResultResponse();
        try {
            ProblemInfo problemInfo = problemService.getById(id);
            //明细表1
            List<ProblemPictureDetail> problemPictureDetails = problemPictureDetailService.getDetailList(id);
            problemInfo.setProblemPictureDetailList(problemPictureDetails);
            //明细表2
//            List<ProblemProofreadTypeRelate> problemProofreadTypeRelates = proofreadTypeRelateService.list(new LambdaQueryWrapper<ProblemProofreadTypeRelate>().eq(ProblemProofreadTypeRelate::getProblemId, id));
//            if(CollUtil.isNotEmpty(problemProofreadTypeRelates)){
//                String checkType = problemProofreadTypeRelates.stream().map(ProblemProofreadTypeRelate::getProofreadTypeId).collect(Collectors.joining(","));
//                problemInfo.setCheckType(checkType);
//            }
            if(problemInfo == null){
                hs.setErrorMessage("id不存在");
            }
            hs.setData(problemInfo);
        } catch (Exception e) {
            log.error("查找问题记录详情失败", e);
            hs.setErrorMessage("查找问题记录详情失败");
        }
        return hs;
    }

    public ResultResponse publish(long id) {
        ResultResponse rs = new ResultResponse().setMessage("发布成功");
        ProblemInfo problemInfo = new ProblemInfo();
        problemInfo.setId(id);
        problemInfo.setStatus(PostStatusEnum.ALREADY_RELEASED);
        problemService.updateById(problemInfo);
        return rs;
    }

    public void updateBook(ProblemInfo problemInfo){
        if(StringUtils.isNotBlank(problemInfo.getCheckResult())){
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookId(Integer.parseInt(problemInfo.getBookInfo()));
            bookInfo.setCheckResult(problemInfo.getCheckResult());
            bookInfoService.updateById(bookInfo);
        }
    }

    public void saveProblemPictureDetail(List<ProblemPictureDetail> list ,Long problemId){
        if(list.size()>0){
                List<ProblemPictureDetail> problemPictureDetailList = new ArrayList<>();
                list.stream().forEach(problemPictureDetail -> {
                    String arr [] = problemPictureDetail.getProblemClassification().split(StringPool.COMMA);
                    for(String m : arr){
                        ProblemPictureDetail temp = new ProblemPictureDetail();
                        temp.setProblemId(problemId.toString());
                        temp.setPictureId(problemPictureDetail.getPictureId());
                        temp.setProblemClassification(m);
                        problemPictureDetailList.add(temp);
                    }
                });
                problemPictureDetailService.saveBatch(problemPictureDetailList);
            }
        }

//        public void saveProblemProofreadTypeRelate(ProblemInfo problemInfo){
//            if(StringUtils.isNotBlank(problemInfo.getCheckType())){
//                List<ProblemProofreadTypeRelate> problemProofreadTypeRelates = new ArrayList<>();
//                String arr [] = problemInfo.getCheckType().split(StringPool.COMMA);
//                for(String m : arr){
//                    ProblemProofreadTypeRelate temp = new ProblemProofreadTypeRelate();
//                    temp.setProblemId(problemInfo.getId().toString());
//                    temp.setProofreadTypeId(m);
//                    problemProofreadTypeRelates.add(temp);
//                }
//                //savebatch
//                proofreadTypeRelateService.saveBatch(problemProofreadTypeRelates);
//            }
//        }
}
