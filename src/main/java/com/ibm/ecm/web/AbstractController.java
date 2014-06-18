package com.ibm.ecm.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.UserException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.ecm.util.JsonUtil;
import com.ibm.eu.constants.P8BpmConstants;
import com.ibm.filenet.helper.pe.SessionHelper;

import filenet.vw.api.VWSession;

/**
 * Superclass of controllers of this system. All children should implement its
 * business logic in doRequest method.
 * 
 */
public abstract class AbstractController {

	protected final Logger logger = Logger.getLogger(getClass());

	/**
	 * 获取Filenet session对象。
	 * 
	 * @return
	 * @throws UserException
	 */
	public VWSession getVWSession(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(P8BpmConstants.VWSESSION_NAME);
		if(obj == null){
			obj = SessionHelper.getPEAdminSession();
			request.getSession().setAttribute(P8BpmConstants.VWSESSION_NAME, obj);
		}
		return (VWSession) obj;
	}

	/**
	 * 获取Spring中定义的bean
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getBean(HttpServletRequest request, String beanName) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		return wac.getBean(beanName);
	}

	protected void writeResponse(Object bizResp, HttpServletResponse res) throws IOException {
		
		PrintWriter out = res.getWriter();
		if(bizResp instanceof String){
			out.print(bizResp);
		} else {
			String body = JsonUtil.toJson(bizResp);
			res.setContentType("application/json");
			out.print(body);
		}
	}

}
