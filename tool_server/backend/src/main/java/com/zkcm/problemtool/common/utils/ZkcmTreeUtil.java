package com.zkcm.problemtool.common.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.Tree;
import com.zkcm.problemtool.system.domain.EsbResponseInfo;
import com.zkcm.problemtool.system.domain.OaResponse;
import com.zkcm.problemtool.system.domain.OaReturnJson;
import com.zkcm.problemtool.system.domain.OaUser;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：Lidl
 * @description：树工具类
 * @date ：2019/11/1
 */
public class ZkcmTreeUtil {


    /**
     * * 解析树形数据
     *
     * @param topId
     * @param treeList
     * @return
     */
    public static  List<Tree> getTreeList(String topId, List<Tree> treeList) {
        List<Tree> resultList = new ArrayList<>();
        // 获取顶层元素集合
        String parentId;
        for (Tree t : treeList) {
            parentId = t.getParentId();
            if (FebsConstant.ROOT_NODE_ID.equals(parentId) || StringUtils.isEmpty(parentId)
                    || topId.equals(parentId)) {
                resultList.add(t);
            }
        }
        // 获取每个顶层元素的子数据集合
        for (Tree t : resultList) {
            t.setChildren(getSubList(t.getKey(), treeList));
        }
        return resultList;
    }

