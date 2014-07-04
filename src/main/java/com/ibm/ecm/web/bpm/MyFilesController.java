package com.ibm.ecm.web.bpm;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.filenet.api.core.Folder;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.ibm.ecm.service.MyFilesService;
import com.ibm.ecm.web.AbstractController;
import com.ibm.filenet.helper.ce.ObjectStoreProvider;
import com.ibm.filenet.helper.pe.BPMException;

@Controller
public class MyFilesController extends AbstractController {
	
	@Resource
	private MyFilesService myFilesService;
	
	public MyFilesService getMyFilesService() {
		return myFilesService;
	}

	public void setMyFilesService(MyFilesService myFilesService) {
		this.myFilesService = myFilesService;
	}

	@RequestMapping("/myfiles")
	public String mywork(HttpServletRequest req,HttpServletResponse resp) throws BPMException {
		
		ObjectStoreProvider osp = getOSP(req);
		
		List<Folder> list = myFilesService.getContainees(osp);
		req.setAttribute("containees", list);
		return "myfiles";
	}
}
