package com.zkcm.problemtool.system.controller.favorites;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zkcm.problemtool.common.annotation.Log;
import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.domain.Tree;
import com.zkcm.problemtool.common.utils.ZkcmTreeUtil;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.controller.picture.facade.PictureFacade;
import com.zkcm.problemtool.system.domain.Favorites;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.ProblemInfo;
import com.zkcm.problemtool.system.service.favorites.IFavoritesService;
import com.zkcm.problemtool.system.service.picture.IPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/favorites")
public class FavoritesController extends BaseController {

    @Resource
    private IFavoritesService favoritesService;


    /**
     * @desc 保存
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @PostMapping("/save")
    public ResultResponse save(@RequestBody List<Favorites> favoritesList) {
        ResultResponse hs = new ResultResponse();
        try {
            favoritesList.stream().forEach(favorites->{
                favorites.setCreateUser(getApiUserName());
//                favoritesService.save(favorites);
            });
            favoritesService.saveBatch(favoritesList);
//            hs.setData(favorites);
            hs.setMessage("收藏成功");
        } catch (Exception e) {
            log.error("收藏失败", e);
            hs.setErrorMessage("收藏失败");
        }
        return hs;
    }

    /**
     * @desc 删除
     * @params
     * @author zzh
     * @date 2020/09/28
     */
    @DeleteMapping("/delete")
    public ResultResponse delete(@RequestParam("recordId") String recordId , @RequestParam(value = "type") String type) {
        ResultResponse hs = new ResultResponse().setMessage("取消收藏成功");
        try {
            List<String> idList = Arrays.asList(recordId.split(StringPool.COMMA));
            favoritesService.remove(new LambdaQueryWrapper<Favorites>().eq(Favorites::getType,type).eq(Favorites::getCreateUser,getApiUserName()).in(Favorites::getRecordId,idList));
        } catch (Exception e) {
            log.error("取消收藏失败", e);
            hs.setErrorMessage("取消收藏失败");
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
    public ResultResponse getRecordList(QueryRequest request, ProblemInfo problemInfo) throws Exception {
        ResultResponse hs = new ResultResponse();
        try {
            List<Favorites> favoritesList = favoritesService.list(new LambdaQueryWrapper<Favorites>().eq(Favorites::getCreateUser,getApiUserName()));
            List<Long> ids = favoritesList.stream().map(Favorites::getRecordId).collect(Collectors.toList());
            IPage<ProblemInfo> problemInfoList = favoritesService.getProblemRecordList(ids,request,problemInfo);
            return ResultResponse.page(problemInfoList);
        } catch (Exception e) {
            log.error("查找收藏失败", e);
            hs.setErrorMessage("查找收藏失败");
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
    public ResultResponse getPictureList(QueryRequest request, Picture picture) throws Exception {
        ResultResponse hs = new ResultResponse();
        try {
            List<Favorites> favoritesList = favoritesService.list(new LambdaQueryWrapper<Favorites>().eq(Favorites::getCreateUser,getApiUserName()));
            List<Long> ids = favoritesList.stream().map(Favorites::getRecordId).collect(Collectors.toList());
            IPage<Picture> pictureList = favoritesService.getPictureRecordList(ids,request,picture);
            return ResultResponse.page(pictureList);
        } catch (Exception e) {
            log.error("查找收藏失败", e);
            hs.setErrorMessage("查找收藏失败");
        }
        return hs;
    }
}