    /**
     * * 获取子数据集合
     *
     * @param id
     * @param entityList
     * @return
     */
    public static  List<Tree> getSubList(String id, List<Tree> entityList) {
        List<Tree> childList = new ArrayList<>();
        String parentId;
        // 子集的直接子对象
        for (Tree t : entityList) {
            parentId = t.getParentId();
            if (id.equals(parentId)) {
                childList.add(t);
            }
        }
        // 子集的间接子对象
        for (Tree t : childList) {
            t.setChildren(getSubList(t.getKey(), entityList));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    /**
     * * 获取子数据集合
     *
     * @param id
     * @param treeList
     * @return
     */
    public static void getSubIdList(String id, List<Tree> treeList,List<String> childrenIdList) {
        List<Tree> childList = new ArrayList<>();
        String parentId;
        // 子集的直接子对象
        for (Tree t : treeList) {
            parentId = t.getParentId();
            if (id.equals(parentId)) {
                childList.add(t);
                childrenIdList.add(t.getId());
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return;
        }
        // 子集的间接子对象
        for (Tree t : childList) {
            getSubIdList(t.getId(), treeList,childrenIdList);
        }

    }

    //获取子级
    public static List<String> getSubListIds(long parentId,List<Tree> groupList){
        List<Tree> childrenList =  ZkcmTreeUtil.getSubList(parentId+"",groupList);
        List<String> ids = new ArrayList<>();
        if(CollUtil.isNotEmpty(childrenList)){
            ids = childrenList.stream().map(Tree::getKey).collect(Collectors.toList());
        }
        ids.add(""+parentId);
        return ids;
    }

    public static void main(String[] args) {
        System.out.println("================================================");
        String json = "{\n" +
                "    \"esbInfo\":{\n" +
                "        \"instId\":\"0556BD66EC714118BFAC34EB09FD249A\",\n" +
                "        \"requestTime\":\"\",\n" +
                "        \"attr1\":\"\",\n" +
                "        \"attr2\":\"\",\n" +
                "        \"attr3\":\"\"\n" +
                "    },\n" +
                "    \"requestInfo\":[{\n" +
                "        \"employeeName\":\"韩赞\",\n" +
                "        \"employeeSex\":\"女\",\n" +
                "        \"employeeAge\":\"32\",\n" +
                "        \"birthday\":\"1985-08-02\",\n" +
                "        \"nation\":\"汉族\",\n" +
                "        \"nativePlace\":\"\",\n" +
                "        \"hrLoginAccount\":\"hanzan\",\n" +
                "        \"birthPlace\":\"\",\n" +
                "        \"identityCode\":\"01\",\n" +
                "        \"registrationName\":\"本市城镇\",\n" +
                "        \"healthStatus\":\"健康或良好\",\n" +
                "        \"maritalStatus\":\"已婚\",\n" +
                "        \"education\":\"硕士研究生毕业\",\n" +
                "        \"degree\":\"工学硕士学位\",\n" +
                "        \"workDate\":\"2011-06-29\",\n" +
                "        \"joinDate\":\"2011-06-29\",\n" +
                "        \"orgDate\":\"2011-06-29\",\n" +
                "        \"editDate\":\"\",\n" +
                "        \"relatedWorkDate\":\"\",\n" +
                "        \"orgCode\":\"010101\",\n" +
                "        \"orgName\":\"社本部\",\n" +
                "        \"deptCode\":\"010101037\",\n" +
                "        \"deptName\":\"其他\",\n" +
                "        \"postCode\":\"01010103702\",\n" +
                "        \"postName\":\"分子公司人员\",\n" +
                "        \"employeeId\":\"5B50D2AB95E4EF80E050E29F1BF105E7\",\n" +
                "        \"staffLibrary\":\"在职人员库\",\n" +
                "        \"jobLevel\":\"中级\",\n" +
                "        \"title\":\"编辑中级\",\n" +
                "        \"titleName\":\"编辑中级\",\n" +
                "        \"employeeType\":\"在职人员\",\n" +
                "        \"employeeCategory\":\"管理人员\",\n" +
                "        \"orderNum\":\"614\",\n" +
                "        \"parttimeJob\":\"/010103/01010300G/01010300G01/\",\n" +
                "        \"email\":\"hanzan@mail.sciencep.com\",\n" +
                "        \"mobile\":\"18133801876\",\n" +
                "        \"tel\":\"0311-66509260\",\n" +
                "        \"telExt\":\"\",\n" +
                "        \"employeeStatus\":\"0\",\n" +
                "        \"employeeNum\":\"614\",\n" +
                "        \"reimburseLevel\":\"其他人员\",\n" +
                "        \"reimburseLevelCode\":\"D\",\n" +
                "        \"bankCardName\":\"韩赞\",\n" +
                "        \"bankCardNum\":\"6226660203417049\",\n" +
                "        \"bankAddr\":\"北京市\",\n" +
                "        \"bankName\":\"中国光大银行股份有限公司北京东城支行\",\n" +
                "        \"bankAddrCode\":\"110000\",\n" +
                "        \"sexCode\":\"2\",\n" +
                "        \"nationCode\":\"01\",\n" +
                "        \"nativePlaceCode\":\"\",\n" +
                "        \"birthPlaceCode\":\"\",\n" +
                "        \"registrationCode\":\"11\",\n" +
                "        \"healthStatusCode\":\"10\",\n" +
                "        \"maritalStatusCode\":\"20\",\n" +
                "        \"ifTalentsGroup\":\"1\",\n" +
                "        \"educationCode\":\"14\",\n" +
                "        \"degreeCode\":\"308\",\n" +
                "        \"jobLevelCode\":\"03\",\n" +
                "        \"titleCode\":\"03\",\n" +
                "        \"employeeTypeCode\":\"01\",\n" +
                "        \"employeeCategoryCode\":\"02\",\n" +
                "        \"attr1\":\"\",\n" +
                "        \"attr2\":\"2\",\n" +
                "        \"attr3\":\"\",\n" +
                "        \"attr4\":\"\",\n" +
                "        \"attr5\":\"\",\n" +
                "        \"employeeCode\":\"00000978\",\n" +
                "        \"uuid\":\"0556BD66EC714118BFAC34EB09FD249A\"\n" +
                "    }]\n" +
                "}";
//        JSONUtil.parseArray(json);
//        OaResponse test = JSONUtil.toBean(new JSONObject(json), OaResponse.class);
//        List<OaUser> listUser= test.getRequestInfo();
//        System.out.println(listUser.get(0).getEmployeeName());
//        listUser.stream().forEach(m->{
//            System.out.println(m.getEmployeeSex());
//        });
        OaResponse oaResponse = JSONUtil.toBean(new JSONObject(json), OaResponse.class);
        OaReturnJson oaReturnJson = new OaReturnJson();
        oaReturnJson.setEsbInfo(oaResponse.getEsbInfo());
        EsbResponseInfo esbResponseInfo = new EsbResponseInfo();
        esbResponseInfo.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        esbResponseInfo.setReturnCode("A0001");
        esbResponseInfo.setReturnMsg("成功");
        esbResponseInfo.setReturnStatus("S");
        oaReturnJson.setResponseInfo(esbResponseInfo);
        System.out.println(JSONUtil.toJsonStr(oaReturnJson));
    }

}
