package com.ibm.ecm.web.bpm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.ecm.web.AbstractController;
import com.ibm.filenet.helper.pe.BPMException;
import com.ibm.filenet.helper.pe.BPMWorkflowServices;
import com.ibm.filenet.helper.vo.HistoryVo;
import com.ibm.filenet.helper.vo.WorkItem;

@Controller
public class MyWorkController extends AbstractController {
	
	@RequestMapping("/mywork")
	public String mywork(HttpServletRequest req,HttpServletResponse resp) throws BPMException {
		
		BPMWorkflowServices service = new BPMWorkflowServices(getVWSession(req));
		
		List<WorkItem> workItems = service.queryWorkItems("Inbox", null);
		req.setAttribute("workItems", workItems);
		
		return "mywork";
	}
	@RequestMapping("/myhistory")
	public String myfinished(HttpServletRequest req,HttpServletResponse resp) throws BPMException, UnsupportedEncodingException {
		
		BPMWorkflowServices service = new BPMWorkflowServices(getVWSession(req));
		List<HistoryVo> workItems = service.queryMyHistory(null);
//		for(HistoryVo vo:workItems){
//			vo.setWorkflowName(URLEncoder.encode(vo.getWorkflowName(), "UTF-8"));
//		}
//		
		req.setAttribute("historyItems", workItems);
		return "myhistory";
	}

}
