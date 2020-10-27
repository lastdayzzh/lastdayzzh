package com.zkcm.problemtool.system.service.group;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkcm.problemtool.common.domain.Tree;
import com.zkcm.problemtool.system.domain.Group;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2020-09-28
 */
public interface IGroupService extends IService<Group> {

    List<Tree> listGroup(String apiUserName,String type);

    List<Tree> groupList(String apiUserName, String type);
}
