package com.ibm.ecm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.UserException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import filenet.vw.api.VWSession;

/**
 * Superclass of controllers of this system. All children should implement its
 * business logic in doRequest method.
 * 
 */
public abstract class AbstractRestController extends AbstractController {

	protected final Logger logger = Logger.getLogger(getClass());

	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>() {
		protected HttpServletRequest initialValue() {
			return null;
		}
	};

	protected HttpServletRequest getRequest() {
		return request.get();
	}

	@RequestMapping(method = RequestMethod.GET)
	public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		handleRequest(null, req, res);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void handleRequest(@RequestBody String body, HttpServletRequest req, HttpServletResponse res) throws Exception {
		request.set(req);
		try{
			process(req, res);
		} finally {
			request.set(null);
		}
	}

	/**
	 * 子类再次方法中实现处理请求的逻辑。
	 * 
	 * @param req
	 *            http请求对象
	 * @param res
	 *            http应答对象
	 * @throws Exception
	 */
	protected abstract void process(HttpServletRequest req, HttpServletResponse res) throws Exception;

	/**
	 * 获取Filenet session对象。
	 * 
	 * @return
	 * @throws UserException
	 */
	public VWSession getVWSession() {
		return getVWSession(getRequest());
	}



}
