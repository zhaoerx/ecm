package com.ibm.ecm.web.bpm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.ecm.web.AbstractController;

@Controller
@RequestMapping("/greeting")
public class GreetingController extends AbstractController{


	protected void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		writeResponse("Hello !", res);
	}
}