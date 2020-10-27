package com.zkcm.problemtool.system.controller.picture;

import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.controller.picture.facade.PictureFacade;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.service.picture.IPictureService;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/file/image")
public class PictureController extends BaseController {

    @Resource
    private IPictureService pictureService;
    @Autowired
    private PictureFacade pictureFacade;

    /**
     * @desc 分页
     * @author zzh
     * @date 2020/09/09
     */
    @GetMapping("/page")
    public ResultResponse page(QueryRequest request, Picture picture){
        picture = picture ==null?new Picture():picture;
//        file.setFileType(FileTypeEnum.IMAGE.name()).setShowState(StateEnum.YES);
//        file.setSiteId(getSiteId());
        return ResultResponse.page(pictureService.page(request,picture,getCurrentUser()));
    }

    /**
     * @desc save
     * @author zzh
     * @date 2020/09/09
     */
    @PostMapping("/save")
    public ResultResponse save(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        return pictureFacade.save(files,getCurrentUser());
    }


    /**
     * 详情查看
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultResponse get(@PathVariable long id) {
        return pictureFacade.get(id);
    }

    /**
     * @desc 图片编辑
     * @author zzh
     * @date 2020/09/14
     */
    @PutMapping("/update")
    public ResultResponse update(@RequestParam(value = "file") MultipartFile file,
                                  @RequestParam(value = "id") String id ) throws Exception {
        return pictureFacade.update(file,id,getApiUserName());
    }

    /**
     * @desc 删除(逻辑)
     * @author zzh
     * @date 2020/09/09
     */
    @DeleteMapping("/{ids}")
    public ResultResponse del(@PathVariable  String ids){
        return pictureFacade.delFlag(ids);
    }

    /**
     * @desc 下载
     * @author zzh
     * @date 2020/09/09
     */
    @GetMapping("downLoad")
    private void downLoad(  String ids, HttpServletResponse response){
        pictureFacade.downLoad(ids,response);
    }

    /**
     * @desc 从图库选择
     * @author zzh
     * @date 2020/09/17
     */
    @GetMapping("/pictureList")
    public ResultResponse pageSelect(QueryRequest request, Picture picture){
        picture = picture ==null?new Picture():picture;
        return ResultResponse.page(pictureFacade.pictureList(request,picture));
    }
}
