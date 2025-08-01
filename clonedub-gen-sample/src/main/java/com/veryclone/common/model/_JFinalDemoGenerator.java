package com.veryclone.common.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.sql.DataSource;

import com.veryclone.common.ClonedubGenConfig;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.TypeMapping;
import com.jfinal.plugin.druid.DruidPlugin;

public class _JFinalDemoGenerator {
	
	public static DataSource getDataSource() {
		DruidPlugin druidPlugin = ClonedubGenConfig.createDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}
	
	public static void main(String[] args) {
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.demo.common.model";
		
		// base model 所使用的包名
		String baseModelPackageName = modelPackageName + ".base";
		
		// base model 文件保存路径
		String baseModelOutputDir = System.getProperty("user.dir")
				+ "/src/main/java/" + baseModelPackageName.replace('.', '/');
		
		System.out.println("输出路径："+ baseModelOutputDir);
		
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成器
		Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		
		// 配置是否生成备注
		generator.setGenerateRemarks(true);
		
		// 设置数据库方言
		generator.setDialect(new MysqlDialect());
		
		// 设置是否生成链式 setter 方法，强烈建议配置成 false，否则 fastjson 反序列化会跳过有返回值的 setter 方法
		generator.setGenerateChainSetter(false);
		
		// 添加不需要生成的表名到黑名单
		generator.addBlacklist("adv");
		
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(false);
		
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		generator.setRemovedTableNamePrefixes("t_");
		
		// 将 mysql 8 以及其它原因之下生成 jdk 8 日期类型映射为 java.util.Date，便于兼容老项目，也便于习惯使用 java.util.Date 的同学
        TypeMapping tm = new TypeMapping();
        tm.addMapping(LocalDateTime.class, Date.class);
        tm.addMapping(LocalDate.class, Date.class);
        // tm.addMapping(LocalTime.class, LocalTime.class);		// LocalTime 暂时不变
        generator.setTypeMapping(tm);
        
		// 生成
		generator.generate();
	}
}




