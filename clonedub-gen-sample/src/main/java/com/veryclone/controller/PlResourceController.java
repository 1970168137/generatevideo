package com.veryclone.controller;

import cn.hutool.core.util.RandomUtil;
import com.jfinal.log.Log;
import com.veryclone.common.ClonedubGenConfig;
import com.veryclone.common.model.PlResource;
import com.veryclone.common.tool.FileUtil;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Path(value = "/plResource")
public class PlResourceController extends Controller {

	private static final Log log = Log.getLog(PlResourceController.class);

	// 列表页
	public void index() {
		List<PlResource> list = PlResource.dao.find("select * from pl_resource order by id desc");
		setAttr("resourceList", list);
		render("/aiv/resource.html");
	}

	// 添加页（共用编辑模板）
	public void add() {
		PlResource resource = new PlResource();
		resource.set("type", "1")
				.set("state", "Y");
		setAttr("resource", resource);
		render("/aiv/resource_add.html");
	}

	// 保存
	public void save() {
		try {
			File uploadDir = new File(ClonedubGenConfig.UPLOAD_PATH);
			System.out.println("Can write: " + uploadDir.canWrite());

			// 获取上传文件
			UploadFile uploadFile = getFile("file", "");
			if (uploadFile == null) {
				throw new RuntimeException("请选择上传文件");
			}

			// 在保存文件前添加校验
			String[] allowTypes = {"jpg", "png", "mp4", "mp3"};
			String ext = FileUtil.getFileExtension(uploadFile.getFileName()).toLowerCase();

			if (!Arrays.asList(allowTypes).contains(ext)) {
				uploadFile.getFile().delete();
				throw new RuntimeException("不支持的文件类型");
			}

			// 生成存储路径
			String fileName = generateFileName(uploadFile);
			String filePath = ClonedubGenConfig.UPLOAD_PATH  + fileName;

			// 移动文件到持久化目录
			File targetFile = new File(ClonedubGenConfig.UPLOAD_PATH + fileName);
			uploadFile.getFile().renameTo(targetFile);

			// 保存到数据库
			PlResource resource = getModel(PlResource.class, "resource");
			resource.set("path", fileName)
					.set("created_at", new Date())
					.save();

			redirect("/plResource");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传失败: " + e.getMessage(), e);
			renderJson(Ret.fail("msg", "上传失败: " + e.getMessage()));
		}

	}

	// 编辑页
	public void edit() {
		Integer id = getParaToInt();
		PlResource resource = PlResource.dao.findById(id);
		if (resource == null) {
			renderError(404);
			return;
		}
		setAttr("resource", resource);
		render("/aiv/resource_add.html");
	}

	// 更新
	public void update() {
		try {
			UploadFile uploadFile = getFile("file", "");
			//当客户端以 “enctype="multipart/form-data"” 方式提交的时候，需要先getFile，在getMmodel，否则获得不到表单数据
			PlResource resource = getModel(PlResource.class, "resource");
			// 如果有新文件上传
			if (uploadFile != null) {
				// 删除旧文件
				String oldPath = resource.getStr("path");
				if (StrKit.notBlank(oldPath)) {
					new File(PathKit.getWebRootPath() + oldPath).delete();
				}

				// 保存新文件
				String fileName = generateFileName(uploadFile);
				String filePath = ClonedubGenConfig.UPLOAD_PATH + fileName;
				uploadFile.getFile().renameTo(new File(ClonedubGenConfig.UPLOAD_PATH + fileName));
				//resource.set("path", filePath);//保存具体路径 +文件名
				resource.set("path", fileName);//只保存文件名
			}

			resource.update();
			redirect("/plResource");
		} catch (Exception e) {
			log.error("更新失败: " + e.getMessage(), e);
			renderJson(Ret.fail("msg", "更新失败: " + e.getMessage()));
		}
	}

	// 删除
	public void delete() {
		Integer id = getParaToInt();
		if (id != null) {
			PlResource.dao.deleteById(id);
		}
		redirect("/plResource");
	}
	// 生成唯一文件名
	private String generateFileName(UploadFile uploadFile) {
		String ext = FileUtil.getFileExtension(uploadFile.getFileName());
		return System.currentTimeMillis() + "_" + RandomUtil.randomString(6) + "." + ext;
	}


}
