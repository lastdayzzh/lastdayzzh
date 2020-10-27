package com.zkcm.problemtool.common.utils;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MyFileUtil {

    /**
     * 文件下载
     * @param name 文件上传名
     * @param fileInputStream 文件数据流
     * @param response
     */
    @SneakyThrows
    public static void downloadFile(String name, InputStream fileInputStream, HttpServletResponse response, boolean isZip){
        //传输类型(true：压缩文档，false：文件)
        String type = isZip ? "application/zip" : "application/octet-stream";
        InputStream in = fileInputStream;
        OutputStream out = null;
        try {
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.addHeader(
                    "Content-Disposition",
                    " attachment;filename="+name);
            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            while ((len = in.read(buffer)) > 0){
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null){
                try {
                    in.close();} catch (Exception e) {
                    throw new RuntimeException(e); }}
            if (out != null) {
                try {
                    out.close();} catch (IOException e) {
                    e.printStackTrace();}}
        }
    }

    /**
     * 批量文件压缩成zip包
     * @param fileMap 文件集  Map<文件名，文件流>
     * @param zipName 文件夹名
     * @param isSaveStructure 是否保留文件结构（true保留）
     * @return
     * @throws IOException
     */
    public static File zipFiles(Map<String,InputStream> fileMap, String zipName, boolean isSaveStructure) throws IOException {
        File zipFile = new File(zipName+".zip");
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        ZipOutputStream zos = null;
        BufferedInputStream bis = null;
        try {
            // 存放的目标文件
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile.getPath())));
            Set<String> keySet = fileMap.keySet();
            ZipEntry zipEntry = null;
            for (String key : keySet) { // key是文件名，value是path
                // 指定源文件
//                File sourceFile = new File(fileMap.get(key));
                // 创建ZIP实体，并添加进压缩包,指定压缩文件中的文件名
                // 是否保留文件结构（true保留）
                String zipEntryName = isSaveStructure ? zipName+File.separator+key : key;
                zipEntry = new ZipEntry(zipEntryName);
                zos.putNextEntry(zipEntry);
                // 读取待压缩的文件并写进压缩包里
                bis = new BufferedInputStream(new BufferedInputStream(fileMap.get(key), 1024 * 10));
                byte[] bufs = new byte[1024 * 10];
                int read = 0;
                while ((read = (bis.read(bufs, 0, 1024 * 10))) != -1) {
                    zos.write(bufs, 0, read);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bis != null) {
                bis.close();
            }
            if (null != zos) {
                zos.close();
            }
        }
        return zipFile;
    }
    public static void downLoad(String filePath, HttpServletResponse response) throws Exception {
             File f = new File(filePath);
             if (!f.exists()) {
                   response.sendError(404, "File not found!");
                   return;
                 }
             BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
             byte[] buf = new byte[1024];
             int len = 0;

             response.reset(); // 非常重要
             response.setContentType("application/x-msdownload");
             response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
             OutputStream out = response.getOutputStream();
             while ((len = br.read(buf)) > 0)
                   out.write(buf, 0, len);
                response.flushBuffer();
             br.close();
             out.close();
           }
}
