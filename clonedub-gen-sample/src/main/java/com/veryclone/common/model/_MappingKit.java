package com.veryclone.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;


public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("pl_script_gen", "id", PlScriptGen.class);
		arp.addMapping("pl_video", "id", PlVideo.class);
		arp.addMapping("pl_resource", "id", PlResource.class);
		arp.addMapping("pl_user", "id", PlUser.class);
	}
}
