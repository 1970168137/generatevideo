package com.veryclone.controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;

/**
 *
 */
@Path(value = "/")
public class IndexController extends Controller {

	public void index() {
		redirect("/login");
	}

	public void main(){
		render("/aiv/main.html");
	}

	public void selectStyle(){
		String title = getPara("title");
		setAttr("title", title);
		render("/aiv/select_style.html");
	}

}
