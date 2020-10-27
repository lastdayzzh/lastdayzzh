package com.zkcm.problemtool.system.controller.picture.facade;


import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.MinioConstant;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.common.utils.MinIOUtil;
import com.zkcm.problemtool.common.utils.MyFileUtil;
import com.zkcm.problemtool.common.utils.SortUtil;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.domain.Picture;
import com.zkcm.problemtool.system.domain.User;
import com.zkcm.problemtool.system.enumerate.ContentTypeEnum;
import com.zkcm.problemtool.system.service.picture.IPictureService;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author ：zzh
 * @description：图片
 * @date ：2020/09/14
 */
@Slf4j
@Component
public class PictureFacade {

    @Resource
    private IPictureService pictureService;
    @Autowired
    private MinioClient minioClient;

    public ResultResponse save(MultipartFile[] files, User user) throws Exception {
        ResultResponse rs = new ResultResponse().setMessage("添加成功");
        if (files.length == 0) {
            return rs.setErrorMessage("请上传图片");
        }
        List<Picture> pictures = new ArrayList<>();
        for (MultipartFile file : files) {
            Picture picture = new Picture();
            setUrl(picture, file);
            picture.setCreateUser(user.getUsername());
            picture.setOrganization(user.getDeptId());
            pictures.add(picture);
        }
        pictureService.saveBatch(pictures);
        rs.setData(pictures);
        return rs;
    }


    /**
     * 详情查看
     *
     * @param id
     * @return
     */
    public ResultResponse get(long id) {
        ResultResponse hs = new ResultResponse();
        try {
            Picture file = pictureService.getById(id);
            if (file == null) {
                hs.setErrorMessage("id不存在");
            }
            hs.setData(file);
        } catch (Exception e) {
            log.error("查找图片详情失败", e);
            hs.setErrorMessage("查找图片详情失败");
        }
        return hs;
    }

    /**
     * @desc 图片编辑
     * @author zzh
     * @date 2020/09/14
     */
    public ResultResponse update(MultipartFile file, String id, String userName) throws Exception {
        ResultResponse rs = new ResultResponse().setMessage("更新图片成功");
        if (ObjectUtils.isNotNull(file)) {
            return rs.setErrorMessage("请上传图片");
        }
        Picture picture = pictureService.getById(id);
        //删除原图片
        MinIOUtil.removeObject(minioClient, MinioConstant.BUCKET_PROBLEM, picture.getFileName());
        //上传新图片
        setUrl(picture, file);
        picture.setUpdateUser(userName);
        pictureService.updateById(picture);
        return rs;
    }

    /**
     * @desc 删除
     * @author zzh
     * @date 2020/09/09
     */
    public ResultResponse del(String ids) {
        ResultResponse rs = new ResultResponse().setMessage("删除成功");
        if (StringUtils.isBlank(ids)) {
            return rs.setErrorMessage("请选择要删除的数据");
        }
        List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
        pictureService.removeByIds(idList);
        List<Picture> pictures = (List<Picture>) pictureService.listByIds(idList);
        pictures.stream().forEach(t -> {
            try {
                MinIOUtil.removeObject(minioClient, MinioConstant.BUCKET_PROBLEM, t.getFileName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return rs;
    }

    /**
     * @desc 删除（逻辑）
     * @author zzh
     * @date 2020/09/17
     */
    public ResultResponse delFlag(String ids) {
        ResultResponse rs = new ResultResponse().setMessage("删除成功");
        if (StringUtils.isBlank(ids)) {
            return rs.setErrorMessage("请选择要删除的数据");
        }
        List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
        if (idList.size() > 0) {
            Picture picture = new Picture();
            picture.setDelFlag("1");
            pictureService.update(picture, new LambdaQueryWrapper<Picture>().in(Picture::getId, idList));
        }

        return rs;
    }

    private void setUrl(Picture picture, MultipartFile file) throws Exception {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        if (file != null) {
            //获取文件名加后缀
            String fileName = file.getOriginalFilename();
            picture.setOriginal(fileName);
            //文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf(StringPool.DOT), fileName.length());
            //新的文件名
            fileName = uuid + suffix;
            picture.setFileName(fileName);
            picture.setPictureId(fileName.substring(0, 10));
            String upload = MinIOUtil.upload(MinioConstant.BUCKET_PROBLEM, minioClient,
                    file.getInputStream(), fileName, ContentTypeEnum.FILE_CONTENT_TYPE, true);
            picture.setUrl(upload);
        }
    }

    /**
     * @desc 下载
     * @author zzh
     * @date 2020/09/09
     */
    public void downLoad(String ids, HttpServletResponse response) {
        List<String> idList = Arrays.asList(ids.split(StringPool.COMMA));
        List<Picture> pictures = (List<Picture>) pictureService.listByIds(idList);
        if (pictures.size() > 0) {
            Map<String, InputStream> fileInputStreams = new HashMap<>();
            pictures.stream().forEach(m -> {
                try {
                    InputStream fileInputStream = MinIOUtil.getObject(MinioConstant.BUCKET_PROBLEM,
                            m.getFileName(), minioClient);
                    if (fileInputStream != null) {
                        //文件原始名称
                        String original = m.getOriginal();
                        String[] arr = original.split(StringPool.COMMA);
                        int count = 0;
                        //判断是否已有同名文件
                        while (fileInputStreams.containsKey(original)) {
                            count++;
                            original = arr[0] + "(" + count + ")" + StringPool.COMMA + arr[1];
                        }
                        //存储
                        fileInputStreams.put(original, fileInputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                if (ObjectUtils.isNotEmpty(fileInputStreams)) {
                    // 压缩文件
                    File zipFile = MyFileUtil.zipFiles(fileInputStreams, "IMAGE", true);
                    // 将文件写出
                    MyFileUtil.downloadFile(zipFile.getName(), new FileInputStream(zipFile.getPath()), response, false);
                    zipFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public IPage<Picture> pictureList(QueryRequest request, Picture file) {
        Page<Picture> page = new Page();
        SortUtil.handlePageSort(request, page, "a.create_time", FebsConstant.ORDER_DESC, false);
        List<Picture> pictures = pictureService.pictureList(page, file);
        return page.setRecords(pictures);
    }
}
