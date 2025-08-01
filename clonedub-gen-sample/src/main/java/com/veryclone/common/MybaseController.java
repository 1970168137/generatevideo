package com.veryclone.common;

import com.veryclone.common.model.PlResource;
import com.jfinal.core.Controller;

import java.util.List;

public abstract class MybaseController extends Controller {

	// 将几种资源配置的方式，存放到attr中
	public void setRes2attr() {
		//查找出所有的列表
		List<PlResource> picList = PlResource.dao.find("select * from pl_resource where type = '1' and state='Y' order by id desc");
		setAttr("picList", picList);
		List<PlResource> videoList = PlResource.dao.find("select * from pl_resource where type = '2' and state='Y' order by id desc");
		setAttr("videoList", videoList);
		List<PlResource> audiolist = PlResource.dao.find("select * from pl_resource where type = '3' and state='Y' order by id desc");
		setAttr("audiolist", audiolist);
	}

}
