package com.ibm.ecm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/Admin")
	public String index(HttpServletRequest req,HttpServletResponse resp) {
		return "index";
	}

}
