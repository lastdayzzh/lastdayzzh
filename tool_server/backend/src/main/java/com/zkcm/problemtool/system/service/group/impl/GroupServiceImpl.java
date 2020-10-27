package com.zkcm.problemtool.system.service.group.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.Tree;
import com.zkcm.problemtool.common.utils.TreeUtil;
import com.zkcm.problemtool.common.utils.ZkcmTreeUtil;
import com.zkcm.problemtool.system.dao.group.GroupMapper;
import com.zkcm.problemtool.system.domain.Group;
import com.zkcm.problemtool.system.service.group.IGroupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2020-09-28
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
    @Override
    public List<Tree> listGroup(String apiUserName,String type) {
        List<Tree> treeList = ZkcmTreeUtil.getTreeList(FebsConstant.ROOT_NODE_ID, groupList(apiUserName,type));
        return  treeList;
    }

    @Override
    public List<Tree> groupList(String apiUserName, String type) {
        List<Group> groups = this.baseMapper.selectList(new LambdaQueryWrapper<Group>().eq(Group::getCreateUser,apiUserName).eq(Group::getType,type));
        List<Tree> trees = new ArrayList<>();
        for (Group group : groups) {
            Tree tree = new Tree();
            tree.setId(group.getId().toString());
            tree.setKey(tree.getId());
            tree.setTitle(group.getName());
            tree.setText(group.getName());
            tree.setValue(group.getId().toString());
            tree.setType(group.getType());
            tree.setParentId(group.getParentId().toString());
            trees.add(tree);
        }
//        List<Tree> treeList = ZkcmTreeUtil.getTreeList(FebsConstant.ROOT_NODE_ID, trees);
        return  trees;
    }

}
