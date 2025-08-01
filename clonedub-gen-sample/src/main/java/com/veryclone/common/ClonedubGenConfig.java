package com.veryclone.common;

import com.veryclone.common.model._MappingKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

import java.io.File;

/***
 * API 引导式配置
 */
public class ClonedubGenConfig extends JFinalConfig {
	
	static Prop p;

	//public static final String UPLOAD_BASE = "E:\\test\\upload\\";
	//public static final String UPLOAD_PATH = "E:\\test\\upload\\"; //window系统用这个
	public static final String UPLOAD_PATH = "/opt/doumaotong/upload"; //Linux系统用这个
	// 初始化时创建目录
	static {
		File uploadDir = new File(UPLOAD_PATH);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
	}

	/**
	 * 启动入口，运行此 main 方法可以启动项目，此 main 方法可以放置在任意的 Class 类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		UndertowServer.start(ClonedubGenConfig.class);
	}
	
	/**
	 * PropKit.useFirstFound(...) 使用参数中从左到右最先被找到的配置文件
	 * 从左到右依次去找配置，找到则立即加载并立即返回，后续配置将被忽略
	 */
	static void loadConfig() {
		if (p == null) {
			p = PropKit.useFirstFound("demo-config-pro.txt", "demo-config-dev.txt");
		}
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadConfig();
		
		me.setDevMode(p.getBoolean("devMode", false));
		
		/**
		 * 支持 Controller、Interceptor、Validator 之中使用 @Inject 注入业务层，并且自动实现 AOP
		 * 注入动作支持任意深度并自动处理循环注入
		 */
		me.setInjectDependency(true);
		
		// 配置对超类中的属性进行注入
		me.setInjectSuperClass(true);

		// 设置上传根目录
		me.setBaseUploadPath(UPLOAD_PATH);

		// 设置上传文件大小限制为100MB (100 * 1024 * 1024)
		me.setMaxPostSize(104857600);

	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// 使用 jfinal 4.9.03 新增的路由扫描功能
		me.scan("com.veryclone.");
	}
	
	public void configEngine(Engine me) {
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
	}
	
	public static DruidPlugin createDruidPlugin() {
		loadConfig();
		
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new ClonedubGenCInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		try {
			//ScanUnOkPicsMonitor.runTash();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得图片的目录路径
	 * @return
	 */
	public static String getPicsPath(){
		return ClonedubGenConfig.p.get("picspath");
	}

	/**
	 * 得到值
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		return ClonedubGenConfig.p.get(key);
	}
}
