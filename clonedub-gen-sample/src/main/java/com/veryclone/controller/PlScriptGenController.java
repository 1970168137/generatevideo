package com.veryclone.controller;

import com.jfinal.log.Log;
import com.veryclone.ai.DeepSeekAPIClient;
import com.veryclone.ai.HuoshanAPIClient;
import com.veryclone.common.ClonedubGenConfig;
import com.veryclone.common.model.*;
import com.veryclone.common.tool.TextUtil;
import com.veryclone.controller.util.PlScriptUtil;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Ret;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Path(value = "/plScriptgen")
public class PlScriptGenController extends Controller {

    private static final Log log = Log.getLog(PlScriptGenController.class);

    //用来标记需要问答模型补全的文本位置
    private static final String _MARK_ = "{}";
    private static final String _QUESTION_HEAD_= "以下是一段宣传tiktok服务的短视频文案，是有分段文本组成，可能不大通顺，" +
            "严格按照以下要求执行：1、在{}这里可以加入一两句文本，每一个{}都必须添加，让文案变得通顺，2、依然保留{}符号，你所添加的文本需要添加在{}中间，" +
            "3、回答只能返回添加后的完整文本，不需要有其他解说和其他添加，4、如果在某个{}实战不好添加文本，可以加一些语气词，如，嗯，最近，我们，等这样的词语，但绝对不能镂空不加。\n";

    // 列表页
    public void index() {
        List<PlScriptGen> genList = PlScriptGen.dao.find(
            "SELECT * FROM pl_script_gen ORDER BY created_at DESC"
        );
        setAttr("genList", genList);
        render("/aiv/script_gen.html");
    }

    public void toText2video(){
		/*List<PlResource> audiolist = PlResource.dao.find("select * from pl_resource where type = '3' and state='Y' order by id desc");
		setAttr("audiolist", audiolist);*/
        setAttr("videoList", PlScriptUtil.getVideoViews());
        render("/aiv/script_text2video.html");
    }


    /**
     * 返回需要数字人处理的视频 配置
     */
    public void getone(){
        PlScriptGen gen = PlScriptGen.dao.findFirst("SELECT * FROM pl_script_gen where state = 'Y'");
        renderJson(gen);
    }

    /**
     * 按文件名获取视频剪辑配置
     */
    public void getonebyfileid(){
        String genFileName = getPara("genfileid");
        PlScriptGen gen = PlScriptGen.dao.findFirst("SELECT * FROM pl_script_gen where gen_video_name = ?", genFileName);
        renderJson(gen);
    }

    /**
     * 接受来自视频生成端的请求，进行完成信息更新
     */
    public void finish(){
        int id = getParaToInt("id");
        String filename = getPara("filename");
        PlScriptGen gen = PlScriptGen.dao.findById(id);
        gen.set("gen_video_name", filename);
        gen.set("state", "N");
        gen.update();
        renderNull();
    }

    /**
     * 提交生成视频申请
     */
    public void genvideo(){
        String scriptText = getPara("scriptText");
        String videoId = getPara("videoId");

        PlScriptGen scriptGen = new PlScriptGen();
        scriptGen.set("all", scriptText);
        scriptGen.set("video", videoId);
        scriptGen.set("type", "2");
        scriptGen.set("created_at", new Date()).save();

        renderJson(Ret.ok("code", 200).set("msg", "OK"));
    }
}