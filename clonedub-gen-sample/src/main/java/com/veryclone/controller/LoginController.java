package com.veryclone.controller;

import com.veryclone.common.Constants;
import com.veryclone.common.model.PlUser;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.log.Log;

@Path(value = "/login")
public class LoginController extends Controller {
    private static final Log log = Log.getLog(LoginController.class);

    public void index() {
        render("/aiv/login.html");
    }

    // 登录处理
    public void doLogin() {
        String password = getPara("password");
        PlUser user = PlUser.dao.findFirst("select * from pl_user where password = ?", password);

        if(user != null) {
            getSession(true).setAttribute(Constants.SESSION_USER, user);
            redirect("/main");
        } else {
            setAttr("errorMsg", "无效的访问密钥");
            render("/aiv/login.html");
        }
    }
    
    // 退出登录
    public void logout() {
        removeSessionAttr(Constants.SESSION_USER);
        redirect("/login");
    }
}