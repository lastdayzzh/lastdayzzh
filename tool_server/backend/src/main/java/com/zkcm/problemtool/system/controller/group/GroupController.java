package com.zkcm.problemtool.system.controller.group;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.domain.Tree;
import com.zkcm.problemtool.common.utils.ZkcmTreeUtil;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.Group;
import com.zkcm.problemtool.system.domain.GroupDetail;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;
import com.zkcm.problemtool.system.service.group.IGroupDetailService;
import com.zkcm.problemtool.system.service.group.IGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController extends BaseController {

    @Resource
    private IGroupService groupService;
    @Resource
    private IGroupDetailService groupDetailService;


    /**
     * @desc 分页
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @GetMapping("/list")
    public ResultResponse page(String type) {
        ResultResponse hs = new ResultResponse();
        List<Tree> groupList = groupService.listGroup(getApiUserName(),type);
        hs.setData(groupList);
        return hs;
    }

    /**
     * @desc 查看
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @Log("查找")
    @GetMapping("/{id}")
    public ResultResponse get(@PathVariable long id) throws Exception {
        ResultResponse hs = new ResultResponse();
        try {
            Group info = groupService.getById(id);
            hs.setData(info);
        } catch (Exception e) {
            log.error("查找分组失败", e);
            hs.setErrorMessage("查找分组失败");
        }
        return hs;
    }

    /**
     * @desc 保存
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @Log("保存分组")
    @PostMapping("/save")
    public ResultResponse save(@RequestBody Group group) {
        ResultResponse hs = new ResultResponse();
        try {
            if(group.getId()!=null){
                group.setModifyUser(getApiUserName());
                groupService.updateById(group);
            }else{
                group.setCreateUser(getApiUserName());
                groupService.save(group);
            }
            hs.setData(group);
            hs.setMessage("保存分组成功");
        } catch (Exception e) {
            log.error("保存分组失败", e);
            hs.setErrorMessage("保存分组失败");
        }
        return hs;
    }

    /**
     * @desc 删除分组
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @Log("删除分组")
    @DeleteMapping("/delete")
    public ResultResponse delete(@RequestParam("ids") String id , @RequestParam(value = "type") String type) {
        ResultResponse hs = new ResultResponse().setMessage("删除成功");
        try {
            List<Tree> groupList = groupService.listGroup(getApiUserName(),type);
            if(CollUtil.isNotEmpty(groupList)){
                List<String> subListIds = ZkcmTreeUtil.getSubListIds(Long.parseLong(id),groupList);
                //删除分组及其子级
                groupService.removeByIds(subListIds);
                //删除分组及其子级对应的明细表
                groupDetailService.remove(new LambdaQueryWrapper<GroupDetail>().in(GroupDetail::getGroupId,subListIds));
            }
        } catch (Exception e) {
            log.error("删除分组失败", e);
            hs.setErrorMessage("删除分组失败");
        }
        return hs;
    }

    /**
     * @desc 移动至分组
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @PostMapping("/saveToGroup")
    public ResultResponse saveToGroup(@RequestParam(value = "recordIds") String recordIds , @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "groupType") String groupType) {
        ResultResponse hs = new ResultResponse();
        try {
            if(StrUtil.isBlank(recordIds)){
                return hs.setErrorMessage("请传入分组");
            }
            if(groupId!=null){
                return hs.setErrorMessage("请传入分组");
            }
            List<String> idList = Arrays.asList(recordIds.split(StringPool.COMMA));
            List<GroupDetail> groupDetailList = new ArrayList<>();
            idList.stream().forEach(recordId->{
                GroupDetail groupDetail = new GroupDetail();
                groupDetail.setGroupId(groupId);
                groupDetail.setGroupType(groupType);
                groupDetail.setRecordId(Long.parseLong(recordId));
                groupDetailList.add(groupDetail);
            });
            //一个record只在一个分组里
            //先删除原来的record
            groupDetailService.remove(new LambdaQueryWrapper<GroupDetail>().eq(GroupDetail::getGroupType,groupType).in(GroupDetail::getRecordId,idList));
            //插入record
            groupDetailService.saveBatch(groupDetailList);

        } catch (Exception e) {
            log.error("保存分组失败", e);
            hs.setErrorMessage("保存分组失败");
        }
        return hs;
    }

    /**
     * @desc 从分组移出
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @DeleteMapping("/removeFromGroup")
    public ResultResponse removeFromGroup(@RequestParam(value = "recordIds") String recordIds , @RequestParam(value = "groupId") Long groupId, @RequestParam(value = "groupType") String groupType) {
        ResultResponse hs = new ResultResponse();
        try {
            if(StrUtil.isBlank(recordIds)){
                return hs.setErrorMessage("请传入分组数据");
            }
            if(groupId!=null){
                return hs.setErrorMessage("请传入分组");
            }
            List<String> idList = Arrays.asList(recordIds.split(StringPool.COMMA));
//            List<GroupDetail> groupDetailList = new ArrayList<>();
//            idList.stream().forEach(recordId->{
//                GroupDetail groupDetail = new GroupDetail();
//                groupDetail.setGroupId(groupId);
//                groupDetail.setGroupType(groupType);
//                groupDetail.setRecordId(Long.parseLong(recordId));
//                groupDetailList.add(groupDetail);
//            });
            //一个record只在一个分组里
            //先删除原来的record
            groupDetailService.remove(new LambdaQueryWrapper<GroupDetail>().eq(GroupDetail::getGroupType,groupType).in(GroupDetail::getRecordId,idList));
            //插入record
//            groupDetailService.saveBatch(groupDetailList);

        } catch (Exception e) {
            log.error("删除分组失败", e);
            hs.setErrorMessage("删除分组失败");
        }
        return hs;
    }

    /**
     * @desc 查看
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @Log("查找问题记录")
    @GetMapping("/getProblemList")
    public ResultResponse getRecordList(@RequestParam(value = "groupId")long groupId,
                                        QueryRequest request, ProblemInfo problemInfo) throws Exception {
        ResultResponse hs = new ResultResponse();
        try {
            List<Tree> groupList = groupService.groupList(getApiUserName(),"0");
            //获取子集
            List<String> ids = ZkcmTreeUtil.getSubListIds(groupId,groupList);
                    IPage<ProblemInfo> problemInfoList = groupDetailService.getProblemRecordList(ids,request,problemInfo);

                    return ResultResponse.page(problemInfoList);
        } catch (Exception e) {
            log.error("查找分组失败", e);
            hs.setErrorMessage("查找分组失败");
        }
        return hs;
    }

    /**
     * @desc 查看
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @Log("查找图片")
    @GetMapping("/getPictureList")
    public ResultResponse getPictureList(@RequestParam(value = "groupId")long groupId,
                                        QueryRequest request, Picture picture) throws Exception {
        ResultResponse hs = new ResultResponse();
        try {
            List<Tree> groupList = groupService.groupList(getApiUserName(),"1");
            //获取子集
            List<String> ids = ZkcmTreeUtil.getSubListIds(groupId,groupList);
                IPage<Picture> pictureList = groupDetailService.getPictureRecordList(ids,request,picture);
                return ResultResponse.page(pictureList);
//            hs.setData(info);
        } catch (Exception e) {
            log.error("查找分组失败", e);
            hs.setErrorMessage("查找分组失败");
        }
        return hs;
    }
}
