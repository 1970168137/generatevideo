package com.veryclone.common;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.veryclone.common.model.PlUser;

import javax.servlet.http.HttpServletRequest;

public class ClonedubGenCInterceptor implements Interceptor {

	public void intercept(Invocation ai) {
		Controller controller = ai.getController();

		try {
			ai.invoke();
		} finally {
		}

		PlUser user = null;
		{
			System.out.println("访问的路径：" + controller.getRequest().getServletPath());
			Object object = controller.getSession(true).getAttribute(Constants.SESSION_USER);

			if (object == null && !controller.getRequest().getServletPath().equals("/")
					&& !controller.getRequest().getServletPath().equals("/bangong")
					&& !controller.getRequest().getServletPath().equals("/login")
					&& !controller.getRequest().getServletPath().startsWith("/file/download")
					&& !controller.getRequest().getServletPath().equals("/finish")
					&& !controller.getRequest().getServletPath().equals("/v")
					&& !controller.getRequest().getServletPath().equalsIgnoreCase("/plScriptgen/getone")
					&& !controller.getRequest().getServletPath().equalsIgnoreCase("/plScriptgen/getonebyfileid")  ) {
				controller.redirect("/login");
				return;
			}
		}

		HttpServletRequest request = ai.getController().getRequest();
		{
			String serverContext = request.getHeader("server-context");
			if (StrKit.isBlank(serverContext)) {
				request.setAttribute("CPATH", request.getContextPath());
			} else {
				request.setAttribute("CPATH", serverContext + request.getContextPath());
			}
		}
		request.setAttribute("muneName", controller.getRequest().getServletPath());

	}

}
