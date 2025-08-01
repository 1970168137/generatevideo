package com.veryclone.controller;

import com.jfinal.log.Log;
import com.veryclone.common.ClonedubGenConfig;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;

import java.io.File;

@Path(value = "/file")
public class FileController extends Controller {
    private static final Log log = Log.getLog(FileController.class);

    // 文件下载接口
    public void download() {
        try {
            String filename = getPara("filename");
            File file = new File(ClonedubGenConfig.UPLOAD_PATH + filename);

            if (!file.exists()) {
                renderError(404, "File Not Found");
                return;
            }

            String downloadName = "custom_" + filename; // 自定义下载文件名
            setAttr("downloadFileName", downloadName);
            
            renderFile(file);
        } catch (Exception e) {
            log.error("Server Error: " + e.getMessage());
            renderError(500, "Server Error: " + e.getMessage());
        }
    }
}