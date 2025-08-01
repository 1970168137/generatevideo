package com.veryclone.controller;

import com.jfinal.log.Log;
import com.veryclone.common.model.PlResource;
import com.veryclone.common.model.PlVideo;
import com.veryclone.controller.util.PlScriptUtil;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Path(value = "/plVideo")
public class PlVideoController extends Controller {

	private static final Log log = Log.getLog(PlVideoController.class);

	// 列表页
	public void index() {
		setAttr("videoList", PlScriptUtil.getVideoViews());
		render("/aiv/video.html");
	}

	// 添加页（共用编辑模板）
	public void add() {
		//查找出所有的列表
		List<PlResource> list = PlResource.dao.find("select * from pl_resource where type = '2' and state='Y' order by id desc");
		setAttr("resourceList", list);

		PlVideo video = new PlVideo();
		video.set("type", "1")
				.set("state", "Y")
				.set("odds", 5);
		setAttr("video", video);
		render("/aiv/video_add.html");
	}

	// 保存
	public void save() {
		try {
			PlVideo video = getModel(PlVideo.class, "video");

			// 数据校验
			if (StrKit.isBlank(video.getStr("video_id"))) {
				throw new RuntimeException("视频不能为空");
			}

			video.set("created_at", new Date()).save();
			redirect("/plVideo");
		} catch (Exception e) {
			log.error("保存失败: " + e.getMessage(), e);
			renderJson(Ret.fail("msg", "保存失败: " + e.getMessage()));
		}
	}

	// 编辑页
	public void edit() {
		//查找出所有的列表
		List<PlResource> list = PlResource.dao.find("select * from pl_resource where type = '2' and state='Y' order by id desc");
		setAttr("resourceList", list);
		//
		Integer id = getParaToInt();
		PlVideo video = PlVideo.dao.findById(id);
		if (video == null) {
			renderError(404);
			return;
		}
		setAttr("video", video);
		render("/aiv/video_add.html");
	}

	// 更新
	public void update() {
		try {
			PlVideo video = getModel(PlVideo.class, "video");
			if (video.get("id") == null) {
				throw new RuntimeException("ID不能为空");
			}
			video.update();
			redirect("/plVideo");
		} catch (Exception e) {
			log.error("更新失败: " + e.getMessage(), e);
			renderJson(Ret.fail("msg", "更新失败: " + e.getMessage()));
		}
	}

	// 删除
	public void delete() {
		Integer id = getParaToInt();
		if (id != null) {
			PlVideo.dao.deleteById(id);
		}
		redirect("/plVideo");
	}
}
