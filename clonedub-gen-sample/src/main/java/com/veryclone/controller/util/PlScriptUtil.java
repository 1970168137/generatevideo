package com.veryclone.controller.util;

import com.veryclone.common.model.*;
import com.veryclone.common.tool.SqlUtil;
import com.veryclone.common.view.PlVideoView;
import com.jfinal.kit.StrKit;

import java.util.*;
import java.util.stream.Collectors;

public class PlScriptUtil {

    /**
     * 根据资源id获得资源
     * @param id
     * @return
     */
    private static PlResource getResource(int id) {
        PlResource videoResource = videoResource = PlResource.dao.findById(id);
        return videoResource;
    }

    private static String getResSQL(){
        String sql = "(select path from pl_resource where id= pic_id) as pic_path, \n" +
                "(select path from pl_resource where id= video_id) as video_path, \n" +
                "(select path from pl_resource where id= audio_id) as audio_path ";
        return sql;
    }

    public static List<PlVideoView> getVideoViews(){
        List<PlVideo> list = PlVideo.dao.find("SELECT pv.*, pr.name, pr.path FROM pl_video pv JOIN pl_resource pr ON pv.video_id = pr.id order by pv.id desc");
        List<PlVideoView> videoViews = new ArrayList<>();
        for (PlVideo PlScript : list) {
            int id = PlScript.getInt("id");
            String type = PlScript.getStr("type");
            String state = PlScript.getStr("state");
            int videoId = PlScript.getInt("video_id");
            int odds = PlScript.getInt("odds");
            String description = PlScript.getStr("description");
            Date createdAt = PlScript.getDate("created_at");
            String name = PlScript.getStr("name");
            String sourePath = PlScript.getStr("path");

            PlVideoView videoView = new PlVideoView(id, type, state, videoId, odds, description, createdAt, name,sourePath);
            videoViews.add(videoView);
        }
        return videoViews;
    }

}
